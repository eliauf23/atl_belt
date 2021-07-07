package com.company;


class DrawRoller {
  public static void main(String[]args){

    StdDraw.setCanvasSize(2048, 2048);
    StdDraw.setScale(0, 100);


    double height = 0.0;
    double width = 0.0;
    double x_current = 0.0; //keeps track of x-location relative to first washer (and serves as sum of widths)
    final double Y_CENTER = 50;

    //TODO: initialize canvas size, pen size, canvas scale



    //TODO: draw washer

    height = 5.0;
    width = 1.0;

    //options for colors: StdDraw.BLACK, StdDraw.BLUE, StdDraw.CYAN, StdDraw.DARK_GRAY, StdDraw.GRAY, StdDraw.GREEN, StdDraw.LIGHT_GRAY, StdDraw.MAGENTA, StdDraw.ORANGE, StdDraw.PINK, StdDraw.RED, StdDraw.WHITE, and StdDraw.YELLOW.
    StdDraw.setPenRadius(0.1);
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.filledRectangle(x_current, Y_CENTER, width/2.0, height/2.0);

    // update x_current
    x_current += width;

    //try adding a text label


    //TODO: draw slider_gasket

    //set color
    //draw filled rectangle

    height = 8.0;
    width = 3.0;

    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.filledRectangle(x_current, Y_CENTER, width/2.0, height/2.0);

    // update x_current
    x_current += width;




    //TODO: draw 4 standard disks
    height = 10.0;
    width = 4.0;

    StdDraw.setPenColor(StdDraw.DARK_GRAY);

    for(int i = 0; i < 4; i++) {

      StdDraw.filledRectangle(x_current, Y_CENTER, width/2.0, height/2.0);
      // update x_current
      x_current += width;
    }

    //TODO: draw standard spacer
    height = 5.0;
    width = 1.0;

    StdDraw.filledRectangle(x_current, Y_CENTER, width/2.0, height/2.0);
    // update x_current
    x_current += width;


    System.out.println("SUm of all widths: " + x_current);
    //TODO: sum up all widths & draw shaft
    //can set pen radius to be thicker & draw a line
    StdDraw.show();
  }


}

