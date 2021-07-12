package com.company;

import static com.company.Main.SCALE;
import static com.company.Main.Y_CENTER;

import java.awt.Color;
import java.util.List;

public class DrawParts {

  public static void drawPart(Part p, double x_current, double Y_CENTER) {
    double height = p.getHeight();
    double width = p.getWidth();
    Color color = p.getColor();
    Draw.setPenColor(color);
    Draw.filledRectangle(x_current, Y_CENTER, width / 2.0, height / 2.0);

  }
  public static void drawShaft(double shaftLength, double sumOfWidths) {
    //default thickness = 1 & default color is light grey

    Draw.setPenColor(Draw.LIGHT_GRAY);

    Draw.filledRectangle(SCALE/2.0, SCALE/2.0, shaftLength / 2.0, 0.25);


  }

  public static void drawPartsFromList(List<Part> partList, double total_width) {

    double x_current = 0.5 * (SCALE - total_width + partList.get(0).getWidth());
    double half_width;

    for (Part p : partList) {
      half_width = 0.5 * p.getWidth();
      x_current += half_width;
      drawPart(p, x_current, Y_CENTER);
      x_current += half_width;
    }

  }
}
