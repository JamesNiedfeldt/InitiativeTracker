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
        FightManager fightManager = new FightManager();
        Combatant testGuy1 = new Combatant.Builder("Guy 1").hp(50).build();
        Combatant testGuy2 = new Combatant.Builder("Guy 2").hp(50).build();
        Combatant testGuy3 = new Combatant.Builder("Guy 3").hp(50).build();
        
        fightManager.printCombatants();
        fightManager.addPlayers(testGuy1, testGuy2);
        fightManager.conditionManager.player(testGuy2).getsCondition("Incapacitated")
                .player(testGuy1).getsCondition("Feared").getsCondition("Unconcious");
        fightManager.printCombatants();
        fightManager.conditionManager.player(testGuy1).recoversCondition("Unconcious")
                .recoversCondition("Poisioned");
        fightManager.printCombatants();
    }
    
}
