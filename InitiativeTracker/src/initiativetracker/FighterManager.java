package initiativetracker;

import java.util.Collections;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class FighterManager {
    private static ObservableList<Combatant> players;
    private static HPManager hpManager;
    private static ConditionManager conditionManager;
    private static int currentPlayer;
    
    FighterManager(){
        players = FXCollections.observableArrayList();
        hpManager = new HPManager();
        conditionManager = new ConditionManager();
        currentPlayer = 0;
    }
    
    public HPManager getHpManager(){
        return hpManager;
    }
    public ConditionManager getConditionManager(){
        return conditionManager;
    }
    public int getPlayerNumber(){
        return players.size();
    }
    
    public int getCurrentPlayer(){
        return currentPlayer;
    }
    
    public void addToTable(TableView table){
        table.setItems(players); 
    }
    
    public void addPlayers(Combatant... toAdd){
        for(Combatant combatant : toAdd){
            players.add(combatant);
        }
    }
    
    public void killPlayers(Combatant... toKill){
        int index;
        
        for(Combatant combatant : toKill){
            index = players.indexOf(combatant);
            if(!combatant.isSaved()){
                if(index != -1 && players.size() == 1){
                    players.remove(combatant);
                    currentPlayer = 0;
                    break;
                }
                else if(index != -1 && players.get(index).isCurrentPlayer()){
                    if(index >= players.size() - 1){
                        players.get(0).setCurrentPlayer(true);
                        currentPlayer = 0;
                    }
                    else{
                        players.get(index + 1).setCurrentPlayer(true);
                    }
                }
                else if(index != -1 && currentPlayer > index) {
                    currentPlayer--;
                }
                players.remove(combatant);
            }
        } 
    }
    
    public void killAllPlayers(){
        Combatant[] toKill = players.toArray(new Combatant[players.size()]);
        
        killPlayers(toKill);
    }
    
    public void replacePlayers(Combatant playerOut, Combatant playerIn){
        int index = players.indexOf(playerOut);
        
        if(playerOut.isCurrentPlayer()){
            playerIn.setCurrentPlayer(true);
        }
        if(index != -1){
            players.remove(playerOut);
            players.add(index, playerIn);
        }
    }
    
    public void nextTurn(){
        if(currentPlayer < players.size() - 1){
            players.get(currentPlayer).setCurrentPlayer(false);
            players.get(currentPlayer + 1).setCurrentPlayer(true);
            currentPlayer++;
        }
        else{
            players.get(players.size() - 1).setCurrentPlayer(false);
            players.get(0).setCurrentPlayer(true);
            currentPlayer = 0;
        }
    }
    
    public void printPlayers(){
        System.out.println("Combatants:\n");
        for(int i = 0; i < players.size(); i++){
            players.get(i).print();
            System.out.println();
        }
    }
    
    public void sortPlayers(){
        Collections.sort(players);
        currentPlayer = 0;
        for(int i = 0; i < players.size(); i++){
            players.get(i).setCurrentPlayer(false);
        }
        players.get(currentPlayer).setCurrentPlayer(true);
    }
    
    class HPManager{
        private Combatant combatant;
        private boolean inFight;
        
        private HPManager(){
            
        }
              
        public PlayerChanger player(Combatant player){
            combatant = player;
            inFight = players.contains(combatant);
            return new PlayerChanger();
        }
        
        public class PlayerChanger{
            private PlayerChanger(){
                
            }
            
           public PlayerChanger getsHitFor(int damage){
                if(inFight){
                    combatant.modifyHitPoints(-damage);
                }
                else{
                    //TODO: something useful here
                    System.out.println(combatant.getName()+" is not accounted for");
                }
                return this;
            }
        
            public PlayerChanger healsFor(int health){
                if(inFight){
                    combatant.modifyHitPoints(health);
                }
                else{
                    //TODO: something useful here
                    System.out.println(combatant.getName()+" is not accounted for");
                }
                return this;
            } 
        }   
    }
    
    class ConditionManager{
        private Combatant combatant;
        private boolean inFight;
        
        private ConditionManager(){
            
        }
        
        public PlayerChanger player(Combatant player){
            combatant = player;
            inFight = players.contains(player);
            return new PlayerChanger();
        }
        
        public class PlayerChanger{
            private PlayerChanger(){
                
            }
            
            public PlayerChanger getsCondition(String condition){
                if(inFight){
                    combatant.addCondition(condition);
                }
                return this;
            }
        
            public PlayerChanger recoversCondition(String condition){
                if(inFight){
                    combatant.removeCondition(condition);
                }                
                return this;
            }
        }
        
    }
}
