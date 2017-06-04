package gui.controllers;

import businesslogic.ErrorCodes;
import businesslogic.ExSystem;
import gui.facade.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Danya on 04.06.2017.
 */
public class ErrorViewController {
    Facade f = ExSystem.facade;
    Stage st;
    @FXML private Text error;

    public void init(int errorCode, Stage stage){
        st = stage;
        error.setText(ErrorCodes.getError(errorCode));
    }

    public void onOkClicked(ActionEvent actionEvent) {
        st.close();
    }

}
