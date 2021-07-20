package com.company;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Test {

  protected JFrame frame;


  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        Test test = new Test();
        test.createUI();
      }
    });
  }

  private void createUI() {
    frame = new JFrame();
    JLabel label = new JLabel("Test");
    label.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseEntered(MouseEvent me) {
        startTimer();
      }
    });

    frame.getContentPane().add(label);
    frame.pack();
    frame.setVisible(true);
  }

  private void startTimer() {
    TimerTask task = new TimerTask() {

      @Override
      public void run() {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Test"));
      }
    };

    Timer timer = new Timer(true);
    timer.schedule(task, 3000);
  }
}