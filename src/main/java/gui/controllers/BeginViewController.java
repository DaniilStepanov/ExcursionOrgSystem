package gui.controllers;

import businesslogic.ExSystem;
import gui.facade.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * Created by Danya on 28.05.2017.
 */
public class BeginViewController {

    Facade f = ExSystem.facade;

    @FXML private Button signIn;
    @FXML private Button signUp;
    @FXML private TextField loginField;
    @FXML private TextField passField;

    @FXML private RadioButton asUser;
    @FXML private RadioButton asDriver;
    @FXML private RadioButton asOrg;

    @FXML private void onClickButton(){
        signIn.setText("Thanks");
    }

    @FXML private void onSignUpClicked() throws Exception{
        if (asUser.isSelected()){
            f.addUser(loginField.getText(), 100);
            // Show user window
        }
        else if (asDriver.isSelected()){
            f.addUser(loginField.getText(), 100);
        }
        else if (asOrg.isSelected()) {
            f.addUser(loginField.getText(), 100);
        }
        //Sign up
    }

}
