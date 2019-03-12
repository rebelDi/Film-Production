package FilmProduction.tablesDB;

public class reviewDB {
    private int idFilm;
    private String login;
    private String text;
    private int number;

    public int getIdFilm() {
        return idFilm;
    }

    public String getLogin() {
        return login;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }

    public reviewDB(int idFilm, String login, String text) {
        this.idFilm = idFilm;
        this.login = login;
        this.text = text;
    }

    public reviewDB(String login, String text) {
        this.login = login;
        this.text = text;
    }

    public reviewDB(String login, String text, int number) {
        this.login = login;
        this.text = text;
        this.number = number;
    }

    public reviewDB(String login, String text, int number, int idFilm) {
        this.idFilm = idFilm;
        this.login = login;
        this.text = text;
        this.number = number;
    }
}
