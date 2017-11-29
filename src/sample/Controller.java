
package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
            Data.loadedFilesList.addAll(Load.load()); // Wczytywanie pliku
            Data.generateData(Data.loadedFilesList, Data.generatedDiscountPack); // Wczytywanie Arraylist z ID, stawką bazową i możliwymi rabatami
            System.out.println(Data.generatedDiscountPack);
            Data.generateVariantsDiscounts(Data.generatedDiscountPack, Data.generatedFinalPack); // Wyliczanie Abo finalnego na podstawie mozłiwych rabatów
            Data.sort(Data.generatedFinalPack);  //Sortowanie wyliczonych rabatów od najniższego do najwyższego
            Data.generateObservableList(Data.generatedDiscountPack, Data.observableList);  // Tworzy liste mozłiwych taryf
            choiceTariff.setItems(FXCollections.observableArrayList(Data.observableList)); // Dodaje itemy do comboobx
            choiceTariff.setValue(Data.observableList.get(0)); // ustawia value combobox jako item 0
            Data.getPosition(choiceTariff.getValue()); // wczytuje do zmiennej position i zapisuej jako integer kt ory parametr jest wybrany
            setSlider(slider); // ustawia itemy slidera
            //Data.finalSummary(slider); // sprawdza które konfiguracje rabatów mieszczą się w ramach widełek slidera



        }catch (Exception error){}

    }

    @FXML
    void choice() throws Exception {

        try {

            Data.getPosition(choiceTariff.getValue()); // wczytuje do zmiennej position i zapisuej jako integer kt ory parametr jest wybrany
            setSlider(slider); // ustawia itemy slidera
            Data.finalSummary(slider); // sprawdza które konfiguracje rabatów mieszczą się w ramach widełek slidera

        }catch (Exception error){}
    }

    @FXML
    void sliderMove () {

        discountOne.setText("");
        discountTwo.setText("");
        discountThree.setText("");
        discountFour.setText("");

        Double interval = (slider.getMax() - slider.getMin()) / Data.filtredPosition.size();

        for (Integer i = 0; i < Data.filtredPosition.size(); i++){

            if (slider.getValue() >= slider.getMin() + (interval*i) && slider.getValue() <= slider.getMin() + (interval*(i+1))){

                discountedAmount.setText(Data.generatedFinalPack.get(Data.position).get(Data.filtredPosition.get(i)).get(0));

                for (Integer j = 1; j < Data.generatedFinalPack.get(Data.position).get(Data.filtredPosition.get(i)).size(); j++ ){

                    switch(j){
                        case 1:
                            discountOne.setText(Data.generatedFinalPack.get(Data.position).get(Data.filtredPosition.get(i)).get(j));
                            break;
                        case 2:
                            discountTwo.setText(Data.generatedFinalPack.get(Data.position).get(Data.filtredPosition.get(i)).get(j));
                            break;
                        case 3:
                            discountThree.setText(Data.generatedFinalPack.get(Data.position).get(Data.filtredPosition.get(i)).get(j));
                            break;
                        case 4:
                            discountFour.setText(Data.generatedFinalPack.get(Data.position).get(Data.filtredPosition.get(i)).get(j));
                            break;
                    }

                }
            }
        }





    }


    void setSlider (Slider slider) throws Exception {

        try {

            Double Aw; // Minimalna wartość wpiana przez usera
            Double Zw; // Maksymalna wartość wpisana przez usera
            Double a; // Minimalna wartość z wczytanych abonentu
            Double z; // Maksymalna wartość z wczytanych abonamnetów

            a = Double.parseDouble(Data.generatedFinalPack.get(Data.position).get(0).get(0));
            z = Double.parseDouble(Data.generatedFinalPack.get(Data.position).get(Data.generatedFinalPack.get(Data.position).size() - 1).get(0));

            //***
            if (textMin.getText().equals("")){
                Aw = 0.0;
            }else { Aw = Double.parseDouble(textMin.getText()); }

            //***
            if (textMax.getText().equals("") || Double.parseDouble(textMax.getText()) > 99999.0){
                Zw = 99999.0;
            }else {
                Zw = Double.parseDouble(textMax.getText());}


                //***
                if (Aw <= a ||  Aw >= z || Aw >= Zw) {

                    slider.setMin(a);
                }else {
                    slider.setMin(Aw);}

                //***
                if (Zw <= a ||  Zw >= z || Zw <= Aw) {

                    slider.setMax(z);
                }else {
                    slider.setMax(Zw);}


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





