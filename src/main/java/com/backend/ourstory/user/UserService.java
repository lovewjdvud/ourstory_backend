package com.backend.ourstory.user;

import com.backend.ourstory.common.config.FileUploadConfig;
import com.backend.ourstory.common.config.JwtTokenProvider;
import com.backend.ourstory.common.dto.ApiResult;
import com.backend.ourstory.common.dto.JwtToken;
import com.backend.ourstory.common.dto.ResponseStatus;
import com.backend.ourstory.common.exception.DuplicateException;
import com.backend.ourstory.common.util.CommonUtils;
import com.backend.ourstory.common.util.FileUtils;
import com.backend.ourstory.common.util.SecurityUtil;
import com.backend.ourstory.user.dto.Request.SignInDto;
import com.backend.ourstory.user.dto.Request.UserDto;
import com.backend.ourstory.user.dto.Request.UserProfileUpdateDto;
import com.backend.ourstory.user.dto.Response.UserProfileImageDto;
import com.backend.ourstory.user.dto.Response.UserProfileInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final FileUploadConfig fileUploadConfig;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final PasswordEncoder passwordEncoder;

//    private final Path rootLocation;
//    private final Path profileImageLocation;

//    @Autowired
//    public UserService(FileStorageProperties fileStorageProperties) {
//        this.rootLocation = Paths.get(fileStorageProperties.getUploadDir());
//        this.profileImageLocation = Paths.get(fileStorageProperties.getProfileImageDir());
//    }

    @Transactional(readOnly = false)
    public ApiResult signIn(SignInDto signInDto) {

        Boolean isExist = userRepository.existsByEmail(signInDto.getUsername());

        if (!isExist) {
            return ApiResult.builder()
                    .status(ResponseStatus.FAILURE)
                    .detail_msg("아이디가 존재하지 않습니다")
                    .build();
        }

        String username = signInDto.getUsername();
        String password = signInDto.getPassword();

        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return ApiResult.builder()
                .status(ResponseStatus.SUCCESS)
                .detail_msg("로근인이 완료되었습니다.")
                .data(jwtToken)
                .build();
    }

    @Transactional(readOnly = false)
    // 회원가입
    public ApiResult userJoin(UserDto userDto){ // vym = 2023.05
//        BCryptPasswordEncoder bCryptPasswordEncoder;
//        Path test = Paths.get(FileStorageProperties.getProfileImageDir());
        Boolean isExist = userRepository.existsByEmail(userDto.getEmail());

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ApiResult.builder()
                    .status(ResponseStatus.FAILURE)
                    .detail_msg("이미 가입된 아이디가 존재합니다")
                    .build();
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(userDto.getNickname())) {
            return ApiResult.builder()
                    .status(ResponseStatus.FAILURE)
                    .detail_msg("중복된 닉네임이 존재합니다")
                    .build();
        }

        // 핸드폰 번호 중복 확인
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            return ApiResult.builder()
                    .status(ResponseStatus.FAILURE)
                    .detail_msg("이미 가입된 번호가 있습니다")
                    .build();
        }

        List<String> roles = new ArrayList<>();
        roles.add("USER");

        UserEntity.UserEntityBuilder builder = UserEntity.builder();
        builder.email(userDto.getEmail());
        builder.password(passwordEncoder.encode(userDto.getPassword()));
        builder.name(userDto.getName());
        builder.nickname(userDto.getNickname());
        builder.phoneNumber(userDto.getPhoneNumber());
        builder.profileImageUrl(userDto.getProfileImageUrl());
        builder.roles(roles);
        UserEntity user = builder
                .build();

        try {
            UserEntity savedUser = userRepository.save(user);
            logger.info("회원가입 처리 완료: {}", savedUser.getEmail());

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .detail_msg("회원가입이 완료되었습니다.")
                    .data(savedUser.getEmail())
                    .build();

        } catch (DataAccessException e) {

            logger.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
            // 트랜잭션 롤백을 명시적으로 설정합니다.
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("회원가입 처리 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    @Transactional(readOnly = false)
    // 유저 정보
    public ApiResult userProfileInfo(int user_id){ // vym = 2023.05

        try {
            Optional<UserEntity> userEntity = userRepository.findUserById(user_id);

            UserProfileInfoResponseDto userProfileInfoResponseDto = new UserProfileInfoResponseDto();
            String userFolderPath = fileUploadConfig.getRootDir() + File.separator + fileUploadConfig.getProfileImageDir() + File.separator + String.valueOf(user_id);

            userProfileInfoResponseDto.setId(userEntity.get().getId());
            userProfileInfoResponseDto.setEmail(userEntity.get().getEmail());
            userProfileInfoResponseDto.setName(userEntity.get().getName());
            userProfileInfoResponseDto.setNickname(userEntity.get().getNickname());
            userProfileInfoResponseDto.setProfile_image_url(getUserProfileImages(userFolderPath));
            userProfileInfoResponseDto.setPhone_number(userEntity.get().getPhoneNumber());

            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .data(userProfileInfoResponseDto)
                    .build();

        } catch (DataAccessException e) {
            logger.error("유저 정보 불러오는 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("유저 정보를 불러오늘 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    // 유저 프로필 업데이트
    @Transactional
    public ApiResult userProfileInfoUpdate(int userId,UserProfileUpdateDto userProfileUpdateDto) {
        // 사용자 엔티티를 조회합니다.
        Optional<UserEntity> optionalUser = userRepository.findUserById(userId);

        try {
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();
                logger.info("유저 프로필 업데이트 중 오류 발생 요청자 이메일 : {} , 수정 이메일 : {} , optional : {} " ,user.getEmail(),userProfileUpdateDto.getEmail(),optionalUser.get().getEmail());
                // 새로운 값으로 업데이트합니다.
                if (userProfileUpdateDto.getEmail() != null) {

                    if (!user.getEmail().equals(userProfileUpdateDto.getEmail())) {
                        if (userRepository.existsByEmail(userProfileUpdateDto.getEmail())) {
                            throw new DuplicateException(ResponseStatus.FAILURE, "중복된 이메일이 있습니다");
                        }
                    }

                    user.setEmail(userProfileUpdateDto.getEmail());

                }
                if (userProfileUpdateDto.getName() != null) {
                    user.setName(userProfileUpdateDto.getName());
                }
                if (userProfileUpdateDto.getNickname() != null) {
                    if (!user.getNickname().equals(userProfileUpdateDto.getNickname())) {
                        if (userRepository.existsByNickname(userProfileUpdateDto.getNickname())) {
                            throw new DuplicateException(ResponseStatus.FAILURE, "중복된 닉네임이 있습니다");
                        }
                    }
                    user.setNickname(userProfileUpdateDto.getNickname());
                }
                if (userProfileUpdateDto.getProfile_image_url() != null) {
                    user.setProfileImageUrl(userProfileUpdateDto.getProfile_image_url());
                }
                if (userProfileUpdateDto.getPhone_number() != null) {
                    if (!user.getPhoneNumber().equals(userProfileUpdateDto.getPhone_number())) {
                        if (userRepository.existsByPhoneNumber(userProfileUpdateDto.getPhone_number())) {
                            throw new DuplicateException(ResponseStatus.FAILURE, "중복된 전화번호가 있습니다");
                        }
                    }
                    user.setPhoneNumber(userProfileUpdateDto.getPhone_number());
                }
                userRepository.save(user);

                // 변경된 사용자 엔티티를 저장합니다.
                return ApiResult.builder()
                        .status(ResponseStatus.SUCCESS)
                        .build();

            } else {
                logger.error("유저 프로필 업데이트 하기 위한 값이 null입니다 ");
                return ApiResult.builder()
                        .status(ResponseStatus.SERVER_ERROR)
                        .detail_msg("유저 프로필 업데이트 하기 위한 값이 null 값 입니다")
                        .build();
            }

        } catch (DataAccessException e) {
            logger.error("유저 프로필 업데이트 중 오류 발생: {}", e.getMessage(), e);
            return ApiResult.builder()
                    .status(ResponseStatus.SERVER_ERROR)
                    .detail_msg("유저 프로필 업데이트 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                    .build();
        }
    }

    // 유저 프로필 이미지 업로드
    @Transactional(readOnly = false)
    public ApiResult userProfileImageFileUpload(MultipartFile multipartFile) {

        try {
            UserEntity userEntity = userRepository.findByEmail(SecurityUtil.getCurrentUsername()); //SecurityUtil.getCurrentUsername();
            String user_id = String.valueOf(userEntity.getId());
            String fileName = null;
            String originalFilename = multipartFile.getOriginalFilename();

            String ext = FileUtils.extractExt(originalFilename);                                        // 파일 확장자 추출
            String originalFileNameExtRemove = FileUtils.removeFileExtension(originalFilename);         // 파일 확장자 제거 후 파일 이름만
            String fileTimestamf = String.valueOf(CommonUtils.getCurrentTimestamp());                   // 파일 업로드 시간

            UUID uuid = UUID.randomUUID(); // 랜덤 UUID

            String userFolderPath = fileUploadConfig.getRootDir() + File.separator + fileUploadConfig.getProfileImageDir() + File.separator + String.valueOf(user_id);

            // 유저별 프로필 폴더 존재 여부 확인
           FileUtils.createDirectoryIfNotExists(userFolderPath);

           // 파일 5개 넘을 시 가장 나중에 저장된 사진 제거
           File userFolder = new File(userFolderPath);
           long profileFolderCount =  Arrays.stream(userFolder.list()).count();
           if (profileFolderCount >= 4) {
               logger.error("profileFolderCount 시작 0  {}", profileFolderCount);
               deleteOldestFolder(userFolderPath);
           }
            // 최종 파일 path
            String filePath = fileUploadConfig.getRootDir() + File.separator + fileUploadConfig.getProfileImageDir() + File.separator + String.valueOf(user_id)  + File.separator  + fileTimestamf + "." + ext;

           // 파일 업로드
            File dest = new File(filePath);
            multipartFile.transferTo(dest);

            String relativePath = "profileimage" + File.separator + String.valueOf(user_id)  + File.separator  + fileTimestamf;

            ProfileImage.ProfileImageBuilder builder = ProfileImage.builder();

            builder.userid(userEntity.getId());
            builder.imageurl(relativePath);
            ProfileImage profileImage = builder
                    .build();

            profileImageRepository.save(profileImage);
            logger.error("유저 프로필 이미지 업로드 중 오류 발생 filePath : {}", profileFolderCount);

        } catch (DataAccessException e) {

        logger.error("유저 프로필 이미지 업로드 중 오류 발생: {}", e.getMessage(), e);
        return ApiResult.builder()
                .status(ResponseStatus.SERVER_ERROR)
                .detail_msg("유저 프로필 이미지 업로드 중 오류가 발생하였습니다. error: \n " + e.getMessage())
                .build();
        }

        catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ApiResult.builder()
                .status(ResponseStatus.SERVER_ERROR)
                .detail_msg("유저 프로필이 업로드 되었습니다")
                .build();
    }

    // 사진 삭제
    public ApiResult profileImageDelete(long fileId) {

        UserEntity userEntity = userRepository.findByEmail(SecurityUtil.getCurrentUsername()); //SecurityUtil.getCurrentUsername();
        String user_id = String.valueOf(userEntity.getId());
        String userFolderPath = fileUploadConfig.getRootDir() + File.separator + fileUploadConfig.getProfileImageDir() + File.separator + String.valueOf(user_id);

        // 1 = 성공, 2 = 파일이 없을 때, 3 = 파일 삭제 실패 시
        int result_cd = FileUtils.deleteSpecificFile(String.valueOf(fileId),userFolderPath);

        if (result_cd == 2) {
            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .detail_msg("파일이 존재하지 않습니다 파일명을 확인해주세요.")
                    .build();
        }else if (result_cd == 3) {
            return ApiResult.builder()
                    .status(ResponseStatus.SUCCESS)
                    .detail_msg("파일 삭제에 실패했습니다")
                    .build();
        }

        System.out.println(result_cd + " 결과 값");

        return ApiResult.builder()
                .status(ResponseStatus.SUCCESS)
                .detail_msg("삭제가 완료 되었습니다")
//                .data(jwtToken)
                .build();
    }

    // 5개 이상시 오래된 사진 삭제
    public void deleteOldestFolder(String userFolderPath) {
        File userFolder = new File(userFolderPath);
        logger.info("profileFolderCount 시작 1  {}", userFolder.getPath());
        // userFolder 내의 모든 하위 폴더를 가져옵니다.
        File[] directories = userFolder.listFiles();
        logger.info("profileFolderCount 시작 2  {}", Arrays.stream(directories).count());

        if (directories != null && directories.length > 0) {
            // 가장 오래된 폴더를 찾습니다.
            Optional<File> oldestFolder = Arrays.stream(directories)
                    .min(Comparator.comparingLong(this::getCreationTime));

            // 가장 오래된 폴더를 삭제합니다.
            oldestFolder.ifPresent(this::deleteFolder);
        }

    }

    // 파일 생성 가져오기
    public long getCreationTime(File file) {
        try {
            Path filePath = file.toPath();
            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            return attrs.creationTime().toMillis();
        } catch (IOException e) {
            throw new RuntimeException("파일 생성 시간을 읽는 도중 오류가 발생했습니다.", e);
        }
    }

    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }

    // 사진 정보들 추출
    public List<UserProfileImageDto> getUserProfileImages(String userFolderPath) {
//        String userFolderPath = rootd + File.separator + profileImageDir + File.separator + userId;
        File userFolder = new File(userFolderPath);
        List<UserProfileImageDto> imageList = new ArrayList<>();

        if (userFolder.exists() && userFolder.isDirectory()) {
            File[] files = userFolder.listFiles((dir, name) -> {
                // 여기에서 파일 확장자를 검사하여 이미지 파일만 필터링할 수 있습니다.
                String lowerCaseName = name.toLowerCase();
                return lowerCaseName.endsWith(".jpg") || lowerCaseName.endsWith(".jpeg") || lowerCaseName.endsWith(".png");
            });

            if (files != null) {
                for (File file : files) {
                    UserProfileImageDto dto = new UserProfileImageDto();

//                    dto.setUrlPath(file.getAbsolutePath());  // 파일의 절대 경로 설정.
                    // 파일 이름에서 타임스탬프를 LocalDateTime으로 변환
                    String fileName = file.getName();
                    String timestampStr = fileName.substring(0, fileName.lastIndexOf('.'));
                    long timestamp = Long.parseLong(timestampStr);
                    LocalDateTime createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
                    dto.setCreatedAt(createdAt);
                    dto.setId(timestamp);
                    String relativePath = file.getAbsolutePath()
                            .substring(fileUploadConfig.getRootDir().length() + fileUploadConfig.getProfileImageDir().length() + 1)
                            .replace(File.separatorChar, '/');

                    dto.setUrlPath("profileimage" + relativePath);  // 파일의 상대 경로 설정.
                    imageList.add(dto);
                }
                // 최신 순서로 정렬
                imageList.sort(Comparator.comparing(UserProfileImageDto::getCreatedAt).reversed());
            }
        }

        return imageList;
    }

}
