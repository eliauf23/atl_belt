package com.company;

import static com.company.Main.SCALE;
import static com.company.Main.Y_CENTER;

import java.awt.Color;
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

  public static void drawPartsFromList(List<Part> partList, double total_width) {

    //TODO: change this function so it draws parts from center
    double x_current = 0.5 * (SCALE - total_width + partList.get(0).getWidth());
    double half_width;

    for (Part p : partList) {
      half_width = 0.5 * p.getWidth();
      x_current += half_width;
      drawPart(p, x_current, Y_CENTER);
      x_current += half_width;
    }
  }

  public static double calcSumOfWidths(List<Part> partList) {
    double sumOfWidths = 0.0;
    for (Part p : partList) {
      sumOfWidths += p.getWidth();
    }
    return sumOfWidths;
  }

  public static void drawPart(Part p, double x_current, double Y_CENTER) {
    double height = p.getHeight();
    double width = p.getWidth();
    Color color = p.getColor();
    StdDraw.setPenColor(color);
    StdDraw.filledRectangle(x_current, Y_CENTER, width / 2.0, height / 2.0);
  }

  public static void drawShaft(double shaftLength, double sumOfWidths) {
    //default thickness = 1 & default color is light grey

    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);

    StdDraw.filledRectangle(SCALE / 2.0, SCALE / 2.0, shaftLength / 2.0, 0.25);


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
  //other function

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

    Part p = new Part(0, 0, StdDraw.BLACK);
    if (obj != null && obj.getClass() == p.getClass()) {
      Part obj_part = (Part) obj;
      return obj_part.getHeight() == (this.getHeight()) && obj_part.getWidth() == (this.getWidth());
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