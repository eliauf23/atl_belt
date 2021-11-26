package com.company;

import static com.company.Draw.getPartLibrary;
import static com.company.Draw.openFileAndGeneratePartList;
import static com.company.Draw.setup;

public class Main {

  public static void main(String[] args) throws Exception {

    setup();
    Draw.getPartLibrary().setupPartLibraryFromTextFile();

    try {

      openFileAndGeneratePartList(getPartLibrary());

      Draw.getPartLibrary().setSumOfWidths();
      Draw.renderDrawing(Draw.getPartLibrary());

    } catch (Exception exception) {
      System.err.println(exception.toString());
      exception.printStackTrace();
    }

  }
}