package com.company;

import static com.company.Draw.SCALE;
import static com.company.Draw.getShaftLength;
import static com.company.Draw.show;
import static com.company.Draw.text;
import static com.company.PartLibrary.calcDistIncludingEndpts;
import static javax.swing.GroupLayout.DEFAULT_SIZE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JPanel;

class DrawMeasurement extends JPanel {
  static ArrayList<BufferedImage> layers;
  private boolean hasInside;
  private boolean hasOutside;
  private boolean hasShaft;
  private boolean hasBom;


  DrawMeasurement(BufferedImage bufferedImage) {
    layers = new ArrayList<>();
    layers.add(bufferedImage);
  }

  DrawMeasurement() {
    layers = new ArrayList<>();
  }

  public void addLayer(BufferedImage... layers) {
    DrawMeasurement.layers.addAll(Arrays.asList(layers));
  }


  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (BufferedImage buf : layers)  //render all layers
    {
      g.drawImage(buf, 0, 0, getWidth(), getHeight(), null);
    }
  }

  //x and y are center of line, value = length
  private static void arrowHelper(Graphics2D topGraphics, double x, double y, double value) {
    double x0 = x - value / 2.0;
    double xf = x + value / 2.0;
    double delta = 0.25;

    //draw top piece of left arrow
    topGraphics.draw(new Line2D.Double(Draw.scaleX(x0), Draw.scaleY(y), Draw.scaleX(x0 + delta), Draw.scaleY(y + delta)));

    //draw bottom piece of left arrow
    topGraphics.draw(new Line2D.Double(Draw.scaleX(x0), Draw.scaleY(y), Draw.scaleX(x0 + delta), Draw.scaleY(y - delta)));

    //draw top piece of right arrow
    topGraphics.draw(new Line2D.Double(Draw.scaleX(xf), Draw.scaleY(y), Draw.scaleX(xf - delta), Draw.scaleY(y + delta)));

    //draw bottom piece of right arrow
    topGraphics.draw(new Line2D.Double(Draw.scaleX(xf), Draw.scaleY(y), Draw.scaleX(xf - delta), Draw.scaleY(y - delta)));


  }

  public void arrow(double x0, double y0, double x1, double y1, double value, Color color, boolean isTopLayer) {
    if (isTopLayer) {
      Draw.validate(x0, "x0");
      Draw.validate(y0, "y0");
      Draw.validate(x1, "x1");
      Draw.validate(y1, "y1");
      BufferedImage topImage = new BufferedImage(2 * Draw.getWidth(), 2 * Draw.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics2D topGraphics = topImage.createGraphics();

      //set radius

      BasicStroke stroke = new BasicStroke((float) (0.0025 * DEFAULT_SIZE), BasicStroke.CAP_SQUARE,
          BasicStroke.JOIN_ROUND);
      topGraphics.setStroke(stroke);

      topGraphics.setColor(color);
      topGraphics.draw(new Line2D.Double(Draw.scaleX(x0), Draw.scaleY(y0), Draw.scaleX(x1), Draw.scaleY(y1)));
      arrowHelper(topGraphics, x0, y0, value);
      arrowHelper(topGraphics, x1, y1, value);

      this.addLayer(topImage);
      this.paintComponent(topGraphics);
    }
  }
}

public class Measurement {

  boolean showShaftMeasurement; //second layer
  boolean showInsideMeasurement; //third layer
  boolean showOutsideMeasurements; //4th layer
  boolean showBillOfMaterials; //5th layer

  Measurement() {
    this.showShaftMeasurement = false;
    this.showInsideMeasurement = false;
    this.showOutsideMeasurements = false;
    this.showBillOfMaterials = false;
  }

  Measurement(boolean shaft, boolean inside, boolean outside, boolean bom) {
    this.showShaftMeasurement = shaft;
    this.showInsideMeasurement = inside;
    this.showOutsideMeasurements = outside;
    this.showBillOfMaterials = bom;
  }

