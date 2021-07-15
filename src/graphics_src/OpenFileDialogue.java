package graphics_src;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

public class OpenFileDialogue {
  public static void main(String[] args) {

    String currentDirectoryPath = "C:\\Users\\eliau\\IdeaProjects\\atl_belt\\input\\test1.txt";
    JFileChooser fileChooser  = new JFileChooser(".");

    fileChooser.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Action");
      }
    });

    int status = fileChooser.showOpenDialog(null);


    if(status == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();

    }


    System.out.println("open file dialogue test");
  }

}
