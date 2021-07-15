package DrawingApp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.*;
/*
 * Created by JFormDesigner on Tue Jul 13 09:08:29 EDT 2021
 */



/**
 * @author Elizabeth A
 */
public class DrawingApp extends JFrame {
  public DrawingApp() {
    initComponents();
  }

  private void fileMenuMouseClicked(MouseEvent e) {
    // TODO add your code here
  }

  private void menu1MenuKeyPressed(MenuKeyEvent e) {
    // TODO add your code here
  }

  private void menu2MenuSelected(MenuEvent e) {
    // TODO add your code here
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Elizabeth A
    scrollPane1 = new JScrollPane();
    editorPane1 = new JEditorPane();

    //======== this ========
    Container contentPane = getContentPane();

    //======== scrollPane1 ========
    {
      scrollPane1.setViewportView(editorPane1);
    }

    GroupLayout contentPaneLayout = new GroupLayout(contentPane);
    contentPane.setLayout(contentPaneLayout);
    contentPaneLayout.setHorizontalGroup(
      contentPaneLayout.createParallelGroup()
        .addGroup(contentPaneLayout.createSequentialGroup()
          .addGap(28, 28, 28)
          .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
          .addContainerGap(586, Short.MAX_VALUE))
    );
    contentPaneLayout.setVerticalGroup(
      contentPaneLayout.createParallelGroup()
        .addGroup(contentPaneLayout.createSequentialGroup()
          .addGap(30, 30, 30)
          .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
          .addContainerGap(360, Short.MAX_VALUE))
    );
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - Elizabeth A
  private JScrollPane scrollPane1;
  private JEditorPane editorPane1;
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
