package filemanager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/registration")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.getUserByCookies(req.getCookies());
        if (user != null) {
            resp.sendRedirect("/");
            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Logger logger =  Logger.getLogger("");

        /*
        if (!UserRepository.containsUserByLogin(login))
        {
            User user = new User(login, email, password);
            UserRepository.addUser(user);

            resp.sendRedirect("/login");

            logger.info("redirect to /");
        }*/

        if (login == null || UserRepository.getUser(login) != null || email == null || password == null) {
            return;
        }

        User user = new User(login, email, password);
        try {
            if (!JDBCConnection.containsUser(user)) {
                JDBCConnection.addUser(user);
                resp.sendRedirect("/login");
                logger.info("redirect to /");
            }
        } catch (SQLException e) {
            logger.info("Problems with database");
            e.printStackTrace();
        }
    }
}
