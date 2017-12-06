
package sample;

import javafx.scene.control.Slider;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * Created by R2D2 on 15.10.2017.
 */
public class Data {

    public Data() {
    }

    public static ArrayList <File> loadedFilesList = new ArrayList<File>(); //Tutaj zapisuej wczytane pliki
    public static ArrayList <ArrayList <String>> generatedDiscountPack = new ArrayList<ArrayList <String>>(); //Tutaj przechowuje wczytane dane z plików. Każdy plik w osobnej liście.
    public static ArrayList <ArrayList <ArrayList <String>>> generatedFinalPack = new ArrayList<ArrayList<ArrayList<String>>>(); //Tutaj przechowuje zestawienia kwoty abonamentu po rabatach i rabaty jakie ją tworzą.
    public static ArrayList <String> observableList = new ArrayList <String>(); //Lisat taryf na urzytek Comboboxa
    public static Integer position = 0; // Pozycja comboboxa
    public static ArrayList<Integer> filtredPosition = new ArrayList<Integer>(); // Pozycje list (abo + rabaty), które mieszczą się w widełkach filtra

    public static void generateData(ArrayList<File> loadedFilesList,ArrayList<ArrayList<String>> generatedDiscountPack ) throws IOException {

        Integer i = 0;
        for (File file : loadedFilesList) {

            generatedDiscountPack.add(new ArrayList<String>());

            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            do {
                line = bufferedReader.readLine();
                if (line != null){
                    generatedDiscountPack.get(i).add(line);
                }
            } while (line != null);

            i++;

        }
        return;
    }

    public static void engine (ArrayList <String> listString, ArrayList<ArrayList<String>> listOut) {

        java.text.DecimalFormat df = new java.text.DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        Double baseRate = Double.parseDouble(listString.get(1));
        int iterator = 0;

        for (int i = 2 ; i < listString.size() ; i++ ){

            Double firstDiscount = Double.parseDouble(listString.get(i));
            Double firstSummary = rounding(baseRate * ((100- firstDiscount)/100));
            listOut.add(new ArrayList<String>());
            listOut.get(iterator).add(firstSummary.toString());
            listOut.get(iterator).add(listString.get(i));
            iterator++;

            for (int j = i+1 ; j < listString.size() ; j++ ){

                Double secondDiscount = Double.parseDouble(listString.get(j));
                Double secondSummary = rounding((baseRate * ((100 - firstDiscount)/100))*((100 - secondDiscount)/100));
                listOut.add(new ArrayList<String>());
                listOut.get(iterator).add(secondSummary.toString());
                listOut.get(iterator).add(listString.get(i));
                listOut.get(iterator).add(listString.get(j));
                iterator++;

                for (int k = j+1 ; k < listString.size() ; k++ ){

                    Double thirdDiscount = Double.parseDouble(listString.get(k));
                    Double thirdSummary = rounding(((baseRate * ((100 - firstDiscount)/100))*((100 - secondDiscount)/100))*((100 - thirdDiscount)/100));
                    listOut.add(new ArrayList<String>());
                    listOut.get(iterator).add(thirdSummary.toString());
                    listOut.get(iterator).add(listString.get(i));
                    listOut.get(iterator).add(listString.get(j));
                    listOut.get(iterator).add(listString.get(k));
                    iterator++;

                    for (int l = k+1 ; l < listString.size() ; l++ ){

                        Double fourthDiscount = Double.parseDouble(listString.get(l));
                        Double fourthSummary = rounding((((baseRate * ((100 - firstDiscount)/100))*((100 - secondDiscount)/100))*((100 - thirdDiscount)/100))*((100 - fourthDiscount)/100));
                        listOut.add(new ArrayList<String>());
                        listOut.get(iterator).add(fourthSummary.toString());
                        listOut.get(iterator).add(listString.get(i));
                        listOut.get(iterator).add(listString.get(j));
                        listOut.get(iterator).add(listString.get(k));
                        listOut.get(iterator).add(listString.get(l));
                        iterator++;
                    }
                }
            }

        }


    }

    public static void generateVariantsDiscounts (ArrayList <ArrayList <String>> listIn, ArrayList<ArrayList <ArrayList <String>>> listOut){

        int i = 0;
        for (ArrayList<String> listString: listIn) {

            listOut.add(new ArrayList<ArrayList<String>>());
            engine(listString, listOut.get(i));
            i++;
        }
        return;
    }

    public static void sort (ArrayList<ArrayList <ArrayList <String>>> unsortedGlobal) {


        for (ArrayList <ArrayList <String>> unsorted : unsortedGlobal) {

            boolean condition = true;
            while (condition) {

                condition = false;
                for (int i = 0; i < unsorted.size() - 1; i++) {

                    if (Double.parseDouble(unsorted.get(i).get(0)) > Double.parseDouble(unsorted.get(i + 1).get(0))) {

                        ArrayList<String> a = unsorted.get(i);
                        ArrayList<String> b = unsorted.get(i + 1);
                        unsorted.set(i, b);
                        unsorted.set(i + 1, a);
                        condition = true;
                    }
                }
            }
        }
        return;
    }

    public static void generateObservableList (ArrayList <ArrayList <String>> generatedDiscountPack, ArrayList <String> observableList) {

        for (ArrayList <String> stringlist : generatedDiscountPack) {

            observableList.add(stringlist.get(0));
        }
        return;
    }



    public static void getPosition (Object choiceValue) throws Exception{

        Integer summary = 0;

        try {

            for (Object id: Data.observableList) {
                if (choiceValue.equals(id)){
                    break;
                }
                summary++;
            }

        } catch (Exception error){}

        position = summary;
    }

    public static void finalSummary (Slider slider) throws Exception {

        Integer i = 0;

        while(!filtredPosition.isEmpty()){
            filtredPosition.remove(0);
        }

        for (ArrayList<String> list: Data.generatedFinalPack.get(Data.position)) {

            if (    Double.parseDouble(list.get(0)) < slider.getMin()
                    || Double.parseDouble(list.get(0)) > slider.getMax()){

            }else{
                filtredPosition.add(i);
            }
            i++;
        }

    }

    public static Double rounding (Double d) {

        double aDouble = d;
        aDouble *= 100;
        aDouble = Math.round(aDouble);
        aDouble /= 100;
        Double summary = aDouble;

        return summary;
    }
}