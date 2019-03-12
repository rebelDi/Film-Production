package FilmProduction.Controllers;

import FilmProduction.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import FilmProduction.tablesDB.*;
import FilmProduction.Models.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static FilmProduction.Controllers.userController.root;

public class cabinetController implements Initializable {
    private final ObservableList<reviewDB> comm = FXCollections.observableArrayList();
    private String oldLog = userController.loginUser;
    private String oldPassword = new userModel().getPasswordByLogin(userController.loginUser);
    private String oldEmail = new userModel().getEmailByLogin(userController.loginUser);

    @FXML
    private Button exitB, back, editLog, editPass, editEm, del;

    @FXML
    private TextField idComm, log, oldPass, newPass, nPass, oldE, newE;

    @FXML
    private Text logErr, passErr, emErr;

    @FXML
    private TableView<reviewDB> comments;

    @FXML
    Text adminInfo;

    @FXML
    Button adding, deleting, editing, usersTime, filmAct;

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
            filmAct.setVisible(true);
        }else {
            adminInfo.setVisible(false);
            adding.setVisible(false);
            editing.setVisible(false);
            deleting.setVisible(false);
            usersTime.setVisible(false);
            filmAct.setVisible(false);
        }
        log.setText(oldLog);

        findReviews();
        TableColumn<reviewDB, String> name = new TableColumn("Номер");
        name.setCellValueFactory(new PropertyValueFactory<>("number"));
        name.setPrefWidth(100);
        name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");

        TableColumn<reviewDB, String> film = new TableColumn("Фильм");
        film.setCellValueFactory(new PropertyValueFactory<>("login"));
        film.setPrefWidth(400);
        film.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");

        TableColumn<reviewDB, String> comment = new TableColumn("Комментарий");
        comment.setCellValueFactory(new PropertyValueFactory<>("text"));
        comment.setPrefWidth(500);
        comment.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");

        comments.getColumns().removeAll(comments.getColumns());
        comments.getColumns().addAll(name, film, comment);
        comments.setItems(comm);
    }

    private void findReviews(){
        new reviewModel().getReviewsByLogin(comm, userController.loginUser);
    }

    @FXML
    private void button(){
        goTo(back, "Views/showAll.fxml");
    }

    private void goTo(Button button, String location){
        Stage stage = (Stage) button.getScene().getWindow();
        try {
            root = FXMLLoader.load(Main.class.getResource(location));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            warningError(1);
        }
    }

    @FXML
    private void exit(){
        goTo(exitB, "Views/authorization.fxml");
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }

    @FXML
    private void delete(ActionEvent actionEvent){
        if(actionEvent.getSource() == deleting){
            new searchController(deleting).goToScene("Views/adminDelete.fxml");
        }else {
            if (idComm.getText().isEmpty()) {
                warningError(2);
            } else {
                try {
                    int b = Integer.parseInt(idComm.getText());
                    System.out.println(b);
                } catch (NumberFormatException e) {
                    warningError(4);
                    return;
                }
                int flag = -10;
                for (int i = 0; i < comm.size(); i++) {
                    if (comm.get(i).getNumber() == Integer.parseInt(idComm.getText())) {
                        flag = i;
                        break;
                    }
                }
                if (flag == -10) {
                    warningError(3);
                } else {
                    boolean err = new reviewModel().deleteReview(comm.get(flag).getText(), userController.loginUser, comm.get(flag).getIdFilm());
                    if (!err) {
                        warningError(1);
                    } else {
                        goTo(del, "Views/cabinet.fxml");
                    }
                }
            }
        }
    }

    @FXML
    private void changeLog(){
        if(log.getText().equals(oldLog)){
            logErr.setText("Ваш старый логин совпадает с новым");
        }else if(!new userModel().checkLogLength(log.getText())){
            logErr.setText("Логин должен быть длинее 1 символа");
        }else if(new userModel().checkLoginExistence(log.getText())){
            logErr.setText("Такой логин уже существует");
        }else{
            boolean flag = new userModel().updateLogin(log.getText(), userController.loginUser);
            if(flag){
                userController.loginUser = log.getText();
                goTo(editLog,"Views/cabinet.fxml");
            }
        }
    }

    @FXML
    private void changePass(){
        if(!oldPass.getText().equals(oldPassword)){
            passErr.setText("Вы неправильно ввели старый пароль");
        }else if(!new userModel().checkPassLength(newPass.getText())){
            passErr.setText("Новый пароль должен быть длинее 6 символов");
        }else if(!newPass.getText().equals(nPass.getText())){
            passErr.setText("Ваши пароль не совпадают");
        }else{
            boolean flag = new userModel().updatePassword(userController.loginUser, newPass.getText());
            if(flag){
                goTo(editLog,"Views/cabinet.fxml");
            }else{
                warningError(1);
            }
        }
    }

    @FXML
    private void changeEm(){
        if(!oldE.getText().equals(oldEmail)){
            emErr.setText("Вы неправильно ввели старый email");
        }else if(!new userModel().checkEmail(newE.getText())) {
            emErr.setText("Неверный формат");
        }else if(new userModel().checkEmailExistence(newE.getText())){
            emErr.setText("Такой email уже существует");
        }else{
            boolean flag = new userModel().updateEmail(userController.loginUser, newE.getText());
            if(flag){
                goTo(editLog,"Views/cabinet.fxml");
            }else{
                warningError(1);
            }
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
            alert.setContentText("Заполните нужное поле");
        }else if (num == 3) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setContentText("Такого номера нет в списке");
        }else if (num == 4) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setContentText("Вы ввели не цифру!");
        }
        alert.showAndWait();
    }
}
