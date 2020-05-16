package model;

import lombok.*;
import org.json.simple.JSONObject;


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
    private String city_name;


    public JSONObject toJsonFormat(){
        JSONObject object = new JSONObject();
        object.put("route_id",id);
        object.put("user_id", user_id);
        object.put("city_id",city_id);
        object.put("city_name", city_name);
        return object;
    }
}
