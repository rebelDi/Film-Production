package FilmProduction.Models;

import javafx.collections.ObservableList;
import FilmProduction.tablesDB.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class userModel {
    private String login;
    private String password;
    private String email;
    private int role;

    public void setParam(String login, String password, String email, int role) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void setParam(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = 0;
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

    public int getRole() {
        return role;
    }

    public boolean checkAccExistence(String login, String password){
        ResultSet rs = SQLConnect.main("SELECT password FROM users WHERE login = '" + login + "';");
        try {
            if(!rs.first()){}
            String tPassword = rs.getString("password");
            if (tPassword.equals(password)){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public String getPasswordByLogin(String login){
        ResultSet rs = SQLConnect.main("SELECT password AS pass FROM users WHERE login = '" + login + "';");
        try {
            if(!rs.first()){}
            return rs.getString("pass");
        } catch (SQLException e) {
            return "";
        }
    }

    public String getEmailByLogin(String login){
        ResultSet rs = SQLConnect.main("SELECT email AS em FROM users WHERE login = '" + login + "';");
        try {
            if(!rs.first()){}
            return rs.getString("em");
        } catch (SQLException e) {
            return "";
        }
    }

    public boolean checkLoginExistence(String login){
        ResultSet rs = SQLConnect.main("SELECT login FROM users WHERE login = '" + login + "';");
        try {
            if(!rs.first()){}
            String log = rs.getString(1);
            if (log != null){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public boolean checkEmailExistence(String email){
        ResultSet rs = SQLConnect.main("SELECT email FROM users WHERE email = '" + email + "';");
        try {
            if(!rs.first()){}
            String em = rs.getString(1);
            if (em != null){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public boolean checkEmail(String email){
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    public boolean updateLogin(String login, String oldLogin){
        if(SQLConnect.formatQuery("UPDATE users SET login = '" + login + "' WHERE login = '" + oldLogin + "';")){
            return SQLConnect.formatQuery("UPDATE review SET login = '" + login + "' WHERE login = '" + oldLogin + "';");
        }return false;
    }

    public boolean updatePassword(String login, String password){
        return SQLConnect.formatQuery("UPDATE users SET password = '" + password + "' WHERE login = '" + login + "';");
    }

    public boolean updateEmail(String login, String email){
        return SQLConnect.formatQuery("UPDATE users SET email = '" + email + "' WHERE login = '" + login + "';");
    }

    public boolean register(String login, String password, String email){
        if(!SQLConnect.formatQuery("INSERT INTO users(login, password, email) VALUES('" + login + "', '"
            + password + "', '" + email + "');")){
            return false;
        }
        return false;
    }

    public boolean checkPassLength(String password){
        if(password.length() >= 6){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkLogLength(String login){
        if(login.length() > 1){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkAdmin(String login){
        ResultSet rs = SQLConnect.main("SELECT role FROM users WHERE login = '" + login + "';");
        try {
            if(!rs.first()){}
            Integer log = rs.getInt(1);
            if (log == 1){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public ObservableList<usersDB> getUsers(ObservableList<usersDB> dating){
        ResultSet rs = SQLConnect.main("SELECT login, IF(role = 1, 'Да', 'Нет') AS role, email FROM users ORDER BY login ASC;");
        try {
            while (rs.next()) {
                String login = rs.getString("login");
                String role = rs.getString("role");
                String email = rs.getString("email");
                dating.add(new usersDB(login, role, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dating;
    }

    public ObservableList<usersDB> getUserByLogin(ObservableList<usersDB> dating, String log){
        ResultSet rs = SQLConnect.main("SELECT login, IF(role = 1, 'Да', 'Нет') AS rolling, email FROM users WHERE login LIKE '%" + log + "%' ORDER BY login ASC;");
        try {
            while (rs.next()) {
                String login = rs.getString("login");
                String role = rs.getString("rolling");
                String email = rs.getString("email");
                dating.add(new usersDB(login, role, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dating;
    }

    public boolean updateRole(String login, String role){
        if(Objects.equals(role, "Да")) {
            return SQLConnect.formatQuery("UPDATE users SET role = 0 WHERE login = '" + login + "';");
        }else if(Objects.equals(role, "Нет")){
            return SQLConnect.formatQuery("UPDATE users SET role = 1 WHERE login = '" + login + "';");
        }return false;
    }
}
