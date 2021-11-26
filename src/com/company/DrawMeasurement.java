package com.company;

import static com.company.Draw.getPartLibrary;
import static com.company.Draw.getScale;
import static com.company.Draw.getShaftLength;
import static com.company.Draw.getYCenter;
import static com.company.Draw.renderDrawing;
import static com.company.Draw.setGreyscale;
import static com.company.Draw.setPenColor;
import static com.company.Draw.setPenRadius;
import static com.company.Draw.show;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class DrawMeasurement {


  static final String measurementsLabel = "display measurements";
  static final String outsideLabel = "outside-outside";
  static final String shaftLenLabel = "shaft length";
  static final String billOfMatLabel = "bill of materials";
  static final String greyscaleLabel = "Greyscale Mode";

  private boolean showShaftLen;
  private boolean measurements;
  private boolean showOutside;
  private boolean showBom;
  private boolean greyscale;

  DrawMeasurement() {
    showShaftLen = false;
    measurements = false;
    showOutside = false;
    showBom = false;
    greyscale = false;

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

  public boolean measurements() {
    return measurements;
  }

  public void setmeasurements(boolean measurements) {
    this.measurements = measurements;
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


  public void arrow(double x0, double y, double x1, double value, Color color) {
    setPenColor(color);

    arrowHelper((x0 + x1) / 2.0, y, value);
    arrowHelper((x0 + x1) / 2.0, y, value);

  }

  public void drawMeasurement(PartLibrary partLibrary, String name) {

    if (name.equalsIgnoreCase(measurementsLabel)) {
      measurements = true;
    } else if (name.equalsIgnoreCase(outsideLabel)) {
      showOutside = true;
    } else if (name.equalsIgnoreCase(shaftLenLabel)) {
      showShaftLen = true;
    } else if (name.equalsIgnoreCase(billOfMatLabel)) {
      showBom = true;
    } else if (name.equalsIgnoreCase(greyscaleLabel)) {
      greyscale = true;
      setGreyscale(true);
    } else {
      System.out.println("measurement name does not match any labels");
    }

    redraw(partLibrary);

  }

  public void eraseMeasurement(PartLibrary partLibrary, String measurementName) {

    if (measurementName.equalsIgnoreCase(measurementsLabel)) {
      measurements = false;
    } else if (measurementName.equalsIgnoreCase(outsideLabel)) {
      showOutside = false;
    } else if (measurementName.equalsIgnoreCase(shaftLenLabel)) {
      showShaftLen = false;
    } else if (measurementName.equalsIgnoreCase(billOfMatLabel)) {
      showBom = false;
    } else if (measurementName.equalsIgnoreCase(greyscaleLabel)) {
      greyscale = false;
      setGreyscale(false);

    } else {
      System.out.println("measurement name does not match any labels");
    }

    redraw(partLibrary);
  }

  private void redraw(PartLibrary partLibrary) {
    Draw.clear();
    renderDrawing(partLibrary);
    renderMeasurements(this, measurements, showOutside, showShaftLen, showBom, greyscale);

    show();
  }

  void drawArrowAndLabel(DrawMeasurement dm, PartLibrary partLibrary, String type, double x, double y, Color color) throws Exception {
    double value = 0.0;

    if (type.equalsIgnoreCase(billOfMatLabel) || type.equalsIgnoreCase(greyscaleLabel)) {
      return;
    }
    if (type.equalsIgnoreCase(measurementsLabel)) {
      List<Measurement> measurementList = partLibrary.getMeasurementList();
      assert (measurementList != null);
      Draw.setPenColor(Color.GRAY);
      setPenRadius(0.0005);

      List<Part> partList = getPartLibrary().partList;
      for (Measurement m : measurementList) {
        Part partBegin = partList.get(m.startIdx);
        Part partEnd = partList.get(m.endIdx);
        double x_init = partBegin.getX_start();
        double x_final = partEnd.getX_start();
        double difference = x_final - x_init;
        System.out.println("Value: " + value);
        System.out.println("Diff: " + difference);

        Draw.setPenColor(Color.GRAY);

        Draw.line(x_init, y - 10, x_init, y + 10);
        Draw.line(x_final, y - 10, x_final, y + 10);

        //find initial x pos of beginning

        //value = calcDistIncludingEndpts(partLibrary.getPartList(), m.startIdx, m.endIdx);
        Draw.setPenColor(Color.BLACK);

        Draw.line(x_init, y, x_final, y);
        dm.arrow(x_init + value / 2.0, y, value, value, color);
        String sb = m.getName() +
            " = " +
            value;

        Draw.setMeasurementFont();
        Draw.text(x_init + value, y + 1, sb);
        y += 5;
        show();

      }
      return;

    } else if (type.equalsIgnoreCase(outsideLabel)) {
      value = calcDistIncludingEndpts(partLibrary.getPartList(), 0, partLibrary.numParts - 1);

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

    show();
  }

  public void renderMeasurements(DrawMeasurement dm, boolean measurements, boolean outside, boolean shaft, boolean bom, boolean greyscale) {

    //where should x and y start?
    double height = 5;
    double x = getScale() / 2.0;
    double y = getYCenter() - height;

    try {

      if (measurements) {
        drawArrowAndLabel(dm, getPartLibrary(), measurementsLabel, x, getYCenter() + height, Color.getColor("blue"));
      }
      if (outside) {
        drawArrowAndLabel(dm, getPartLibrary(), outsideLabel, x, y, Color.getColor("blue"));
        y -= height;
      }
      if (shaft) {
        drawArrowAndLabel(dm, getPartLibrary(), shaftLenLabel, x, y, Color.getColor("blue"));
        y -= height;
      }
      if (bom) {
        drawBillOfMaterials(x, 75, getPartLibrary().createBillOfMaterials());
      }
      if (greyscale) {
        Part.drawPartsFromList(getPartLibrary());
      }


    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
    }

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
    Draw.setPenColor(Color.BLACK);
    Draw.text(x, y, "Bill of Materials: " + billOfMaterials.toString());
    Iterator<Map.Entry<String, Integer>> iter = billOfMaterials.entrySet().iterator();

    while (iter.hasNext()) {
      y += 16; //change to look good
      Map.Entry<String, Integer> entry = iter.next();
      Draw.text(x, y, entry.getKey() + " : " + entry.getValue().toString());
    }
    show();
  }


}
