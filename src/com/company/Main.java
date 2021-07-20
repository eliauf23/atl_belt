package com.company;

import static com.company.Draw.openFileAndDrawContents;
import static com.company.Draw.setup;

import java.io.IOException;

public class Main {


  public static void main(String[] args) throws IOException {
    setup();
    openFileAndDrawContents();
    //for testing purposes
    //drawMeasurements(true, true, true);
    //Draw.text(20, SCALE/2.0, Part.convertResultsToString(sumOfWidths, billOfMaterials));

  }

}