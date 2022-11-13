package filemanager;

import java.sql.*;
import java.util.logging.Logger;

public class JDBCConnection {

    private static String url = "jdbc:mysql://localhost:3306/users";
    private static String username = "root";
    private static String password = "password";
    private static Connection connection = null;
    private static Logger logger = Logger.getLogger("");

    public static void connect(){
        logger.info("Connecting...");

        try {
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Connection successful!");
        } catch (SQLException e) {
            logger.info("Connection failed!");
            e.printStackTrace();
        }
    }

    private static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = null;
        try{
            result = statement.executeQuery(query);
        } catch (SQLException e){
            logger.info("Query execution failed!");
            e.printStackTrace();
        }
        return result;
    }

    private static void executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        try{
            statement.executeUpdate(query);
        } catch (SQLException e){
            logger.info("Adding a user failed!");
            e.printStackTrace();
        }
    }

    public static boolean containsUser(User user) throws SQLException {
        connect();
        int result = 0;
        try {
            result = executeQuery("SELECT COUNT(*) FROM user \n WHERE user.login = \'"
                    + user.getLogin() + "\'").getInt("1");

            logger.info("Check contains success");
            disconnect();
        } catch (SQLException e){
            logger.info("Contains failed");
            disconnect();
        }

        return result !=0;
    }

    public static void addUser(User user) throws SQLException {
        connect();
        try{
            executeUpdate("INSERT INTO user VALUES (\'" + user.getLogin() + "\', \'"
                        + user.getEmail() + "\', \'" + user.getPassword() + "\')");

            logger.info("Add user success");
            disconnect();
        }catch (SQLException e){
            logger.info("Add user failed");
            disconnect();
        }
    }

    public static User getUserByLogin(String login) throws SQLException {
        connect();
        ResultSet result = null;
        try{
            result = executeQuery("SELECT * FROM user WHERE user.login =\'" + login + "\'");
            logger.info("Select login");
        } catch (SQLException e){
            logger.info("Select login failed");
            e.printStackTrace();
        }
        if (!result.next()) {
            logger.info("no user with such a login");
            return null;
        }
        logger.info(result.toString());
        User resultUser = new User(result.getString(1), result.getString(2), result.getString(3));
        logger.info(resultUser.toString());
        return resultUser;
    }

    public static boolean authUser(User user, String loginPassword){
        if (user == null)
            return false;

        logger.info(user.toString());
        return user.getPassword() == loginPassword;
    }

    private static void disconnect(){

        try {
            logger.info("Disonnecting...");
            connection.close();
            logger.info("Disconnected");
        } catch (SQLException e) {
            logger.info("Can't close connection");
            e.printStackTrace();
        }

        logger.info("Disconnection ended");
    }
}
