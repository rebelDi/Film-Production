package FilmProduction.tablesDB;

public class usersDB {
    private String login;
    private String role;
    private String email;

    public usersDB(String login, String role, String email) {
        this.login = login;
        this.role = role;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}
