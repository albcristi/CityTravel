package servlets;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class LoginServlet extends HttpServlet {


    RequestHelper requestHelper = new RequestHelper();

    AuthenticationService authenticationService = new AuthenticationService();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println("caaa");
        JSONObject requestBody = requestHelper.readRequestBody(request);
        String user_name = (String) requestBody.get("user_name");
        String password = (String) requestBody.get("password");

        if(authenticationService.log_in(user_name,password)){
            // log in is a success
            String sessionString = authenticationService.createUserSession(user_name);
            System.out.println(sessionString);
            Cookie cookie = new Cookie("session", sessionString);
            response.addCookie(cookie);
            cookie.setMaxAge(8000);
            requestHelper.writeBody(response,HttpServletResponse.SC_OK,"OK");
        }
        else{
            requestHelper.writeBody(response,HttpServletResponse.SC_BAD_REQUEST, "Invalid user data");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHelper.writeBody(resp,HttpServletResponse.SC_OK,"SSS");
    }
}
