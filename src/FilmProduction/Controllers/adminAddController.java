package FilmProduction.Controllers;

import FilmProduction.Main;
import FilmProduction.tablesDB.*;
import FilmProduction.Models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;

import static FilmProduction.Controllers.userController.root;

public class adminAddController implements Initializable {
    private static ObservableList<dataDB> directors = FXCollections.observableArrayList();
    static public ObservableList<dataDB> data = FXCollections.observableArrayList();
    static boolean editMode;
    static String editWhat;
    static int editWhere;
    private boolean wasEdit;

    @FXML
    private Button back, adding, deleting, editing, usersTime, cab, exitB, addSomething;

    @FXML
    private Text nameT, yearT, genreT, countryT, timeT, directorT, plotT, budgetT, moneyT, rateT, hoursT, minutesT, addT;

    @FXML
    private TextField name, year, genre, country, timeH, timeM, budget, money;

    @FXML
    private RadioButton yes, no;

    @FXML
    private TextArea plot;

    @FXML
    private Rectangle rect;

    @FXML
    private Text adminInfo;

    @FXML
    private ChoiceBox<String> directorSel;

    @FXML
    private ChoiceBox rateSel;

    @FXML
    private RadioButton film, actor, director;
    private ToggleGroup choice = new ToggleGroup();
    private ToggleGroup choice1 = new ToggleGroup();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directors.clear();
        data.clear();

        yes.setVisible(false);
        no.setVisible(false);
        film.setToggleGroup(choice);
        actor.setToggleGroup(choice);
        director.setToggleGroup(choice);

        yes.setToggleGroup(choice1);
        no.setToggleGroup(choice1);

        new dataModel().showAllDirectors(directors);
        for (dataDB director1 : directors) {
            directorSel.getItems().add(director1.getName());
        }
        rateSel.setItems(FXCollections.observableArrayList("G", "PG", "PG-13", "R", "NC-17"));
        rateSel.setTooltip(new Tooltip("G - Фильм демонстрируется без ограничений\nPG - Детям рекомендуется " +
                "смотреть фильм с родителями\nPG-13 - Просмотр не желателен детям до 13 лет\nR - Лица, не достигшие " +
                "17-летнего возраста допускаются \nна фильм только в сопровождении одного из родителей, \nлибо законного " +
                "представителя\nNC-17 - Лица 17-летнего возраста и младше на фильм не допускаются"));

