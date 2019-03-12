package FilmProduction.Controllers;

import FilmProduction.Main;
import FilmProduction.Models.userModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class userController implements Initializable {
    static boolean role = false;
    static String loginUser;

    static Stage stage;
    static Parent root;

    @FXML
    private Button login, register;

    @FXML
    private TextField log;

    @FXML
    private PasswordField password;

    @FXML
    private TextField email;

    @FXML
    private Label emErr, passErr, logErr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Stage stage;
        Parent root;

        //log.appendText("RebelDi");
        //password.appendText("230119");

        if(event.getSource() == login){
            if(new userModel().checkAccExistence(log.getText(), password.getText())){
                stage = (Stage) login.getScene().getWindow();
                try {
                    loginUser = log.getText();
                    role = new userModel().checkAdmin(log.getText());
                    root = FXMLLoader.load(Main.class.getResource("Views/showAll.fxml"));
                } catch (IOException e) {
                    warningError(1);
                    return;
                }
            }else {
                warningError(0);
                return;
            }
        }else{
            stage = (Stage) register.getScene().getWindow();
            try {
                root = FXMLLoader.load(Main.class.getResource("Views/register.fxml"));
            } catch (IOException e) {
                warningError(1);
                return;
            }
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleButtonRegisterPage(ActionEvent event) {
        if(event.getSource() == register){
            if(!Objects.equals(log.getText(), "") & !Objects.equals(password.getText(), "") & !Objects.equals(email.getText(), "")){
                if(!new userModel().checkLoginExistence(log.getText())){
                    if(new userModel().checkEmail(email.getText())){
                        if(!new userModel().checkEmailExistence(email.getText())){
                            if(new userModel().checkPassLength(password.getText())){
                                if(new userModel().checkLogLength(log.getText())){
                                    stage = (Stage) register.getScene().getWindow();
                                    try {
                                        new userModel().register(log.getText(), password.getText(), email.getText());
                                        warningError(2);
                                        root = FXMLLoader.load(Main.class.getResource("Views/authorization.fxml"));
                                    } catch (IOException e) {
                                        warningError(1);
                                        return;
                                    }
                                }else{
                                    emErr.setText("");
                                    logErr.setText("Логин должен быть длинее 1 символа");
                                    passErr.setText("");
                                    return;
                                }
                            }else {
                                emErr.setText("");
                                logErr.setText("");
                                passErr.setText("Пароль должен быть длинее 6 символов");
                                return;
                            }
                        }else{
                            emErr.setText("Такой email уже зарегистрирован");
                            logErr.setText("");
                            passErr.setText("");
                            return;
                        }
                    }else{
                        emErr.setText("Ваш email не соответсвует стандартам");
                        logErr.setText("");
                        passErr.setText("");
                        return;
                    }
                }else {
                    logErr.setText("Такой логин уже зарегистрирован");
                    emErr.setText("");
                    passErr.setText("");
                    return;
                }
            }else{
                warningError(3);
                return;
            }
        }else{
            stage = (Stage) login.getScene().getWindow();
            try {
                root = FXMLLoader.load(Main.class.getResource("Views/authorization.fxml"));
            } catch (IOException e) {
                warningError(1);
                return;
            }
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void warningError(int num){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if(num == 0){
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("НЕСАНКЦИОНИРОВАННЫЙ ДОСТУП");
            alert.setContentText("Вы ошиблись в вводе или вас нет в системе!");
        }else if(num == 1){
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка в системе");
            alert.setContentText("Попробуйте позже");
        }else if(num == 2){
            alert.setHeaderText("Поздравляем");
            alert.setContentText("Вы успешно зарегистрированы");
        }else if(num == 3){
            alert.setHeaderText("Ошибка");
            alert.setContentText("Заполните все поля");
        }
        alert.showAndWait();
    }
}
