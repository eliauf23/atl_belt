package com.company;

import static com.company.Draw.getDrawMeasurement;
import static com.company.Draw.getFrame;
import static com.company.Draw.getPartLibrary;
import static com.company.Draw.launchSaveFileDialogue;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Menu {


  // create the menu bar (changed to protected)
  protected static JMenuBar createMenuBar() {
    JMenuBar menuBar;

    menuBar = new JMenuBar();

    getFrame().setJMenuBar(menuBar);

    final CloseAction closeAction = new CloseAction(getFrame());

    JPanel panel = new JPanel();
    panel.add(new JButton(closeAction));


    getFrame().addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        closeAction.confirmClosing();

      }
    });

    // also use the same Action in your ctrl-q key bindings
    int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
    InputMap inputMap = panel.getInputMap(condition);
    ActionMap actionMap = panel.getActionMap();
    KeyStroke ctrlQKey = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK);
    inputMap.put(ctrlQKey, ctrlQKey.toString());
    actionMap.put(ctrlQKey.toString(), closeAction);

    getFrame().add(panel);
    String name, itemName;
    int keyEvent;
    JMenu newMenu;
    JMenuItem newMenuItem;

    //add file menu;
    name = "File";
    keyEvent = KeyEvent.VK_F;
    newMenu = createNewMenu(name, keyEvent);
    menuBar.add(newMenu);

    //add menu items to file menu
    itemName = " Save ... ";
    keyEvent = KeyEvent.VK_S;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    itemName = " Open text file  ";
    keyEvent = KeyEvent.VK_O;
    newMenu.add(createNewMenuItem(itemName, keyEvent));


    itemName = " Open LIBRARY file  ";
    keyEvent = KeyEvent.VK_L;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    newMenu.addSeparator();

    itemName = " Exit     ";
    keyEvent = KeyEvent.VK_Q;
    newMenuItem = createNewMenuItem(itemName, keyEvent);
    newMenuItem.setAction(closeAction);
    newMenu.add(newMenuItem);


    //add measure menu

    name = "Measure";
    keyEvent = KeyEvent.VK_M;
    newMenu = createNewMenu(name, keyEvent);
    menuBar.add(newMenu);

    itemName = "Shaft Length";
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName));

    itemName = "Outside-Outside";
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName));

    itemName = "Display Measurements";
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName));

    name = "Display Options";
    keyEvent = KeyEvent.VK_D;
    newMenu = createNewMenu(name, keyEvent);
    menuBar.add(newMenu);

    itemName = "Greyscale Mode";
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName));

    itemName = "Bill of Materials";
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName));

    name = "Help";
    keyEvent = KeyEvent.VK_H;
    newMenu = createNewMenu(name, keyEvent);
    menuBar.add(newMenu);

    itemName = "FAQ"; //i.e. view README.md as a pop up
    keyEvent = KeyEvent.VK_1;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    itemName = "About"; //i.e. view software licence as a pop up
    keyEvent = KeyEvent.VK_2;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    return menuBar;
  }

  private static ActionListener getActionListener(String itemName) {

    if (itemName.toLowerCase().contains("save")) {
      return e -> {
        JFileChooser fileChooser = null;
        try {
          fileChooser = launchSaveFileDialogue();
        } catch (RuntimeException | IOException exception) {
          exception.printStackTrace();
        }

        assert fileChooser != null;
        int status = fileChooser.showSaveDialog(null);
        fileChooser.setVisible(true);

        if (status == JFileChooser.APPROVE_OPTION) {
          File fileToSave = fileChooser.getSelectedFile();
          String fileName = fileToSave.getAbsolutePath();
          Draw.save(fileName);
        }


      };

    } else if (itemName.toLowerCase().contains("open")) {
      return e -> {
        File f = FileIO.getTextFileFromUser(itemName);
        try {
          System.out.println("Generating part list from file...");
          Part.generatePartListFromNewFile(f, getPartLibrary());
        } catch (Exception exception) {
          exception.printStackTrace();
        }
        System.out.println("Finished creating part list from file!");

        getPartLibrary().setSumOfWidths();
        getPartLibrary().drawMeasurementArrows();
        Draw.renderDrawing(getPartLibrary());

      };
    } else if (itemName.toLowerCase().contains("exit")) {
      return e -> {
        try {
          //something
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      };
    } else {
      return null;
    }


  }

  private static JMenuItem createNewMenuItem(String itemName, int keyEvent) {
    JMenuItem newMenuItem = new JMenuItem(itemName, keyEvent);
    newMenuItem.addActionListener(getActionListener(itemName));
    newMenuItem.setAccelerator(KeyStroke.getKeyStroke(keyEvent, InputEvent.CTRL_DOWN_MASK));
    return newMenuItem;
  }

  private static JCheckBoxMenuItem createNewJMenuCheckBoxMenuItem(String itemName) {
    JCheckBoxMenuItem newCheckBoxItem = new JCheckBoxMenuItem(itemName);
    newCheckBoxItem.addItemListener(e -> {
      if (newCheckBoxItem.isSelected()) {
        getDrawMeasurement().drawMeasurement(getPartLibrary(), itemName);
      } else {
        getDrawMeasurement().eraseMeasurement(getPartLibrary(), itemName);
      }
    });
    return newCheckBoxItem;
  }

  private static JMenu createNewMenu(String name, int keyEvent) {
    JMenu newMenu = new JMenu(name);
    newMenu.setMnemonic(keyEvent);
    return newMenu;
  }
}