
package sample;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

/**
 * Created by R2D2 on 15.10.2017.
 */
public class Load {

    public static List load (){

        while(!GenerateData.loadedFilesList.isEmpty()){
            GenerateData.loadedFilesList.remove(0);
        }

        while(!GenerateData.generatedDiscountPack.isEmpty()){
            GenerateData.generatedDiscountPack.remove(0);
        }

        while(!GenerateData.generatedFinalPack.isEmpty()){
            GenerateData.generatedFinalPack.remove(0);
        }

        while(!GenerateData.observableList.isEmpty()){
            GenerateData.observableList.remove(0);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj plik z rabatami");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> fileList = fileChooser.showOpenMultipleDialog(null);
        return fileList;
    }

}
