package com.company;

import static com.company.Draw.getPartLibrary;
import static com.company.Draw.getScale;
import static com.company.Draw.getShaftLength;
import static com.company.Draw.getYCenter;
import static com.company.Draw.renderDrawing;
import static com.company.Draw.setPenColor;
import static com.company.Draw.setPenRadius;

import com.company.Resources.myColors;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;

class DrawMeasurement {


  static final String insideLabel = "inside-inside";
  static final String outsideLabel = "outside-outside";
  static final String shaftLenLabel = "shaft length";
  static final String billOfMatLabel = "bill of materials";

  private boolean showShaftLen;
  private boolean showInside;
  private boolean showOutside;
  private boolean showBom;

  DrawMeasurement() {
    showShaftLen = false;
    showInside = false;
    showOutside = false;
    showBom = false;

  }

  //x and y are center of line, value = length
  private static void arrowHelper(double x, double y, double value) {
    double x0 = x - (value / 2.0);
    double xf = x + (value / 2.0);
    double delta = 0.25;

    //draw top piece of left arrow
    Draw.line(x0, y, x0 + delta, y + delta);

    //draw bottom piece of left arrow
    Draw.line(x0, y, x0 + delta, y - delta);


    //draw top piece of right arrow
    Draw.line(xf, y, xf - delta, y + delta);

    //draw bottom piece of right arrow
    Draw.line(xf, y, xf - delta, y - delta);

  }

  public boolean showShaftLen() {
    return showShaftLen;
  }

  public void showShaftLen(boolean showShaftLen) {
    this.showShaftLen = showShaftLen;
  }

  public boolean showInside() {
    return showInside;
  }

  public void setShowInside(boolean showInside) {
    this.showInside = showInside;
  }

  public boolean showOutside() {
    return showOutside;
  }

  public void setShowOutside(boolean showOutside) {
    this.showOutside = showOutside;
  }

  public boolean showBom() {
    return showBom;
  }

  public void setShowBom(boolean showBom) {
    this.showBom = showBom;
  }

  public boolean getStateofItem(String itemName) {
    if (itemName.equalsIgnoreCase(insideLabel)) {
      return showInside();
    } else if (itemName.equalsIgnoreCase(outsideLabel)) {
      return showOutside();
    } else if (itemName.equalsIgnoreCase(shaftLenLabel)) {
      return showShaftLen();
    } else if (itemName.equalsIgnoreCase(billOfMatLabel)) {
      return showBom();
    } else {
      throw new IllegalArgumentException("trying to get state of item that doesnt exist");
    }
  }


  public void arrow(double x0, double y, double x1, double value, Color color) {
    setPenColor(color);

    arrowHelper((x0 + x1) / 2.0, y, value);
    arrowHelper((x0 + x1) / 2.0, y, value);

  }

  public void drawMeasurement(PartLibrary partLibrary, String measurementName) {

    if (measurementName.equalsIgnoreCase(insideLabel)) {
      showInside = true;
    } else if (measurementName.equalsIgnoreCase(outsideLabel)) {
      showOutside = true;
    } else if (measurementName.equalsIgnoreCase(shaftLenLabel)) {
      showShaftLen = true;
    } else if (measurementName.equalsIgnoreCase(billOfMatLabel)) {
      showBom = true;
    } else {
      System.out.println("measurement name does not match any labels");
    }

    redraw(partLibrary);

  }

  public void eraseMeasurement(PartLibrary partLibrary, String measurementName) {

    if (measurementName.equalsIgnoreCase(insideLabel)) {
      showInside = false;
    } else if (measurementName.equalsIgnoreCase(outsideLabel)) {
      showOutside = false;
    } else if (measurementName.equalsIgnoreCase(shaftLenLabel)) {
      showShaftLen = false;
    } else if (measurementName.equalsIgnoreCase(billOfMatLabel)) {
      showBom = false;
    } else {
      System.out.println("measurement name does not match any labels");
    }

    redraw(partLibrary);
  }

  private void redraw(PartLibrary partLibrary) {
    Draw.clear();
    renderDrawing(partLibrary);
    renderMeasurements(this, showInside, showOutside, showShaftLen, showBom);
    Draw.show();
  }

  void drawMeasurement(DrawMeasurement dm, PartLibrary partLibrary, String type, double x, double y, Color color) {
    double value = 0.0;

    if (type.equalsIgnoreCase(billOfMatLabel)) {
      return;
    }


    if (type.equalsIgnoreCase(insideLabel)) {

      value = calcDistIncludingEndpts(partLibrary.getPartList(), partLibrary.getStartInside(), partLibrary.getEndInside());

    } else if (type.equalsIgnoreCase(outsideLabel)) {
      value = calcDistIncludingEndpts(partLibrary.getPartList(), partLibrary.getStartOutside(), partLibrary.getEndOutside());

    } else if (type.equalsIgnoreCase(shaftLenLabel)) {
      value = getShaftLength();
    } else {
      System.out.println("Error: doesn't match any measurement options");
    }


    Draw.setPenColor(color);
    setPenRadius(0.0005);
    Draw.line(x - value / 2.0, y, x + value / 2.0, y);

    dm.arrow(x - value / 2.0, y, x + value / 2.0, value, color);

    //draw text label underneath
    String sb = type +
        " = " +
        value;
    Draw.text(x, y - 2, sb);

    Draw.show();
  }

  public void renderMeasurements(DrawMeasurement dm, boolean inside, boolean outside, boolean shaft, boolean bom) {

    //where should x and y start?
    double height = 5;
    double x = getScale() / 2.0;
    double y = getYCenter() - height;

    if (inside) {
      drawMeasurement(dm, getPartLibrary(), insideLabel, x, y, myColors.NAVY_BLUE);
      y -= height;
    }
    if (outside) {
      drawMeasurement(dm, getPartLibrary(), outsideLabel, x, y, myColors.NAVY_BLUE);
      y -= height;
    }
    if (shaft) {
      drawMeasurement(dm, getPartLibrary(), shaftLenLabel, x, y, myColors.NAVY_BLUE);
      y -= height;
    }
    if (bom) {
      drawBillOfMaterials(x, 75, getPartLibrary().createBillOfMaterials());
    }
  }

  void setMeasurementIndices(PartLibrary partLibrary, String input) {
    String[] p = input.split(",", 4);
    int[] ans = new int[4];

    int currentValue = 0;
    int index = 0;
    for (String s : p) {
      if (s != null && index < 4) {
        s = s.trim();
        currentValue = Integer.parseInt(s);
        assert (currentValue >= 0);
        ans[index] = currentValue;
        index++;
      }
    }

    for (int i = 0; i < ans.length; i++) {
      if (ans[i] == -1) {
        ans[0] = -1;
        ans[1] = -1;
        ans[2] = -1;
        ans[3] = -1;
        break;
      }
    }
    setMeasurementIndicesHelper(partLibrary, ans);
  }

  private void setMeasurementIndicesHelper(PartLibrary partLibrary, int[] arr) {
    partLibrary.setStartOutside(arr[0]);
    partLibrary.setStartInside(arr[1]);
    partLibrary.setEndInside(arr[2]);
    partLibrary.setEndOutside(arr[3]);
  }

  //inclusive distance
  public double calcDistIncludingEndpts(List<Part> partList, int start, int end) {
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

  public void drawBillOfMaterials(double x, double y, HashMap<String, Integer> billOfMaterials) {
    Draw.text(x, y, "Bill of Materials: " + billOfMaterials.toString());

    Draw.show();
  }

}
