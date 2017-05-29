package gui.controllers;

import businesslogic.ExSystem;
import gui.facade.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Created by Danya on 29.05.2017.
 */
public class AddVehicleViewController {
    Facade f = ExSystem.facade;
    String login;

    @FXML private TextField model;
    @FXML private TextField mileage;
    @FXML private TextField capacity;
    @FXML private TextField numbers;

    @FXML private Text error;

    public void init(String login){
        this.login = login;
    }

    public void addVehicleButtonClicked(ActionEvent actionEvent) throws Exception{
        String mod = model.getText();
        String mil = mileage.getText();
        String cap = capacity.getText();
        String num = numbers.getText();

        int mi = Integer.parseInt(mil);
        int ca = Integer.parseInt(cap);
        if(num.length() != 8){
            error.setText("ERROR");
            return;
        }
        else{
            f.addVechicleToDriver(login, mod, mi, ca, num);
            //Return to view
            ExSystem.showDriverView(login);
        }
    }
}
