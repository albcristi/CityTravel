package model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Session {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private Integer user_id;
    private Date ttl;
}



