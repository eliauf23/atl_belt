package com.company;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//TODO: create library of parts as a list of strings
//make a hashmap with key = name (String) & value = part (Part)

/**
 * Class used to build library of parts from text file (end goal).
 * Currently, part library is hard coded as a HashMap with KEY=str, VALUE = parts.
 */
public class PartLibrary {
  static HashMap<String, Part> lib;
  static List<Part> partList;
  //measurement labels:
  private static int start_inside;
  private static int start_outside;
  private static int end_inside;
  private static int end_outside;

  public static int getStartInside() {
    return start_inside;
  }

  public static void setStartInside(int index) {
    start_inside = index;
  }

  public static int getStartOutside() {
    return start_outside;
  }

  public static void setStartOutside(int index) {
    start_outside = index;
  }

  public static int getEndInside() {
    return end_inside;
  }

  public static void setEndInside(int index) {
    end_inside = index;
  }

  public static int getEndOutside() {
    return end_outside;
  }

  public static void setEndOutside(int index) {
    end_outside = index;
  }


  PartLibrary() {
    lib = new HashMap<>();
    partList = new ArrayList<>();
  }

  /**
   * Creates bill of materials from partList.
   * Puts stuff into bill of materials hash map that's passed in by reference as a param
   *
   * @param billOfMaterials lists parts & amounts of respective parts in design.
   * @param partList        list.
   */
  public static void createBillOfMaterials(HashMap<String,
      Integer> billOfMaterials, List<Part> partList) {
    for (Part p : partList) {
      if (billOfMaterials.containsKey(p.getName())) {
        billOfMaterials.put(p.getName(), billOfMaterials.get(p.getName()) + 1);
      } else {
        billOfMaterials.put(p.getName(), 1);
      }
    }
  }

  /**
   * Create part library from text file where each line has the format:
   * "name"=(<height></height>, <width></width>, <color></color>).
   *
   * @param fileName name of file.
   * @throws IOException
   */
  public void setupPartLibraryFromTextFile(String fileName) throws IOException {
    //TODO: implement this method
    //first use FileCollector? to get file from user input into dialogue box.
    //next parse thru file line by line & tokenize into name, height, width, color
    //then,
  }

  /**
   * Create part library from text file where each line has the format:
   * "name"=(<height></height>, <width></width>, <color></color>).
   */
  public void setupPartLibraryWithTestParts() {

    this.addPart("washer", 1.0, 0.5, Draw.BLACK);
    this.addPart("disk", 4.0, 1.0, Draw.DARK_GRAY);
    this.addPart("gasket", 2.0, 1.0, Draw.PRINCETON_ORANGE);
    this.addPart("std_spacer", 2.0, 3.5, Draw.MEDIUM_BLUE);
    this.addPart("short_spacer", 2.0, 2.0, Draw.CYAN);
    //TODO: remove this after I figure out how to read in input from text file
  }

  /**
   * You can't insert two parts with the same name.
   * But, you can use two parts with the exact same values ->
   * (will get a console message saying that you've added two parts with
   * different names & the same specifications.
   */
  private void addPart(String name, double height, double width, Color color) {

    if (!lib.containsKey(name) && height > 0 && width > 0) {
      lib.put(name, new Part(height, width, color));
    } else {
      System.out.println("Error: part with this name is already in library "
          + "OR height or width of part <= 0");
    }
  }


  //properties

  //constructor

  //methods (search, get number of parts, check if empty etc.)

  /**
   * Function to remove part from part library.
   *
   * @param name - name of part
   * @return - returns Part that has been removed from Hash Map
   */
  public Part removePart(String name) {
    return lib.remove(name);
  }


  //inclusive distance
  public static double calcDistIncludingEndpts(List<Part> partList, int start, int end) {
    double dist = 0.0;
    if (start <= end) {
      for (int i = start; i <= end; i++) {
        dist += partList.get(i).getWidth();
      }
      return dist;
    } else {
      return calcDistIncludingEndpts(partList, end, start);
    }

  }

  //TODO: implement calculate distance function - inclusive for both ends: i.e. from beginning of part 1 to end of part 2 if index of p1 < index p2
  public static double calcDist(int index1, int index2) {
    return calcDistIncludingEndpts(partList, index1, index2);
  }


}
