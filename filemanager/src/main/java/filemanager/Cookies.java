package filemanager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class Cookies {
    public static String getLoginCookie(Cookie[] cookies) {
        String value = "";
        if (cookies == null)
            return value;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("login"))
            {
                value = cookie.getValue();
                Logger logger = Logger.getLogger("");
                logger.info(value);
            }
        }
        return value;
    }
    public static void removeCookie(Cookie[] cookies){
        if (cookies == null)
            return;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("login"))
            {
                cookie.setValue("");
            }
        }
    }

    public static void addCookie(HttpServletResponse resp, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 24);
        resp.addCookie(cookie);
    }
}
