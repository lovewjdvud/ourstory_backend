package com.backend.ourstory.common.util;

import com.backend.ourstory.common.config.FileUploadConfig;
import com.backend.ourstory.user.dto.Response.UserProfileImageDto;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class FileUtils {
    // 파일 확장자를 제거하는 메서드
    public static String removeFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return fileName; // No extension found
        }
        return fileName.substring(0, lastIndexOfDot);
    }

    // 확장자 추출
    public static String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // 폴더가  존재하지 않으면 폴더 생성
    public static void createDirectoryIfNotExists(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // 사진 삭제
    public static int deleteSpecificFile(String fileName,String userFolderPath) {

        // 1 = 성공, 2 = 파일이 없을 때, 3 = 파일 삭제 실패 시

        File userFolder = new File(userFolderPath);
        System.out.println(fileName + userFolderPath + " 사진 삭제 " + userFolder.exists() + userFolder.isDirectory());
        if (userFolder.exists() && userFolder.isDirectory()) {
            // userFolder 내의 모든 파일과 하위 디렉토리를 가져옵니다.
            File[] files = userFolder.listFiles();

            if (files.length > 0) {

                for (File file : files) {

//                    String fileName = file.getName();
                    int lastIndex = file.getName().lastIndexOf('.');
                    String fileNameWithoutExtension = (lastIndex == -1) ? file.getName() : file.getName().substring(0, lastIndex);
                    System.out.println(file.getName() + "   파일 for문       " + fileName + "          "  + fileNameWithoutExtension      );

                    if (fileNameWithoutExtension.equals(fileName)) {

                        // 파일을 삭제합니다.
                        boolean isDeleted = file.delete();
                        if (isDeleted) {
                            System.out.println(fileName + " 파일이 성공적으로 삭제되었습니다.");
                            break;
                        } else {
                            System.out.println(fileName + " 파일 삭제에 실패하였습니다.");
                            return  3;
                        }
                    } else if (file.isDirectory()) {
                        // 하위 디렉토리를 재귀적으로 탐색하여 파일을 삭제합니다.
                        System.out.println(file.getName() + "   파일 for문 isDirectory " + fileName);
                        deleteFileInDirectory(file, fileName);
                    }
                }

            } else {

                System.out.println(fileName + " 파일이 없습니다");
                return 2;
            }
        }
        return  1;
    }

    private static void deleteFileInDirectory(File directory, String fileName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    boolean isDeleted = file.delete();
                    if (isDeleted) {
                        System.out.println(fileName + " 파일이 성공적으로 삭제되었습니다.");
                    } else {
                        System.out.println(fileName + " 파일 삭제에 실패하였습니다.");
                    }
                } else if (file.isDirectory()) {
                    deleteFileInDirectory(file, fileName);
                }
            }
        }
    }



}
