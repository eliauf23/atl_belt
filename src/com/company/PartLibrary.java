package com.company;

import com.company.Resources.myColors;
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
  HashMap<String, Part> lib;
  List<Part> partList;
  double sumOfWidths;


  //measurement labels:
  private int start_inside;
  private int start_outside;
  private int end_inside;
  private int end_outside;

  PartLibrary() {
    this.lib = new HashMap<>();
    this.partList = new ArrayList<>();
    sumOfWidths = 0.0;
  }

  public double getSumOfWidths() {
    return sumOfWidths;
  }

  public void setSumOfWidths() {
    double result = 0.0;
    for (Part p : this.partList) {
      result += p.getWidth();
    }
    this.sumOfWidths = result;
  }

  /**
   * Creates bill of materials from partList.
   * Puts stuff into bill of materials hash map that's passed in by reference as a param
   */
  public HashMap<String, Integer> createBillOfMaterials() {
    HashMap<String, Integer> billOfMaterials = new HashMap<>();
    for (Part p : this.partList) {
      if (billOfMaterials.containsKey(p.getName())) {
        billOfMaterials.put(p.getName(), billOfMaterials.get(p.getName()) + 1);
      } else {
        billOfMaterials.put(p.getName(), 1);
      }
    }
    return billOfMaterials;
  }

  public void setLibrary(HashMap<String, Part> lib) {
    this.lib = lib;
  }

  public HashMap<String, Part> getLibHashMap() {
    return lib;
  }

  public List<Part> getPartList() {
    return partList;
  }

  public void setPartList(List<Part> partList) {
    this.partList = partList;
  }

  public int getStartInside() {
    return start_inside;
  }

  public void setStartInside(int index) {
    start_inside = index;
  }

  public int getStartOutside() {
    return start_outside;
  }

  public void setStartOutside(int index) {
    start_outside = index;
  }

  public int getEndInside() {
    return end_inside;
  }

  public void setEndInside(int index) {
    end_inside = index;
  }

  public int getEndOutside() {
    return end_outside;
  }

  public void setEndOutside(int index) {
    end_outside = index;
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
    this.addPart("diff", 1.0, 0.5, myColors.WHITE);
    this.addPart("washer", 1.0, 0.5, myColors.BLACK);
    this.addPart("disk", 4.0, 1.0, myColors.DARK_GRAY);
    this.addPart("gasket", 2.0, 1.0, myColors.PRINCETON_ORANGE);
    this.addPart("std_spacer", 2.0, 3.5, myColors.MEDIUM_BLUE);
    this.addPart("short_spacer", 2.0, 2.0, myColors.CYAN);
    //TODO: remove this after I figure out how to read in input from text file
  }

  /**
   * You can't insert two parts with the same name.
   * But, you can use two parts with the exact same values ->
   * (will get a console message saying that you've added two parts with
   * different names & the same specifications.
   */
  public void addPart(String name, double height, double width, Color color) {

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

}
