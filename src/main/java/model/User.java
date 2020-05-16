package model;

import lombok.*;
import org.json.simple.JSONObject;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private Integer id;
    private String user_name;
    private String user_password;

    public JSONObject toJsonFormat(){
        JSONObject object = new JSONObject();
        object.put("user_id", id);
        object.put("user_name",user_name);
        return object;
    }
}
