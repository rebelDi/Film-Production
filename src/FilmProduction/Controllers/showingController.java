package FilmProduction.Controllers;

import FilmProduction.Main;
import FilmProduction.Models.dataModel;
import FilmProduction.tablesDB.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static FilmProduction.Controllers.userController.root;

public class showingController implements Initializable {
    static public ObservableList<dataDB> data = FXCollections.observableArrayList();

    private int flag = 1;
    static public String selectedRow;

    @FXML
    private Button showAllFilms, showAllActors, showAllDirectors, search, rand, exitB, cab, extSearch;

    @FXML
    private TextField searchQuery;

    @FXML
    private TableView<dataDB> view;

    @FXML
    private TableColumn<dataDB, String> film;

    @FXML
    private CheckBox nameF, genreF, yearF, countryF, directorF, timeF;

    @FXML
    Text adminInfo;

    @FXML
    Button adding, deleting, editing, usersTime, filmAct;

    public void delete() {
        new searchController(deleting).goToScene("Views/adminDelete.fxml");
    }

    public void add() {
        new searchController(adding).goToScene("Views/adminAdd.fxml");
    }

    public void edit() {
        new searchController(editing).goToScene("Views/adminEdit.fxml");
    }

    public void users() {
        new searchController(usersTime).goToScene("Views/adminUsers.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(userController.role){
            adminInfo.setVisible(true);
            adding.setVisible(true);
            editing.setVisible(true);
            deleting.setVisible(true);
            usersTime.setVisible(true);
            usersTime.setVisible(true);
            filmAct.setVisible(true);
        }else {
            adminInfo.setVisible(false);
            adding.setVisible(false);
            editing.setVisible(false);
            deleting.setVisible(false);
            usersTime.setVisible(false);
            usersTime.setVisible(false);
            filmAct.setVisible(false);
        }
        data.clear();
        selectAllFilms();
        film.setCellValueFactory(new PropertyValueFactory<>("name"));
        film.setResizable(false);
        film.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        view.setItems(data);

        view.setRowFactory( tv -> {
            TableRow<dataDB> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    dataDB rowData = row.getItem();
                    selectedRow = rowData.getName();
                    data.clear();
                    new searchController(selectedRow, showAllFilms).search();
                }
            });
            return row ;
        });
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }

    @FXML
    private void randomize(){
        data.clear();
        selectedRow = new searchController().randoming();
        new searchController(selectedRow, rand).search();
    }

    @FXML
    public void exit(){
        new searchController(exitB).goToScene("Views/authorization.fxml");
    }

    @FXML
    public void cabinet(){
        new searchController(cab).goToScene("Views/cabinet.fxml");
    }

    @FXML
    public void extendedSearch() {
        new searchController(extSearch).goToScene("Views/extendedS.fxml");
    }

    private void selectAllFilms() {
        new dataModel().showAllFilms(data);
    }

    private void selectAllActors() {
        new dataModel().showAllActors(data);
    }

    private void selectAllDirectors() {
        new dataModel().showAllDirectors(data);
    }

    public void searchData(ActionEvent actionEvent){
        if(actionEvent.getSource() == search){
            data.clear();
            new searchController(searchQuery.getText(), search).search();
        }
    }

    public void button(ActionEvent actionEvent) {
        if(actionEvent.getSource() == showAllFilms){
            flag = 1;
            prepareFilms();

            if(nameF.isSelected()){
                TableColumn<dataDB, String> name = new TableColumn("Название");
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                name.setPrefWidth(view.getWidth()/2-100);
                name.setStyle("-fx-font-size: 12pt");
                view.getColumns().add(name);
            }if(genreF.isSelected()){
                TableColumn<dataDB, String> genre = new TableColumn("Жанр");
                genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
                genre.setPrefWidth(view.getWidth()/4-100);
                genre.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(genre);
            }if(yearF.isSelected()){
                TableColumn<dataDB, String> year = new TableColumn("Год");
                year.setCellValueFactory(new PropertyValueFactory<>("year"));
                year.setPrefWidth(view.getWidth()/4-100);
                year.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(year);
            }if(countryF.isSelected()){
                TableColumn<dataDB, String> country = new TableColumn("Страна");
                country.setCellValueFactory(new PropertyValueFactory<>("country"));
                country.setPrefWidth(view.getWidth()/4-100);
                country.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(country);
            }if(directorF.isSelected()){
                TableColumn<dataDB, String> director = new TableColumn("Режиссёр");
                director.setCellValueFactory(new PropertyValueFactory<>("director"));
                director.setPrefWidth(view.getWidth()/4-100);
                director.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(director);
            }if(timeF.isSelected()){
                TableColumn<dataDB, String> time = new TableColumn("Время");
                time.setCellValueFactory(new PropertyValueFactory<>("time"));
                time.setPrefWidth(view.getWidth()/4-100);
                time.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(time);
            }
            view.setItems(data);
        }else if(actionEvent.getSource() == showAllActors){
            flag = 2;
            prepareActors();
            if(nameF.isSelected()){
                TableColumn<dataDB, String> name = new TableColumn("Имя");
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                name.setPrefWidth(view.getWidth()/5);
                name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(name);
            }if(genreF.isSelected()){
                TableColumn<dataDB, String> year = new TableColumn("Дата Рождения");
                year.setCellValueFactory(new PropertyValueFactory<>("dateA"));
                year.setPrefWidth(view.getWidth()/5);
                year.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(year);
            }if(yearF.isSelected()){
                TableColumn<dataDB, String> country = new TableColumn("Оскар");
                country.setCellValueFactory(new PropertyValueFactory<>("oscarA"));
                country.setPrefWidth(view.getWidth()/5);
                country.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(country);
            }if(countryF.isSelected()) {
                TableColumn<dataDB, String> сountF = new TableColumn("Кол-во фильмов");
                сountF.setCellValueFactory(new PropertyValueFactory<>("filmsCountA"));
                сountF.setPrefWidth(view.getWidth() / 5);
                сountF.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                view.getColumns().add(сountF);
            }
            view.setItems(data);
        }else if(actionEvent.getSource() == showAllDirectors){
            flag = 3;
            prepareDirectors();
            if(nameF.isSelected()){
                    TableColumn<dataDB, String> name = new TableColumn("Имя");
                    name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                    name.setPrefWidth(view.getWidth()/5);
                    view.getColumns().add(name);
            }if(genreF.isSelected()){
                    TableColumn<dataDB, String> year = new TableColumn("Дата Рождения");
                    year.setCellValueFactory(new PropertyValueFactory<>("yearD"));
                    year.setPrefWidth(view.getWidth()/5);
                    year.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                    view.getColumns().add(year);
            }if(yearF.isSelected()){
                    TableColumn<dataDB, String> country = new TableColumn("Престиж");
                    country.setCellValueFactory(new PropertyValueFactory<>("prestigeD"));
                    country.setPrefWidth(view.getWidth()/5);
                    country.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                    view.getColumns().add(country);
            }if(countryF.isSelected()){
                    TableColumn<dataDB, String> countF = new TableColumn("Кол-во фильмов");
                    countF.setCellValueFactory(new PropertyValueFactory<>("filmsCountD"));
                    countF.setPrefWidth(view.getWidth()/5);
                    countF.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
                    view.getColumns().add(countF);
            }
            view.setItems(data);
        }
    }

    private void flagForCheckBoxCheck() {
        if (flag == 1) {
            nameF.setText("Название фильма");
            genreF.setText("Жанр");
            yearF.setText("Год выпуска");
            countryF.setText("Страна производства");
            directorF.setText("Режиссер");
            timeF.setText("Продолжительность");

            nameF.setVisible(true);
            genreF.setVisible(true);
            yearF.setVisible(true);
            countryF.setVisible(true);
            directorF.setVisible(true);
            timeF.setVisible(true);
        } else if (flag == 2) {
            nameF.setText("Имя актера");
            genreF.setText("Дата рождения");
            yearF.setText("Наличие Оскара");
            countryF.setText("Количество фильмов");

            nameF.setVisible(true);
            genreF.setVisible(true);
            yearF.setVisible(true);
            countryF.setVisible(true);
            directorF.setVisible(false);
            timeF.setVisible(false);
        } else if (flag == 3) {
            nameF.setText("Имя режиссера");
            genreF.setText("Дата рождения");
            yearF.setText("Престиж");
            countryF.setText("Количество фильмов");

            nameF.setVisible(true);
            genreF.setVisible(true);
            yearF.setVisible(true);
            countryF.setVisible(true);
            directorF.setVisible(false);
            timeF.setVisible(false);
        }
    }

    private void prepareFilms(){
        flagForCheckBoxCheck();
        data.removeAll(data);

        selectAllFilms();
        view.getColumns().removeAll(view.getColumns());
    }

    private void prepareActors(){
        flagForCheckBoxCheck();
        data.removeAll(data);

        selectAllActors();
        view.getColumns().removeAll(view.getColumns());
    }

    private void prepareDirectors(){
        flagForCheckBoxCheck();
        data.removeAll(data);

        selectAllDirectors();
        view.getColumns().removeAll(view.getColumns());
    }
}