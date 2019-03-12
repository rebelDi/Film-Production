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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static FilmProduction.Controllers.userController.root;

public class extendedSearchController implements Initializable{
    private ObservableList<dataDB> films = FXCollections.observableArrayList();

    @FXML
    private Text year, genre, country, budget, money, rate, mark, c, to, dateTip, names;

    @FXML
    private TextField yearFrom, yearAfter, genreF, countryF, budgetF, moneyF, markF, namy;

    @FXML
    private RadioButton bB, bE, bL, mB, mE, mL, markB, markE, markL, film, actor, director, yes, no;

    @FXML
    private Button back, exitB, cab, search;

    @FXML
    private TableView<dataDB> table;

    private ToggleGroup where = new ToggleGroup();
    private ToggleGroup aboutBudget = new ToggleGroup();
    private ToggleGroup aboutMark = new ToggleGroup();
    private ToggleGroup aboutMoney = new ToggleGroup();
    private ToggleGroup aboutPerson = new ToggleGroup();

    @FXML
    private ChoiceBox<String> rating;

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
        rating.setItems(FXCollections.observableArrayList("G", "PG", "PG-13", "R", "NC-17"));
        rating.setTooltip(new Tooltip("G - Фильм демонстрируется без ограничений\nPG - Детям рекомендуется " +
                "смотреть фильм с родителями\nPG-13 - Просмотр не желателен детям до 13 лет\nR - Лица, не достигшие " +
                "17-летнего возраста допускаются \nна фильм только в сопровождении одного из родителей, \nлибо законного " +
                "представителя\nNC-17 - Лица 17-летнего возраста и младше на фильм не допускаются"));

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
        film.setToggleGroup(where);
        actor.setToggleGroup(where);
        director.setToggleGroup(where);

        bB.setToggleGroup(aboutBudget);
        bE.setToggleGroup(aboutBudget);
        bL.setToggleGroup(aboutBudget);

        mB.setToggleGroup(aboutMoney);
        mE.setToggleGroup(aboutMoney);
        mL.setToggleGroup(aboutMoney);

        markB.setToggleGroup(aboutMark);
        markE.setToggleGroup(aboutMark);
        markL.setToggleGroup(aboutMark);

        yes.setToggleGroup(aboutPerson);
        no.setToggleGroup(aboutPerson);
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
    public void button() {
        new searchController(back).goToScene("Views/showAll.fxml");
    }

    private String query = null;