  /*
  Type must equal: (1) "inside-inside", (2) "outside-outside", (3) "shaft-len"
   */
  void drawMeasurement(DrawMeasurement dm, int index1, int index2, String type, double x, double y, Color color) {
    double value = 0.0;


    if (type.equalsIgnoreCase("inside-inside")) {

      value = calcDistIncludingEndpts(PartLibrary.partList, PartLibrary.getStartInside(), PartLibrary.getEndInside());

    } else if (type.equalsIgnoreCase("outside-outside")) {
      value = calcDistIncludingEndpts(PartLibrary.partList, PartLibrary.getStartOutside(), PartLibrary.getEndOutside());

    } else if (type.equalsIgnoreCase("shaft length")) {
      value = getShaftLength();

    } else {
      System.out.println("Error: doesn't match any measurement options");
    }

    Draw.setPenColor(color);
    Draw.line(x - value / 2.0, y, x + value / 2.0, y);
    //Draw.filledRectangle(x, y, value / 2.0, 0.05);

    dm.arrow(x - value / 2.0, y, x + value / 2.0, y, value, color, true);

    //draw text label underneath
    String sb = type +
        " = " +
        value;
    text(x, y - 2, sb);

    System.out.println("(x, y) = " + x + ", " + (y - 2));
    System.out.println("value = " + value);

    Draw.show();
  }


  public static void removeMeasurements(boolean inside, boolean outside, boolean shaft) {


  }

  public static void drawMeasurements(DrawMeasurement dm, boolean inside, boolean outside, boolean shaft) {

    //where should x and y start?
    double height = 5;
    double x = SCALE / 2.0;
    double y = SCALE / 2.0 - height;

 /*   if (inside && dm.hasInside() == false) {
      dm.addLayer();
      drawMeasurement(PartLibrary.getStartInside(), PartLibrary.getEndInside(), "inside-inside", x, y, Draw.NAVY_BLUE);
      y -= height;
    }
    if (outside) {
      drawMeasurement(PartLibrary.getStartInside(), PartLibrary.getEndInside(), "outside-outside", x, y, Draw.BLUE);
      y -= height;
    }
    if (shaft) {
      drawMeasurement(PartLibrary.getStartInside(), PartLibrary.getEndInside(), "shaft length", x, y, Draw.CYAN);
    }
*/
  }

  static void setMeasurementIndices(String input) {
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
    setMeasurementIndicesHelper(ans);
  }

  private static void setMeasurementIndicesHelper(int[] arr) {
    PartLibrary.setStartOutside(arr[0]);
    PartLibrary.setStartInside(arr[1]);
    PartLibrary.setEndInside(arr[2]);
    PartLibrary.setEndOutside(arr[3]);
  }

  public static void displayBillOfMateirals(double x, double y, double sumOfWidths, HashMap<String, Integer> billOfMaterials) {
    Draw.text(x, y, "Bill of Materials: " + billOfMaterials.toString());

  }

  public static void displayResults(double sumOfWidths, HashMap<String, Integer> billOfMaterials) {
    System.out.println("\nResults:");
    System.out.println("Sum of all widths: " + sumOfWidths);
    System.out.println("Bill of Materials: " + billOfMaterials.toString());

  }

  public static String convertResultsToString(double sumOfWidths, HashMap<String, Integer> billOfMaterials) {
    show();
    String sb = "\nResults:" +
        "Sum of all widths: " + sumOfWidths +
        "Bill of Materials: " + billOfMaterials.toString();
    return sb;
  }

  public boolean isShowShaftMeasurement() {
    return showShaftMeasurement;
  }

  public void setShowShaftMeasurement(boolean showShaftMeasurement) {
    this.showShaftMeasurement = showShaftMeasurement;
  }

  public boolean isShowInsideMeasurement() {
    return showInsideMeasurement;
  }

  public void setShowInsideMeasurement(boolean showInsideMeasurement) {
    this.showInsideMeasurement = showInsideMeasurement;
  }

  public boolean isShowOutsideMeasurements() {
    return showOutsideMeasurements;
  }

  public void setShowOutsideMeasurements(boolean showOutsideMeasurements) {
    this.showOutsideMeasurements = showOutsideMeasurements;
  }

  public boolean isShowBillOfMaterials() {
    return showBillOfMaterials;
  }

  public void setShowBillOfMaterials(boolean showBillOfMaterials) {
    this.showBillOfMaterials = showBillOfMaterials;
  }
}
