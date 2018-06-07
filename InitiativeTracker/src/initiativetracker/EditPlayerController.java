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
        
        button_finish.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{                  
                    Combatant editedPlayer = new Combatant
                            .Builder(textfield_name.getText())
                            .hp(Integer.parseInt(textfield_hp.getText()))
                            .dex(Integer.parseInt(textfield_dex.getText()))
                            .init(Integer.parseInt(textfield_init.getText()))
                            .build();;
                            
                    MainScreenController.fighterManager
                            .replacePlayers(combatant, editedPlayer);
                    
                    exit();
                }
                catch(NumberFormatException x){
                    //TODO: make specific wrong fields red
                }  
            }
        });
    }
    
    public void initNewPlayer(){
        label_title.setText("Create Player");
        button_finish.setText("Add Player");
        
        textfield_name.clear();
        textfield_hp.clear();
        textfield_dex.clear();
        textfield_init.clear();
        
        button_finish.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{                  
                    Combatant newPlayer = new Combatant
                            .Builder(textfield_name.getText())
                            .hp(Integer.parseInt(textfield_hp.getText()))
                            .dex(Integer.parseInt(textfield_dex.getText()))
                            .init(Integer.parseInt(textfield_init.getText()))
                            .build();
                            
                    MainScreenController.fighterManager.addPlayers(newPlayer);
                    
                    exit();
                }
                catch(NumberFormatException x){
                    //TODO: make specific wrong fields red
                }                
            }
        });
    }
    
    private void exit(){
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }
}
