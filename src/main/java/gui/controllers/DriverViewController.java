package gui.controllers;

import businesslogic.ExSystem;
import businesslogic.userfactory.Driver;
import gui.facade.Facade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Danya on 29.05.2017.
 */
public class DriverViewController {

    Facade f = ExSystem.facade;

    String log;

    @FXML private Button addNewVehicle;
    @FXML private Button checkVehicle;

    @FXML private ListView<String> excList;

    @FXML private Text welcome;
    @FXML private Text balance;
    @FXML private Text vehicle;
    @FXML private Text excDesc;
    @FXML private Text yourExc;

    public void init(String login) throws Exception{
        log = login;
        welcome.setText("Welcome, " + login);
        balance.setText("Your balance: " + f.getMoney(login));
        vehicle.setText(f.getDriversVehicleInfo(login));
        yourExc.setText(yourExc.getText() + " " + f.getDriversExcursion(login));
        ArrayList<String> excs = f.getExcursionList();
        ObservableList<String> items = FXCollections.observableArrayList(excs);
        excList.setItems(items);
        excList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ArrayList<String> excDescriptions = null;
                try {
                    excDescriptions = f.getExcursionDescription(newValue);
                    excDesc.setText("");
                    excDesc.setWrappingWidth(250);
                    for (int i = 0; i < excDescriptions.size(); i++){
                        excDesc.setText(excDesc.getText() + i + ". " +
                                excDescriptions.get(i) + "\n\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addNewVehicle(ActionEvent actionEvent) throws Exception{
        //Show new window;
        ExSystem.showAddVehicleView(log);
    }

    public void checkVehicleClicked(ActionEvent actionEvent) throws Exception {
        f.checkVehicle(log);
        vehicle.setText(f.getDriversVehicleInfo(log));
    }

    public void clickOnUpdate(ActionEvent actionEvent) throws Exception {
        ExSystem.showOfferView(log);
    }
}
