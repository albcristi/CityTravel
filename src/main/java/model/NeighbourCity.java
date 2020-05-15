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
public class NeighbourCity {
    @Id
    private Integer id;
    private Integer origin_city;
    private Integer neighbour_city;
}
