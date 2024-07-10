package security.loginsecurity.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String email;
    private String password;
    private String phoneNumber;
    private String name;

}
