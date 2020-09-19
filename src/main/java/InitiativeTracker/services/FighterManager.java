package InitiativeTracker.services;

import java.util.Collections;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import InitiativeTracker.classes.Combatant;

public class FighterManager {
    private static ObservableList<Combatant> players;
    private static int currentPlayer;
    
    //Singleton
    private static final FighterManager instance = new FighterManager();
    
    private FighterManager() {
        players = FXCollections.observableArrayList();
        currentPlayer = 0;
    }
    
    public static FighterManager getInstance() {
        return instance;
    }
    
    public int getPlayerCount() {
        return players.size();
    }
    
    public int getCurrentPlayer() {
        return currentPlayer;
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
                    currentPlayer = 0;
                    break;
                } else if (index != -1 && players.get(index).isCurrentPlayer()) {
                    if (index >= players.size() - 1) {
                        players.get(0).setCurrentPlayer(true);
                        currentPlayer = 0;
                    } else {
                        players.get(index + 1).setCurrentPlayer(true);
                    }
                } else if (index != -1 && currentPlayer > index) {
                    currentPlayer--;
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
        
        if (playerOut.isCurrentPlayer()) {
            playerIn.setCurrentPlayer(true);
        }
        if (index != -1) {
            players.remove(playerOut);
            players.add(index, playerIn);
        }
    }
    
    public void nextTurn() {
        if (currentPlayer < players.size() - 1) {
            players.get(currentPlayer).setCurrentPlayer(false);
            players.get(currentPlayer + 1).setCurrentPlayer(true);
            currentPlayer++;
        } else {
            players.get(players.size() - 1).setCurrentPlayer(false);
            players.get(0).setCurrentPlayer(true);
            currentPlayer = 0;
        }
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
        currentPlayer = 0;
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setCurrentPlayer(false);
        }
        players.get(currentPlayer).setCurrentPlayer(true);
    }
    
    public String[] formatForFile() {
        String[] formatted = new String[players.size()];
        
        for (int i = 0; i < formatted.length; i++) {
            formatted[i] = players.get(i).formatForFile();
        }
        
        return formatted;
    }
}
