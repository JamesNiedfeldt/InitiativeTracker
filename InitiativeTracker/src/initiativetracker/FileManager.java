package initiativetracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;

public class FileManager {
    private final FileChooser fc = new FileChooser();
    
    public FileManager() {
        fc.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fc.setInitialFileName("Encounter.csv");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
    } 
    
    public void promptSave() {
        fc.setTitle("Save Battle File");
        File file = fc.showSaveDialog(null);
        if (file != null) {
            savePlayers(file);
        }
    }
    
    public void promptLoad() {
        fc.setTitle("Load Battle File");
        File file = fc.showOpenDialog(null);
        if (file != null) {
            MainScreenController.fighterManager.reset();
            
            loadPlayers(file);
        }
    }
    
    private void savePlayers(File file) {
        FileWriter fileWriter;
        BufferedWriter writer;
        
        try {
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);
            
            for (String fighter : MainScreenController.fighterManager.formatForFile()) {
                writer.write(fighter);
            }
            
            writer.close();
        } catch(IOException e) {
            
        }
    }
    
    private void loadPlayers(File file) {
        FileReader fileReader;
        BufferedReader reader;
        String line;
        Combatant fighter;
        
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            
            line = reader.readLine();
            while (line != null) {
                fighter = parseLine(line);
                
                if (fighter != null) {
                    MainScreenController.fighterManager.addPlayers(fighter);
                }
                
                line = reader.readLine();
            }
            
            reader.close();
        } catch(IOException e) {
            
        }
    }
    
    private Combatant parseLine(String line) {
        Combatant fighter;
        String[] fields = line.split(",");
        
        try {
            fighter = new Combatant.Builder(fields[0])
                .hp(Integer.parseInt(fields[1]))
                .tempHp(Integer.parseInt(fields[2]))
                .dex(Integer.parseInt(fields[3]))
                .init(Integer.parseInt(fields[4]))
                .ac(Integer.parseInt(fields[5])).build();
            
            fighter.setSaved(Boolean.parseBoolean(fields[6]));
            
            for (int i = 7; i < fields.length; i++) {
                fighter.addCondition(fields[i]);
            }
            
        } catch(NumberFormatException e) {
            fighter = null;
        }
        
        return fighter;
    }
}
