package security.loginsecurity.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSendService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //난수 설정(임시비밀번호)
    private int generateRandomNumber() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // 100000 ~ 999999
    }

    public String joinEmail(String email) {
        if(!memberService.existsByEmail(email)){
            return "등록되지 않은 이메일입니다.";
        }
        int authNumber = generateRandomNumber();


        //임시비밀번호 생성
        String tempPassword = String.valueOf(generateRandomNumber());
        String encryptedPassword = bCryptPasswordEncoder.encode(tempPassword);

        // 비밀번호 업데이트
        memberService.updatePassword(email, encryptedPassword);



        String title = "회원 가입 인증 이메일 입니다.";
        String content = "BYTE The BLUES를 방문해주셔서 감사합니다." +
                "<br><br>" +
                "인증 번호는 " + tempPassword + "입니다." +
                "<br>" +
                "인증번호를 제대로 입력해주세요."+
                "임시비밀번호를 통해 로그인하신후 패스워드를 변경해주세요!.";

        return sendEmail(email, title, content) ?
                "인증 번호가 발송되었습니다. 임시비밀번호를 통해 패스워드를 바로 변경해주세요!" :
                "이메일 전송에 실패했습니다. 이메일 주소를 확인해주세요.";
    }

    // 이메일전송

    private boolean sendEmail(String toMail, String title, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
            return true;  // 이메일 전송 성공
        } catch (MessagingException e) {
            System.err.println("이메일 전송 실패: " + e.getMessage());
            return false;  // 이메일 전송 실패
        }
    }
}