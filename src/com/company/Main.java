package com.company;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import java.io.*;
import javax.swing.JApplet;
import javax.swing.JFrame;


public class Main {

    public static void main(String[] args) {


        Scanner scnr = new Scanner(System.in);

        //prompt user for name of text file
        //System.out.println("Please enter file name (include full path if file isn't located in current working directory): ");
        //String filename = scnr.next(); //collects next string until parser encounters whitespace
        //open text file and parse

        //set up drawing environement
        int curr_x = 0;
        int curr_y = 0;

        System.out.println("initializing JApplet...");
        JFrame f = new JFrame("Roller Spec");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JApplet applet = new DrawRoller2D();
        f.getContentPane().add("Center", applet);
        applet.init();
        f.pack();
        f.setSize(new Dimension(1000,1000));
        f.setVisible(true);
    }
}