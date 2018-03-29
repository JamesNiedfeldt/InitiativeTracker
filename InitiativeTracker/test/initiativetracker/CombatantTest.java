package initiativetracker;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class CombatantTest {
    private Combatant testCombatant;
    
    public CombatantTest() {
    }

    @Before
    public void Setup() {
        testCombatant = new Combatant.Builder("Test").build();
    }
    
    @Test
    public void testSetName() {
        testCombatant.setName(null);
        assertEquals("Default", testCombatant.getName());
        testCombatant.setName("");
        assertEquals("Default", testCombatant.getName());
        testCombatant.setName("new name");
        assertEquals("new name", testCombatant.getName());
    }

    @Test
    public void testSetHitPoints() {
        testCombatant.setHitPoints(50);
        assertEquals(50, testCombatant.getHitPoints());
    }

    @Test
    public void testSetDexterity() {
        testCombatant.setDexterity(50);
        assertEquals(50, testCombatant.getDexterity());
    }

    @Test
    public void testSetInitiative() {
        testCombatant.setInitiative(50);
        assertEquals(50, testCombatant.getInitiative());
    }

    @Test
    public void testModifyHitPoints() {
        testCombatant.setHitPoints(50);
        testCombatant.modifyHitPoints(10);
        assertEquals(60, testCombatant.getHitPoints());
        testCombatant.modifyHitPoints(0);
        assertEquals(60, testCombatant.getHitPoints());
        testCombatant.modifyHitPoints(-10);
        assertEquals(50, testCombatant.getHitPoints());
    }

    @Test
    public void testCompareTo() {
        Combatant newTest = new Combatant.Builder("compare to").init(15).dex(15).build();
        testCombatant.setInitiative(15);
        testCombatant.setDexterity(15);
        assertEquals(0, testCombatant.compareTo(newTest));
        
        newTest.setDexterity(16);
        assertEquals(1, testCombatant.compareTo(newTest));
        
        newTest.setDexterity(14);
        assertEquals(-1, testCombatant.compareTo(newTest));
        
        newTest.setDexterity(15);
        newTest.setInitiative(14);
        assertEquals(-1, testCombatant.compareTo(newTest));
        
        newTest.setInitiative(16);
        assertEquals(1, testCombatant.compareTo(newTest));
    }
    
}
