package com.backend.ourstory.board;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/v1/board")
@ResponseBody
public class BoardController {

    @GetMapping("/") // TODO: headers -> customer_id
    public String getAccount() {
        return  "sss";
        //return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(customerId));
    }

}
