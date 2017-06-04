package gui.controllers;

import businesslogic.ExSystem;
import businesslogic.userfactory.Driver;
import gui.facade.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Created by Danya on 04.06.2017.
 */
public class OfferViewController {
    Facade f = ExSystem.facade;
    String log;

    @FXML private Text text;
    @FXML private Button yesButton;
    @FXML private Button noButton;
    @FXML private Button okButton;

    public void init(String login) throws Exception {
        log = login;
        text.setText(f.getDriverOffers(login));
        String te = text.getText();
        if (te.contains("No offers")) {
            yesButton.setVisible(false);
            noButton.setVisible(false);
        }
        else
            okButton.setVisible(false);
    }


    public void yesButtonClicked(ActionEvent actionEvent) throws Exception {
        f.setDriverAgree(log);
        ExSystem.showDriverView(log);
    }

    public void noButtonClicked(ActionEvent actionEvent) throws Exception {
        f.setDriverdisagree(log);
        ExSystem.showDriverView(log);
    }

    public void okButtonClicked(ActionEvent actionEvent) throws Exception {
        ExSystem.showDriverView(log);
    }
}
