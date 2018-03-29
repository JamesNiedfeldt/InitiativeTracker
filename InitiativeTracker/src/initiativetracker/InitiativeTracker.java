package initiativetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InitiativeTracker extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreenXML.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        //This stuff is just here for testing
        FighterManager fighterManager = new FighterManager();
        Combatant testGuy1 = new Combatant.Builder("Guy 1").hp(50).init(12).build();
        Combatant testGuy2 = new Combatant.Builder("Guy 2").hp(50).init(12).build();
        Combatant testGuy3 = new Combatant.Builder("Guy 3").hp(50).init(11).build();
              
        fighterManager.addPlayers(testGuy1, testGuy2, testGuy3);
        fighterManager.sortPlayers();
        fighterManager.hpManager.player(testGuy1).getsHitFor(25);
        fighterManager.hpManager.player(testGuy2).healsFor(25).getsHitFor(12);
        fighterManager.conditionManager.player(testGuy3).getsCondition("sick").getsCondition("dead");
        fighterManager.printPlayers();
    }
    
}
