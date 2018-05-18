package initiativetracker;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Combatant implements Comparable{
    private String name;
    private int hitPoints, dexterity, initiative;
    private ObservableList<String> conditions;
    private boolean isCurrentPlayer;
    
    private Combatant(Builder builder){
        name = builder.name;
        hitPoints = builder.hitPoints;
        dexterity = builder.dexterity;
        initiative = builder.initiative;
        conditions = FXCollections.observableArrayList();
        isCurrentPlayer = false;
    }
    
    //Setters
    public void setName(String inName){ 
        if(inName == "" || inName == null){
            name = "Default";
        }
        else{
            name = inName;
        }
    }
    public void setHitPoints(int inHp){ 
        hitPoints = inHp; 
    }
    public void setDexterity(int inDex){ 
        dexterity = inDex; 
    }
    public void setInitiative(int inInitiative){ 
        initiative = inInitiative; 
    }
    public void setIsCurrentPlayer(boolean bool){
        isCurrentPlayer = bool;
    }
    
    //Getters
    public String getName(){ 
        return name; 
    }
    public int getHitPoints(){ 
        return hitPoints; 
    }
    public int getDexterity(){ 
        return dexterity; 
    }
    public int getInitiative(){ 
        return initiative; 
    }
    public ObservableList<String> getConditions(){
        ObservableList<String> toReturn = conditions;
        FXCollections.sort(toReturn);
        return toReturn;
    }
    public String getIsCurrentPlayer(){
        if(isCurrentPlayer){
            return Character.toString((char)0x23E9);
        }
        else{
            return "";
        }
    }
    
    //Other methods  
    public void modifyHitPoints(int modifier){
        hitPoints += modifier;
    }
    
    public void addCondition(String condition){
        if(conditions.contains(condition)){
            //TODO: something useful
            System.out.println(name+" already has condition "+condition);
        }
        else{
            conditions.add(condition);
        }
    }
    
    public void removeCondition(String condition){
        conditions.remove(condition);
    }
    
    public void print(){
        System.out.println(name);
        System.out.println("HP: "+hitPoints);
        System.out.println("Dexterity: "+dexterity);
        System.out.println("Initiative: "+initiative);
        System.out.println("Conditions: "+conditions);
    }
    
    @Override
    public int compareTo(Object o){
        Combatant player = (Combatant)o;
        if(initiative > player.initiative){
            return -1;
        }
        else if(initiative == player.initiative){
            if(dexterity > player.dexterity){
                return -1;
            }
            else if(dexterity < player.dexterity){
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            return 1;
        }
    }    
    
    //Builder subclass
    static class Builder{
        private String name;
        private int hitPoints, dexterity, initiative;
        
        Builder(String inName){
            name = inName;
            hitPoints = 0;
            dexterity = 0;
            initiative = 0;
        }
        
        public Builder hp(int inHp){
            hitPoints = inHp;
            return this;
        }
        
        public Builder dex(int inDex){
            dexterity = inDex;
            return this;
        }
        
        public Builder init(int inInit){
            initiative = inInit;
            return this;
        }
        
        public Combatant build(){
            return new Combatant(this);
        }
    }
}
