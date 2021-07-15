package com.company;

import static com.company.Draw.openFileAndDrawContents;
import static com.company.Draw.setup;

import java.io.IOException;

public class Main {


  final static int CANVAS_WIDTH = 2048;
  final static int CANVAS_HEIGHT = 2048;
  final static int SCALE = 100;

  static double Y_CENTER = SCALE / 2.0;
  static double shaft_len = 0.0;

  public static void main(String[] args) throws IOException {
    setup(CANVAS_WIDTH, CANVAS_HEIGHT, SCALE);
    openFileAndDrawContents();
  }

}