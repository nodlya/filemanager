package filemanager;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private static final Map<String, User> usersByLogin = new HashMap<>();

    public static User getUserByCookies(Cookie[] cookies) {
        String login = Cookies.getLoginCookie(cookies);
        if (login == null)
            return null;
        User user = usersByLogin.get(login);

        return user;
    }

    public static User getUserByLogin(String login) {
        return usersByLogin.get(login);
    }

    public static void addUser(User user) {
        usersByLogin.put(user.getLogin(), user);
    }

    public static boolean containsUserByLogin(String login) {
        return usersByLogin.containsKey(login);
    }
}
