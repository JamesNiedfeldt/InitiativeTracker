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
                   checkFields();
                   finish();
               }
               catch(Exception x){
                   
               }
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
        int baseHp;
        int enemyNum = Integer.parseInt(textfield_enemynum.getText());
        int dex = Integer.parseInt(textfield_dex.getText());
        int hitPoints;
        int init;
        String name;
        Combatant enemy;
        
        if(textfield_hpbase.getText().isEmpty()){
            baseHp = 0;
        }
        else{
            baseHp = Integer.parseInt(textfield_hpbase.getText());
        }

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
    
    private void checkFields(){
        boolean foundExc = false;
        
        try{
            if(textfield_name.getText().isEmpty()){
                textfield_name.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(textfield_hprolls.getText().isEmpty()){
                textfield_hprolls.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(textfield_dex.getText().isEmpty()){
                textfield_dex.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(textfield_enemynum.getText().isEmpty()){
                textfield_enemynum.setStyle("-fx-border-color: RED");
            }
            
            
            try{
                Integer.parseInt(textfield_hprolls.getText());
            }
            catch(NumberFormatException x){
                textfield_hprolls.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(!textfield_hpbase.getText().isEmpty()){
                try{
                    Integer.parseInt(textfield_hpbase.getText());
                }
                catch(NumberFormatException x){
                    textfield_hpbase.setStyle("-fx-border-color: RED;");
                }
            }
            
            try{
                Integer.parseInt(textfield_dex.getText());
            }
            catch(NumberFormatException x){
                textfield_dex.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            try{
                Integer.parseInt(textfield_enemynum.getText());
            }
            catch(NumberFormatException x){
                textfield_enemynum.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            
            if(Integer.parseInt(textfield_hprolls.getText()) <= 0){
                textfield_hprolls.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(!textfield_hpbase.getText().isEmpty() && 
                    Integer.parseInt(textfield_hpbase.getText()) < 0){
                textfield_hpbase.setStyle("-fx-border-color: RED;");
                foundExc = true;
            }
            if(Integer.parseInt(textfield_enemynum.getText()) <= 0){
                textfield_enemynum.setStyle("-fx-border-color: RED;");
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
