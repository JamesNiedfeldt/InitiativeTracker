package InitiativeTracker.classes;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Combatant implements Comparable{
    private String name;
    private int maxHp, currentHp, tempHp, dexterity, initiative, armorClass;
    private ObservableList<String> conditions;
    private boolean saved;
    
    private Combatant(Builder builder) {
        name = builder.name;
        maxHp = builder.maxHp;
        currentHp = builder.currentHp;
        if (currentHp > maxHp) {
            currentHp = maxHp;
        }
        tempHp = builder.tempHp;
        dexterity = builder.dexterity;
        initiative = builder.initiative;
        armorClass = builder.armorClass;
        conditions = FXCollections.observableArrayList();
        saved = false;
    }
    
    //Setters
    public void setName(String inName) { 
        if(inName == "" || inName == null) {
            name = "Default";
        } else {
            name = inName;
        }
    }
    public void setMaxHp(int inHp) { 
        maxHp = inHp; 
    }
    public void setCurrentHp(int inHp) {
        currentHp = inHp;
        if (currentHp > maxHp) {
            currentHp = maxHp;
        }
    }
    public void setTempHp(int inHp) {
        tempHp = inHp;
    }
    public void setDexterity(int inDex) { 
        dexterity = inDex; 
    }
    public void setInitiative(int inInitiative) { 
        initiative = inInitiative; 
    }
    public void setArmorClass(int inAc) {
        armorClass = inAc;
    }
    public void setSaved(boolean bool) {
        saved = bool;
    }
    
    //Getters
    public String getName() { 
        return name; 
    }
    public int getMaxHp() { 
        return maxHp; 
    }
    public int getCurrentHp() {
        return currentHp;
    }
    public int getTempHp() {
        return tempHp;
    }
    public int getTotalHp() {
        return currentHp + tempHp;
    }
    public int getDexterity() { 
        return dexterity; 
    }
    public int getInitiative() { 
        return initiative; 
    }
    public int getArmorClass() {
        return armorClass;
    }
    public ObservableList<String> getConditions() {
        ObservableList<String> toReturn = conditions;
        FXCollections.sort(toReturn);
        return toReturn;
    }
    public boolean isSaved() {
        return saved;
    }
    
    //Other methods  
    public void takeDamage(int damage) {
        modifyHitPoints(-damage);
    }
    
    public void heal(int hp) {
        modifyHitPoints(hp);
    }
    
    private void modifyHitPoints(int modifier) {
        if (modifier < 0 && tempHp > 0) {
            tempHp += modifier;
            if (tempHp < 0){
                currentHp += tempHp;
                tempHp = 0;
            }
        } else {
          currentHp += modifier;
        }
        
        if (currentHp > maxHp) {
            currentHp = maxHp;
        } else if (currentHp < 0) {
            currentHp = 0;
        }
    }
    
    public void addCondition(String condition) {
        if (conditions.contains(condition)) {
            throw new RuntimeException(name + " already has condition \""
                    + condition + "\".");
        } else {
            conditions.add(condition);
        }
    }
    
    public void removeCondition(String condition) {
        conditions.remove(condition);
    }
    
    public void print() {
        System.out.println(name);
        System.out.println("HP: "+currentHp+" + "+tempHp+"/"+maxHp);
        System.out.println("Dexterity: "+dexterity);
        System.out.println("Initiative: "+initiative);
        System.out.println("Conditions: "+conditions);
        System.out.println("AC: "+armorClass);
    }
    
    public String formatForFile() {
        String str;
        
        str = name + ',' + maxHp + ',' + currentHp + ',' + tempHp + 
                ',' + dexterity + 
                ',' + initiative + ',' + armorClass + ',' + saved;
        
        for (String cond : conditions) {
            str = str + ',' + cond;
        }
        
        str = str + "\n";
        
        return str;
    }
    
    @Override
    public int compareTo(Object o) {
        Combatant player = (Combatant)o;
        if (initiative > player.initiative) {
            return -1;
        } else if (initiative == player.initiative) {
            if (dexterity > player.dexterity) {
                return -1;
            } else if(dexterity < player.dexterity) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }    
    
    //Builder subclass
    public static class Builder {
        private String name;
        private int maxHp, currentHp, tempHp, dexterity, initiative, armorClass;
        
        public Builder(String inName) {
            name = inName;
            maxHp = 0;
            currentHp = 0;
            tempHp = 0;
            dexterity = 0;
            initiative = 0;
            armorClass = 0;
        }
        
        public Builder maxHp(int inHp) {
            maxHp = inHp;
            return this;
        }
        
        public Builder currentHp(int inHp) {
            currentHp = inHp;
            return this;
        }
        
        public Builder tempHp(int inHp) {
            tempHp = inHp;
            return this;
        }
        
        public Builder dex(int inDex) {
            dexterity = inDex;
            return this;
        }
        
        public Builder init(int inInit) {
            initiative = inInit;
            return this;
        }
        
        public Builder ac(int inAc) {
            armorClass = inAc;
            return this;
        }
        
        public Combatant build() {
            return new Combatant(this);
        }
    }
}
