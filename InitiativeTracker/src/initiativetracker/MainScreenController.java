package initiativetracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainScreenController implements Initializable {
    
    @FXML private TableView tableview_players;
    @FXML private TableColumn column_name;
    @FXML private TableColumn column_hp;
    @FXML private TableColumn column_currentturn;
    
    @FXML private Label label_playername;
    @FXML private Label label_hpvalue;
    @FXML private TextField textfield_changehp;
    @FXML private Button button_gethit;
    @FXML private Button button_heal;
    @FXML private ListView<String> listview_conditions;
    @FXML private Button button_addcond;
    @FXML private Button button_removecond;
    @FXML private Button button_editplayer;
    @FXML private Button button_removeplayer;
    
    @FXML private Button button_nextturn;
    @FXML private Button button_addplayers;
    
    private FighterManager fighterManager;
    private Combatant selectedPlayer;
    private SimpleListProperty listProperty;
    
    //These are for testing
    private Combatant tempGuy1, tempGuy2, tempGuy3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fighterManager = new FighterManager();
        listProperty = new SimpleListProperty();
        //For testing
        tempGuy1 = new Combatant.Builder("Phoenix").hp(50).init(12).build();
        tempGuy2 = new Combatant.Builder("Apollo").hp(50).init(13).build();
        tempGuy3 = new Combatant.Builder("Athena").hp(50).init(11).build();
        
        fighterManager.addPlayers(tempGuy1, tempGuy2, tempGuy3);
        fighterManager.sortPlayers();
        //End testing
        
        column_currentturn.setCellValueFactory(new PropertyValueFactory<>("isCurrentPlayer"));
        column_currentturn.setSortable(false);
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_name.setSortable(false);
        column_hp.setCellValueFactory(new PropertyValueFactory<>("hitPoints"));
        column_hp.setSortable(false);
        
        fighterManager.addToTable(tableview_players);
        tableview_players.getColumns().clear();
        tableview_players.getColumns().addAll(column_currentturn,column_name,column_hp);
              
        tableview_players.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                selectedPlayer = (Combatant)tableview_players.getSelectionModel().selectedItemProperty().get();
                
                label_playername.setText(selectedPlayer.getName());
                label_hpvalue.setText(String.valueOf(selectedPlayer.getHitPoints()));
                listProperty.set(selectedPlayer.getConditions());
                listview_conditions.itemsProperty().bind(listProperty);
            }
        });
        
        button_gethit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                textfield_changehp.setStyle("-fx-border-color: NULL;");
                try{
                    int damage = Integer.parseInt(textfield_changehp.getText());
                    
                    if(damage < 0){
                        throw new NumberFormatException();
                    }

                    fighterManager.getHpManager()
                            .player(selectedPlayer)
                            .getsHitFor(damage);

                    label_hpvalue
                            .setText(String.valueOf(selectedPlayer.getHitPoints()));
                    tableview_players.refresh();
                }
                catch(NumberFormatException x){
                    //TODO: something useful here
                    textfield_changehp.setStyle("-fx-border-color: RED");
                    System.out.println("Problem");
                }
            }
        });
        
        button_heal.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                textfield_changehp.setStyle("-fx-border-color: NULL;");
                try{
                    int damage = Integer.parseInt(textfield_changehp.getText());

                    if(damage < 0){
                        throw new NumberFormatException();
                    }

                    fighterManager.getHpManager()
                            .player(selectedPlayer)
                            .healsFor(damage);

                    label_hpvalue
                            .setText(String.valueOf(selectedPlayer.getHitPoints()));
                    tableview_players.refresh();
                }
                catch(NumberFormatException x){
                    //TODO: something useful here
                    textfield_changehp.setStyle("-fx-border-color: RED");
                    System.out.println("Problem");
                }
            }
        });
        
        button_addcond.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                fighterManager.getConditionManager()
                        .player(selectedPlayer)
                        .getsCondition("Poison");
                listview_conditions.refresh();
            }
        });
        
        button_removecond.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                String condition = listview_conditions.getSelectionModel().selectedItemProperty().get();
                fighterManager.getConditionManager()
                        .player(selectedPlayer)
                        .recoversCondition(condition);
                listview_conditions.refresh();
            }
        });
        
        button_editplayer.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                //TODO
            }
        });
        
        button_removeplayer.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                //TODO: Add a confirm box
                fighterManager.killPlayers(selectedPlayer);
            }
        });
        
        button_nextturn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                fighterManager.nextTurn();
                tableview_players.refresh();
            }
        });
        
        tableview_players.getSelectionModel().select(0);
    }        
}
