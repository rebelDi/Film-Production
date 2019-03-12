package FilmProduction.Controllers;

import FilmProduction.Models.userModel;
import FilmProduction.tablesDB.usersDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class adminUserController implements Initializable {
    private ObservableList<usersDB> dataUsers = FXCollections.observableArrayList();

    @FXML
    private Button back, adding, deleting, editing, usersTime, cab, exitB, search;

    @FXML
    private TextField searchQuery;

    @FXML
    private Text adminInfo;

    @FXML
    private TableView<usersDB> users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findUsers();
        formTable();
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }

    private void formTable(){
        users.getColumns().removeAll(users.getColumns());
        TableColumn<usersDB, String> login = new TableColumn("Логин");
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        login.setPrefWidth(406);
        login.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");

        TableColumn<usersDB, String> email = new TableColumn("E-mail");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        email.setPrefWidth(406);
        email.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");

        TableColumn<usersDB, String> role = new TableColumn("Админ?");
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        role.setPrefWidth(100);
        role.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        users.getColumns().addAll(login, email, role);


        users.setRowFactory( tv -> {
            TableRow<usersDB> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && !Objects.equals(row.getItem().getLogin(), userController.loginUser)) {
                    usersDB rowData = row.getItem();
                    new userModel().updateRole(rowData.getLogin(), rowData.getRole());
                    new searchController(back).goToScene("Views/adminUsers.fxml");
                }
            });
            return row ;
        });
        users.setItems(dataUsers);
    }

    public void button() {
        new searchController(back).goToScene("Views/showAll.fxml");
    }

    private void findUsers(){
        new userModel().getUsers(dataUsers);
    }

    public void searching(){
        if(!searchQuery.getText().isEmpty()){
            dataUsers.clear();
            new userModel().getUserByLogin(dataUsers, searchQuery.getText());
        }else{
            warningError(2);
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

    public void warningError(int num) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (num == 1) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка в системе");
            alert.setContentText("Попробуйте позже");
        }else if (num == 2) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Напишите в поле хотя бы одну букву");
        }
        alert.showAndWait();
    }
}
