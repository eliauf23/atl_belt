package com.company;

import com.company.StdDraw;
import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


//TODO: create library of parts as a list of strings
//make a hashmap with key = name (String) & value = part (Part)


public class Part {
  private String name;
  private Color color;
  private double height;
  private double width;

  //String materials; TODO: figure out how to implement materials to consturct Bill of Materials

  //default constructor
  Part(String str) {

    //check if string matches name of any part in library


    //if so, construct object w/ the height, width, color associated w/ that name

    for (:
         ) {

    }



  }

  //parameterized constructor
  Part(double h, double w, Color c, String n) {
    this.name = n;
    this.height = h;
    this.width = w;
    this.color = c;
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
  //other function


  public static double drawPartsFromList(List<Part> partList, double Y_CENTER) {
    double x_current = 0.0;
    for (Part p: partList) {

      drawPart(p, x_current, Y_CENTER);
      x_current += p.getWidth();

    }

    return x_current;
  }


  public void drawShaft(double shaftLength, double sumOfWidths, double pageWidth) {
    //TODO: implement drawShaft function

  }

  public static void drawPart(Part p, double x_current, double Y_CENTER) {
    double height = p.getHeight();
    double width = p.getWidth();
    Color color = p.getColor();

    StdDraw.setPenColor(color);
    StdDraw.filledRectangle(x_current, Y_CENTER, width / 2.0, height / 2.0);

  }
}




}