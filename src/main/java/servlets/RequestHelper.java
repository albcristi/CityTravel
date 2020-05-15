package servlets;




import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.stream.Collectors;


public class RequestHelper {
    private static final JSONParser jsonParser = new JSONParser();

    @SneakyThrows
    public JSONObject readRequestBody( HttpServletRequest request){
        return (JSONObject) jsonParser.parse(
                request.getReader()
                .lines()
                .collect(Collectors.joining())
        );
    }

    @SneakyThrows
    public void writeBody(HttpServletResponse response, int status, String body)
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        Writer writer = response.getWriter();
        writer.write(body);
        writer.flush();
    }

    public void writeUnauthorizedResponse(HttpServletResponse response)
    {
        writeBody(response, HttpServletResponse.SC_UNAUTHORIZED, "Authenticate: access denied");
    }

    public void writeBadRequestResponse(HttpServletResponse response)
    {
        writeBody(response, HttpServletResponse.SC_BAD_REQUEST, "Bad request");
    }
}
