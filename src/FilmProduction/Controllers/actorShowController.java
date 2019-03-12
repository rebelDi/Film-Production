package FilmProduction.Controllers;

import FilmProduction.Main;
import FilmProduction.Models.searchModel;
import FilmProduction.tablesDB.dataDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static FilmProduction.Controllers.userController.root;

public class actorShowController implements Initializable {
    static private final ObservableList<dataDB> dataA = showingController.data;
    private final ObservableList<dataDB> dataF = FXCollections.observableArrayList();;

    @FXML
    private Button back, exitB, cab;

    @FXML
    private Text name, birth, oscar;

    @FXML
    private TableView<dataDB> films;

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

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
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
        name.setText(dataA.get(0).getName());
        birth.setText(dataA.get(0).getDateA().toString());
        oscar.setText(dataA.get(0).getOscarA());

        findFilmOfActor();
        TableColumn<dataDB, String> name = new TableColumn("Фильмы");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(962);
        name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        name.setResizable(false);
        films.getColumns().removeAll(films.getColumns());
        films.getColumns().add(name);
        films.setItems(dataF);

        films.setRowFactory( tv -> {
            TableRow<dataDB> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    dataDB rowD = row.getItem();
                    showingController.selectedRow = rowD.getName();

                    new searchController(showingController.selectedRow, back).search();
                }
            });
            return row ;
        });
    }

    public void findFilmOfActor(){
        new searchModel().searchFilmActor(dataF, dataA.get(0).getName());
    }

    @FXML
    public void cabinet(){
        new searchController(cab).goToScene("Views/cabinet.fxml");
    }

    @FXML
    private void exit(){
        new searchController(exitB).goToScene("Views/authorization.fxml");
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
                new filmShowController().warningError(1);
                return;
            }
        } else {
            new filmShowController().warningError(1);
            return;
        }
    }
}
