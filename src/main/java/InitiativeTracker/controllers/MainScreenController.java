package InitiativeTracker.controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import InitiativeTracker.classes.Combatant;
import InitiativeTracker.services.FighterManager;
import InitiativeTracker.services.FileManager;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.TextAlignment;

public class MainScreenController implements Initializable {
    
    @FXML private Button button_save;
    @FXML private Button button_load;
    
    @FXML private TableView tableview_players;
    @FXML private TableColumn column_name;
    @FXML private TableColumn column_hp;
    @FXML private Button button_sortplayers;
    @FXML private Button button_nextturn;
    
    @FXML private Label label_playername;
    @FXML private Label label_hpvalue;
    @FXML private ProgressBar bar_hp;
    @FXML private TextField textfield_changehp;
    @FXML private Label label_acvalue;
    @FXML private Button button_gethit;
    @FXML private Button button_heal;
    @FXML private ListView<String> listview_conditions;
    @FXML private MenuButton button_addcond;
    @FXML private Button button_removecond;
    @FXML private Button button_editplayer;
    @FXML private Button button_removeplayer;
    @FXML private CheckBox checkbox_save;
    @FXML private Label label_message;
  
    @FXML private Button button_addplayers;
    @FXML private Button button_addenemies;
    @FXML private Button button_deleteall;
    
    private Combatant selectedPlayer;
    private SimpleListProperty listProperty;
    
    private EditPlayerController editPlayerController;
    private BulkPlayerAddController bulkPlayerAddController;
    private FXMLLoader editPlayerLoader;
    private FXMLLoader bulkAddLoader;
    private Parent editPlayerRoot;
    private Parent bulkAddRoot;
    private Scene editPlayerScene;
    private Scene bulkAddScene;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listProperty = new SimpleListProperty();
        
        editPlayerLoader = new FXMLLoader();
        bulkAddLoader = new FXMLLoader();
        editPlayerLoader.setLocation(getClass().getResource("/fxml/EditPlayerFXML.fxml"));
        bulkAddLoader.setLocation(getClass().getResource("/fxml/BulkPlayerAddFXML.fxml"));
        try {
            editPlayerRoot = editPlayerLoader.load();
            editPlayerScene = new Scene(editPlayerRoot);
            bulkAddRoot = bulkAddLoader.load();
            bulkAddScene = new Scene(bulkAddRoot);
        } catch(IOException e) {
            //TODO
        }
        
        disableButtons(true);
        
