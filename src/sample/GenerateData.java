
package sample;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Created by R2D2 on 15.10.2017.
 */
public class GenerateData {

    public static ArrayList <File> loadedFilesList = new ArrayList<File>(); //Tutaj zapisuej wczytane pliki
    public static ArrayList <ArrayList <String>> generatedDiscountPack = new ArrayList<ArrayList <String>>(); //Tutaj przechowuje wczytane dane z plików. Każdy plik w osobnej liście.
    public static ArrayList <ArrayList <ArrayList <String>>> generatedFinalPack = new ArrayList<ArrayList<ArrayList<String>>>(); //Tutaj przechowuje zestawienia kwoty abonamentu po rabatach i rabaty jakie ją tworzą.
    public static ArrayList <String> observableList = new ArrayList <String>(); //Lisat taryf na urzytek Comboboxa


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

        //TODO Przerobić sposób liczenia bo zamiast odajmować rabat mnoży razy rabat i daje to za małe wartości.

        Double baseRate = Double.parseDouble(listString.get(1));
        int iterator = 0;

        for (int i = 2 ; i < listString.size() ; i++ ){

            Double firstDiscount = Double.parseDouble(listString.get(i));
            Double firstSummary = baseRate * (firstDiscount/100);
            listOut.add(new ArrayList<String>());
            listOut.get(iterator).add(firstSummary.toString());
            listOut.get(iterator).add(listString.get(i));
            iterator++;

            for (int j = i+1 ; j < listString.size() ; j++ ){

                Double secondDiscount = Double.parseDouble(listString.get(j));
                Double secondSummary = (baseRate * (firstDiscount/100))*(secondDiscount/100);
                listOut.add(new ArrayList<String>());
                listOut.get(iterator).add(secondSummary.toString());
                listOut.get(iterator).add(listString.get(i));
                listOut.get(iterator).add(listString.get(j));
                iterator++;

                for (int k = j+1 ; k < listString.size() ; k++ ){

                    Double thirdDiscount = Double.parseDouble(listString.get(k));
                    Double thirdSummary = ((baseRate * (firstDiscount/100))*(secondDiscount/100))*(thirdDiscount/100);
                    listOut.add(new ArrayList<String>());
                    listOut.get(iterator).add(thirdSummary.toString());
                    listOut.get(iterator).add(listString.get(i));
                    listOut.get(iterator).add(listString.get(j));
                    listOut.get(iterator).add(listString.get(k));
                    iterator++;

                    for (int l = k+1 ; l < listString.size() ; l++ ){

                        Double fourthDiscount = Double.parseDouble(listString.get(l));
                        Double fourthSummary = (((baseRate * (firstDiscount/100))*(secondDiscount/100))*(thirdDiscount/100))*(fourthDiscount/100);
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

    public static void k (){

        int licznik = 0;
        for (ArrayList<ArrayList<String>> lvl1: GenerateData.generatedFinalPack) {
            System.out.println("PLIK " + (licznik+1) +  " ---------------------------------------");
            for (ArrayList<String> lvl2: lvl1) {
                System.out.println(lvl2);
            }
            licznik++;
        }
    }


    public static Integer choicePosition(Object choiceValue) throws Exception{

        Integer summary = 0;

        try {

            for (Object id: GenerateData.observableList) {
                if (choiceValue.equals(id)){
                    break;
                }
                summary++;
            }

        } catch (Exception error){}

        return summary;
    }

    public static Integer arraySize (Object choiceValue) throws Exception {

        Integer summary = generatedDiscountPack.get(choicePosition(choiceValue)).size();

        return summary;
    }
}