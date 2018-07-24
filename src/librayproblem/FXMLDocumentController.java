/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librayproblem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Nijes
 */
public class FXMLDocumentController implements Initializable {

    private hold Holder;

    @FXML
    private Label label;
    @FXML
    private TextField name;
    @FXML
    private TextField callnumber;
    @FXML
    private TextArea outputPane;
    @FXML
    private TextArea outputPaneFirst;
    @FXML
    private TextField checkincallnumber;

    @FXML
    private void addHold(ActionEvent event) {

        Holder = new hold();
        String patronName = name.getText();
        int addCallNumber = Integer.parseInt(callnumber.getText());
        int iditem = Holder.IDItem(addCallNumber);
        int idpatron = Holder.IDPatron(patronName);
        
        if (Holder.getHoldCount(idpatron) <= 10) {
            Holder.addHold(iditem, idpatron);
        } else {
            outputPaneFirst.setText("Unable to take your Hold request!");
        }

    }

    @FXML
    private void checkin(ActionEvent event) {

        int checkincallnumberinput = Integer.parseInt(checkincallnumber.getText());
        int iditeminput = Holder.IDItem(checkincallnumberinput);
        Holder.checkinholdinfo(iditeminput);
        outputPane.setText(Holder.checkinholdinfo(iditeminput));
    }
    
    @FXML
    private void deleteOldestHold(ActionEvent event){
        Holder.deleteOldestHold();
    }

    @FXML
    private void terminateHold(ActionEvent event) {

        String patronName = name.getText();
        int addCallNumber = Integer.parseInt(callnumber.getText());
        int iditem = Holder.IDItem(addCallNumber);
        int idpatron = Holder.IDPatron(patronName);
        Holder.terminatingHold(iditem, idpatron);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("");
        label.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Holder = new hold();
        
    }

}
