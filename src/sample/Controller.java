
package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class Controller {


    @FXML
    BorderPane globalPane;

    @FXML
    VBox topPane;

    @FXML
    VBox topInsideFrame;

    @FXML
    ComboBox choiceTariff;

    @FXML
    TextField textMin;

    @FXML
    TextField textMax;

    @FXML
    VBox centerPane;

    @FXML
    HBox centerInsideFrameOne;

    @FXML
    HBox centerInsideFrameTwo;

    @FXML
    Slider slider;

    @FXML
    TextField discountedAmount;

    @FXML
    TextField discountOne;

    @FXML
    TextField discountTwo;

    @FXML
    TextField discountThree;

    @FXML
    TextField discountFour;

    @FXML
    VBox bottomPane;

    @FXML
    HBox bottomInsideFrame;

    @FXML
    Label infoLabel;




    @FXML
    void init (){

        textMin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textMin.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        textMax.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textMax.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    void wczytaj () throws Exception {

        try {
            GenerateData.loadedFilesList.addAll(Load.load()); // Wczytywanie pliku
            GenerateData.generateData(GenerateData.loadedFilesList,GenerateData.generatedDiscountPack); // Wczytywanie Arraylist z ID, stawką bazową i możliwymi rabatami
            System.out.println(GenerateData.generatedDiscountPack);
            GenerateData.generateVariantsDiscounts(GenerateData.generatedDiscountPack, GenerateData.generatedFinalPack); // Wyliczanie Abo finalnego na podstawie mozłiwych rabatów
            GenerateData.sort(GenerateData.generatedFinalPack);  //Sortowanie wyliczonych rabatów od najniższego do najwyższego
            GenerateData.generateObservableList(GenerateData.generatedDiscountPack, GenerateData.observableList);  // Tworzy liste mozłiwych taryf
            choiceTariff.setItems(FXCollections.observableArrayList(GenerateData.observableList));
            choiceTariff.setValue(GenerateData.observableList.get(0));
            setSlider(slider);
            finalSummary();


        }catch (Exception error){}

    }

    @FXML
    void choice() throws Exception {

        try {

            setSlider(slider);
            finalSummary();

        }catch (Exception error){}
    }

    @FXML
    void finalSummary () throws Exception {

        ArrayList<Integer> position = new ArrayList<Integer>();
        Integer i = 0;

        for (ArrayList<String> list: GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue()))) {

            if (Double.parseDouble(list.get(0)) < slider.getMin() ){

            }else if(Double.parseDouble(list.get(0)) > slider.getMax()){

            }else{
                position.add(i);
            }
            i++;
        }
        System.out.println(slider.getMin());
        System.out.println(slider.getMax());
        System.out.println(position);

        //TODO Mam pozycje w arraju które abo mieszczą sie  w ramach min max. na podstawie tego zmapować to z sliderem
        //TODO MAm błąd jeśli wartość max jest mniejsza od wartości min to nie podstawia nic a powinno podstawic przynajmniej poprawną wartośc min.


    }


    void setSlider (Slider slider) throws Exception {

        try {

            //Podstawianie Min pod slider z zabezpieczenieami
            if (textMin.getText().equals("")
                    || Double.parseDouble(textMin.getText()) > Double.parseDouble(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue()))
                    .get(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue())).size() - 1).get(0))
                    || Double.parseDouble(textMin.getText()) < Double.parseDouble(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue()))
                    .get(0).get(0))
                    || (!textMax.getText().equals("") && !textMin.getText().equals("") && Double.parseDouble(textMax.getText()) <= Double.parseDouble(textMin.getText()))
                    ) {

                slider.setMin(Double.parseDouble(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue())).get(0).get(0)));

            } else {

                slider.setMin(Double.parseDouble(textMin.getText()));
            }

            //Podstawianie Max pod slider z zabezpieczenieami
            if (
                    textMax.getText().equals("")
                            || Double.parseDouble(textMax.getText()) > Double.parseDouble(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue()))
                            .get(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue())).size() - 1).get(0))

                            || Double.parseDouble(textMax.getText()) < Double.parseDouble(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue()))
                            .get(0).get(0))
                            || (!textMax.getText().equals("") && !textMin.getText().equals("") && Double.parseDouble(textMax.getText()) <= Double.parseDouble(textMin.getText()))
                    ) {

                slider.setMax(Double.parseDouble(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue()))
                        .get(GenerateData.generatedFinalPack.get(GenerateData.choicePosition(choiceTariff.getValue())).size() - 1).get(0)));

            } else {

                slider.setMax(Double.parseDouble(textMax.getText()));
            }

            //Reszta ustawień slidera
            slider.setValue((slider.getMax()-slider.getMin())/2+slider.getMin());
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(10);
            slider.setMinorTickCount(5);
            slider.setBlockIncrement(1);

        }catch (Exception error){}

    }
}





