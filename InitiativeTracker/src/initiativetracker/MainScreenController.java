package initiativetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainScreenController implements Initializable {
    
    @FXML private TableView tableview_players;
    @FXML private TableColumn column_name;
    @FXML private TableColumn column_hp;
    
    @FXML private Label label_playername;
    @FXML private Label label_hpvalue;
    @FXML private TextField textfield_changehp;
    @FXML private Button button_gethit;
    @FXML private Button button_heal;
    
    private FighterManager fighterManager;
    private Combatant selectedPlayer;
    
    //These are for testing
    private Combatant tempGuy1, tempGuy2, tempGuy3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fighterManager = new FighterManager();
        //For testing
        tempGuy1 = new Combatant.Builder("Phoenix").hp(50).init(12).build();
        tempGuy2 = new Combatant.Builder("Apollo").hp(50).init(13).build();
        tempGuy3 = new Combatant.Builder("Athena").hp(50).init(11).build();
        
        fighterManager.addPlayers(tempGuy1, tempGuy2, tempGuy3);
        //End testing
        
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_hp.setCellValueFactory(new PropertyValueFactory<>("hitPoints"));
        
        tableview_players.setItems(fighterManager.players);
        tableview_players.getColumns().clear();
        tableview_players.getColumns().addAll(column_name,column_hp);
              
        tableview_players.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                selectedPlayer = (Combatant)tableview_players.getSelectionModel().selectedItemProperty().get();
                
                label_playername.setText(selectedPlayer.getName());
                label_hpvalue.setText(String.valueOf(selectedPlayer.getHitPoints()));
            }
        });
        
        button_gethit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                int damage = Integer.parseInt(textfield_changehp.getText());
                
                fighterManager.hpManager
                        .player(selectedPlayer)
                        .getsHitFor(damage);
                
                label_hpvalue
                        .setText(String.valueOf(selectedPlayer.getHitPoints()));
                tableview_players.refresh();
                
                }
                catch(Exception x){
                    //TODO: something useful here
                    System.out.println("Problem");
                }
            }
        });
        
        button_heal.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                try{
                int damage = Integer.parseInt(textfield_changehp.getText());
                
                fighterManager.hpManager
                        .player(selectedPlayer)
                        .healsFor(damage);
                
                label_hpvalue
                        .setText(String.valueOf(selectedPlayer.getHitPoints()));
                tableview_players.refresh();
                
                }
                catch(Exception x){
                    //TODO: something useful here
                    System.out.println("Problem");
                }
            }
        });
        
        tableview_players.getSelectionModel().select(0);
    }        
}
