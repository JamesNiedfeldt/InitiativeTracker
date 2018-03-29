package initiativetracker;

import java.util.ArrayList;

public class FightManager {
    private static ArrayList<Combatant> players;
    private static ArrayList<Combatant> enemies;
    public static HPManager hpManager;
    public static ConditionManager conditionManager;
    
    FightManager(){
        players = new ArrayList<Combatant>();
        enemies = new ArrayList<Combatant>();
        hpManager = new HPManager();
        conditionManager = new ConditionManager();
    }
    
    public void addPlayers(Combatant... args){
        for(int i = 0; i < args.length; i++){
            if(players.contains(args[i]) || enemies.contains(args[i])){
                //TODO: Something useful here
                System.out.println(args[i].getName()
                        + " is already accounted for");
            }
            else{
                players.add(args[i]);  
            } 
        }
    }
    
    public void addEnemies(Combatant... args){
        for(int i = 0; i < args.length; i++){
            if(players.contains(args[i]) || enemies.contains(args[i])){
                //TODO: Something useful here
                System.out.println(args[i].getName()
                        + " is already accounted for");
            }
            else{
                enemies.add(args[i]);  
            } 
        }
    }
    
    public void killPlayers(Combatant... args){
        for(int i = 0; i < args.length; i++){
            players.remove(args[i]);
        } 
    }
    
    public void killEnemies(Combatant... args){
        for(int i = 0; i < args.length; i++){
            enemies.remove(args[i]);
        } 
    }
    
    public void printCombatants(){
        System.out.println("Players:\n");
        for(int i = 0; i < players.size(); i++){
            players.get(i).print();
            System.out.println();
        }
        System.out.println("Enemies:\n");
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).print();
            System.out.println();
        }
    }
    
    public class HPManager{
        private Combatant combatant;
        
        HPManager(){
            
        }
              
        public HPManager player(Combatant player){
        /*This method is necessary for determining the player for which you want
        to change the stats of. Unfortunately I don't know how to require it so 
        it's possible to cal the the change-state functions on whatever 
        combatant is already there, which is a problem.
        */
            combatant = player;
            return this;
        }
        
        public HPManager getsHitFor(int damage){
            if(players.contains(combatant) || enemies.contains(combatant)){
                players.get(players.indexOf(combatant)).modifyHitPoints(-damage);
            }
            else{
                //TODO: something useful here
                System.out.println(combatant.getName()+" is not accounted for");
            }
            return this;
        }
        
        public HPManager healsFor(int health){
            if(players.contains(combatant) || enemies.contains(combatant)){
                players.get(players.indexOf(combatant)).modifyHitPoints(health);
            }
            else{
                //TODO: something useful here
                System.out.println(combatant.getName()+" is not accounted for");
            }
            return this;
        }
    }
    
    public class ConditionManager{
        private Combatant combatant;
        
        ConditionManager(){
            
        }
        
        public ConditionManager player(Combatant player){
            combatant = player;
            return this;
        }
        
        public ConditionManager getsCondition(String condition){
            combatant.addCondition(condition);
            return this;
        }
        
        public ConditionManager recoversCondition(String condition){
            combatant.removeCondition(condition);
            return this;
        }
    }
}
