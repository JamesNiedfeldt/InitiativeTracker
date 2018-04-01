package initiativetracker;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FighterManager {
    public static ObservableList<Combatant> players;
    public static HPManager hpManager;
    public static ConditionManager conditionManager;
    
    FighterManager(){
        players = FXCollections.observableArrayList();
        hpManager = new HPManager();
        conditionManager = new ConditionManager();
    }
    
    public void addPlayers(Combatant... args){
        for(int i = 0; i < args.length; i++){
            if(players.contains(args[i])){
                //TODO: Something useful here
                System.out.println(args[i].getName()
                        + " is already accounted for");
            }
            else{
                players.add(args[i]);  
            } 
        }
    }
    
    public void sortPlayers(){
        Collections.sort(players);
    }
    
    public void killPlayers(Combatant... args){
        for(int i = 0; i < args.length; i++){
            players.remove(args[i]);
        } 
    }
    
    public void printPlayers(){
        System.out.println("Combatants:\n");
        for(int i = 0; i < players.size(); i++){
            players.get(i).print();
            System.out.println();
        }
    }
    
    public class HPManager{
        private Combatant combatant;
        private boolean inFight;
        
        HPManager(){
            
        }
              
        public PlayerChanger player(Combatant player){
            combatant = player;
            inFight = players.contains(combatant);
            return new PlayerChanger();
        }
        
        public class PlayerChanger{
            PlayerChanger(){
                
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
    
    public class ConditionManager{
        private Combatant combatant;
        private boolean inFight;
        
        ConditionManager(){
            
        }
        
        public PlayerChanger player(Combatant player){
            combatant = player;
            inFight = players.contains(player);
            return new PlayerChanger();
        }
        
        public class PlayerChanger{
            PlayerChanger(){
                
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
