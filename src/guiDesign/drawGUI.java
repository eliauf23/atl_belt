package guiDesign;

import static com.company.Draw.SCALE;
import static com.company.Draw.Y_CENTER;
import static com.company.Draw.getSCALE;
import static com.company.Draw.getShaftLength;
import static com.company.Draw.launchFileOpenDialogue;
import static com.company.Part.calcSumOfWidths;
import static com.company.Part.convertFileToPartList;
import static com.company.Part.drawPart;
import static com.company.Part.drawPartsFromList;
import static com.company.Part.drawShaft;

import com.company.Draw;
import com.company.Part;
import com.company.PartLibrary;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class drawGUI {

  public static void main(String[] args) throws IOException {
    // Create a window and set its layout manager to be BorderLayout.
    // (This happens to be the default layout manager for a JFrame.)
    JFrame frame = new JFrame("Main window");
    frame.setSize(2048, 2048);
    Container cf = frame.getContentPane();
    cf.setLayout(new BorderLayout());

    // Create a panel and set its layout manager to be FlowLayout.
    // (This happens to be the default layout manager for a JPanel.)
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());     // No content pane for JPanel.

    // Create buttons for all parts and add them to the panel.
    File selectedFile;

    JFileChooser fileChooser = launchFileOpenDialogue();
    int status = fileChooser.showOpenDialog(null);

    if (status == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
      List<Part> partList = convertFileToPartList(selectedFile);
      HashMap<String, Integer> billOfMaterials = new HashMap<>();
      PartLibrary.createBillOfMaterials(billOfMaterials, partList);
      double sumOfWidths = calcSumOfWidths(partList);
      drawShaft(getShaftLength());

      /*
      START DRAWING PARTS & BUTTONS FROM LIST
       */


      double currentXvalue = 0.5 * (getSCALE() - (sumOfWidths) + partList.get(0).getWidth());
      double halfWidth;

      for (Part p : partList) {
        halfWidth = 0.5 * p.getWidth();
        currentXvalue += halfWidth;
        drawPart(p, currentXvalue);

        //draw button @ (currentXvalue, Y_Center, width = p.getWidth(), height = p.getHeight



        currentXvalue += halfWidth;
      }


      /*
      END DRAWING PARTS AND BUTTONS FROM LIST
       */

      //displayResults(sumOfWidths, billOfMaterials);
      //instead I want bill of materials to be displayed in scroll bar below

      JButton button1 = new JButton("Left");
      JButton button2 = new JButton("Right");
      panel.add(button1);
      panel.add(button2);

      // Create a text area for displaying messages.  We embed the text
      // area in a scroll pane so that it doesn't grow unboundedly.
      JTextArea textArea = new JTextArea();
      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setPreferredSize(new Dimension(400, 100));
      textArea.setEditable(false);

      // Position the panel and the text area within the frame.
      cf.add(panel, "North");
      cf.add(scrollPane, "South");

      // Add event handlers for button clicks.
      class MyListener implements ActionListener {     // A local class.
        private JTextArea mTextArea;

        public void setTextArea(JTextArea t) {
          mTextArea = t;
        }

        public void actionPerformed(ActionEvent e) {
          mTextArea.append(e.getActionCommand() + "\n");
        }
      }
      MyListener listener = new MyListener();
      listener.setTextArea(textArea);      // Cannot do this with an anonymous class.
      button1.addActionListener(listener);
      button2.addActionListener(listener);

      // Make the frame closable.
      frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
        }
      });

      // Make the frame visible after adding the components to it.
      frame.setVisible(true);
    }
  }

