package servlets;

import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import service.AuthenticationService;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {
    private RequestHelper requestHelper = new RequestHelper();

    private AuthenticationService authenticationService = new AuthenticationService();

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        JSONObject requestBody = requestHelper.readRequestBody(req);
        String user_name = (String) requestBody.get("user_name");
        String password = (String) requestBody.get("password");

        if(authenticationService.sign_up(user_name,password)){
            requestHelper.writeBody(resp,HttpServletResponse.SC_OK,"Success");
        }
        else {
            requestHelper.writeBody(resp, HttpServletResponse.SC_BAD_REQUEST,"Failed");
        }
    }
}
