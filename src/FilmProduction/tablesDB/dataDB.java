package FilmProduction.tablesDB;

import java.util.Date;

public class dataDB {
    private int id;
    private String name;
    private int year;
    private String genre;
    private String country;
    private int idD;
    private String time;
    private String plot;
    private int budget, money;
    private String rate;
    private float mark;
    private String director;

    private String surnameA;
    private Date dateA;
    private String oscarA;
    private int filmsCountA;

    private String surnamingD;
    private Date yearD;
    private String prestigeD;
    private int filmsCountD;

    public dataDB(String name) {
        this.name = name;
    }

    public dataDB(String namingD, String prestigeD, Date yearD, int filmsCountD) {
        this.name = namingD;
        this.yearD = yearD;
        this.prestigeD = prestigeD;
        this.filmsCountD = filmsCountD;
    }

    public dataDB(String nameA, Date dateA, String oscarA, int filmsCountA) {
        this.name = nameA;
        this.dateA = dateA;
        this.oscarA = oscarA;
        this.filmsCountA = filmsCountA;
    }

    public dataDB(String name, String genre, int year, String country, String time, String plot, int budget, int money, String rate, float mark, String director) {
        this.name = name;
        this.year = year;
        this.genre = genre;
        this.country = country;
        this.time = time;
        this.plot = plot;
        this.budget = budget;
        this.money = money;
        this.rate = rate;
        this.mark = mark;
        this.director = director;
    }

    public String getSurnamingD() {
        return surnamingD;
    }

    public Date getYearD() {
        return yearD;
    }

    public String getPrestigeD() {
        return prestigeD;
    }

    public int getFilmsCountD() {
        return filmsCountD;
    }

    public String getSurnameA() {
        return surnameA;
    }

    public Date getDateA() {
        return dateA;
    }

    public String getOscarA() {
        return oscarA;
    }

    public int getFilmsCountA() {
        return filmsCountA;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public int getIdD() {
        return idD;
    }

    public String getTime() {
        return time;
    }

    public String getPlot() {
        return plot;
    }

    public int getBudget() {
        return budget;
    }

    public int getMoney() {
        return money;
    }

    public String getRate() {
        return rate;
    }

    public float getMark() {
        return mark;
    }

    public String getDirector() {
        return director;
    }
}
