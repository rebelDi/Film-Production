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
import java.util.Optional;
import java.util.ResourceBundle;

import static FilmProduction.Controllers.userController.root;

public class adminEditController implements Initializable {
    private static ObservableList<dataDB> dataD = FXCollections.observableArrayList();

    @FXML
    private Button back, adding, deleting, editing, usersTime, cab, exitB;

    @FXML
    private Text adminInfo;

    @FXML
    private RadioButton film, actor, director;

    private ToggleGroup choice = new ToggleGroup();

    @FXML
    private TableView<dataDB> table;

    @FXML
    private TextField searchQ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        film.setToggleGroup(choice);
        actor.setToggleGroup(choice);
        director.setToggleGroup(choice);

        film.isSelected();
        changing(0);
    }

    public void button() {
        Stage stage = (Stage) back.getScene().getWindow();
        try {
            root = FXMLLoader.load(Main.class.getResource("Views/showAll.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            warningError(1);
        }
    }

    private void formTable(){
        TableColumn<dataDB, String> name = new TableColumn("Результат поиска");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(969);
        name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        name.setResizable(false);
        table.getColumns().clear();
        table.getColumns().add(name);
        table.setItems(dataD);

        table.setRowFactory( tv -> {
            TableRow<dataDB> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    dataDB rowData = row.getItem();
                    adminAddController.editMode = true;
                    adminAddController.editWhat = rowData.getName();
                    if(film.isSelected()){
                        adminAddController.editWhere = 0;
                    }else if(actor.isSelected()){
                        adminAddController.editWhere = 1;
                    }else{
                        adminAddController.editWhere = 2;
                    }
                    new searchController(back).goToScene("Views/adminAdd.fxml");
                }
            });
            return row ;
        });
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

    public void warningError(int num) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (num == 1) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка в системе");
            alert.setContentText("Попробуйте позже");
        }
        alert.showAndWait();
    }

    public void doSearch() {
        if(searchQ.getText().isEmpty()){
            warningError(2);
        }else{
            if(actor.isSelected()) {
                dataD.clear();
                new dataModel().getActorByLetter(dataD, searchQ.getText());
                formTable();
            }else if(film.isSelected()){
                dataD.clear();
                new dataModel().getFilmsByLetter(dataD, searchQ.getText());
                formTable();
            }else if(director.isSelected()){
                dataD.clear();
                new dataModel().getDirectorByLetter(dataD, searchQ.getText());
                formTable();
            }
        }
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }


    public void viewChange(ActionEvent actionEvent) {
        if(actionEvent.getSource() == film){
            changing(0);
        }else if(actionEvent.getSource() == director){
            changing(2);
        }else if(actionEvent.getSource() == actor) {
            changing(1);
        }
    }

    private void changing(int num){
        if(num == 0){
            dataD.clear();
            selectAllFilms();
            formTable();
        }else if(num == 1){
            dataD.clear();
            selectAllActors();
            formTable();
        }else if(num == 2){
            dataD.clear();
            selectAllDirectors();
            formTable();
        }
    }

    private void selectAllFilms() {
        new dataModel().showAllFilms(dataD);
    }

    private void selectAllActors() {
        new dataModel().showAllActors(dataD);
    }

    private void selectAllDirectors() {
        new dataModel().showAllDirectors(dataD);
    }
}