        if(editMode){
            addSomething.setText("Редактировать");
            addT.setText("Кого вы хотите изменить?");
            if(editWhere == 0){
                film.setSelected(true);
                actor.setDisable(true);
                director.setDisable(true);
                formPageFilms();
                data.clear();
                new searchModel().searchInFilms(data, editWhat);
                name.setText(data.get(0).getName());
                year.setText(String.valueOf(data.get(0).getYear()));
                genre.setText(data.get(0).getGenre());
                country.setText(data.get(0).getCountry());
                timeH.setText(data.get(0).getTime().substring(0, 2));
                timeM.setText(data.get(0).getTime().substring(3, 5));
                directorSel.setValue(data.get(0).getDirector());
                plot.setText(data.get(0).getPlot());
                budget.setText(String.valueOf(data.get(0).getBudget()));
                money.setText(String.valueOf(data.get(0).getMoney()));
                rateSel.setValue(data.get(0).getRate());
            }else if(editWhere == 1){
                actor.setSelected(true);
                film.setDisable(true);
                director.setDisable(true);
                formPageActor();
                data.clear();
                new searchModel().searchInActors(data, editWhat);
                String[] nameSurname = data.get(0).getName().split(" ");
                name.setText(nameSurname[0]);
                year.setText(nameSurname[1]);
                genre.setText(String.valueOf(data.get(0).getDateA()));
                if(Objects.equals(data.get(0).getOscarA(), "Есть")){
                    yes.setSelected(true);
                    no.setSelected(false);
                }else if(Objects.equals(data.get(0).getOscarA(), "Нет")){
                    no.setSelected(true);
                    yes.setSelected(false);
                }
            }else{
                director.setSelected(true);
                actor.setDisable(true);
                film.setDisable(true);
                formPageDirector();
                data.clear();
                new searchModel().searchInDirector(data, editWhat);
                String[] nameSurname = data.get(0).getName().split(" ");
                name.setText(nameSurname[0]);
                year.setText(nameSurname[1]);
                genre.setText(String.valueOf(data.get(0).getYearD()));
                if(Objects.equals(data.get(0).getPrestigeD(), "Высокий")){
                    yes.setSelected(true);
                    no.setSelected(false);
                }else if(Objects.equals(data.get(0).getPrestigeD(), "Средний")){
                    no.setSelected(true);
                    yes.setSelected(false);
                }
            }
            editMode = false;
            wasEdit = true;
        }else{
            addSomething.setText("Добавить");
            addT.setText("Кого вы хотите добавить?");
            actor.setDisable(false);
            director.setDisable(false);
            film.setDisable(false);
        }
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }

    private void formPageFilms(){
        nameT.setText("Название");
        yearT.setText("Год выпуска");
        genreT.setText("Жанр");
        countryT.setText("Страна производства");
        yes.setVisible(false);
        no.setVisible(false);
        country.setVisible(true);
        timeT.setVisible(true);
        timeH.setVisible(true);
        hoursT.setVisible(true);
        timeM.setVisible(true);
        minutesT.setVisible(true);
        directorT.setVisible(true);
        directorSel.setVisible(true);
        plotT.setVisible(true);
        plot.setVisible(true);
        budgetT.setVisible(true);
        budget.setVisible(true);
        moneyT.setVisible(true);
        money.setVisible(true);
        rateT.setVisible(true);
        rateSel.setVisible(true);
        if(rect.getHeight() == 182){
            rect.setHeight(437);
            addSomething.setLayoutY(addSomething.getLayoutY()+255);
        }
    }

    private void formPageActor(){
        nameT.setText("Имя");
        yearT.setText("Фамилия");
        genreT.setText("Дата Рождения");
        countryT.setText("Есть Оскар?");
        country.setVisible(false);
        timeT.setVisible(false);
        timeH.setVisible(false);
        hoursT.setVisible(false);
        timeM.setVisible(false);
        minutesT.setVisible(false);
        directorT.setVisible(false);
        directorSel.setVisible(false);
        plotT.setVisible(false);
        plot.setVisible(false);
        budgetT.setVisible(false);
        budget.setVisible(false);
        moneyT.setVisible(false);
        money.setVisible(false);
        rateT.setVisible(false);
        rateSel.setVisible(false);
        yes.setVisible(true);
        no.setVisible(true);
        yes.setText("Да");
        no.setText("Нет");
        if(rect.getHeight() != 182){
            rect.setHeight(182);
            addSomething.setLayoutY(addSomething.getLayoutY()-255);
        }
    }

    private void formPageDirector(){
        nameT.setText("Имя");
        yearT.setText("Фамилия");
        genreT.setText("Дата Рождения");
        countryT.setText("Престиж?");
        country.setVisible(false);
        timeT.setVisible(false);
        timeH.setVisible(false);
        hoursT.setVisible(false);
        timeM.setVisible(false);
        minutesT.setVisible(false);
        directorT.setVisible(false);
        directorSel.setVisible(false);
        plotT.setVisible(false);
        plot.setVisible(false);
        budgetT.setVisible(false);
        budget.setVisible(false);
        moneyT.setVisible(false);
        money.setVisible(false);
        rateT.setVisible(false);
        rateSel.setVisible(false);
        yes.setVisible(true);
        no.setVisible(true);
        yes.setText("Высокий");
        no.setText("Средний");
        if(rect.getHeight() != 182){
            rect.setHeight(182);
            addSomething.setLayoutY(addSomething.getLayoutY()-255);
        }
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

    public void cabinet(ActionEvent actionEvent) {
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
            alert.setContentText("Все поля должны быть заполнены");
        }else if (num == 3) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Фильм с таким названием уже есть в системе");
        }else if (num == 4) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("В полях, предполагающих наличие только цифр, должны быть только цифры");
        }else if (num == 5) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Поле с датой должно быть в виде гггг-мм-дд");
        }
        alert.showAndWait();
    }

    public void justAdd() {
        if(film.isSelected()){
            if(name.getText().isEmpty() || year.getText().isEmpty() || country.getText().isEmpty() || genre.getText().isEmpty() ||
                    timeH.getText().isEmpty() || timeM.getText().isEmpty() || directorSel.getValue() == null || plot.getText().isEmpty() ||
                    budget.getText().isEmpty() || money.getText().isEmpty() || rateSel.getValue() == null){
                warningError(2);
                return;
            }else{
                if(new dataModel().checkFilmNameExistence(name.getText()) && !wasEdit){
                    warningError(3);
                    return;
                }else{
                    if(checkNumber(year.getText())){
                        if(checkNumber(timeH.getText())){
                            if(checkNumber(timeM.getText())){
                                if(checkNumber(budget.getText())){
                                    if(checkNumber(money.getText())){
                                        if(wasEdit){
                                            if(!Objects.equals(name.getText(), editWhat)){
                                                if(new dataModel().checkFilmNameExistence(name.getText())) {
                                                    warningError(3);
                                                    return;
                                                }
                                            }
                                            if(!new dataModel().editFilm(name.getText(), year.getText(), genre.getText(), country.getText(), timeH.getText(),
                                                    timeM.getText(), plot.getText(), budget.getText(), money.getText(), rateSel.getValue(), directorSel.getValue(), editWhat)){
                                                warningError(1);
                                                return;
                                            }else{
                                                wasEdit = false;
                                                edit();
                                                return;
                                            }
                                        }else{
                                            if(!new dataModel().addFilm(name.getText(), year.getText(), genre.getText(), country.getText(), timeH.getText(),
                                                    timeM.getText(), plot.getText(), budget.getText(), money.getText(), rateSel.getValue(), directorSel.getValue())){
                                                warningError(1);
                                                return;
                                            }
                                        }
                                    }else {
                                        warningError(4);
                                        return;
                                    }
                                }else {
                                    warningError(4);
                                    return;
                                }
                            }else {
                                warningError(4);
                                return;
                            }
                        }else {
                            warningError(4);
                            return;
                        }
                    }else{
                        warningError(4);
                        return;
                    }
                }
            }
        }else if(actor.isSelected()){
            if(name.getText().isEmpty() || year.getText().isEmpty() || genre.getText().isEmpty() || (!yes.isSelected() && !no.isSelected())){
                warningError(2);
                return;
            }else{
                if(!checkDate(genre.getText())){
                    warningError(5);
                    return;
                }else{
                    int i = 5;
                    String query = "'" + name.getText() + "', '" + year.getText() + "', '" + genre.getText() + "', ";
                    if(yes.isSelected()){
                        query += "'1'";
                        i = 1;
                    }else if(no.isSelected()){
                        query += "'0'";
                        i = 0;
                    }
                    if(wasEdit){
                        if(!new dataModel().editdActor(name.getText(), year.getText(), genre.getText(), i, editWhat)){
                            warningError(1);
                            return;
                        }else{
                            wasEdit = false;
                            edit();
                            return;
                        }
                    }else{
                        if(!new dataModel().addActor(query)){
                            warningError(1);
                            return;
                        }
                    }
                }
            }
        }else if(director.isSelected()){
            if(name.getText().isEmpty() || year.getText().isEmpty() || genre.getText().isEmpty() || (!yes.isSelected() && !no.isSelected())){
                warningError(2);
                return;
            }else{
                if(!checkDate(genre.getText())){
                    warningError(5);
                    return;
                }else{
                    int i;
                    String query = "'" + name.getText() + "', '" + year.getText() + "', '" + genre.getText() + "', ";
                    if(yes.isSelected()){
                        query += "'1'";
                        i = 1;
                    }else{
                        query += "'0'";
                        i = 0;
                    }
                    if(wasEdit){
                        if(!new dataModel().editDirector(name.getText(), year.getText(), genre.getText(), i, editWhat)){
                            warningError(1);
                            return;
                        }else{
                            wasEdit = false;
                            edit();
                            return;
                        }
                    }else {
                        if (!new dataModel().addDirector(query)) {
                            warningError(1);
                            return;
                        }
                    }
                }
            }
        }
        reloadPage();
    }

    private boolean checkDate(String q){
        try {
            new SimpleDateFormat("yyyy-mm-dd").parse(q);
            try{
                Integer.parseInt(q.substring(0, 4));
                if((Integer.parseInt(q.substring(5, 7)) > 0) && (Integer.parseInt(q.substring(5, 7)) <= 12)) {
                    if ((Integer.parseInt(q.substring(8, 10)) > 0) && (Integer.parseInt(q.substring(8, 10)) < 32)) {
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }catch (NumberFormatException e){
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean checkNumber(String string){
        try{
            Integer.parseInt(string);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public void viewChange(ActionEvent actionEvent) {
        if(actionEvent.getSource() == film){
            formPageFilms();
        }else if(actionEvent.getSource() == actor){
            formPageActor();
        }else if(actionEvent.getSource() == director){
            formPageDirector();
        }
    }

    private void reloadPage(){
        new searchController(back).goToScene("Views/adminAdd.fxml");
    }
}
