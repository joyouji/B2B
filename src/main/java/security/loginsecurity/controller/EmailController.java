package security.loginsecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import security.loginsecurity.dto.EmailRequestDto;
import security.loginsecurity.service.EmailSendService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailSendService emailSendService;


    @PostMapping("/reset_password")
    public ResponseEntity<?> emailSend(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        System.out.println("이메일 인증 이메일: " + emailRequestDto.getEmail());
        String result = emailSendService.joinEmail(emailRequestDto.getEmail());
        return ResponseEntity.ok(Map.of("message", "인증 번호가 발송되었습니다.", "authNumber", result));
    }

}