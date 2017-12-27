
package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        progressBar.setVisible(false);

        Skin.skinInit(); // Inicjalizuje skiny
        globalPane.getStylesheets().add(Skin.stringList.get(0)); //Podpina pod program wybrany arkusz stylu - link do niego jest w zmiennym stringu
        globalPane.getStyleClass().add("globalPane");

        topPane.getStyleClass().add("topPane");
        topInsideFrame.getStyleClass().add("topInsideFrame");
        choiceTariff.getStyleClass().add("choiceTariff");
        textMin.getStyleClass().add("textMin");
        textMax.getStyleClass().add("textMax");

        centerPane.getStyleClass().add("centerPane");
        centerInsideFrameOne.getStyleClass().add("centerInsideFrameOne");
        centerInsideFrameTwo.getStyleClass().add("centerInsideFrameTwo");
        slider.getStyleClass().add("slider");
        discountedAmount.getStyleClass().add("discountedAmount");
        discountOne.getStyleClass().add("discountOne");
        discountTwo.getStyleClass().add("discountTwo");
        discountThree.getStyleClass().add("discountThree");
        discountFour.getStyleClass().add("discountFour");

        bottomPane.getStyleClass().add("bottomPane");
        bottomInsideFrame.getStyleClass().add("bottomInsideFrame");
    }

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
    Label sliderMin;

    @FXML
    Label sliderMax;

    @FXML
    Label baseInfo;

    @FXML
    RadioMenuItem firstSkinn;

    @FXML
    RadioMenuItem secondSkin;

    @FXML
    RadioMenuItem thirdSkin;

    @FXML
    ProgressBar progressBar;




    @FXML
    void wczytaj () throws Exception {

        try {
            
            Data.loadedFilesList.addAll(Load.load()); // Wczytywanie pliku
            Data.generateData(Data.loadedFilesList, Data.generatedDiscountPack); // Wczytywanie Arraylist z ID, stawką bazową i możliwymi rabatami
            Data.generateVariantsDiscounts(Data.generatedDiscountPack, Data.generatedFinalPack); // Wyliczanie Abo finalnego na podstawie mozłiwych rabatów
            Data.sort(Data.generatedFinalPack);  //Sortowanie wyliczonych rabatów od najniższego do najwyższego
            Data.generateObservableList(Data.generatedDiscountPack, Data.observableList);  // Tworzy liste mozłiwych taryf
            choiceTariff.setItems(FXCollections.observableArrayList(Data.observableList)); // Dodaje itemy do comboobx
            choiceTariff.setValue(Data.observableList.get(0)); // ustawia value combobox jako item 0
            Data.getPosition(choiceTariff.getValue()); // wczytuje do zmiennej position i zapisuej jako integer kt ory parametr jest wybrany
            setSlider(slider); // ustawia itemy slidera
            Data.finalSummary(slider); // sprawdza które konfiguracje rabatów mieszczą się w ramach widełek slidera
            sliderMove(); // Wyświetla rabaty i wartość abo dla danej pozycji/wartości slidera



        }catch (Exception error){}
    }

    @FXML
    void choice() throws Exception {

        try {
            textMin.setText("");
            textMax.setText("");
            baseInfo.setText("Stawka bazowa to: " + Data.generatedDiscountPack.get(Data.position).get(1)); // wyświetla w lapelce kwotę bazową

            Data.getPosition(choiceTariff.getValue()); // wczytuje do zmiennej position i zapisuej jako integer kt ory parametr jest wybrany
            setSlider(slider); // ustawia itemy slidera
            Data.finalSummary(slider); // sprawdza które konfiguracje rabatów mieszczą się w ramach widełek slidera
            sliderMove(); // Wyświetla rabaty i wartość abo dla danej pozycji/wartości slidera
            printDiscount(); // wyświetla w dolnej labelce wczytane rabaty

        }catch (Exception error){}
    }

    @FXML
    void write() throws Exception {

        try {
            Data.getPosition(choiceTariff.getValue()); // wczytuje do zmiennej position i zapisuej jako integer kt ory parametr jest wybrany
            setSlider(slider); // ustawia itemy slidera
            Data.finalSummary(slider); // sprawdza które konfiguracje rabatów mieszczą się w ramach widełek slidera
            sliderMove(); // Wyświetla rabaty i wartość abo dla danej pozycji/wartości slidera

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
                    sliderMin.setText(Double.toString(a));
                }else {
                    slider.setMin(Aw);
                    sliderMin.setText(Double.toString(Aw)); }

                //***
                if (Zw <= a ||  Zw >= z || Zw <= Aw) {

                    slider.setMax(z);
                    sliderMax.setText(Double.toString(z));
                }else {
                    slider.setMax(Zw);
                    sliderMax.setText(Double.toString(Zw)); }


            //Reszta ustawień slidera
            slider.setValue((slider.getMax()-slider.getMin())/2+slider.getMin());


        }catch (Exception error){}
    }

    void printDiscount () {

        String string = "Wczytane rabaty to: ";

        for (Integer i = 4; i < Data.generatedDiscountPack.get(Data.position).size(); i++) {

            string = string + Data.generatedDiscountPack.get(Data.position).get(i) + "%  ";
        }

        infoLabel.setText(string);
    }


}





