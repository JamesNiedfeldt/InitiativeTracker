package initiativetracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class FighterManager {
    private static ArrayList<Combatant> players;
    public static HPManager hpManager;
    public static ConditionManager conditionManager;
    
    FighterManager(){
        players = new ArrayList<Combatant>();
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
        /*This method is necessary for determining the player for which you want
        to change the stats of. Unfortunately I don't know how to require it so 
        it's possible to cal the the change-state functions on whatever 
        combatant is already there, which is a problem.
        */
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
