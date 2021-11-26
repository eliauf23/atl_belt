package com.company;

import static com.company.myColors.MyColors.getColor;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Class used to build library of parts from text file (end goal).
 * Currently, part library is hard coded as a HashMap with KEY=str, VALUE = parts.
 */
public class PartLibrary {

  /* Fields */
  HashMap<String, Part> lib;
  List<Part> partList;
  List<Measurement> measurementList;
  double sumOfWidths;
  int numParts = 0;

  /* Constructors */

  PartLibrary() {
    this.lib = new HashMap<>();
    this.partList = new ArrayList<>();
    this.measurementList = new ArrayList<>();
    sumOfWidths = 0.0;
  }

  public double getSumOfWidths() {
    return sumOfWidths;
  }

  public void setSumOfWidths() {
    double result = 0.0;
    for (Part p : this.partList) {
      result += p.getWidth();
      numParts++;
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
      if (!p.getName().equalsIgnoreCase("end")) {
        if (billOfMaterials.containsKey(p.getName())) {
          billOfMaterials.put(p.getName(), billOfMaterials.get(p.getName()) + 1);
        } else {
          billOfMaterials.put(p.getName(), 1);
        }
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

  public List<Measurement> getMeasurementList() {
    return measurementList;
  }

  public void setPartList(List<Part> partList) {
    this.partList = partList;
  }


  /**
   * Create part library from text file where each line has the format:
   * "name"=(<height></height>, <width></width>, <color></color>).
   *
   * @throws IOException
   */
  public void setupPartLibraryFromTextFile() throws IOException {
    File f = FileIO.getTextFileFromUser("Select file with part library: ");
    initializePartLibraryFromFile(f);
  }


  public void initializePartLibraryFromFile(File file) throws IOException {
    List<String> lines = Files.readAllLines(file.toPath());

    for (String line : lines) {
      //tokenize string into "string", height, width, color
      String[] tokens = line.split(" ", 5);

      //TODO: need to make sure arguments are valid


      addPart(tokens[0], Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]), getColor(tokens[3]), getColor(tokens[4]));
    }
    //add end part
    addPart("end", 0.0, 0.0, getColor("CLEAR"), getColor("CLEAR"));
    System.out.println("Part Library is initialized");
  }

  /**
   * You can't insert two parts with the same name.
   * But, you can use two parts with the exact same values ->
   * (will get a console message saying that you've added two parts with
   * different names & the same specifications.
   */
  public void addPart(String name, double height, double width, Color color, Color greyscaleColor) {

    if (!lib.containsKey(name) && height > 0 && width > 0) {
      lib.put(name, new Part(height, width, color, greyscaleColor));
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

  public void drawMeasurementArrows() {
    //todo: implement!
  }
}
