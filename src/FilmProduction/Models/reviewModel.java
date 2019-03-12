package FilmProduction.Models;

import FilmProduction.tablesDB.reviewDB;
import javafx.collections.ObservableList;
import FilmProduction.Controllers.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class reviewModel {

    public ObservableList<reviewDB> getReviews(ObservableList<reviewDB> dataR, String filmName){
        ResultSet rs = SQLConnect.main("SELECT * FROM review WHERE idF = (SELECT idF FROM films WHERE name = '" + filmName + "');");
        try {
            while (rs.next()) {
                String login = rs.getString("login");
                String text = rs.getString("text");
                dataR.add(new reviewDB(login, text));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataR;
    }

    public ObservableList<reviewDB> getReviewsByLogin(ObservableList<reviewDB> dataR, String login){
        int i = 1;
        ResultSet rs = SQLConnect.main("SELECT r.text, r.idF, f.name FROM review r JOIN films f ON r.idF = f.idF WHERE r.login = '" + login + "';");
        try {
            while (rs.next()) {
                String text = rs.getString("text");
                String name = rs.getString("name");
                int id = rs.getInt("idF");
                dataR.add(new reviewDB(name, text, i, id));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataR;
    }

    public boolean addReview(String text, String filmName, String userLogin) {
        return SQLConnect.formatQuery("INSERT INTO review(idF, login, text) VALUES ((SELECT idF from films WHERE name = '" + filmName + "'), '" + userLogin + "', '" + text + "');");
    }

    public boolean checkMarkExistence(String user, String film){
        ResultSet rs = SQLConnect.main("SELECT count(idF) AS film FROM marks WHERE idF = (SELECT idF FROM films " +
                "WHERE name = '" + film +"') AND idU = (SELECT id FROM users WHERE login = '" + user + "');");
        try {
            if(!rs.first()){}
            int count = rs.getInt("film");
            if(count == 0){
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return false;
    }


    public boolean addMark(int mark, String filmName, String login) {
        float oldMark = 0;
        int id = 0;

        ResultSet rs = SQLConnect.main("SELECT mark FROM films WHERE name = '" + filmName + "';");
        try {
            if(!rs.first()){}
            oldMark = rs.getFloat("mark");
        } catch (SQLException e) {
            return false;
        }
        float newMark = (oldMark + mark) / 2;
        boolean flag = SQLConnect.formatQuery("UPDATE films SET mark = " + newMark + " WHERE name = '" + filmName + "';");

        if (flag) {
            ResultSet result = SQLConnect.main("SELECT f.idF from films f WHERE f.name = '" + filmName + "';");
            try {
                if(!result.first()){}
                id = result.getInt("idF");
            } catch (SQLException e) {
                return false;
            }
           boolean flag1 = SQLConnect.formatQuery("INSERT INTO marks(idF, idU) VALUES (" + id +", (SELECT u.id FROM users u WHERE u.login = '" + login + "'));");
           if(flag1){
               return true;
           }else {
               return false;
           }
        }else{
            return false;
        }
    }

    public boolean deleteReview(String text, String userLogin, int idF) {
        return SQLConnect.formatQuery("DELETE FROM review WHERE idF = " + idF + " AND login = '" + userLogin + "' AND text = '" + text + "';");
    }
}
