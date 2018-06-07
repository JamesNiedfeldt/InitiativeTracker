package initiativetracker;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

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
    @FXML private MenuButton button_addcond;
    @FXML private Button button_removecond;
    @FXML private Button button_editplayer;
    @FXML private Button button_removeplayer;
    
    @FXML private Button button_nextturn;
    @FXML private Button button_addplayers;
    @FXML private Button button_sortplayers;
    
    public static FighterManager fighterManager;
    private Combatant selectedPlayer;
    private SimpleListProperty listProperty;
    private boolean noPlayers = true;
    
    private EditPlayerController editPlayerController;
    private FXMLLoader loader;
    private Parent root;
    private Scene scene;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fighterManager = new FighterManager();
        listProperty = new SimpleListProperty();
        
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditPlayerFXML.fxml"));
        try{
            root = loader.load();
            scene = new Scene(root);
        }
        catch(IOException ex){
            //TODO
        }
        
        setupLeft();
        setupRight();
        setupBottom();
    }  
    
    private void setupLeft(){
        column_currentturn.setCellValueFactory(new PropertyValueFactory<>("currentPlayerIndicator"));
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
                
                if(!noPlayers){
                    enableButtons(true);
                }
            }
            else{
                selectedPlayer = null;
                
                label_playername.setText("No player selected");
                label_hpvalue.setText("");
                listProperty.set(null);
                listview_conditions.itemsProperty().bind(listProperty);
                
                enableButtons(false);
            }
        });
        
        tableview_players.getSelectionModel().select(0);
    }
    
    private void setupRight(){     
        ObservableList<MenuItem> condMenu = FXCollections.observableArrayList();
        for(Condition condition : Condition.values()){
            condMenu.add(new MenuItem(condition.name));
        }
        button_addcond.getItems().addAll(condMenu);
        
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
        
        for(MenuItem item : button_addcond.getItems()){
            item.setOnAction(a->{
                if("Custom...".equals(item.getText())){
                    TextInputDialog dialog = new TextInputDialog("Condition");
                    dialog.setTitle("Custom Condition");
                    dialog.setHeaderText("Custom Condition");
                    dialog.setContentText("Enter condition name:");
                    
                    Optional<String> result = dialog.showAndWait();
                    if(result.isPresent() && !result.get().isEmpty()){
                        fighterManager.getConditionManager()
                                .player(selectedPlayer)
                                .getsCondition(result.get());
                    }
                }
                else{
                    fighterManager.getConditionManager()
                            .player(selectedPlayer)
                            .getsCondition(item.getText());
                    listview_conditions.refresh();
                }
            });
        }
        
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
                editPlayerController = loader.getController();
                editPlayerController.initEditPlayer(selectedPlayer);
                editPlayerController.setScene(scene);
                editPlayerController.showAndWait();
            }
        });
        
        button_removeplayer.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete Player");
                alert.setHeaderText(String.format(
                        "Are you sure you want to delete %s?", selectedPlayer.getName()));
                
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    fighterManager.killPlayers(selectedPlayer);
                    if(fighterManager.getPlayerNumber() <= 0){
                        noPlayers = true;
                        enableButtons(false);
                    }
                }
            }
        });
    }
    
    private void setupBottom(){
        button_nextturn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                if(fighterManager.getPlayerNumber() > 0){
                   fighterManager.nextTurn();
                    tableview_players.refresh(); 
                } 
            }
        });
        
        button_addplayers.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                editPlayerController = loader.getController();
                editPlayerController.initNewPlayer();
                editPlayerController.setScene(scene);
                editPlayerController.showAndWait();
                
                if(fighterManager.getPlayerNumber() > 0){
                    noPlayers = false;
                }
            }
        });
        
        button_sortplayers.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                if(fighterManager.getPlayerNumber() > 0){
                    fighterManager.sortPlayers();
                    tableview_players.refresh();
                }
            }
        });
    }
    
    private void enableButtons(boolean enable){
        if(enable){
            button_addcond.setDisable(false);
            button_removecond.setDisable(false);
            button_gethit.setDisable(false);
            button_heal.setDisable(false);
            button_editplayer.setDisable(false);
            button_removeplayer.setDisable(false);
        }
        else{
            button_addcond.setDisable(true);
            button_removecond.setDisable(true);
            button_gethit.setDisable(true);
            button_heal.setDisable(true);
            button_editplayer.setDisable(true);
            button_removeplayer.setDisable(true);
        }
    }
    
    private enum Condition{
        BLINDED("Blinded"),
        CHARMED("Charmed"),
        DEAFENED("Deafened"),
        FATIGUED("Fatigued"),
        FRIGHTENED("Frightened"),
        GRAPPLED("Grappled"),
        INCAPPED("Incapacitated"),
        INVISIBLE("Invisible"),
        PARALYZED("Paralyzed"),
        PETRIFIED("Petrified"),
        POISONED("Poisoned"),
        PRONE("Prone"),
        RESTRAINED("Restrained"),
        STUNNED("Stunned"),
        UNCONCIOUS("Unconcious"),
        CUSTOM("Custom...");
        
        private String name;
        Condition(String name){
            this.name = name;
        }
    }
}
