package InitiativeTracker.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import InitiativeTracker.classes.Combatant;
import InitiativeTracker.services.FighterManager;

public class EditPlayerController extends Stage implements Initializable {

    @FXML private Label label_title;
    @FXML private TextField textfield_name;
    @FXML private TextField textfield_currenthp;
    @FXML private TextField textfield_maxhp;
    @FXML private TextField textfield_temphp;
    @FXML private TextField textfield_ac;
    @FXML private TextField textfield_dex;
    @FXML private TextField textfield_init;
    @FXML private Button button_finish;
    @FXML private Button button_cancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initModality(Modality.APPLICATION_MODAL);
        
        button_cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                exit();
            }
        });
    }   
    
    public void initEditPlayer(Combatant combatant) {
        label_title.setText("Edit Player");
        button_finish.setText("Finish");
        
        textfield_name.setText(combatant.getName());
        textfield_currenthp.setText(Integer.toString(combatant.getCurrentHp()));
        textfield_maxhp.setText(Integer.toString(combatant.getMaxHp()));
        textfield_temphp.setText(Integer.toString(combatant.getTempHp()));
        textfield_ac.setText(Integer.toString(combatant.getArmorClass()));
        textfield_dex.setText(Integer.toString(combatant.getDexterity()));
        textfield_init.setText(Integer.toString(combatant.getInitiative()));
        clearFieldStyles();
        
        textfield_name.requestFocus();
        
        button_finish.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                
                try {                   
                    clearFieldStyles();
                    checkFields();
                    
                    Combatant editedPlayer = new Combatant
                            .Builder(textfield_name.getText())
                            .currentHp(Integer.parseInt(textfield_currenthp.getText()))
                            .maxHp(Integer.parseInt(textfield_maxhp.getText()))
                            .tempHp(Integer.parseInt(textfield_temphp.getText()))
                            .ac(Integer.parseInt(textfield_ac.getText()))
                            .dex(Integer.parseInt(textfield_dex.getText()))
                            .init(Integer.parseInt(textfield_init.getText()))
                            .build();
                            
                    FighterManager.getInstance()
                            .replacePlayers(combatant, editedPlayer);
                    
                    exit();
                } catch(Exception x) {

                }  
            }
        });
    }
    
    public void initNewPlayer() {
        label_title.setText("Add Player");
        button_finish.setText("Add Player");
        
        textfield_name.clear();
        textfield_currenthp.clear();
        textfield_maxhp.clear();
        textfield_temphp.setText("0");
        textfield_ac.clear();
        textfield_dex.clear();
        textfield_init.clear();
        clearFieldStyles();
        
        textfield_name.requestFocus();
        
        button_finish.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try{     
                    textfield_name.setStyle("");
                    textfield_currenthp.setStyle("");
                    textfield_maxhp.setStyle("");
                    textfield_temphp.setStyle("");
                    textfield_ac.setStyle("");
                    textfield_dex.setStyle("");
                    textfield_init.setStyle("");
                    checkFields();
                    Combatant newPlayer = new Combatant
                            .Builder(textfield_name.getText())
                            .currentHp(Integer.parseInt(textfield_currenthp.getText()))
                            .maxHp(Integer.parseInt(textfield_maxhp.getText()))
                            .tempHp(Integer.parseInt(textfield_temphp.getText()))
                            .ac(Integer.parseInt(textfield_ac.getText()))
                            .dex(Integer.parseInt(textfield_dex.getText()))
                            .init(Integer.parseInt(textfield_init.getText()))
                            .build();
                            
                    FighterManager.getInstance().addPlayers(newPlayer);
                    
                    exit();
                } catch(Exception x) {
                    
                }                
            }
        });
    }
    
    private void exit() {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }
    
    private void clearFieldStyles() {
        textfield_name.setStyle("");
        textfield_currenthp.setStyle("");
        textfield_maxhp.setStyle("");
        textfield_temphp.setStyle("");
        textfield_ac.setStyle("");
        textfield_dex.setStyle("");
        textfield_init.setStyle("");
    }
    
    private void checkFields() {
        boolean foundExc = false;
        
        try{
            if (textfield_name.getText().isEmpty()) {
                textfield_name.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            if (textfield_currenthp.getText().isEmpty()) {
                textfield_currenthp.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            if (textfield_maxhp.getText().isEmpty()) {
                textfield_maxhp.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            if (textfield_temphp.getText().isEmpty()) {
                textfield_temphp.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            if (textfield_ac.getText().isEmpty()) {
                textfield_ac.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            if (textfield_dex.getText().isEmpty()) {
                textfield_dex.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            if (textfield_init.getText().isEmpty()) {
                textfield_init.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            
            
            try {
                Integer.parseInt(textfield_currenthp.getText());
            } catch(NumberFormatException x) {
                textfield_currenthp.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            try {
                Integer.parseInt(textfield_maxhp.getText());
            } catch(NumberFormatException x) {
                textfield_maxhp.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            try {
                if(Integer.parseInt(textfield_temphp.getText()) < 0){
                    textfield_temphp.setStyle("-fx-border-color: red;");
                    foundExc = true;
                };
            } catch(NumberFormatException x) {
                textfield_temphp.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            try {
                Integer.parseInt(textfield_ac.getText());
            } catch(NumberFormatException x) {
                textfield_ac.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            try {
                Integer.parseInt(textfield_dex.getText());
            } catch(NumberFormatException x) {
                textfield_dex.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            try {
                Integer.parseInt(textfield_init.getText());
            } catch(NumberFormatException x) {
                textfield_init.setStyle("-fx-border-color: red;");
                foundExc = true;
            }
            
            if(foundExc) {
                throw new RuntimeException();
            }
        } catch(Exception x) {
            throw x;
        }
    }
}
