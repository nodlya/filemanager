package filemanager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/")
public class FileManagerServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = UserRepository.getUserByCookies(req.getCookies());
        Logger logger = Logger.getLogger("name");
        if (user == null) {
            resp.sendRedirect("/login");
            logger.info("redirect to login");
        } else {
            String path = req.getParameter("path");
            if (path == null || !path.startsWith("C:\\" + user.getLogin() + "\\")) {
                path = "C:\\Users\\" + user.getLogin() + "\\";
            }

            path = path.replaceAll("%20", " ");

            File currentPath = new File(path);
            if (!currentPath.exists()) {
                currentPath.mkdir();
            }

            if (currentPath.isDirectory()) {
                //showFiles(req, currentPath.listFiles(), path);
                showFiles(req, currentPath);
                req.setAttribute("date", DATE_FORMAT.format(new Date()));
                req.setAttribute("currentPath", path);


                RequestDispatcher requestDispatcher = req.getRequestDispatcher("explorer.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                downloadFile(resp, currentPath);
            }
            logger.info("redirect to filemanager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("exit") != null) {
            Cookies.removeCookie(req.getCookies());
            resp.sendRedirect("/");
        }
    }

    private void downloadFile(HttpServletResponse resp, File file) throws IOException {
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        try (InputStream in = Files.newInputStream(file.toPath()); OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[1048];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }

    private void showFiles(HttpServletRequest req, File currentPath) {
        File[] allFiles = currentPath.listFiles();
        if (allFiles == null) {
            return;
        }
        List<File> directories = new ArrayList<>();
        List<File> files = new ArrayList<>();
        for (File file : allFiles) {
            (file.isDirectory() ? directories : files).add(file);
        }
        req.setAttribute("files", files);
        req.setAttribute("directories", directories);
    }
}

    /*private void showFiles(HttpServletRequest req, File[] files, String currentPath) {

        StringBuilder attrFolders = new StringBuilder();

        StringBuilder attrFiles = new StringBuilder();

        for (File file : files) {
            if (file.isDirectory()) {
                attrFolders.append("<a href=\"?path=").append(currentPath).append("/").append(file.getName())
                        .append("\">")
                        .append(file.getName())
                        .append("</a>");

            } else {

                attrFiles.append("<a href=\"?path=").append(currentPath).append("/").append(file.getName())
                        .append("\">")
                        .append(file.getName())
                        .append("</a>");

            }
        }
        Logger logger = Logger.getLogger("");
        logger.info(attrFolders.toString());
        logger.info(attrFiles.toString());
        req.setAttribute("folders", attrFolders);
        req.setAttribute("files", attrFiles);

    }
}*/