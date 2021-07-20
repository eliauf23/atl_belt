package com.company;

import java.util.ArrayList;
import javax.swing.JButton;

/**
 * The purpose of this class is to allow the user to get info about a single part.
 * It can also be used to take measurement of length between two parts - which will produce a length & draw an arrow.
 */

//Todo: add "Take measurement" tab in file menu - but first I need to make it easier to add file menus and submenus
//Todo:
public class PartUI extends Part {

  /*
  Fields for single part info
   */

  /*array list of all part "buttons"*/

  ArrayList<Part> buttonList;
  //field of current button being selected with reference to corresponding part
  JButton currentButton;
  Part currentPart;
  //String - text to show when said part is clicked

  String displayPartInfo;

  //which two numbers from part list you measure


  /*
  fields for making measurements between two parts
   */
  //FOR MEASUREMENT PURPOSES
  int startAtIndex;
  int endAtIndex;


}
