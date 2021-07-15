package com.company;

import static com.company.Main.SCALE;
import static com.company.Main.Y_CENTER;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Part {

  private String name;
  private Color color;
  private double height;
  private double width;

  //default constructor
  Part(String str, HashMap<String, Part> map) {
    this.name = str;
    this.height = map.get(str).getHeight();
    this.width = map.get(str).getWidth();
    this.color = map.get(str).getColor();

  }

  //parameterized constructor w/o name
  Part(double h, double w, Color c) {
    this.height = h;
    this.width = w;
    this.color = c;
  }

  //parameterized constructor - full
  Part(double h, double w, Color c, String n) {
    this.name = n;
    this.height = h;
    this.width = w;
    this.color = c;
  }


  public static void drawPartsFromList(List<Part> partList, double totalWidth) {

    double currentXvalue = 0.5 * (SCALE - totalWidth + partList.get(0).getWidth());
    double halfWidth;

    for (Part p : partList) {
      halfWidth = 0.5 * p.getWidth();
      currentXvalue += halfWidth;
      drawPart(p, currentXvalue);
      currentXvalue += halfWidth;
    }
  }

  public static double calcSumOfWidths(List<Part> partList) {
    double sumOfWidths = 0.0;
    for (Part p : partList) {
      sumOfWidths += p.getWidth();
    }
    return sumOfWidths;
  }

  public static void drawPart(Part p, double currentXvalue) {
    double height = p.getHeight();
    double width = p.getWidth();
    Color color = p.getColor();
    Draw.setPenColor(color);
    Draw.filledRectangle(currentXvalue, Y_CENTER, width / 2.0, height / 2.0);
  }


  public static void drawShaft(double shaftLength) {
    //default thickness = 1 & default color is light grey
    Draw.setPenColor(Draw.LIGHT_GRAY);
    Draw.filledRectangle(SCALE / 2.0, SCALE / 2.0, shaftLength / 2.0, 0.25);
  }

  public static List<Part> convertFileToPartList(File file) throws IOException {
    PartLibrary pl = new PartLibrary();
    //TODO: replace with method that gets part library from text file
    pl.setupPartLibraryWithTestParts();

    HashMap<String, Part> library = PartLibrary.lib;

    List<String> lines = Files.readAllLines(file.toPath());
    List<Part> partList = new ArrayList<>();

    int index = 0;
    for (String str : lines) {
      if (index == 0 && str != null) {
        if (Double.parseDouble(str) > 0) {
          Main.shaft_len = Double.parseDouble(str);
        }
      } else {
        //trim any whitespace & add Part obj. to array list
        assert (str != null);
        Part p = new Part(str.trim(), library);

        partList.add(p);
      }

      index++;
    }
    return partList;
  }

  public static List<Part> convertFileToPartList(String fileName) throws IOException {
    PartLibrary pl = new PartLibrary();

    //TODO: replace with method that gets part library from text file
    pl.setupPartLibraryWithTestParts();

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
        assert (str != null);
        Part p = new Part(str.trim(), library);

        partList.add(p);
      }

      index++;
    }
    return partList;
  }

  public static void displayResults(double sumOfWidths, HashMap<String, Integer> billOfMaterials) {
    Draw.show();
    System.out.println("\nResults:");
    System.out.println("Sum of all widths: " + sumOfWidths);
    System.out.println("Bill of Materials: " + billOfMaterials.toString());

  }

  //inclusive distance (index starts @ 0, ends at # of components - 1
  public static double calcDistIncludingEndpts(List<Part> partList, int start, int end) {
    double dist = 0.0;
    for (int i = start; i <= end - 1; i++) {
      dist += partList.get(i).getWidth();
    }
    return dist;
  }

  //getter & setter methods
  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  @Override
  public boolean equals(Object obj) throws NullPointerException {

    Part p = new Part(0, 0, Draw.BLACK);
    if (obj != null && obj.getClass() == p.getClass()) {
      Part objPart = (Part) obj;
      return objPart.getHeight() == (this.getHeight()) && objPart.getWidth() == (this.getWidth());
    }
    return false;
  }

  /**
   * Returns a string representation of the object.
   */
  @Override
  public String toString() {
    return this.getName();
  }

}

