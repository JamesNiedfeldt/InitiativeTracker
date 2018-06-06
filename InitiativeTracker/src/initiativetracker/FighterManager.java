package initiativetracker;

import java.util.Collections;
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
    
    //Getters
    public HPManager getHpManager(){
        return hpManager;
    }
    public ConditionManager getConditionManager(){
        return conditionManager;
    }
    
    public void addToTable(TableView table){
        table.setItems(players); 
    }
    
    public void addPlayers(Combatant... toAdd){
        for(int i = 0; i < toAdd.length; i++){
            /* Not sure if this is really necessary as the user may want 
             * seemingly duplicate entries so it's left but commented out
             */
            
            /*if(players.contains(toAdd[i])){
                //TODO: Something useful here
                System.out.println(toAdd[i].getName()
                        + " is already accounted for");
            }
            else{
                players.add(toAdd[i]);  
            }*/
            players.add(toAdd[i]);
        }
        sortPlayers();
    }
    
    public void killPlayers(Combatant... toKill){
        int index;
        
        for(int i = 0; i < toKill.length; i++){
            index = players.indexOf(toKill[i]);
            if(index != -1 && players.size() == 1){
                players.remove(toKill[i]);
                currentPlayer = 0;
                break;
            }
            else if(index != -1 && players.get(index).getIsCurrentPlayer()){
                if(index > players.size()){
                    players.get(0).setIsCurrentPlayer(true);
                }
                else{
                    players.get(index + 1).setIsCurrentPlayer(true);
                }
            }
            players.remove(toKill[i]);
        } 
    }
    
    public void nextTurn(){
        if(currentPlayer < players.size() - 1){
            players.get(currentPlayer).setIsCurrentPlayer(false);
            players.get(currentPlayer + 1).setIsCurrentPlayer(true);
            currentPlayer++;
        }
        else{
            players.get(players.size() - 1).setIsCurrentPlayer(false);
            players.get(0).setIsCurrentPlayer(true);
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
    
    private void sortPlayers(){
        Collections.sort(players);
        
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getIsCurrentPlayer()){
                currentPlayer = i;
                break;
            }
        }
        
        players.get(currentPlayer).setIsCurrentPlayer(true);
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
