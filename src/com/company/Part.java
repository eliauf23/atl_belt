package com.company;


import static com.company.Draw.getScale;
import static com.company.Draw.getYCenter;
import static com.company.Draw.isGreyscale;
import static com.company.Draw.setPenColor;
import static com.company.Draw.setPenRadius;
import static com.company.Draw.setShaftLength;

import java.awt.Color;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;


public class Part {
  private String name;
  private Color color; //for exploded view - meant to contrast
  private Color greyscaleColor;
  private double height;
  private double width;
  public int position; //initialized to -1

  public double getX_start() {
    return x_start;
  }

  public void setX_start(double x_start) {
    this.x_start = x_start;
  }

  public double x_start;

  public Part(String str, HashMap<String, Part> map, int position) {
    assert (map != null);
    this.name = str;
    this.height = map.get(str).getHeight();
    this.width = map.get(str).getWidth();
    this.color = map.get(str).getColor();
    this.greyscaleColor = map.get(str).getGreyscaleColor();
    this.position = position;

  }


//  Part() {
//    this.name = "default part";
//    this.height = 0.0;
//    this.width = 0.0;
//    this.color = Color.BLACK;
//
//  }

  //default constructor
  Part(String str, HashMap<String, Part> map) {
    this.name = str;
    this.height = map.get(str).getHeight();
    this.width = map.get(str).getWidth();
    this.color = map.get(str).getColor();
    this.greyscaleColor = map.get(str).getGreyscaleColor();
  }

  //parameterized constructor w/o name
  Part(double h, double w, Color c, Color gc) {
    this.height = h;
    this.width = w;
    this.color = c;
    this.greyscaleColor = gc;
    this.position = -1;

  }
  /*Part(double h, double w, Color c) {
    this.height = h;
    this.width = w;
    this.color = c;
  }
*/

  @Override
  public int hashCode() {
    return Objects.hash(position);
  }

  public static void drawPartsFromList(PartLibrary partLibrary) {
    List<Part> partList = partLibrary.getPartList();
    double totalWidth = partLibrary.getSumOfWidths();

    double currentXvalue = 0.5 * (getScale() - totalWidth + partList.get(0).getWidth());

    double halfWidth;

    for (Part p : partList) {
      p.setX_start(currentXvalue);
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
    Color color;

    if (isGreyscale()) { //add border
      //draw border
      color = p.getGreyscaleColor();
      Draw.setPenColor(color);
      Draw.filledRectangle(currentXvalue, getYCenter(), width / 2.0, height / 2.0);

    } else {
      color = p.getColor();
      Draw.setPenColor(color);
      Draw.filledRectangle(currentXvalue, getYCenter(), width / 2.0, height / 2.0);
      Part.drawBorder(Color.white, 0.001, currentXvalue, width, height);

    }


  }


  private static void drawBorder(Color borderColor, double penRadius, double currentXvalue, double width, double height) {
    setPenColor(borderColor);
    setPenRadius(penRadius);
    Draw.rectangle(currentXvalue, getYCenter(), width / 2.0, height / 2.0);

  }

  public static void drawShaft(double shaftLength) {
    //default thickness = 1 & default color is light grey
    Draw.setPenColor(Color.LIGHT_GRAY);
    Draw.filledRectangle(getScale() / 2.0, getScale() / 2.0, shaftLength / 2.0, 0.25);
  }


/*
  public static void generatePartListFromFile(File file, PartLibrary partLibrary) throws IOException {

    List<String> lines = Files.readAllLines(file.toPath());
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
  }*/

  public static void generatePartListFromNewFile(File file, PartLibrary partLibrary) throws Exception {

    try {
      List<String> lines = Files.readAllLines(file.toPath());
      List<Part> partList = partLibrary.getPartList();
      List<Measurement> measurementList = partLibrary.getMeasurementList();

      int index = 0;
      int position = 0;
      for (String str : lines) {
        //get shaft length from first line
        if (str == null || !str.isEmpty() && str.charAt(0) != '#') {
          if (position == 0 && str.contains("shaft=")) {
            String[] arr = str.split("=");
            str = arr[1];
            if (Double.parseDouble(str) > 0) {
              setShaftLength(Double.parseDouble(str));
            } else {
              throw new Exception("Shaft length can't be negative!");
            }
          } else if (str.charAt(0) == '<' && str.charAt(1) != '/' && str.endsWith(">")) {
            //set beginning of measurement
            Measurement m = new Measurement(str.substring(1, str.length() - 1), position);
            System.out.println(str);
            System.out.println(str.substring(1, str.length() - 1));
            measurementList.add(m);
          } else if (str.charAt(0) == '<' && str.charAt(1) == '/' && str.endsWith(">")) {

            //check that name matches an opening tag
            String name = str.substring(2, str.length() - 1);
            System.out.println(str);
            System.out.println(str.substring(2, str.length() - 1));


            ListIterator<Measurement> iter = measurementList.listIterator();

            while (iter.hasNext()) {
              Measurement m = iter.next();
              if (m.getName().equals(name)) {
                //set end to be part at prev position
                m.setEndIdx(position);
                break;
              }
            }
          } else {
            String[] str_arr = str.split("x");
            String name = str_arr[0].trim();
            String quant_str = str_arr[1].trim();
            int quantity = Integer.parseInt(quant_str);
            System.out.println("quantity: " + quantity);
            for (int i = 0; i < quantity; i++) {
              System.out.println("Position before adding part: " + position);
              System.out.println("Length of part list before adding part: " + partList.size());

              partList.add(new Part(name, partLibrary.getLibHashMap(), position));
              position++;
              System.out.println("Length of part list after adding part: " + partList.size());

              System.out.println("Position after incrementing adding part: " + position);

            }
          }
          index++;
        } else if (str.isEmpty() || (str.length() > 0 && str.charAt(0) == '#')) {
          continue; //probably EOF? todo: check this!
        } else {
//          System.err.println(str);
//          throw new Exception("Issue with configuration file @ line " + index);
          //return?
        }


      }
    } catch (Exception e) {
      System.err.println(e.toString());
      e.printStackTrace();
    }
  }

  //getter & setter methods
  public Color getGreyscaleColor() {
    return greyscaleColor;
  }

  public void setGreyscaleColor(Color greyscaleColor) {
    this.greyscaleColor = greyscaleColor;
  }

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

    Part p = new Part(0, 0, Color.BLACK, Color.BLACK);
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
    // return this.getName();
    return this.getName() + ", width = " + this.getWidth() + ", height = " + this.getHeight() + this.getColor().toString();
  }

}

