package com.company;

import static com.company.Draw.getDrawMeasurement;
import static com.company.Draw.getPartLibrary;
import static com.company.Draw.setPenColor;
import static com.company.Draw.setPenRadius;
import static com.company.Draw.show;

public class Measurement {
  public String getName() {
    return name;
  }

  private final String name;
  private final double length = -1;
  private Part begin;
  public int startIdx;
  private Part end;

  public void setEndIdx(int endIdx) {
    this.endIdx = endIdx;
  }

  public int endIdx;


  public Measurement(String name, int startIdx) {
    this.name = name;
    this.startIdx = startIdx;
  }


  public double getLength() throws Exception {
    if (length == -1) {
      throw new Exception("measurement length isn't set yet");
    }
    return length;
  }

  public double calcLength() throws Exception {
    //todo: sum widths between parts - can reuse code from get width from part list
//
//    double dist = 0.0;
//    int start = this.getBegin().position;
//    int end = this.getEnd().position;
//    List<Part> partList = Draw.getPartLibrary().partList;
//    if (start <= end) {
//      for (int i = start; i <= end; i++) {
//        dist += partList.get(i).getWidth();
//      }
//      return dist;
//    } else {
//      throw new Exception("invalid index! (in Measurement class");
//    }

    return getDrawMeasurement().calcDistIncludingEndpts(getPartLibrary().partList, startIdx, endIdx);

  }

  public Part getBegin() {
    return begin;
  }

  public void setBegin(Part begin) {
    this.begin = begin;
  }

  public Part getEnd() {
    return end;
  }

  public void setEnd(Part end) {
    this.end = end;
  }

  public void drawMeasurement(double x, double y) throws Exception {
    try {


      double value = this.calcLength();
      //draw arrow & draw length & optionally draw name
      setPenColor();
      setPenRadius(0.0005);
      Draw.line(x - value / 2.0, y, x + value / 2.0, y);

      arrow(x - value / 2.0, y, x + value / 2.0, value);

      //draw text label underneath
      String sb = this.name +
          " = " +
          value;
      Draw.text(x, y - 2, sb);

      show();
    } catch (IllegalArgumentException illegArgExp) {
      System.out.println("Illegal arg exception!");
      System.err.println(illegArgExp.toString());
      illegArgExp.printStackTrace();
    } catch (Exception e) {
      System.err.println(e.toString());
      e.printStackTrace();
    }


  }

  public void arrow(double x0, double y, double x1, double value) {
    arrowHelper((x0 + x1) / 2.0, y, value);
    arrowHelper((x0 + x1) / 2.0, y, value);

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

}
