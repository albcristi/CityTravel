package model;

import lombok.*;
import org.json.simple.JSONObject;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class City {
    private Integer id;

    private String name;


    public JSONObject toJsonFormat(){
        JSONObject object = new JSONObject();
        object.put("city_id", id);
        object.put("city_name",name);
        return object;
    }
}
