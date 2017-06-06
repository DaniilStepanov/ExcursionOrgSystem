package gui.controllers;

import businesslogic.ExSystem;
import gui.facade.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

/**
 * Created by Danya on 05.06.2017.
 */
public class CreateExcursionViewController {
    Facade f = ExSystem.facade;
    String login;

    @FXML private TextField name;
    @FXML private TextField minToursts;
    @FXML private TextField maxTourists;
    @FXML private TextField equipment;
    @FXML private DatePicker departureDate;

    @FXML private Text error;

    public void init(String login){
        this.login = login;
    }

    public void addExcursionButtonClicked(ActionEvent actionEvent) throws Exception{
        String n = name.getText();
        String min = minToursts.getText();
        String max = maxTourists.getText();
        String cap = equipment.getText();
        LocalDate d = departureDate.getValue();
        System.out.println(d);
        int st = f.addExcursion(login, n, min, max, cap, d);
        if (st != 0)
            ExSystem.showErrorView(st);
        else
            ExSystem.showOrganizatorView(login);
    }
}
