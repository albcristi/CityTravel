package model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRoute {
    @Id
    private Integer id;
    private Integer user_id;
    private Integer city_id;
}
