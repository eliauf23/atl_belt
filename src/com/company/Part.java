package com.company;


import static com.company.Draw.getDrawMeasurement;
import static com.company.Draw.getPartLibrary;
import static com.company.Draw.getScale;
import static com.company.Draw.getYCenter;
import static com.company.Draw.setShaftLength;

import com.company.Resources.myColors;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;


public class Part {

  private String name;
  private Color color;
  private Color breakoutColor; //for exploded view - meant to contrast
  private double height;
  private double width;


  Part() {
    this.name = "default part";
    this.height = 0.0;
    this.width = 0.0;
    this.color = myColors.BLACK;

  }

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


  public static void drawPartsFromList(PartLibrary partLibrary) {
    List<Part> partList = partLibrary.getPartList();
    double totalWidth = partLibrary.getSumOfWidths();

    double currentXvalue = 0.5 * (getScale() - totalWidth + partList.get(0).getWidth());
    double halfWidth;

    for (Part p : partList) {
      halfWidth = 0.5 * p.getWidth();
      currentXvalue += halfWidth;
      drawPart(p, currentXvalue);
      currentXvalue += halfWidth;
    }
  }

  public static void drawPart(Part p, double currentXvalue) {
    //TODO: want to draw parts such that each part is a clickable object where you can get info and see that it's selected
    double height = p.getHeight();
    double width = p.getWidth();
    Color color = p.getColor();
    Draw.setPenColor(color);
    Draw.filledRectangle(currentXvalue, getYCenter(), width / 2.0, height / 2.0);
  }


  public static void drawShaft(double shaftLength) {
    //default thickness = 1 & default color is light grey
    Draw.setPenColor(myColors.LIGHT_GRAY);
    Draw.filledRectangle(getScale() / 2.0, getScale() / 2.0, shaftLength / 2.0, 0.25);
  }


  public static void convertFileToPartList(File file) throws IOException {
    //TODO: replace with method that gets part library from text file
    initializeFieldsInPartLibraryFromFile(getPartLibrary(), Files.readAllLines(file.toPath()));
  }

  private static void initializeFieldsInPartLibraryFromFile(PartLibrary partLibrary, List<String> lines) {
    List<Part> partList = partLibrary.getPartList();
    int index = 0;
    for (String str : lines) {
      if (index == 0 && str != null) {
        if (Double.parseDouble(str) > 0) {
          setShaftLength(Double.parseDouble(str));
        }
      } else if (index == 1 && str != null) {
        //second line should specify outside_start, inside_start, inside_end, outside_end
        getDrawMeasurement().setMeasurementIndices(getPartLibrary(), str);
      } else {
        //trim any whitespace & add Part obj. to array list
        assert (str != null);
        partList.add(new Part(str.trim(), partLibrary.getLibHashMap()));
      }

      index++;
    }
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

    Part p = new Part(0, 0, myColors.BLACK);
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

