package com.company;

import java.awt.Color;

public class myColors {
  public static class MyColors {
    private static Color color;

    public MyColors() {
      color = Color.WHITE;
    }


    /*
    List of colors that you can use
     */
    public static final Color BLACK = Color.BLACK;
    public static final Color BLUE = Color.BLUE;
    public static final Color CYAN = Color.CYAN;
    public static final Color DARK_GRAY = Color.DARK_GRAY;
    public static final Color GRAY = Color.GRAY;
    public static final Color GREEN = Color.GREEN;
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
    public static final Color MAGENTA = Color.MAGENTA;
    public static final Color ORANGE = Color.ORANGE;
    public static final Color PINK = Color.PINK;
    public static final Color RED = Color.RED;
    public static final Color WHITE = Color.WHITE;
    public static final Color YELLOW = Color.YELLOW;
    public static final Color MEDIUM_BLUE = new Color(9, 90, 166);
    public static final Color AQUA = new Color(103, 198, 243);
    public static final Color BOOK_RED = new Color(150, 35, 31);
    public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);
    public static final Color MEDIUM_GRAY = new Color(88, 88, 88);
    public static final Color NAVY_BLUE = new Color(4, 22, 56);
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);


    //grey colors


    static Color getColor(String colorName) {
      switch (colorName.toLowerCase()) {
        case "black":
          color = Color.BLACK;
          break;
        case "blue":
          color = Color.BLUE;
          break;
        case "cyan":
          color = Color.CYAN;
          break;
        case "dark gray":
        case "dark grey":
        case "darkgray":
        case "darkgrey":
          color = Color.DARK_GRAY;
          break;

        case "lightgray":
        case "light gray":
        case "light grey":
        case "lightgrey":

          color = Color.LIGHT_GRAY;
          break;

        case "medium grey":
        case "mediumgray":
        case "mediumgrey":
        case "medium gray":
          color = new Color(88, 88, 88);
          break;
        case "gray":
          color = Color.GRAY;
          break;
        case "grey":
          color = Color.GRAY;
          break;
        case "green":
          color = Color.GREEN;
          break;
        case "yellow":
          color = Color.YELLOW;
          break;
        case "magenta":
          color = Color.MAGENTA;
          break;
        case "orange":
          color = Color.ORANGE;
          break;
        case "pink":
          color = Color.PINK;
          break;
        case "red":
          color = Color.RED;
          break;
        case "white":
          color = Color.WHITE;
          break;
      }
      return color;
    }
  }
}
