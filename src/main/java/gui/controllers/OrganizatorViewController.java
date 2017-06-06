package gui.controllers;

import businesslogic.ExSystem;
import businesslogic.excursionobject.Excursion;
import gui.facade.Facade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Danya on 29.05.2017.
 */
public class OrganizatorViewController {
    Facade f = ExSystem.facade;

//    @FXML private Button createExcursion;
//    @FXML private Button setDriver;
//    @FXML private Button payDriver;
//    @FXML private Button addObject;
    @FXML private Button createExcursion;
    @FXML private Button ok;
    @FXML private Button ok2;
    @FXML private Button startExcursion;
    @FXML private Button endExcursion;

    @FXML private Text welcome;
    @FXML private Text balance;
    @FXML private Text excDesc;
    @FXML private Text excName;

    @FXML private ListView<String> driversList;

    @FXML private TextField payAmount;

    @FXML private TextArea addObj;

    public void init(String login) throws Exception{
        log = login;
        welcome.setText("Welcome, " + login);
        balance.setText("Your balance: " + f.getMoney(login));
        excName.setText(f.getOrganizatorExcursion(login));
        excDesc.setText(f.getExcustionDescriptionForOrg(login));
        ok.setVisible(false);
        ok2.setVisible(false);

        ArrayList<String> excs = f.getDriversList(login);
        ObservableList<String> items = FXCollections.observableArrayList(excs);
        driversList.setItems(items);

    }

    public void createExcursionClicked(ActionEvent actionEvent) throws Exception  {
        ExSystem.showCreateExcursionView(log);
//        addObj.setText("");
//        addObj.setVisible(true);
//        ok.setVisible(true);
//        ok.setDisable(false);
    }

    public void setDriverClicked(ActionEvent actionEvent) throws Exception {
        String selected = driversList.getSelectionModel().getSelectedItem();
        String dr = selected.substring(0, selected.indexOf("("));
        //f.setDriverToExc(log, dr);
        String amount = payAmount.getText();
        if (amount == "")
            return;
        int price = Integer.parseInt(amount);
        int status = f.sendOfferToDriver(log, dr, price);
        if (status != 0)
            ExSystem.showErrorView(status);
        init(log);
    }

//    public void payDriverClicked(ActionEvent actionEvent) throws Exception {
//        String selected = driversList.getSelectionModel().getSelectedItem();
//        if (selected == null)
//            return;
//        String dr = selected.substring(0, selected.indexOf("("));
//        String amount = payAmount.getText();
//        int am = Integer.parseInt(amount);
//        f.payToDriver(log, dr, am);
//        init(log);
//    }

    public void addObjectClicked(ActionEvent actionEvent) {
        addObj.setText("");
        addObj.setVisible(true);
        ok.setVisible(true);
        ok.setDisable(false);
    }

    public void okButtonClicked(ActionEvent actionEvent) throws Exception {
        //ok.setVisible(false);
        //ok.setDisable(true);
        addObj.setVisible(false);
        ok.setVisible(false);
        int st = f.addExcursionObject(log, addObj.getText());
        if (st != 0)
            ExSystem.showErrorView(st);
        init(log);
    }

    public void onOk2ButtonClicked(ActionEvent actionEvent) throws Exception {
//        ok2.setVisible(false);
//        ok2.setDisable(true);
//        addObj.setVisible(false);
//        f.addExcursion(log, addObj.getText());
//        System.out.println("LOL");
//        init(log);

    }

    public void onStartExcursionClicked(ActionEvent actionEvent) throws Exception {
        int status = f.beginExcursion(log);
        if (status != 0)
            ExSystem.showErrorView(status);
        else{
            startExcursion.setVisible(false);
            endExcursion.setVisible(true);
            init(log);
        }
    }

    public void onEndExcursionClicked(ActionEvent actionEvent) throws Exception{
        startExcursion.setVisible(true);
        endExcursion.setVisible(false);
        f.endExcursion(log);
        init(log);
    }

    public void onUpdateButtonClicked(ActionEvent actionEvent) throws Exception{
        f.updateOrg(log);
        init(log);
    }

    String log;

}
