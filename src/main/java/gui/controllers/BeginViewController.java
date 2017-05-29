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
    @FXML private Text error;

    @FXML private RadioButton asUser;
    @FXML private RadioButton asDriver;
    @FXML private RadioButton asOrg;

    @FXML private void onClickButton() throws Exception{
        if (f.authenticate(loginField.getText(), passField.getText())){
            error.setText("");
            if(f.isDriver(loginField.getText())){
                signIn.setText("Driver");
                ExSystem.showDriverView(loginField.getText());
                //Show driver view
            }
            else if (f.isOrg(loginField.getText())){
                signIn.setText("Org");
                ExSystem.showOrganizatorView(loginField.getText());
                //Show org view
            }
            else {
                signIn.setText("User");
                // Show user window
                ExSystem.showUserView(loginField.getText());
            }
        }
        else {
            error.setText("Incorrect user or password");
        }
    }

    @FXML private void onSignUpClicked() throws Exception{
        if (asUser.isSelected()){
            f.addUser(loginField.getText(), 100);
            ExSystem.showUserView(loginField.getText());
        }
        else if (asDriver.isSelected()){
            f.addDriver(loginField.getText(), 100);
            ExSystem.showDriverView(loginField.getText());
        }
        else if (asOrg.isSelected()) {
            f.addOrg(loginField.getText(), 100);
            ExSystem.showOrganizatorView(loginField.getText());
        }
        //Sign up
    }

}
