package mo.eeg.visualization.attention;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mo.organization.Configuration;
import mo.visualization.Playable;
import mo.visualization.VisualizableConfiguration;

public class AttConfig implements VisualizableConfiguration {

    private String id;
    private List<File> files;
    private final static String[] creators 
            = {"mo.eeg.capture.EEGRecorder"};
    
    private EEGPlayer player;
   
    private static final Logger logger = Logger.getLogger(AttConfig.class.getName());

    public AttConfig() {
        files = new ArrayList<>();
    }
        
    @Override
    public List<String> getCompatibleCreators() {
        return Arrays.asList(creators);
    }

    @Override
    public void addFile(File file) {
        if (!files.contains(file)) {
            files.add(file);
        }
    }

    @Override
    public void removeFile(File file) {
        if (files.contains(file)) {
            files.remove(file);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public File toFile(File parent) {
        File f = new File(parent, "attention-visualization"+"_"+id+".xml");
        try {
            f.createNewFile();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return null;
        }
        return f;
    }

    @Override
    public Configuration fromFile(File file) {
        String fileName = file.getName();

        if (fileName.contains("_") && fileName.contains(".")) {
            String name = fileName.substring(fileName.indexOf("_")+1, fileName.lastIndexOf("."));
            AttConfig config = new AttConfig();
            config.id = name;
            return config;
        }
        return null;
    }

    @Override
    public Playable getPlayer() {
        if (player == null) {
            player = new EEGPlayer(files.get(0));
        }
        return player;
    }
}
