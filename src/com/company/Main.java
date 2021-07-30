package com.company;

import static com.company.Draw.getPartLibrary;
import static com.company.Draw.openFileAndGeneratePartList;
import static com.company.Draw.setup;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    setup();
    PartLibrary partLib = Draw.getPartLibrary();
    partLib.setupPartLibraryWithTestParts();
    openFileAndGeneratePartList(getPartLibrary());
    partLib.setSumOfWidths();
    Draw.renderDrawing(partLib);
  }
}