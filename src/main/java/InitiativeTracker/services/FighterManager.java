package InitiativeTracker.services;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import InitiativeTracker.classes.Combatant;

public class FighterManager {
    private static ObservableList<Combatant> players;
    
    //Singleton
    private static final FighterManager instance = new FighterManager();
    
    private FighterManager() {
        players = FXCollections.observableArrayList();
    }
    
    public static FighterManager getInstance() {
        return instance;
    }
    
    public int getPlayerCount() {
        return players.size();
    }
    
    public void addToTable(TableView table) {
        table.setItems(players); 
    }
    
    public void addPlayers(Combatant... toAdd) {
        for (Combatant combatant : toAdd) {
            players.add(combatant);
        }
    }
    
    public void killPlayers(Combatant... toKill) {
        int index;
        
        for (Combatant combatant : toKill) {
            index = players.indexOf(combatant);
            if (!combatant.isSaved()) {
                if(index != -1 && players.size() == 1){
                    players.remove(combatant);
                    break;
                }
                players.remove(combatant);
            }
        } 
    }
    
    public void killAllPlayers() {
        Combatant[] toKill = players.toArray(new Combatant[players.size()]);
        
        killPlayers(toKill);
    }
    
    /** Completely kills ALL players regardless of save status -- only to be used
     * when loading a new encounter state 
     */
    public void reset() {
        for (Combatant fighter : players) {
            fighter.setSaved(false);
        }
        killAllPlayers();
    }
    
    public void replacePlayers(Combatant playerOut, Combatant playerIn) {
        int index = players.indexOf(playerOut);
        
        if (index != -1) {
            players.remove(playerOut);
            players.add(index, playerIn);
        }
    }
    
    public void nextTurn() {
        Combatant tmp = players.get(0);
        players.remove(0);
        players.add(tmp);
    }
    
    public void printPlayers() {
        System.out.println("Combatants:\n");
        for (int i = 0; i < players.size(); i++) {
            players.get(i).print();
            System.out.println();
        }
    }
    
    public void sortPlayers() {
        Collections.sort(players);
    }
    
    public String[] formatForFile() {
        String[] formatted = new String[players.size()];
        
        for (int i = 0; i < formatted.length; i++) {
            formatted[i] = players.get(i).formatForFile();
        }
        
        return formatted;
    }
}
