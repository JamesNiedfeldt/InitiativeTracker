package initiativetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BulkPlayerAddController extends Stage implements Initializable {

    @FXML private TextField textfield_name;
    @FXML private TextField textfield_hprolls;
    @FXML private TextField textfield_hpbase;
    @FXML private TextField textfield_dex;
    @FXML private TextField textfield_enemynum;
    @FXML private ChoiceBox dropdown_die;
    @FXML private Button button_finish;
    @FXML private Button button_cancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Die> dice = FXCollections.observableArrayList();
        for(Die die : Die.values()){
            dice.add(die);
        }
        dropdown_die.getItems().addAll(dice);
        
        this.initModality(Modality.APPLICATION_MODAL);
        dropdown_die.getSelectionModel().select(0);
        
        button_finish.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent e){
               try{
                   textfield_name.setStyle("-fx-border-color: NULL;");
                   textfield_hprolls.setStyle("-fx-border-color: NULL;");
                   textfield_hpbase.setStyle("-fx-border-color: NULL;");
                   textfield_dex.setStyle("-fx-border-color: NULL;");
                   textfield_enemynum.setStyle("-fx-border-color: NULL;");
               }
               catch(Exception x){
                   
               }
               
               finish();
           } 
        });
        
        button_cancel.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                exit();
            }
        });
    }    
    
    public void clear(){
        textfield_name.setStyle("-fx-border-color: NULL;");
        textfield_hprolls.setStyle("-fx-border-color: NULL;");
        textfield_hpbase.setStyle("-fx-border-color: NULL;");
        textfield_dex.setStyle("-fx-border-color: NULL;");
        textfield_enemynum.setStyle("-fx-border-color: NULL;");
        textfield_name.clear();
        textfield_hprolls.clear();
        textfield_hpbase.clear();
        textfield_dex.clear();
        textfield_enemynum.clear();
    }
    
    private void exit(){
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }
    
    private void finish(){
        int rollNum = Integer.parseInt(textfield_hprolls.getText());
        //This seems really dirty. Fix?
        int dieMax = ((Die)dropdown_die.getSelectionModel().getSelectedItem()).sides;
        int baseHp = Integer.parseInt(textfield_hpbase.getText());
        int enemyNum = Integer.parseInt(textfield_enemynum.getText());
        int dex = Integer.parseInt(textfield_dex.getText());
        int hitPoints;
        int init;
        String name;
        Combatant enemy;
        
        for(int i = 0; i < enemyNum; i++){
            name = textfield_name.getText() + " " + (i + 1);
            hitPoints = 0;
            init = 0;
            for(int j = 0; j < rollNum; j++){
                hitPoints += (Math.random() * (dieMax - 1)) + 1;
            }
            hitPoints += baseHp;
            init += ((Math.random() * 19) + 1) + dex;
            
            enemy = new Combatant.Builder(name).dex(Integer.parseInt(textfield_dex.getText()))
                    .hp(hitPoints)
                    .init(init)
                    .build();
            
            MainScreenController.fighterManager.addPlayers(enemy);
        }
        
        exit();
    }
    
    private enum Die{
        d4("d4", 4),
        d6("d6", 6),
        d8("d8", 8),
        d10("d10", 10),
        d12("d12", 12),
        d20("d20", 20);
        
        private String name;
        private int sides;
        
        Die(String name, int sides){
            this.name = name;
            this.sides = sides;
        }
    }
}
