package com.company;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  static final int CANVAS_WIDTH = 2048;
  static final int CANVAS_HEIGHT = 1024;
  static final int SCALE = 100;
  final static double Y_CENTER = 0.5*SCALE;

  public static void main(String[] args) throws IOException {

//TODO: Config. dwg environment
    //Initialize canvas size, scale
    StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
    StdDraw.setScale(0, SCALE); //i.e. x and y range from 0, 100 w/ (0,0) in bottom left corner


//TODO: File I/O


    Scanner scnr = new Scanner(System.in);
    String fileName = scnr.next();
    System.out.println("Please enter file name (include full path if file isn't located in current working directory): ");


    List<String> fileLines = readTextFileByLines(fileName);
    int num_parts = 0;
    List<Part> partList = new ArrayList<>();

    //goal here is to convert text file into list of parts
    for (String str : fileLines) {
      //trim any whitespace
      str = str.trim();
      partList.add(new Part(str));
      num_parts++;
      //match string to one of the part names in universal part list


      //increment count of that part
      //if that part has a star -> indicate that the item begins or ends a measurement

      //sum widths & # of each item to make bill of materials
    }
    //first iterate over array list to find any deliniators

    //sum up total length of rollers

    //create variables for tags e.g. "inside-inside" and "outside-outside" measurements
    //if part has width of 0, then it's a tag -> I want to keep track of indeces of tags


    double shaftLength = 0.0;
    double insideEndRolls = 0.0;

    int beginIER_index;

    double outsideEndRolls = 0.0;

          /*
        System.out.println("Welcome! Would you like to enter data via command line arguments?");
        System.out.println("Please enter name of text file you'd like to load: ");
        */
        /*
        - load file
        - parse line by line
        - draw objects & keep track of total width
        - draw shaft @ end
        - include any text measurements - using command line args
        - port app to be used online? install java on dads computer?
        */

    //TODO: draw rollers
    //1. create instance of each object

    Part gasket = new Part(1.0, 1.0, StdDraw.PRINCETON_ORANGE);
    Part washer = new Part(0.75, 0.5, StdDraw.BLACK);
    Part disk = new Part(4.0, 1.0, StdDraw.DARK_GRAY);
    Part std_spacer = new Part(1.0, 3.5, StdDraw.GRAY);
    Part short_spacer = new Part(1.0, 2.0, StdDraw.GRAY);


    StdDraw.setPenRadius(0.0005);


    //create function to draw stuff given list created from text file

    double sumOfWidths = drawPartsFromList(partList, Y_CENTER);


    StdDraw.show();
    System.out.println("Sum of all widths: " + sumOfWidths);

  }

  public static List<String> readTextFileByLines(String fileName) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get(fileName));
    return lines;
  }
}

