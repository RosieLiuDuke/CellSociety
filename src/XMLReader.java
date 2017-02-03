import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Simple example of XML parsing.
 * 
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class XMLReader extends Application {
    // kind of data files to look for
    public static final String DATA_FILE_EXTENSION = "*.java";

    // it is generally accepted behavior that the chooser remembers where user left it last
    private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);


    @Override
    public void start (Stage primaryStage) throws Exception {
        File dataFile = myChooser.showOpenDialog(primaryStage);
        if (dataFile != null) {
        	 System.out.println("I've successfully selected a file");
            // silly trick to select data file multiple times for this demo
            start(primaryStage);
        }
        else {
            // nothing selected, so quit the application
            Platform.exit();
        }
    }

    // set some sensible defaults when the FileChooser is created
    private FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }


    public static void main (String[] args) {
        launch(args);
    }
}
