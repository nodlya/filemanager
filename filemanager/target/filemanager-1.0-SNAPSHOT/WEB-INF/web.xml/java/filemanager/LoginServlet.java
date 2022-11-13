package filemanager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.getUserByCookies(req.getCookies());
        Logger logger = Logger.getLogger("");

        if (user != null) {
            logger.info(user.toString());
            resp.sendRedirect("/");
            logger.info("you're authorized");
            return;
        }
        logger.info("user not null");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Logger logger = Logger.getLogger("");
        logger.info("post!");

        User user = UserRepository.getUserByLogin(login);
        logger.info(login);
        if (user == null || user.getPassword() == password)
            resp.sendRedirect("/login");

        Cookies.addCookie(resp, "login", login);
        logger.info("added cookies");
        resp.sendRedirect("/");
    }
}