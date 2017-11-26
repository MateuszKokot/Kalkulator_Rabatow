
package sample;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

/**
 * Created by R2D2 on 15.10.2017.
 */
public class Load {

    public static List load (){

        while(!Data.loadedFilesList.isEmpty()){
            Data.loadedFilesList.remove(0);
        }

        while(!Data.generatedDiscountPack.isEmpty()){
            Data.generatedDiscountPack.remove(0);
        }

        while(!Data.generatedFinalPack.isEmpty()){
            Data.generatedFinalPack.remove(0);
        }

        while(!Data.observableList.isEmpty()){
            Data.observableList.remove(0);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik z rabatami");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> fileList = fileChooser.showOpenMultipleDialog(null);
        return fileList;
    }

}
