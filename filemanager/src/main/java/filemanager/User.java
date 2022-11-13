package filemanager;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    @NaturalId
    private String login;
    private String password;
    private String email;

    public User(){}

    public User(String login, String email, String password) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" + "login='" + login + '\'' + ", password='" + password + '\'' + ", email='" + email + '\'' + '}';
    }
}
