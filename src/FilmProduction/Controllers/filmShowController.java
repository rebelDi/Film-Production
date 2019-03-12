package FilmProduction.Controllers;

import FilmProduction.Main;
import FilmProduction.Models.*;
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
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static FilmProduction.Controllers.userController.root;

public class filmShowController implements Initializable{
    private String userLogin = userController.loginUser;

    @FXML
    private Text name, director, country, year, genre, time, budget, money, rate, mark, plot;

    static private final ObservableList<dataDB> data = showingController.data;
    private final ObservableList<dataDB> dataA = FXCollections.observableArrayList();
    private final ObservableList<reviewDB> dataReview = FXCollections.observableArrayList();

    @FXML
    private Button back, addReview, mark1, mark2, mark3, mark4, mark5, exitB, cab;

    @FXML
    private GridPane grid;

    @FXML
    private TextField review;

    @FXML
    private TableView<dataDB> actors;

    @FXML
    private TableView<reviewDB> reviews;

    @FXML
    private TableColumn<dataDB, String> actor;

    @FXML
    private Rectangle rect;

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
        if(new reviewModel().checkMarkExistence(userLogin, data.get(0).getName())){
            grid.setVisible(true);
        }else{
            grid.setVisible(false);
        }
        name.setText(data.get(0).getName());
        director.setText(data.get(0).getDirector());
        country.setText(data.get(0).getCountry());
        year.setText(String.valueOf(data.get(0).getYear()));
        genre.setText(data.get(0).getGenre());
        time.setText(data.get(0).getTime());
        budget.setText("$" + String.valueOf(data.get(0).getBudget()));
        money.setText("$" + String.valueOf(data.get(0).getMoney()));
        rate.setText(data.get(0).getRate());
        mark.setText(String.valueOf(data.get(0).getMark()));
        plot.setText(String.valueOf(data.get(0).getPlot()));
        rect.setHeight(plot.getBoundsInLocal().getHeight()+10);

        findActors();
        TableColumn<dataDB, String> name = new TableColumn("Актёры");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(406);
        name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        actors.getColumns().removeAll(actors.getColumns());
        actors.getColumns().add(name);
        actor.setResizable(false);
        actors.setItems(dataA);

        actors.setRowFactory( tv -> {
            TableRow<dataDB> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    dataDB rowData = row.getItem();
                    showingController.selectedRow = rowData.getName();
                    new searchController(showingController.selectedRow, addReview).search();
                }
            });
            return row ;
        });

        findReviews();
        TableColumn<reviewDB, String> login = new TableColumn("Имя пользователя");
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        login.setPrefWidth(200);
        login.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");

        TableColumn<reviewDB, String> text = new TableColumn("Комментарий");
        text.setCellValueFactory(new PropertyValueFactory<>("text"));
        text.setPrefWidth(768);
        text.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");

        reviews.getColumns().removeAll(reviews.getColumns());
        reviews.getColumns().addAll(login, text);
        reviews.setItems(dataReview);
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }

    @FXML
    public void cabinet(){
        new searchController(cab).goToScene("Views/cabinet.fxml");
    }

    public void findActors(){
        new searchModel().showAllActors(dataA, data.get(0).getName());
    }

    public void findReviews(){
        new reviewModel().getReviews(dataReview, data.get(0).getName());
    }

    public void button(ActionEvent actionEvent) {
        if (actionEvent.getSource() == back) {
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
        } else {
                warningError(1);
                return;
        }
    }

    @FXML
    public void reloadPage(){
        Stage stage = (Stage) exitB.getScene().getWindow();
        try {
            root = FXMLLoader.load(Main.class.getResource("Views/showAloneFilm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            warningError(1);
            return;
        }
    }

    public void addSomething(ActionEvent actionEvent){
        if(actionEvent.getSource() == addReview){
            if(review.getText().isEmpty()){
                warningError(2);
            }else if(review.getText().length() > 50){
                warningError(3);
            }else{
                boolean flag = new reviewModel().addReview(review.getText(), data.get(0).getName(), userLogin);
                if(!flag){
                    warningError(1);
                }else {
                    review.setText("");
                    reloadPage();
                }
            }
        }else if(actionEvent.getSource() == mark1){
            addMark(1, mark1);
        }else if(actionEvent.getSource() == mark2){
            addMark(2, mark2);
        }else if(actionEvent.getSource() == mark3){
            addMark(3, mark3);
        }else if(actionEvent.getSource() == mark4){
            addMark(4, mark4);
        }else if(actionEvent.getSource() == mark5){
            addMark(5, mark5);
        }
    }

    @FXML
    public void addMark(int mark, Button button){
        new reviewModel().addMark(mark, data.get(0).getName(), userLogin);
        reloadPage();
    }

    public void warningError(int num) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (num == 1) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка в системе");
            alert.setContentText("Попробуйте позже");
        }else if (num == 2) {
            alert.setHeaderText("Ошибка");
            alert.setContentText("Сначала заполните поле с комментарием");
        }else if (num == 3) {
            alert.setHeaderText("Ошибка");
            alert.setContentText("В вашем комментарии содержится более 50 символов (" + review.getText().length() + ")!");
        }
        alert.showAndWait();
    }

    @FXML
    private void exit(){
        new searchController(exitB).goToScene("Views/authorization.fxml");
    }
}
