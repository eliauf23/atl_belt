package com.company;

import static com.company.Draw.openFileAndDrawContents;
import static com.company.Operations.setup;

import java.io.File;
import java.io.IOException;

public class Main {


  final static int CANVAS_WIDTH = 2048;
  final static int CANVAS_HEIGHT = 2048;
  final static int SCALE = 100;

  static double Y_CENTER = SCALE/2.0;
  static double shaft_len = 0.0;
  private static IOException FileNotFoundException;


  public static void main(String[] args) throws IOException {
    setup(CANVAS_WIDTH, CANVAS_HEIGHT, SCALE);
    String fileName;
    File selectedFile;
    /*
    //OPTION 1: hard-coded file name
    filename = "C:\\Users\\eliau\\IdeaProjects\\atl_belt\\input\\test1.txt";
   
     List<Part> partList = convertFileToPartList(fileName);
    HashMap<String, Integer> billOfMaterials = new HashMap<>();
    PartLibrary.createBillOfMaterials(billOfMaterials, partList);
    double sumOfWidths = calcSumOfWidths(partList);
    drawShaft(shaft_len, sumOfWidths);
    drawPartsFromList(partList, sumOfWidths);
    displayResults(sumOfWidths, billOfMaterials);
     */
    
    //option 2: prompt user to enter file with dialogue window
    openFileAndDrawContents();
  }

}