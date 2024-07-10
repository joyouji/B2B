package security.loginsecurity.exercise;

import jakarta.persistence.*;
import lombok.*;
import security.loginsecurity.member.Member;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Exercise {

    @Id
    private Long id;

    @Setter
    private String exercise;

    @Setter
    private int goal;

}
