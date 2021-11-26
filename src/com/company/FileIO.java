package com.company;


import static java.nio.file.Files.newBufferedReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Provides general functionality to open files & parse data & saving images to a file.
 * <p>
 * 2 main use cases:
 * (1) loading a text file containing part library
 * (2) opening a part list file for individual drawing
 */
public class FileIO {

  public static JFileChooser LaunchFileOpenDialogue(String message, String filterDescription, String extension) {
    FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    //File file = new File("C:\\Users\\Public");

    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    jfc.setDialogTitle(message);
    jfc.setAcceptAllFileFilterUsed(false);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDescription, extension);
    jfc.addChoosableFileFilter(filter);
    return jfc;

  }


  public static File getTextFileFromUser(String message) {
    JFileChooser jfc = FileIO.LaunchFileOpenDialogue(message, "Text files", "txt");

    int returnValue = jfc.showOpenDialog(null);
    File selectedFile = null;
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      selectedFile = jfc.getSelectedFile();

      System.out.println(selectedFile.getAbsolutePath());

    } else if (returnValue == JFileChooser.CANCEL_OPTION) {
      System.out.println("User cancelled open file dialogue.");
      //do nothing
      //throw new FileNotFoundException();
    }
    return selectedFile;
  }
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
