package com.gdsc.docs.error;

import com.gdsc.common.model.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {
    @GetMapping
    public ResponseEntity<ErrorResponseDto> error() {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of("오류 코드", "오류 이유"));
    }
}
