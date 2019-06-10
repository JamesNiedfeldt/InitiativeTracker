package initiativetracker;

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

public class EditPlayerController extends Stage implements Initializable {

    @FXML private Label label_title;
    @FXML private TextField textfield_name;
    @FXML private TextField textfield_hp;
    @FXML private TextField textfield_dex;
    @FXML private TextField textfield_init;
    @FXML private Button button_finish;
    @FXML private Button button_cancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initModality(Modality.APPLICATION_MODAL);
        
        button_cancel.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                exit();
            }
        });
    }   
    
    public void initEditPlayer(Combatant combatant){
        label_title.setText("Edit Player");
        button_finish.setText("Finish");
        
        textfield_name.setText(combatant.getName());
        textfield_hp.setText(Integer.toString(combatant.getHitPoints()));
        textfield_dex.setText(Integer.toString(combatant.getDexterity()));
        textfield_init.setText(Integer.toString(combatant.getInitiative()));
        textfield_name.setStyle("-fx-border-color: NULL;");
        textfield_hp.setStyle("-fx-border-color: NULL;");
        textfield_dex.setStyle("-fx-border-color: NULL;");
        textfield_init.setStyle("-fx-border-color: NULL;");
        
        textfield_name.requestFocus();
        
        button_finish.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                
                try{                   
                    textfield_name.setStyle("-fx-border-color: NULL;");
                    textfield_hp.setStyle("-fx-border-color: NULL;");
                    textfield_dex.setStyle("-fx-border-color: NULL;");
                    textfield_init.setStyle("-fx-border-color: NULL;");
                    checkFields();
                    
                    Combatant editedPlayer = new Combatant
                            .Builder(textfield_name.getText())
                            .hp(Integer.parseInt(textfield_hp.getText()))
                            .dex(Integer.parseInt(textfield_dex.getText()))
                            .init(Integer.parseInt(textfield_init.getText()))
                            .build();
                            
                    MainScreenController.fighterManager
                            .replacePlayers(combatant, editedPlayer);
                    
                    exit();
                }
                catch(Exception x){

                }  
            }
        });
    }
    
    public void initNewPlayer(){
        label_title.setText("Add Player");
        button_finish.setText("Add Player");
        
        textfield_name.clear();
        textfield_hp.clear();
        textfield_dex.clear();
        textfield_init.clear();
        textfield_name.setStyle("-fx-border-color: NULL;");
        textfield_hp.setStyle("-fx-border-color: NULL;");
        textfield_dex.setStyle("-fx-border-color: NULL;");
        textfield_init.setStyle("-fx-border-color: NULL;");
        
        textfield_name.requestFocus();
        
        button_finish.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{     
                    textfield_name.setStyle("-fx-border-color: NULL;");
                    textfield_hp.setStyle("-fx-border-color: NULL;");
                    textfield_dex.setStyle("-fx-border-color: NULL;");
                    textfield_init.setStyle("-fx-border-color: NULL;");
                    checkFields();
                    Combatant newPlayer = new Combatant
                            .Builder(textfield_name.getText())
                            .hp(Integer.parseInt(textfield_hp.getText()))
                            .dex(Integer.parseInt(textfield_dex.getText()))
                            .init(Integer.parseInt(textfield_init.getText()))
                            .build();
                            
                    MainScreenController.fighterManager.addPlayers(newPlayer);
                    
                    exit();
                }
                catch(Exception x){
                    
                }                
            }
        });
    }
    
    private void exit(){
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }
    
    private void checkFields(){
        boolean foundExc = false;
        
        try{
            if(textfield_name.getText().isEmpty()){
                textfield_name.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(textfield_hp.getText().isEmpty()){
                textfield_hp.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(textfield_dex.getText().isEmpty()){
                textfield_dex.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(textfield_init.getText().isEmpty()){
                textfield_init.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            
            
            try{
                Integer.parseInt(textfield_hp.getText());
            }
            catch(NumberFormatException x){
                textfield_hp.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            try{
                Integer.parseInt(textfield_dex.getText());
            }
            catch(NumberFormatException x){
                textfield_dex.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            try{
                Integer.parseInt(textfield_init.getText());
            }
            catch(NumberFormatException x){
                textfield_init.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            
            if(foundExc){
                throw new RuntimeException();
            }
        }
        catch(Exception x){
            throw x;
        }
    }
}
