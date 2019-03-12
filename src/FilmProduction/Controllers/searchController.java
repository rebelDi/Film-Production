package FilmProduction.Controllers;

import FilmProduction.Main;
import FilmProduction.Models.*;
import FilmProduction.tablesDB.dataDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class searchController{
    public String selectedSearch;
    public static final ObservableList<dataDB> data = FXCollections.observableArrayList();
    @FXML
    private Button button;
    private int flag = 0;

    public searchController(String selectedSearch, Button button) {
        this.selectedSearch = selectedSearch;
        this.button = button;

    }


    public searchController(Button button) {
        this.button = button;
    }

    public searchController() {
    }

    public String randoming(){
        return new dataModel().randFilms();
    }

    public void search(){
        showingController.data.clear();
        flag = searchInFilms();
        showingController.data = data;
        if(flag == 1) {
            goToScene("Views/showAloneFilm.fxml");
        }else{
            flag = searchInActors();
            if (flag == 2) {
                goToScene("Views/showAloneActor.fxml");
            }
            else if (flag == 0) {
                flag = searchInDirectors();
                if (flag == 3) {
                    goToScene("Views/showAloneDirector.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Ничего не найдено");
                    alert.setContentText("Вы будете переведены на главную страницу");
                    alert.showAndWait();
                    goToScene("Views/showAll.fxml");
                }
            }
        }
    }

    public void searchFilm(){
        showingController.data.clear();
        showingController.data = data;
        new searchModel().searchInFilms(data, selectedSearch);
        goToScene("Views/showAloneFilm.fxml");
    }

    public void searchActor(){
        showingController.data.clear();
        showingController.data = data;
        new searchModel().searchInActors(data, selectedSearch);
        goToScene("Views/showAloneActor.fxml");
    }

    public void searchDirector(){
        showingController.data.clear();
        showingController.data = data;
        new searchModel().searchInDirector(data, selectedSearch);
        goToScene("Views/showAloneDirector.fxml");
    }

    private int searchInFilms() {
        new searchModel().searchInFilms(data, selectedSearch);
        if(data.isEmpty()) {
            return flag = 0;
        }return flag = 1;
    }

    private int searchInActors() {
        new searchModel().searchInActors(data, selectedSearch);
        if(data.isEmpty()) {
            return flag = 0;
        }return flag = 2;
    }

    private int searchInDirectors() {
        new searchModel().searchInDirector(data, selectedSearch);
        if(data.isEmpty()) {
            return flag = 0;
        }return flag = 3;
    }

    public void goToScene(String nameScene){
        Stage stage = null;
        Parent root = null;
        stage = (Stage) button.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(nameScene));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