    @FXML
    public void doSearch(){
        TableColumn<dataDB, String> name = new TableColumn("Результат поиска");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setPrefWidth(950);
        name.setStyle("-fx-font-size: 12pt; -fx-alignment: CENTER;");
        query = "";

        if(film.isSelected()){
            if(namy.getText().isEmpty() && yearFrom.getText().isEmpty() && yearAfter.getText().isEmpty() && genreF.getText().isEmpty() && countryF.getText().isEmpty() &&
                    budgetF.getText().isEmpty() && moneyF.getText().isEmpty() && rating.getValue()==null && markF.getText().isEmpty()){
                warningError(2);
                return;
            }else{
                query = "WHERE ";
                if(!Objects.equals(namy.getText(), "") && !namy.getText().isEmpty()){
                    query += "name LIKE '%" + namy.getText() + "%'";
                }
                if(!yearFrom.getText().isEmpty()){
                    if(checkNumber(yearFrom.getText())){
                        checkPreviousTextField(namy);
                        query += "year >= " + Integer.parseInt(yearFrom.getText());
                    }else{
                        warningError(3);
                        return;
                    }
                }
                if(!yearAfter.getText().isEmpty()){
                    if(checkNumber(yearAfter.getText())){
                        checkPreviousTextField(yearFrom);
                        query += "year <= " + Integer.parseInt(yearAfter.getText());
                    }else{
                        warningError(3);
                        return;
                    }
                }
                if(!genreF.getText().isEmpty()){
                    if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}else if(checkPreviousTextField(yearAfter)){}
                    query += "genre = '" + genreF.getText() + "'";
                }
                if(!countryF.getText().isEmpty()){
                    if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}else if(checkPreviousTextField(yearAfter)){}else if(checkPreviousTextField(genreF)){}
                    query += "country = '" + countryF.getText() + "'";
                }
                if(!budgetF.getText().isEmpty()){
                    if(checkNumber(budgetF.getText())) {
                        if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}else if(checkPreviousTextField(yearAfter)){}else if(checkPreviousTextField(genreF)){}else if(checkPreviousTextField(countryF)){}
                        query += "budget " + checkSigns(aboutBudget) + " " + budgetF.getText();
                    }else{
                        warningError(3);
                        return;
                    }
                }
                if(!moneyF.getText().isEmpty()){
                    if(checkNumber(moneyF.getText())) {
                        if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}else if(checkPreviousTextField(yearAfter)){}else if(checkPreviousTextField(genreF)){}
                        else if(checkPreviousTextField(countryF)){}else if(checkPreviousTextField(budgetF)){}
                        query += "money " + checkSigns(aboutMoney) + " " + moneyF.getText();
                    }else{
                        warningError(3);
                        return;
                    }
                }
                if(rating.getValue() != null){
                    if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}else if(checkPreviousTextField(countryF)){}else if(checkPreviousTextField(budgetF)){}
                    else if(checkPreviousTextField(yearAfter)){}else if(checkPreviousTextField(genreF)){}else if(checkPreviousTextField(moneyF)){}
                    query += "rate = '" + rating.getValue() + "'";
                }
                if(!markF.getText().isEmpty()){
                    if(checkNumberFloat(markF.getText())){
                        if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}else if(checkPreviousTextField(countryF)){}else if(checkPreviousTextField(budgetF)){}
                        else if(checkPreviousTextField(yearAfter)){}else if(checkPreviousTextField(genreF)){}else if(checkPreviousTextField(moneyF)){}
                        else if(rating.getValue() != null){ query += " AND ";}
                        query += "mark " + checkSigns(aboutMark) + " " + markF.getText();
                    }else{
                        warningError(3);
                        return;
                    }
                }
                films.clear();
                films = new searchModel().searchFilmsByQuery(films, query);
                table.getColumns().removeAll(table.getColumns());
                table.getColumns().add(name);
                name.setResizable(false);
                table.setItems(films);

                table.setRowFactory( tv -> {
                    TableRow<dataDB> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                            dataDB rowData = row.getItem();
                            films.clear();
                            new searchController(rowData.getName(), search).searchFilm();
                        }
                    });
                    return row ;
                });
            }
        }else if(actor.isSelected()){
            if(namy.getText().isEmpty() && yearFrom.getText().isEmpty() && yearFrom.getText().isEmpty() && yearAfter.getText().isEmpty() && !yes.isSelected() && !no.isSelected()){
                warningError(4);
                return;
            }else{
                query = "WHERE ";
                if(!Objects.equals(namy.getText(), "") && !namy.getText().isEmpty()){
                    query += "CONCAT(name, ' ', surname) LIKE '%" + namy.getText() + "%'";
                }
                if(!yearFrom.getText().isEmpty()){
                    if(yearFrom.getText().length() == 4){
                        if(checkNumber(yearFrom.getText())){
                            if(checkPreviousTextField(namy)){}
                            query += "YEAR(dateBirth) >= " + yearFrom.getText();
                        }else{
                            warningError(5);
                            return;
                        }
                    }else{
                        if(checkDate(yearFrom.getText())){
                            if(checkPreviousTextField(namy)){}
                            query += "dateBirth >= '" + yearFrom.getText() + "'";
                        }else {
                            warningError(5);
                            return;
                        }
                    }
                }
                if(!yearAfter.getText().isEmpty()){
                    if(yearAfter.getText().length() == 4){
                        if(checkNumber(yearAfter.getText())){
                            if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}
                            query += "YEAR(dateBirth) <= " + Integer.parseInt(yearAfter.getText());
                        }else{
                            warningError(5);
                            return;
                        }
                    }else{
                        if(checkDate(yearAfter.getText())){
                            if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}
                            query += "dateBirth <= '" + yearAfter.getText() + "'";
                        }else {
                            warningError(5);
                            return;
                        }
                    }
                }
                if(yes.isSelected()){
                    if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearAfter)){}else if(checkPreviousTextField(yearFrom)){};
                    query += "oscar = 1";
                }else if(no.isSelected()){
                    if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearAfter)){}else if(checkPreviousTextField(yearFrom)){};
                    query += "oscar = 0";
                }

                films.clear();
                films = new searchModel().searchActorsByQuery(films, query);
                table.getColumns().removeAll(table.getColumns());
                table.getColumns().add(name);
                name.setResizable(false);
                table.setItems(films);

                table.setRowFactory( tv -> {
                    TableRow<dataDB> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                            dataDB rowData = row.getItem();
                            films.clear();
                            new searchController(rowData.getName(), search).searchActor();
                        }
                    });
                    return row ;
                });
            }
        }else if(director.isSelected()){
            if(namy.getText().isEmpty() && yearFrom.getText().isEmpty() && yearFrom.getText().isEmpty() && yearAfter.getText().isEmpty() && !yes.isSelected() && !no.isSelected()){
                warningError(4);
                return;
            }else {
                query = "WHERE ";
                if(!Objects.equals(namy.getText(), "") && !namy.getText().isEmpty()){
                    query += "CONCAT(name, ' ', surname) LIKE '%" + namy.getText() + "%'";
                }
                if (!yearFrom.getText().isEmpty()) {
                    if (yearFrom.getText().length() == 4) {
                        if (checkNumber(yearFrom.getText())) {
                            if(checkPreviousTextField(namy)){}
                            query += "YEAR(dateBirth) >= " + yearFrom.getText();
                        } else {
                            warningError(5);
                            return;
                        }
                    } else {
                        if (checkDate(yearFrom.getText())) {
                            if(checkPreviousTextField(namy)){}
                            query += "dateBirth >= '" + yearFrom.getText() + "'";
                        } else {
                            warningError(5);
                            return;
                        }
                    }
                }
                if (!yearAfter.getText().isEmpty()) {
                    if (yearAfter.getText().length() == 4) {
                        if (checkNumber(yearAfter.getText())) {
                            if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}
                            query += "YEAR(dateBirth) <= " + Integer.parseInt(yearAfter.getText());
                        } else {
                            warningError(5);
                            return;
                        }
                    } else {
                        if (checkDate(yearAfter.getText())) {
                            if(checkPreviousTextField(namy)){}else if(checkPreviousTextField(yearFrom)){}
                            query += "dateBirth <= '" + yearAfter.getText() + "'";
                        } else {
                            warningError(5);
                            return;
                        }
                    }
                }
                if (yes.isSelected()) {
                    if(checkPreviousTextField(namy)){} else if (checkPreviousTextField(yearAfter)) {}else if (checkPreviousTextField(yearFrom)) {}
                    query += "prestige = 1";
                } else if (no.isSelected()) {
                    if(checkPreviousTextField(namy)){} else if (checkPreviousTextField(yearAfter)) {} else if (checkPreviousTextField(yearFrom)) {}
                    query += "prestige = 0";
                }

                films.clear();
                films = new searchModel().searchDirectorsByQuery(films, query);
                table.getColumns().removeAll(table.getColumns());
                table.getColumns().add(name);
                name.setResizable(false);
                table.setItems(films);

                table.setRowFactory(tv -> {
                    TableRow<dataDB> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && (!row.isEmpty())) {
                            dataDB rowData = row.getItem();
                            films.clear();
                            new searchController(rowData.getName(), search).searchDirector();
                        }
                    });
                    return row;
                });
            }
        }
    }

    private boolean checkPreviousTextField(TextField textField){
        if(!textField.getText().isEmpty()){
            query += " AND ";
            return true;
        }return false;
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

    private String checkSigns(ToggleGroup toggleGroup){
        if(toggleGroup.equals(aboutBudget)){
            if(bB.isSelected()){
                return ">";
            }else if(bE.isSelected()){
                return "=";
            }else if(bL.isSelected()){
                return "<";
            }
        }else if(toggleGroup.equals(aboutMoney)){
            if(mB.isSelected()){
                return ">";
            }else if(mL.isSelected()){
                return "<";
            }else if(mE.isSelected()){
                return "=";
            }
        }else if(toggleGroup.equals(aboutMark)){
            if(markL.isSelected()){
                return "<";
            }else if(markE.isSelected()){
                return "=";
            }else if(markB.isSelected()){
                return ">";
            }
        }
        return "";
    }

    @FXML
    private void filmA() {
        new searchController(usersTime).goToScene("Views/adminAddFilmActor.fxml");
    }

    private void warningError(int num) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (num == 1) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка в системе");
            alert.setContentText("Попробуйте позже");
        }else if (num == 2) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Заполните хотя бы одно поле");
        }else if (num == 3) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Если вы хотите искать по цифре, введите в нужное поле только цифры");
        }else if (num == 4) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Выберите хотя бы что-то для поиска");
        }else if (num == 5) {
            alert.setTitle("ВНИМАНИЕ");
            alert.setHeaderText("Ошибка");
            alert.setContentText("Дата в неправильном формате");
        }
        alert.showAndWait();
    }

    private boolean checkNumber(String string){
        try{
            Integer.parseInt(string);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    private boolean checkNumberFloat(String string){
        try{
            Float.parseFloat(string);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public void viewChange(ActionEvent actionEvent) {
        if(actionEvent.getSource() == film){
            names.setText("Название");
            year.setText("Год выпуска");
            genre.setText("Жанр");
            yes.setVisible(false);
            no.setVisible(false);
            genreF.setVisible(true);
            country.setVisible(true);
            countryF.setVisible(true);
            budget.setVisible(true);
            bB.setVisible(true);
            bE.setVisible(true);
            bL.setVisible(true);
            budgetF.setVisible(true);
            money.setVisible(true);
            mB.setVisible(true);
            mE.setVisible(true);
            mL.setVisible(true);
            moneyF.setVisible(true);
            rate.setVisible(true);
            rating.setVisible(true);
            mark.setVisible(true);
            markB.setVisible(true);
            markE.setVisible(true);
            markL.setVisible(true);
            markF.setVisible(true);
            dateTip.setVisible(false);
        }else if(actionEvent.getSource() == actor){
            names.setText("Имя (Фамилия)");
            year.setText("Год рождения");
            genre.setText("Есть Оскар?");
            yes.setText("Да");
            no.setText("Нет");
            genreF.setVisible(false);
            country.setVisible(false);
            countryF.setVisible(false);
            budget.setVisible(false);
            bB.setVisible(false);
            bE.setVisible(false);
            bL.setVisible(false);
            budgetF.setVisible(false);
            money.setVisible(false);
            mB.setVisible(false);
            mE.setVisible(false);
            mL.setVisible(false);
            moneyF.setVisible(false);
            rate.setVisible(false);
            rating.setVisible(false);
            mark.setVisible(false);
            markB.setVisible(false);
            markE.setVisible(false);
            markL.setVisible(false);
            markF.setVisible(false);
            yes.setVisible(true);
            no.setVisible(true);
            dateTip.setVisible(true);
        }else if(actionEvent.getSource() == director){
            names.setText("Имя (Фамилия)");
            year.setText("Год рождения");
            genre.setText("Уровень престижа");
            yes.setText("Высокий");
            no.setText("Средний");
            yes.setVisible(true);
            genreF.setVisible(false);
            no.setVisible(true);
            country.setVisible(false);
            countryF.setVisible(false);
            budget.setVisible(false);
            bB.setVisible(false);
            bE.setVisible(false);
            bL.setVisible(false);
            budgetF.setVisible(false);
            money.setVisible(false);
            mB.setVisible(false);
            mE.setVisible(false);
            mL.setVisible(false);
            moneyF.setVisible(false);
            rate.setVisible(false);
            rating.setVisible(false);
            mark.setVisible(false);
            dateTip.setVisible(true);
            markB.setVisible(false);
            markE.setVisible(false);
            markL.setVisible(false);
            markF.setVisible(false);
            yes.setVisible(true);
            no.setVisible(true);
        }
    }
}
