package FilmProduction.Controllers;

import FilmProduction.Main;
import FilmProduction.Models.dataModel;
import FilmProduction.tablesDB.dataDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static FilmProduction.Controllers.userController.root;

public class adminAFController implements Initializable {
    static public ObservableList<dataDB> dataFilms = FXCollections.observableArrayList();
    static public ObservableList<dataDB> dataActors = FXCollections.observableArrayList();
    @FXML
    private Button back, adding, deleting, editing, usersTime, cab, exitB;

    @FXML
    private Text condition, filmT, actorT, what;

    @FXML
    private RadioButton delA, addA, delF, addF;

    @FXML
    private Button s1, s2, confirm;

    private ToggleGroup choice = new ToggleGroup();

    @FXML
    private TableView<dataDB> films, actors;

    @FXML
    private TextField f, a;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        condition.setText("");
        filmT.setText("");
        actorT.setText("");
        delA.setToggleGroup(choice);
        addA.setToggleGroup(choice);
        addF.setToggleGroup(choice);
        delF.setToggleGroup(choice);

        dataActors.clear();
        dataFilms.clear();
        selectAllFilms();
        selectAllActors();
        formTableActor();
        formTableFilms();
    }

    private void formTableFilms(){
        TableColumn<dataDB, String> name = new TableColumn("Фильмы");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(459);
        name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        name.setResizable(false);
        films.getColumns().clear();
        films.getColumns().add(name);
        films.setItems(dataFilms);

        films.setRowFactory( tv -> {
            TableRow<dataDB> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    dataDB rowData = row.getItem();
                    if(addA.isSelected()){
                        dataActors.clear();
                        new dataModel().getNonActorsByFilm(dataActors, rowData.getName());
                        actorT.setText("");
                    }else if(delA.isSelected()){
                        dataActors.clear();
                        new dataModel().getAllActorsByFilm(dataActors, rowData.getName());
                        actorT.setText("");
                    }filmT.setText(rowData.getName());

                }
            });
            return row ;
        });
    }

    private void formTableActor(){
        TableColumn<dataDB, String> name = new TableColumn("Актёры");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(459);
        name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        name.setResizable(false);
        actors.getColumns().clear();
        actors.getColumns().add(name);
        actors.setItems(dataActors);

        actors.setRowFactory( tv -> {
            TableRow<dataDB> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 &&(!row.isEmpty()) ) {
                    dataDB rowData = row.getItem();
                    if(delF.isSelected()){
                        dataFilms.clear();
                        new dataModel().getAllFilmsByActor(dataFilms, rowData.getName());
                        filmT.setText("");
                    }else if(addF.isSelected()){
                        dataFilms.clear();
                        new dataModel().getNonFilmsByActor(dataFilms, rowData.getName());
                        filmT.setText("");
                    }

                    actorT.setText(rowData.getName());
                }
            });
            return row ;
        });
    }

    private void reloadPage(){
        new searchController(back).goToScene("Views/adminAddFilmActor.fxml");
    }

    public void button(ActionEvent actionEvent) {
        Stage stage = (Stage) back.getScene().getWindow();
        try {
            root = FXMLLoader.load(Main.class.getResource("Views/showAll.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            warningError(1);
            return;
        }
    }


    public void exit() {
        new searchController(exitB).goToScene("Views/authorization.fxml");
    }

    public void cabinet() {
        new searchController(cab).goToScene("Views/cabinet.fxml");
    }

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

    public void doSearch(ActionEvent actionEvent){
        if(actionEvent.getSource() == s1){
            if(!f.getText().isEmpty()){
                dataFilms.clear();
                new dataModel().getFilmsByLetter(dataFilms, f.getText());
            }
        }else if(actionEvent.getSource() == s2){
            if(!a.getText().isEmpty()){
                dataActors.clear();
                new dataModel().getActorByLetter(dataActors, a.getText());
            }
        }
    }

    public void viewChange(ActionEvent actionEvent) {
        dataActors.clear();
        dataFilms.clear();
        if(actionEvent.getSource() == delA){
            condition.setText("убрать");
            what.setText("у фильма");
            filmT.setText("");
            actorT.setText("");
            selectAllFilms();
        }else if(actionEvent.getSource() == addA){
            condition.setText("добавить");
            what.setText("в фильм");
            filmT.setText("");
            actorT.setText("");
            selectAllFilms();
        }else if(actionEvent.getSource() == addF){
            condition.setText("добавить");
            what.setText("в фильм");
            filmT.setText("");
            actorT.setText("");
            selectAllActors();
        }else if(actionEvent.getSource() == delF){
            condition.setText("убрать у");
            what.setText("фильм");
            filmT.setText("");
            actorT.setText("");
            selectAllActors();
        }
    }

    public void warningError(int num) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (num == 1) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка в системе");
            alert.setContentText("Попробуйте позже");
        }else if (num == 2) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Введите хотя бы одну букву в поле поиска");
        }else if (num == 3) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Выберите что-нибудь для совершения этого действия");
        }else if (num == 4) {
            alert.setTitle("Поздравляем");
            alert.setContentText("Все прошло успешно");
        }
        alert.showAndWait();
    }

    @FXML
    private void confirmation(){
        if(!filmT.getText().isEmpty() && !actorT.getText().isEmpty()){
            if(addA.isSelected() || addF.isSelected()){
                if(new dataModel().addActorAndFilm(actorT.getText(), filmT.getText())){
                    warningError(4);
                }else{
                    warningError(1);
                    return;
                }
            }else if(delF.isSelected() || delA.isSelected()){
                if(new dataModel().deleteActorAndFilm(actorT.getText(), filmT.getText())){
                    warningError(4);
                }else{
                    warningError(1);
                    return;
                }
            }
        }else{
            warningError(3);
            return;
        }
        filmA();
    }

    private void selectAllFilms() {
        new dataModel().showAllFilms(dataFilms);
    }

    private void selectAllActors() {
        new dataModel().showAllActors(dataActors);
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }

}
