/*
package com.company;

import static com.company.Draw.setGreyscale;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class drawingUpdater {

  public static List<DrawingElement> getDrawingElementsList() {
    return drawingElementsList;
  }

  public static List<DrawingElement> drawingElementsList = new ArrayList<>();



  public void addElement(String label, boolean isVisible, boolean isMeasurement ) {
    drawingElementsList.add(new DrawingElement());
  }
  public DrawingElement removeElement(String label) {
    int i = 0;
    DrawingElement elem = null;

    for (DrawingElement de: drawingElementsList) {
      if(de.getLabel().equals(label)) {
        elem = drawingElementsList.remove(i);
      } else {
        i++;
      }
    }
    return elem;
  }


}

public class DrawingElement {
  static final String insideLabel = "inside-inside";
  static final String outsideLabel = "outside-outside";
  static final String shaftLenLabel = "shaft length";
  static final String billOfMatLabel = "bill of materials";
  static final String greyscaleLabel ="Greyscale Mode";

  String label; //could be measurements, greyscale, borders, bill of mat. (i.e. any menu options that you have to draw and erase)
  boolean isVisible;

  public DrawingElement(String label, boolean isVisible) {
    this.label = label;
    this.isVisible = isVisible;
  }

  //constructors



  //getters and setters

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean isVisible) {
    this.isVisible = isVisible;
  }


  //methods - draw & erase

  public void drawElement(PartLibrary partLibrary, String name) {
    for (DrawingElement de: drawingUpdater.getDrawingElementsList()) {
      if(de.getLabel().equalsIgnoreCase(name)) {
        de.setVisible(true);

        if(name.equalsIgnoreCase(greyscaleLabel)) setGreyscale(true);

        redraw(partLibrary);


      }
    }
    if (name.equalsIgnoreCase(insideLabel)) {
      showInside = true;
    } else if (name.equalsIgnoreCase(outsideLabel)) {
      showOutside = true;
    } else if (name.equalsIgnoreCase(shaftLenLabel)) {
      showShaftLen = true;
    } else if (name.equalsIgnoreCase(billOfMatLabel)) {
      showBom = true;
    } else if() {
      greyscale = true;
      setGreyscale(true);
    } else {
      System.out.println("measurement name does not match any labels");
    }

    redraw(partLibrary);

  }

  public void eraseElement();
}

public class Measurement extends DrawingElement{

  double value;
  double xPos;
  double yPos;
  Color color;
  public Measurement(String label, boolean showElement, double value) {
    super(label, showElement);
  }
}
*/
