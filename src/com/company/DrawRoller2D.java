package com.company;

import static com.company.Roller.std_spacer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.JApplet;

public class DrawRoller2D extends JApplet {

  final static int maxCharHeight = 15;
  final static int minFontSize = 8;

  final static Color bg = Color.white; //background
  final static Color fg = Color.black; //foreground
  final static Color red = Color.red;
  final static Color white = Color.white;

  final static BasicStroke stroke = new BasicStroke(2.0f);
  final static BasicStroke boldStroke = new BasicStroke(8.0f);

  public void init() {
    //Initialize drawing colors
    setBackground(bg);
    setForeground(fg);
  }

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;


    //draw example shaft

    double shaft_length = 700.0d;
    double sum_widths = 500.0d;
    double x_init = 50.0d + sum_widths/2.0;
    double x_final = 50.0d + shaft_length;
    double y_center = 500.0d;
    Line2D.Double line = new Line2D.Double(x_init, y_center, x_final, y_center);
    g2.draw(line);


    //draw example rectangle centered about shaft
    double x = 100.0d;
    double y = 500.0d;
    double width = 50.7d;
    double height = 50.0d;
    Rectangle2D.Double rect = new Rectangle2D.Double(x - (width/2), y - (height/2), width, height);
    g2.draw(rect);


    //need functions for:

    //TODO: drawCenterRectangle(x_pos, height, width)
    //TODO: drawShaft
    //TODO: drawShaft
/*
    Dimension size = getSize();

    //x and y specify bottom left corner of rectangle

    int d = Math.min(size.width, size.height);
    int x = (size.width - d)/2;
    int y = (size.height - d)/2;
    //draw rectangle

    g2.setPaint(fg);
    g2.setStroke(boldStroke);
    g2.draw(new Rectangle2D.Double(x, y, d, d));*/

  }


  //TODO: draw function - draws rollers, end cap, shaft etc.









}