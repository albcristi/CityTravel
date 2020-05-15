package model;


import lombok.*;


import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Session {
    private Integer id;
    private String token;
    private Integer user_id;
    private Date ttl;
}



