package FilmProduction.Models;

import javafx.collections.ObservableList;

public class checkBoxesModel {
    private ObservableList<String> moneyFrom;
    private ObservableList<String> budgetFrom;

    public ObservableList<String> getMoneyFrom() {
        return moneyFrom;
    }

    public ObservableList<String> getBudgetFrom() {
        return budgetFrom;
    }

    public checkBoxesModel() {
    }

    public checkBoxesModel(ObservableList<String> moneyFrom, ObservableList<String> budgetFrom) {
        this.moneyFrom = moneyFrom;
        this.budgetFrom = budgetFrom;
    }

    public ObservableList<String> setMoneyFrom(ObservableList<String> moneyFrom) {
        this.moneyFrom.add("<");
        this.moneyFrom.add("=");
        this.moneyFrom.add(">");
        return moneyFrom;
    }

    public void setBudgetFrom(ObservableList<String> budgetFrom) {
        this.budgetFrom.add("<");
        this.budgetFrom.add("=");
        this.budgetFrom.add(">");
    }
}
