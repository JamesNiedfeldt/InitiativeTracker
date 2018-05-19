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
                Stage stage = (Stage) button_cancel.getScene().getWindow();
                stage.close();
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
    }
    
    public void initNewPlayer(){
        label_title.setText("Create Player");
        button_finish.setText("Add Player");
        
        textfield_name.clear();
        textfield_hp.clear();
        textfield_dex.clear();
        textfield_init.clear();
    }
}
