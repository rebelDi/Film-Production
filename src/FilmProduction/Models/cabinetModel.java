package FilmProduction.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class cabinetModel {
    private String login;

    public cabinetModel(String login) {
        this.login = login;
    }

    public String getPassByLog() {
        ResultSet rs = SQLConnect.main("SELECT password FROM users WHERE login = '" + login + "';");
        try {
            if (!rs.first()) {
            }
            return rs.getString("password");
        } catch (SQLException e) {
            return "";
        }
    }

    public String getEmByLog() {
        ResultSet rs = SQLConnect.main("SELECT email FROM users WHERE login = '" + login + "';");
        try {
            if (!rs.first()) {
            }
            return rs.getString("email");
        } catch (SQLException e) {
            return "";
        }
    }
}