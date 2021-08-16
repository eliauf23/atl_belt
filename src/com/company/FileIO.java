package com.company;


import static java.nio.file.Files.newBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides general functionality to open files & parse data & saving images to a file.
 * <p>
 * 2 main use cases:
 * (1) loading a text file containing part library
 * (2) opening a part list file for individual drawing
 */
public class FileIO {


  //function to open a file


  //function to save to file


  //helper functions


  //go over file line by line - same idea as nio.Files readAllLines()

  private static List<String> convertTextFileToStringArray(Path path, Charset cs) throws IOException {

    try (BufferedReader reader = newBufferedReader(path, cs)) {
      List<String> result = new ArrayList<>();
      for ( ; ; ) {
        String line = reader.readLine();
        if (line == null) {
          break;
        }
        result.add(line);
      }
      return result;
    }
  }

}
