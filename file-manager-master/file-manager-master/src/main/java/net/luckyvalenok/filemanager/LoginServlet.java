package net.luckyvalenok.filemanager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static javax.swing.JOptionPane.showMessageDialog;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            return;
        }

        User user = UserRepository.USER_REPOSITORY.getUserByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            resp.sendRedirect("/login");
            if (!user.getPassword().equals(password))
                //showMessageDialog(null, "fuck you");
                 throw new IOException(user.getLogin() + user.getPassword());
            return;
        }

        CookieUtil.addCookie(resp, login, login);
        resp.sendRedirect("/");
    }
}
