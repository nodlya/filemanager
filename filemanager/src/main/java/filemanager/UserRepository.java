package filemanager;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    //private static final Map<String, User> usersByLogin = new HashMap<>();

//    public static User getUserByCookies(Cookie[] cookies) {
//        String login = Cookies.getLoginCookie(cookies);
//        if (login == null)
//            return null;
//        User user = usersByLogin.get(login);
//
//        return user;
//    }

    public static User getUserByCookies(Cookie[] cookies) {
        User user;
        if ((user = getUser(Cookies.getValue(cookies, "login"))) == null) {
            return null;
        }

        return user;
    }

    public static User getUser(String login) {
        Session session = null;
        try {session = HibernateUtil.getSessionFactory().openSession();} catch (Exception e){ e.printStackTrace(); }
        User user = session.byNaturalId(User.class).using("login", login).load();
        session.close();
        return user;
    }

    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }

//    public static User getUserByLogin(String login) {
//        return usersByLogin.get(login);
//    }

    /*public static void addUser(User user) {
        usersByLogin.put(user.getLogin(), user);
    }*/

//    public static boolean containsUserByLogin(String login) {
//        return usersByLogin.containsKey(login);
//    }
}
