package gui.controllers;

import businesslogic.ExSystem;
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
public class UserViewController {
    Facade f = ExSystem.facade;

    @FXML private Text welcome;
    @FXML private Text excDesc;
    @FXML private Text myExcs;

    @FXML private ListView<String> excList;

    public void init(String login) throws Exception{
        log = login;
        welcome.setText("Welcome, " + login);
        ArrayList<String> excs = f.getExcursionList();
        ObservableList<String> items = FXCollections.observableArrayList(excs);
        if(items.size() != 0){
            excList.setItems(items);
            excList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ArrayList<String> excDescriptions = null;
                    try {
                        excDescriptions = f.getExcursionDescription(newValue);
                        excDesc.setText("");
                        excDesc.setWrappingWidth(250);
                        // excDesc.setText("Name: " + excDescriptions.get(0) + "\n");
                        for (int i = 1; i < excDescriptions.size(); i++){
                            if(i > 5)
                                excDesc.setText(excDesc.getText() + (i - 5) + ". " +
                                        excDescriptions.get(i) + "\n\n");
                            else
                                excDesc.setText(excDesc.getText() + excDescriptions.get(i) + "\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        ArrayList<String> userExcs = f.getUserExcursions(login);
        myExcs.setText("");
        int i = 1;
        for (String s : userExcs){
            myExcs.setText(myExcs.getText() + i + ". " + s + "\n");
            ++i;
        }
    }

    public void addToExcursion(ActionEvent actionEvent) throws Exception {
        String selected = excList.getSelectionModel().getSelectedItem();
        if (selected == null)
            return;
        int st = f.addUserToExcursion(log, selected);
        if(st != 0)
            ExSystem.showErrorView(st);
        init(log);
    }

    public void delFromExcursion(ActionEvent actionEvent) throws Exception {
        String selected = excList.getSelectionModel().getSelectedItem();
        if (selected == null)
            return;
        f.delUserFromExcursion(log, selected);
        init(log);
    }

    private String log;

    public void onUpdateClicked(ActionEvent actionEvent) throws Exception{
        init(log);
    }
}
