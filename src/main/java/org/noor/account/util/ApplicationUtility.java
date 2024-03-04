package org.noor.account.util;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.*;
import java.util.Objects;

import static org.noor.account.util.ApplicationConstants.*;

public final class ApplicationUtility {


    public static final File CONFIG_FILE;

    public static String DATABASE_DIRECTORY;

    static
    {
        CONFIG_FILE = createConfigFile();
        DATABASE_DIRECTORY = readDatabaseDirectory();
    }

    public static void initializeDatabaseDirectory() {
        if (StringUtils.isBlank(DATABASE_DIRECTORY)) {

            promptForDatabaseDirectory();
            if (StringUtils.isBlank(DATABASE_DIRECTORY)) {
                showMessage("You must select a data directory, Run again!");
            }
        }
    }
    public static String readDatabaseDirectory() {
        String databaseDirectory = null;
        try {
            if ( CONFIG_FILE.exists()) {
                FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                databaseDirectory = (String) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("ApplicationUtility.readDatabaseDirectory: "+ e.getMessage());
        }
        return databaseDirectory;
    }

    public static void promptForDatabaseDirectory() {
        try {
            JFileChooser fileChooser = new JFileChooser(System.getProperty(USER_HOME));
            fileChooser.setDialogTitle("Select Database Directory");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                saveDatabaseDirectory(fileChooser.getSelectedFile().getAbsolutePath(), CONFIG_FILE);
            }else{
                showMessage("You must select a data directory, Run again!");
            }
        } catch (Exception e) {
            showMessage("ApplicationUtility.promptForDatabaseDirectory: "+ e.getMessage());
        }
    }

    public static void saveDatabaseDirectory(String selectedDir, File configFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(configFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(selectedDir);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Database directory stored successfully.");
        } catch (Exception e) {
            showMessage("ApplicationUtility.saveDatabaseDirectory: "+ e.getMessage());
        }
    }

    public static void showMessage(String message) {
        JFrame frame = new JFrame("Error");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel(message);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(label);

        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static File createConfigFile() {
        File configDir = getConfigDirectory();
        if (!configDir.exists()) {
            if (!configDir.mkdir()) {
                showMessage("Initialization Failed, Run again with Administrator");
            }
        }
        return new File(configDir, CONFIG_FILE_NAME);
    }


    public static File getConfigDirectory() {
        return new File(getAppDataDirectory(), APPLICATION_NAME);
    }

    public static String getAppDataDirectory() {
        return System.getProperty(USER_HOME) + File.separator + APP_DATA + File.separator + LOCAL;
    }



    private ApplicationUtility(){

    }
}
