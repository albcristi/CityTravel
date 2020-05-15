package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "LoginServlet",
        urlPatterns = {"/log-in"})
public class LoginServlet extends HttpServlet {
}
