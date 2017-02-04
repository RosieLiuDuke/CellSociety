import java.io.File;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Simple example of XML parsing.
 * 
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author Yilin Gao
 */
public class XMLReader{
    // kind of data files to look for
    public static final String DATA_FILE_EXTENSION = "*.xml";

    // it is generally accepted behavior that the chooser remembers where user left it last
    private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
    
    private CellSociety theCellSociety;
    
    public XMLReader(CellSociety cs){
    	theCellSociety = cs;
    }


    public void chooseFile (Stage primaryStage) throws Exception {
        File dataFile = myChooser.showOpenDialog(primaryStage);
        if (dataFile != null) {
        	 theCellSociety.setFile(dataFile);
        }
        else {
            Platform.exit();
        }
    }

    // set some sensible defaults when the FileChooser is created
    private FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Choose Simulation XML File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }
}
