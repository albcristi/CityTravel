package Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRoute {
    private Integer id;
    private Integer user_id;
    private Integer city_id;
}
