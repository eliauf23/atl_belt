package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Operations {


  public static List<Part> convertFileToPartList(String fileName) throws IOException {
    PartLibrary pl = new PartLibrary();

    HashMap<String, Part> library = PartLibrary.lib;

    List<String> lines = Files.readAllLines(Paths.get(fileName));
    List<Part> partList = new ArrayList<>();

    int index = 0;
    for (String str : lines) {
      if (index == 0 && str != null) {
        if (Double.parseDouble(str) > 0) {
          Main.shaft_len = Double.parseDouble(str);
        }
      } else {
        //trim any whitespace & add Part obj. to array list
        assert(str!=null);
        Part p = new Part(str.trim(), library);

        partList.add(p);
      }

      index++;
    }
    return partList;
  }


  public static void setup(int CANVAS_WIDTH, int CANVAS_HEIGHT, int SCALE) {
    //Initialize canvas size, scale
    StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
    StdDraw.setScale(0, SCALE); //i.e. x and y range from 0, 100 w/ (0,0) in bottom left corner
    StdDraw.setPenRadius(0.005);
    StdDraw.enableDoubleBuffering();

  }

  public static void displayResults(double sumOfWidths, HashMap<String, Integer> billOfMaterials) {
    StdDraw.show();
    System.out.println("Sum of all widths: " + sumOfWidths);
    System.out.println("Bill of Materials: " + billOfMaterials.toString());

  }


}
