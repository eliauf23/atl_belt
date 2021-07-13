package com.company;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;


//TODO: create library of parts as a list of strings
//make a hashmap with key = name (String) & value = part (Part)

public class PartLibrary {
  static HashMap<String, Part> lib;

  PartLibrary() {
    lib = new HashMap<>();

    lib.put("washer", new Part(1.0, 0.5, Draw.BLACK, "washer"));
    lib.put("disk", new Part(4.0, 1.0, Draw.GRAY, "disk"));
    lib.put("gasket", new Part(2.0, 1.0, Draw.PRINCETON_ORANGE, "gasket"));
    lib.put("std_spacer", new Part(2.0, 3.5, Draw.MEDIUM_BLUE, "std_spacer"));
    lib.put("short_spacer", new Part(2.0, 2.0, Draw.CYAN, "short_spacer"));
  }


  public void addPart(String name, double height, double width, Color color) {
    lib.put(name, new Part(height, width, color));
  }

  public Part removePart(String name) {
    return lib.remove(name);
  }


  //properties

  //constructor

  //methods (search, get number of parts, check if empty etc. )

  public static void createBillOfMaterials(HashMap<String, Integer> billOfMaterials, List<Part> partList) {
    for (Part p : partList) {
      if (billOfMaterials.containsKey(p.getName())) {
        billOfMaterials.put(p.getName(), billOfMaterials.get(p.getName()) + 1);
      } else {
        billOfMaterials.put(p.getName(), 1);
      }
    }
  }


}
