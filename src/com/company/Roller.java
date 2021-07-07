package com.company;


import java.awt.Color;

public class Roller {

  final static Color background = Color.white;
  final static Color end_disc = Color.darkGray;
  final static Color std_spacer = Color.lightGray;
  final static Color slider_gasket = Color.orange;
  final static Color washer = Color.black;


  private double height;
  private double width;
  private String materials;
  private Color color; //might want to implement list of colors later to select from





  /** constructor for roller w/ params h, w, materials, color
   *
   * @param height - height of roller
   * @param width - width of roller
   * @param materials - materials (String - must specify material & thickness)
   * @param color - write name as a string.
   */
  public Roller(double height, double width, String materials, Color color) {
    this.height = height;
    this.width = width;
    this.materials = materials;
    this.color = color;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }


  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public String getMaterials() {
    return materials;
  }

  public void setMaterials(String materials) {
    this.materials = materials;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
