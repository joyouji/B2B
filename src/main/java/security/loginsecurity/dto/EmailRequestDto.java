package security.loginsecurity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmailRequestDto {
    @Email(message = "유호한 이메일 주소를 입력해주세요.")
    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;
}
