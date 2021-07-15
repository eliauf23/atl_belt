package com.company;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class FrameClass extends JFrame {

  public FrameClass() {


    try {
      UIManager.setLookAndFeel(new FlatDarculaLaf());
    } catch( Exception ex ) {
      System.err.println( "Failed to initialize LaF" );
    }

    FlatLaf.updateUI();


    setSize(1000, 800);
    setTitle("Engineering drawing app");




    ImageIcon icon = new ImageIcon("C:\\Users\\eliau\\IdeaProjects\\atl_belt\\input\\atl_belt.png");
    Image logo = icon.getImage();
    setResizable(true);
    setVisible(true);


    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int w = (int) getSize().getWidth();
    int h = (int) getSize().getHeight();
    int x = (int) ((dimension.getWidth() - w)/2.0);
    int y = (int) ((dimension.getHeight() - h)/2.0);

    setLocation(x, y);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    FrameClass frameClass = new FrameClass();
  }

}
