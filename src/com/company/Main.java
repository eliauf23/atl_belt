package com.company;

import static com.company.Operations.convertFileToPartList;
import static com.company.Operations.displayResults;
import static com.company.Operations.setup;
import static com.company.Part.calcSumOfWidths;
import static com.company.Part.drawPartsFromList;
import static com.company.Part.drawShaft;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {


  final static int CANVAS_WIDTH = 2048;
  final static int CANVAS_HEIGHT = 2048;
  final static int SCALE = 100;

  static double Y_CENTER = SCALE/2.0;
  static double shaft_len = 0.0;

  public static void main(String[] args) throws IOException {
    setup(CANVAS_WIDTH, CANVAS_HEIGHT, SCALE);

    String fileName = "C:\\Users\\eliau\\IdeaProjects\\atl_belt\\input\\test1.txt";
    List<Part> partList = convertFileToPartList(fileName);

    HashMap<String, Integer> billOfMaterials = new HashMap<String, Integer>();
    PartLibrary.createBillOfMaterials(billOfMaterials, partList);
    double sumOfWidths = calcSumOfWidths(partList);
    drawShaft(shaft_len, sumOfWidths);
    drawPartsFromList(partList, sumOfWidths);
    displayResults(sumOfWidths, billOfMaterials);
  }
}