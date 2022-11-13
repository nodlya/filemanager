package filemanager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    public static Logger logger = Logger.getLogger("");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.getUserByCookies(req.getCookies());

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

        logger.info("post!");

        User user = UserRepository.getUser(login);
        logger.info(login);

        if (user == null || user.getPassword() != password) {
            resp.sendRedirect("/login");
            return;
        }

//        User user = null;
//        try {
//            user = JDBCConnection.getUserByLogin(login);
//            if (JDBCConnection.authUser(user, password))
//                resp.sendRedirect("/login");
//        } catch (SQLException e) {
//            logger.info("problems with auth");
//            e.printStackTrace();
//        }
//
//        UserRepository.addUser(user);

        Cookies.addCookie(resp, "login", login);
        logger.info("added cookies");
        resp.sendRedirect("/");
    }
}