package com.company;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Menu {
}

class JCheckBoxMenuItemTest {
  JFrame myFrame = null;

  public static void main(String[] a) {
    (new JCheckBoxMenuItemTest()).test();
  }

  private void test() {
    myFrame = new JFrame("Check Box Menu Item Test");
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myFrame.setBounds(50, 50, 250, 150);
    myFrame.setContentPane(new JDesktopPane());

    JMenuBar myMenuBar = new JMenuBar();
    JMenu myMenu = getFileMenu();
    myMenuBar.add(myMenu);
    myMenu = getColorMenu();
    myMenuBar.add(myMenu);
    myMenu = getOptionMenu();
    myMenuBar.add(myMenu);
    JMenuItem myItem = new JMenuItem("Help");
    myMenuBar.add(myItem);

    myFrame.setJMenuBar(myMenuBar);
    myFrame.setVisible(true);
  }

  private JMenu getFileMenu() {
    JMenu myMenu = new JMenu("File");
    JMenuItem myItem = new JMenuItem("Open");
    myMenu.add(myItem);
    myItem = new JMenuItem("Close");
    myItem.setEnabled(false);
    myMenu.add(myItem);
    myMenu.addSeparator();
    myItem = new JMenuItem("Exit");
    myMenu.add(myItem);
    return myMenu;
  }

  private JMenu getColorMenu() {
    JMenu myMenu = new JMenu("Color");
    JMenuItem myItem = new JMenuItem("Red");
    myMenu.add(myItem);
    myItem = new JMenuItem("Green");
    myMenu.add(myItem);
    myItem = new JMenuItem("Blue");
    myMenu.add(myItem);
    return myMenu;
  }

  private JMenu getOptionMenu() {
    JMenu myMenu = new JMenu("Option");
    JCheckBoxMenuItem myItem = new JCheckBoxMenuItem("Sound");
    myItem.setSelected(true);
    myMenu.add(myItem);
    myItem = new JCheckBoxMenuItem("Auto save");
    myMenu.add(myItem);
    return myMenu;
  }
}