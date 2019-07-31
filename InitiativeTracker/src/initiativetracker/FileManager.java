package initiativetracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;

public class FileManager {
    private final FileChooser fc = new FileChooser();
    
    public FileManager() {
        fc.getExtensionFilters().addAll(
          new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fc.setInitialFileName("Encounter.csv");
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
        
    }
}