        setupTop();
        setupLeft();
        setupRight();
        setupBottom();
    }  
    
    private void setupTop() {
        button_save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                FileManager.getInstance().promptSave();
            }
        });
        
        button_load.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                FileManager.getInstance().promptLoad();
            }
        });
    }
    
    private void setupLeft() {
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        column_name.setSortable(false);
        column_hp.setCellValueFactory(new PropertyValueFactory<>("totalHp"));
        column_hp.setSortable(false);
        
        FighterManager.getInstance().addToTable(tableview_players);
        tableview_players.getColumns().clear();
        tableview_players.getColumns().addAll(column_name,column_hp);
              
        tableview_players.getSelectionModel().selectedItemProperty().addListener((newSelection) -> {
            label_message.setText("");
            selectedPlayer = (Combatant)tableview_players
                    .getSelectionModel().getSelectedItem();
            
            if (selectedPlayer != null) {
                label_playername.setText(selectedPlayer.getName());
                label_hpvalue.setText(String.valueOf(selectedPlayer.getTotalHp()
                    + " / " + String.valueOf(selectedPlayer.getMaxHp())));
                bar_hp.setProgress((double) 
                    selectedPlayer.getTotalHp() / selectedPlayer.getMaxHp());
                label_acvalue.setText(String.valueOf(selectedPlayer.getArmorClass()));
                listProperty.set(selectedPlayer.getConditions());
                listview_conditions.itemsProperty().bind(listProperty);
                checkbox_save.setSelected(selectedPlayer.isSaved());
                
                disableButtons(false);
            } else {
                label_playername.setText("No player selected");
                label_hpvalue.setText("");
                label_acvalue.setText("");
                listProperty.set(null);
                listview_conditions.itemsProperty().bind(listProperty);
                
                disableButtons(true);
            }
        });
        
        button_nextturn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                if (FighterManager.getInstance().getPlayerCount() > 0) {
                   textfield_changehp.clear();
                   bar_hp.setProgress(0);
                   FighterManager.getInstance().nextTurn();
                   tableview_players.refresh();
                   tableview_players.getSelectionModel().select(0);
                } 
            }
        });
        
        button_sortplayers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                if (FighterManager.getInstance().getPlayerCount() > 0) {
                    FighterManager.getInstance().sortPlayers();
                    tableview_players.refresh();
                    tableview_players.getSelectionModel().select(0);
                }
            }
        });
        
        tableview_players.getSelectionModel().select(0);
    }
    
    private void setupRight() {     
        ObservableList<MenuItem> condMenu = FXCollections.observableArrayList();
        for (Condition condition : Condition.values()) {
            condMenu.add(new MenuItem(condition.name));
        }
        button_addcond.getItems().addAll(condMenu);
        
        label_hpvalue.setAlignment(Pos.CENTER);
        bar_hp.setStyle("-fx-accent: GREEN;"
                + "-fx-control-inner-background: BLACK;");
        
        button_gethit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                textfield_changehp.setStyle("-fx-border-color: NULL;");
                try {
                    int damage = Integer.parseInt(textfield_changehp.getText());
                    
                    if (damage < 0) {
                        throw new NumberFormatException();
                    }

                    selectedPlayer.takeDamage(damage);

                    label_hpvalue
                            .setText(String.valueOf(selectedPlayer.getTotalHp()
                             + " / " + String.valueOf(selectedPlayer.getMaxHp())));
                    bar_hp.setProgress((double) 
                            selectedPlayer.getTotalHp() / selectedPlayer.getMaxHp());
                    tableview_players.refresh();
                } catch(NumberFormatException x) {
                    label_message.setText("Improper damage value.");
                    textfield_changehp.setStyle("-fx-border-color: RED;");
                }
            }
        });
        
        button_heal.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                textfield_changehp.setStyle("-fx-border-color: NULL;");
                try {
                    int damage = Integer.parseInt(textfield_changehp.getText());

                    if (damage < 0) {
                        throw new NumberFormatException();
                    }
                    
                    selectedPlayer.heal(damage);

                    label_hpvalue
                            .setText(String.valueOf(selectedPlayer.getTotalHp()
                             + " / " + String.valueOf(selectedPlayer.getMaxHp())));
                    bar_hp.setProgress((double) 
                            selectedPlayer.getTotalHp() / selectedPlayer.getMaxHp());
                    tableview_players.refresh();
                } catch(NumberFormatException x) {
                    label_message.setText("Improper damage value.");
                    textfield_changehp.setStyle("-fx-border-color: RED;");
                }
            }
        });
        
        for (MenuItem item : button_addcond.getItems()) {
            item.setOnAction(a-> {
                clearMessage();
                
                try {
                    if ("Custom...".equals(item.getText())) {
                        TextInputDialog dialog = new TextInputDialog("Condition");
                        dialog.setTitle("Custom Condition");
                        dialog.setHeaderText("Custom Condition");
                        dialog.setContentText("Enter condition name:");

                        Optional<String> result = dialog.showAndWait();
                        if(result.isPresent() && !result.get().isEmpty()){
                            selectedPlayer.addCondition(result.get());
                        }
                    } else {
                        selectedPlayer.addCondition(item.getText());
                        listview_conditions.refresh();
                    }
                } catch(RuntimeException e) {
                    label_message.setText(e.getMessage());
                }
            });
        }
        
        button_removecond.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                String condition = listview_conditions.getSelectionModel().selectedItemProperty().get();
                selectedPlayer.removeCondition(condition);
                listview_conditions.refresh();
            }
        });
        
        button_editplayer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                editPlayerController = editPlayerLoader.getController();
                editPlayerController.initEditPlayer(selectedPlayer);
                editPlayerController.setScene(editPlayerScene);
                editPlayerController.showAndWait();
            }
        });
        
        button_removeplayer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                if (selectedPlayer.isSaved()) {
                    label_message.setText(selectedPlayer.getName() 
                            + " is set to not be deleted.");
                } else {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Delete Player");
                    alert.setHeaderText(String.format(
                            "Are you sure you want to delete %s?", selectedPlayer.getName()));

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        FighterManager.getInstance().killPlayers(selectedPlayer);
                        if (FighterManager.getInstance().getPlayerCount() <= 0) {
                            tableview_players.getSelectionModel().clearSelection();
                        } else {
                            tableview_players.getSelectionModel().select(0);
                        }
                    }
                }  
            }
        });
        
        checkbox_save.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent e) {
               selectedPlayer.setSaved(checkbox_save.isSelected());
           } 
        });
    }
    
    private void setupBottom() {
        button_addplayers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                clearMessage();
                
                editPlayerController = editPlayerLoader.getController();
                editPlayerController.initNewPlayer();
                editPlayerController.setScene(editPlayerScene);
                editPlayerController.showAndWait();
            }
        });
               
        button_addenemies.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent e) {
               clearMessage();
               
               bulkPlayerAddController = bulkAddLoader.getController();
               bulkPlayerAddController.clear();
               bulkPlayerAddController.setScene(bulkAddScene);
               bulkPlayerAddController.showAndWait();
           } 
        });
        
        button_deleteall.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent e) {
               clearMessage();
               
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Delete All");
                alert.setHeaderText(String.format(
                        "Are you sure you want to delete all fighters?\n\n" + 
                        "You can select \"Do not delete\" on fighters you want to keep."));
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    FighterManager.getInstance().killAllPlayers();
                }
            } 
        });
    }
    
    private void clearMessage() {
        label_message.setText("");
    }
    
    private void disableButtons(boolean bool) {
        button_addcond.setDisable(bool);
        button_removecond.setDisable(bool);
        bar_hp.setDisable(bool);
        button_gethit.setDisable(bool);
        button_heal.setDisable(bool);
        button_editplayer.setDisable(bool);
        button_removeplayer.setDisable(bool);
        listview_conditions.setDisable(bool);
        textfield_changehp.setDisable(bool);
        checkbox_save.setDisable(bool);
        
        if (bool) {
            textfield_changehp.clear();
            bar_hp.setProgress(0);
            checkbox_save.setSelected(false);
        }
    }
    
    private enum Condition {
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
        Condition(String name) {
            this.name = name;
        }
    }
}
