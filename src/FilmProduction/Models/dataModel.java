package FilmProduction.Models;
import FilmProduction.tablesDB.*;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class dataModel {

    public ObservableList<dataDB> showAllFilms(ObservableList<dataDB> filmData) {
        ResultSet rs = SQLConnect.main("Select *, CONCAT(d.name,\" \", d.surname) AS dir from films f left join director d on f.idD = d.idD ORDER BY f.name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int year = rs.getInt("year");
                String genre = rs.getString("genre");
                String country = rs.getString("country");
                String time = rs.getString("time");
                String plot = rs.getString("plot");
                int budget = rs.getInt("budget");
                int money = rs.getInt("money");
                float mark = rs.getFloat("mark");
                String rate = rs.getString("rate");
                String director = rs.getString("dir");

                filmData.add(new dataDB(name, genre, year, country, time, plot, budget, money, rate, mark, director));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmData;
    }

    public String randFilms(){
        ArrayList<String> names = new ArrayList<>();
        ResultSet rs = SQLConnect.main("SELECT name FROM films;");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                names.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int min = 1;
        int count = names.size()-1;
        count -= min;
        int rand = (int) (Math.random() * ++count) + min;
        return names.get(rand);
    }

    public ObservableList<dataDB> showAllActors(ObservableList<dataDB> actorData) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(a.name, \" \", a.surname) as name, a.dateBirth, IF (a.oscar = 1, 'Есть', 'Нет') AS \"oscar\", " +
                "(SELECT count(idA) from filmactor WHERE idA = a.idA) AS \"filmCount\" FROM actor a ORDER BY a.name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                Date date = rs.getDate("dateBirth");
                String oscar = rs.getString("oscar");
                int filmCount = rs.getInt("filmCount");
                actorData.add(new dataDB(name, date, oscar, filmCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actorData;
    }

    public ObservableList<dataDB> getAllFilmsByActor(ObservableList<dataDB> film, String actor) {
        ResultSet rs = SQLConnect.main("SELECT name FROM films WHERE idF IN (SELECT idF FROM filmactor WHERE idA = (SELECT idA FROM actor WHERE " +
                "CONCAT(name, ' ', surname) = '" + actor + "')) ORDER BY name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                film.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film;
    }

    public ObservableList<dataDB> getNonFilmsByActor(ObservableList<dataDB> film, String actor) {
        ResultSet rs = SQLConnect.main("SELECT name FROM films WHERE idF NOT IN (SELECT idF FROM filmactor WHERE idA = (SELECT idA FROM actor WHERE " +
                "CONCAT(name, ' ', surname) = '" + actor + "')) ORDER BY name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                film.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return film;
    }


    public ObservableList<dataDB> getAllActorsByFilm(ObservableList<dataDB> actors, String filmName) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(name, ' ', surname) AS name FROM actor WHERE idA IN (SELECT idA FROM filmactor WHERE idF = " +
                "(SELECT idF FROM films WHERE name = '" + filmName + "')) ORDER BY name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                actors.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public ObservableList<dataDB> getNonActorsByFilm(ObservableList<dataDB> actors, String filmName) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(name, ' ', surname) AS name FROM actor WHERE idA NOT IN (SELECT idA FROM filmactor WHERE idF = " +
                "(SELECT idF FROM films WHERE name = '" + filmName + "')) ORDER BY name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                actors.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }


    public ObservableList<dataDB> showAllDirectors(ObservableList<dataDB> directorData) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(d.name, \" \", d.surname) as name, d.dateBirth, IF (d.prestige = 1, 'Высокий', 'Средний') AS \"prestige\", \n" +
                "(SELECT count(idD) from films WHERE idD = d.idD) AS \"filmCount\" FROM director d ORDER BY d.name ASC");
        try {
            while (rs.next()) {
                String nameD = rs.getString("name");
                Date dateD = rs.getDate("dateBirth");
                String prestige = rs.getString("prestige");
                int filmCount = rs.getInt("filmCount");
                directorData.add(new dataDB(nameD, prestige, dateD, filmCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directorData;
    }

    public ObservableList<dataDB> getFilmsByLetter(ObservableList<dataDB> filmData, String query) {
        ResultSet rs = SQLConnect.main("SELECT name FROM films WHERE name LIKE '%" + query + "%' ORDER BY name ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                filmData.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmData;
    }

    public ObservableList<dataDB> getActorByLetter(ObservableList<dataDB> actorData, String query) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(name, \" \", surname) AS naming FROM actor WHERE CONCAT(name, \" \", surname) LIKE '%" + query + "%' ORDER BY naming ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("naming");
                actorData.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actorData;
    }

    public ObservableList<dataDB> getDirectorByLetter(ObservableList<dataDB> directorData, String query) {
        ResultSet rs = SQLConnect.main("SELECT CONCAT(name, \" \", surname) AS names FROM director WHERE CONCAT(name, \" \", surname) LIKE '%" + query + "%' ORDER BY names ASC");
        try {
            while (rs.next()) {
                String name = rs.getString("names");
                directorData.add(new dataDB(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return directorData;
    }

    public boolean deleteFilm(String name){
        ResultSet rs = SQLConnect.main("SELECT idF FROM films WHERE name = '" + name + "';");
        try {
            if(!rs.first()){}
            int id = rs.getInt("idF");
            if(SQLConnect.formatQuery("DELETE FROM films WHERE name = '" + name + "';")){
                SQLConnect.formatQuery("DELETE FROM filmactor WHERE idF = " + id + ";");
                SQLConnect.formatQuery("DELETE FROM review WHERE idF = " + id + ";");
                SQLConnect.formatQuery("DELETE FROM marks WHERE idF = " + id + ";");
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean deleteActor(String name){
        ResultSet rs = SQLConnect.main("SELECT idA FROM actor WHERE CONCAT(name, \" \", surname) = '" + name + "';");
        try {
            if(!rs.first()){}
            int id = rs.getInt("idA");
            if(SQLConnect.formatQuery("DELETE FROM actor WHERE CONCAT(name, \" \", surname) = '" + name + "';")) {
                SQLConnect.formatQuery("DELETE FROM filmactor WHERE idA = " + id + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteDirector(String name){
        ResultSet rs = SQLConnect.main("SELECT idD FROM director WHERE CONCAT(name, \" \", surname) = '" + name + "';");
        try {
            if(!rs.first()){}
            int id = rs.getInt("idD");
            if(SQLConnect.formatQuery("DELETE FROM director WHERE CONCAT(name, \" \", surname) = '" + name + "';")){
                return SQLConnect.formatQuery("UPDATE films SET idD = 0 WHERE idD = " + id + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkFilmNameExistence(String name){
        ResultSet rs = SQLConnect.main("SELECT name FROM films WHERE name = '" + name + "';");
        try {
            if(!rs.first()){}
            String naming = rs.getString("name");
            if (naming != null){
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public boolean addFilm(String name, String year, String genre, String country, String timeH, String timeM, String plot, String budget, String money, Object rate, String director){
        ResultSet rs = SQLConnect.main("SELECT idD FROM director WHERE CONCAT(name, \" \", surname) = '" + director + "';");
        try {
            if(!rs.first()){}
            int id = rs.getInt("idD");
            return SQLConnect.formatQuery("INSERT INTO films(name, year, genre, country, idD, time, plot, budget, money, rate, mark) VALUES('"+ name +"', " +
                    "'"+ year +"', '"+ genre +"', '"+ country +"', "+ id +", '"+ timeH +":"+ timeM +"', '"+ plot +"', '"+ budget +"', '"+ money +"', '" + rate + "', 0);");
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addActor(String query){
        return SQLConnect.formatQuery("INSERT INTO actor(name, surname, dateBirth, oscar) VALUES(" + query + ");");
    }

    public boolean addDirector(String query){
        return SQLConnect.formatQuery("INSERT INTO director(name, surname, dateBirth, prestige) VALUES(" + query + ");");
    }

    public boolean editFilm(String name, String year, String genre, String country, String timeH, String timeM, String plot, String budget, String money, Object rate, String director, String oldName){
        ResultSet rs = SQLConnect.main("SELECT idD FROM director WHERE CONCAT(name, \" \", surname) = '" + director + "';");

        try {
            if(!rs.first()){}
            int id = rs.getInt("idD");
            return SQLConnect.formatQuery("UPDATE films SET name = '" + name +"', year = '" + year + "', genre = '" + genre +"', country = '" + country +"', idD = " + id +", " +
                    "time = '" + timeH +":"+ timeM +"', plot = '" + plot +"', budget = '" + budget +"', money = '" + money +"', rate = '" + rate +"' WHERE name = '" + oldName + "';");
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean editdActor(String name, String surname, String date, int oscar, String nameOld){
        return SQLConnect.formatQuery("UPDATE actor SET name = '" + name + "', surname = '" + surname + "', dateBirth = '" + date + "', oscar = " + oscar + " WHERE " +
                "CONCAT(name, \" \", surname) = '" + nameOld + "';");
    }

    public boolean editDirector(String name, String surname, String date, int prestige, String nameOld){
        return SQLConnect.formatQuery("UPDATE director SET name = '" + name + "', surname = '" + surname + "', dateBirth = '" + date + "', prestige = " + prestige + " WHERE " +
                "CONCAT(name, \" \", surname) = '" + nameOld + "';");
    }

    public boolean addActorAndFilm(String actor, String film){
        return SQLConnect.formatQuery("INSERT INTO filmactor(idF, idA) VALUES((SELECT idF FROM films WHERE name = '" + film + "'), (SELECT idA FROM actor WHERE CONCAT(name, \" \", surname) = " +
                "'" + actor + "'));");
    }

    public boolean deleteActorAndFilm(String actor, String film){
        return SQLConnect.formatQuery("DELETE FROM filmactor WHERE idF = (SELECT idF FROM films WHERE name = '" + film + "') AND idA = (SELECT idA FROM actor WHERE CONCAT(name, \" \", surname) = " +
                "'" + actor + "');");
    }
}

