package Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NeighbourCity {
    private Integer id;
    private Integer origin_city;
    private Integer neighbour_city;
}
