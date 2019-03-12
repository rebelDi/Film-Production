package FilmProduction.Models;

import FilmProduction.tablesDB.dataDB;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class searchModel {

    public ObservableList<dataDB> searchInFilms(ObservableList<dataDB> filmData, String query) {
        //ResultSet rs = SQLConnect.main("SELECT *, (SELECT CONCAT(name, \" \", surname) FROM director WHERE idD IN (SELECT idD FROM films WHERE " +
          //      "name LIKE '" + query + "')) AS dir from films f WHERE f.idD IN (SELECT idD FROM films WHERE name LIKE '" + query + "') ORDER BY f.name ASC;");
        ResultSet rs = SQLConnect.main("SELECT *, (SELECT CONCAT(name, \" \", surname) FROM director WHERE idD IN (SELECT idD FROM films WHERE " +
                "name = '" + query + "')) AS dir from films f WHERE name = '" + query + "' ORDER BY f.name ASC;");

        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String director = rs.getString("dir");
                int year = rs.getInt("year");
                String genre = rs.getString("genre");
                String country = rs.getString("country");
                String time = rs.getString("time");
                String plot = rs.getString("plot");
                int budget = rs.getInt("budget");
                int money = rs.getInt("money");
                float mark = rs.getFloat("mark");
                String rate = rs.getString("rate");
                filmData.add(new dataDB(name, genre, year, country, time, plot, budget, money, rate, mark, director));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return filmData;
        }
        return filmData;
    }

    public ObservableList<dataDB> showAllActors(ObservableList<dataDB> actorData, String filmName) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(a.name,\" \", a.surname) AS act FROM actor a WHERE a.idA " +
                "IN (SELECT idA FROM filmactor WHERE idF = (SELECT idF FROM films WHERE name = '" + filmName + "')) ORDER BY a.name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("act");
                //System.out.println(name);
                actorData.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actorData;
    }

    public ObservableList<dataDB> searchInActors(ObservableList<dataDB> dataA, String query) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(a.name, \" \", a.surname) as name, a.dateBirth, IF (a.oscar = 1, 'Есть', 'Нет') AS \"oscar\"" +
                " FROM actor a WHERE CONCAT(a.name,\" \", a.surname) = '" + query + "';");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String oscar = rs.getString("oscar");
                Date date = rs.getDate("dateBirth");
                dataA.add(new dataDB(name, date, oscar, 0));
            }
        } catch (SQLException e) {
            return dataA;
        }
        return dataA;
    }

    public ObservableList<dataDB> searchFilmActor(ObservableList<dataDB> dataF, String query) {
        ResultSet rs = SQLConnect.main("SELECT f.name as film FROM films f WHERE f.idF IN (SELECT idF FROM filmactor WHERE idA = (SELECT idA FROM actor a WHERE \n" +
                "CONCAT(a.name, \" \", a.surname) = '" + query + "')) ORDER BY f.name ASC;");
        try {
            while (rs.next()) {
                String name = rs.getString("film");
                dataF.add(new dataDB(name));
            }
        } catch (SQLException e) {
            return dataF;
        }
        return dataF;
    }

    public ObservableList<dataDB> searchInDirector(ObservableList<dataDB> dataD, String query) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(d.name, \" \", d.surname) AS name, d.dateBirth, " +
                "IF(d.prestige = 1, 'Высокий', 'Средний') AS prestige FROM director d WHERE CONCAT(d.name, \" \", d.surname) = " +
                "'" + query + "' ORDER BY d.name ASC;");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                Date date = rs.getDate("dateBirth");
                String prestige = rs.getString("prestige");
                dataD.add(new dataDB(name, prestige, date, 0));
            }
        } catch (SQLException e) {
            return dataD;
        }
        return dataD;
    }

    public ObservableList<dataDB> searchFilmDirector(ObservableList<dataDB> dataFilm, String query) {
        ResultSet rs = SQLConnect.main("SELECT f.name FROM films f WHERE f.idD = (SELECT idD FROM director WHERE " +
                "CONCAT(name, \" \", surname) = '" + query + "') ORDER BY f.name ASC;");
        try {
            while (rs.next()) {
                String film = rs.getString("name");
                dataFilm.add(new dataDB(film));
            }
        } catch (SQLException e) {
            return dataFilm;
        }
        return dataFilm;
    }

    public ObservableList<dataDB> searchFilmsByQuery(ObservableList<dataDB> filmData, String query) {
        ResultSet rs = SQLConnect.main("Select name as names from films " + query + " ORDER BY name ASC;");
        try {
            while (rs.next()) {
                String name = rs.getString("names");
                filmData.add(new dataDB(name));
            }
        } catch (SQLException e) {
            return filmData;
        }
        return filmData;
    }

    public ObservableList<dataDB> searchActorsByQuery(ObservableList<dataDB> actors, String query) {
        //System.out.println("Select CONCAT(name, \" \", surname) as naming from actor " + query + " ORDER BY CONCAT(name, \" \", surname) ASC;");
        ResultSet resultSet = SQLConnect.main("Select CONCAT(name, \" \", surname) as naming from actor " + query + " ORDER BY CONCAT(name, \" \", surname) ASC;");
        try {
            while (resultSet.next()) {
                String name = resultSet.getString("naming");
                actors.add(new dataDB(name));
            }
        } catch (SQLException e) {
            return actors;
        }
        return actors;
    }

    public ObservableList<dataDB> searchDirectorsByQuery(ObservableList<dataDB> director, String query) {
        ResultSet resultSet = SQLConnect.main("Select CONCAT(name, \" \", surname) AS nam FROM director " + query + " ORDER BY CONCAT(name, \" \", surname) ASC;");
        try {
            while (resultSet.next()) {
                String name = resultSet.getString("nam");
                director.add(new dataDB(name));
            }
        } catch (SQLException e) {
            return director;
        }
        return director;
    }
}
