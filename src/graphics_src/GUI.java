package graphics_src;

import com.company.Draw;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.TreeSet;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class GUI extends Draw {


  private static Color penColor;
  private static double penRadius;

  private static final int DEFAULT_SIZE = 512;
  private static final int width = DEFAULT_SIZE;
  private static final int height = DEFAULT_SIZE;

  //currently not using next two lines TODO: remove if unnecessary for rendering
  private static final Object mouseLock = new Object();
  private static final Object keyLock = new Object();

  private static Font font;


  // show we draw immediately or wait until next time show() is called?
  private static final boolean defer = false; //enableDoubleBuffer() will set defer = true

  private static double xmin;
  private static double ymin;
  private static double xmax;
  private static double ymax;

  // double-buffered graphics (can animate drawing - if desired)
  private static BufferedImage offscreenImage;
  private static BufferedImage onscreenImage;
  private static Graphics2D offscreen;
  private static Graphics2D onscreen;
  private static final Draw drawInstance = new Draw();


  // the frame for drawing to the screen
  private static JFrame frame;
  // mouse state
  private static final boolean isMousePressed = false;
  private static final double mouseX = 0;
  private static final double mouseY = 0;

  // queue of typed key characters
  private static LinkedList<Character> keysTyped;

  // set of key codes currently pressed down
  private static TreeSet<Integer> keysDown;


  public static void main(String[] args) throws NullPointerException {

    try {
      UIManager.setLookAndFeel(new FlatDarculaLaf());
    } catch(Exception ex ) {
      System.err.println( "Failed to initialize LaF" );
    }

    FlatLaf.updateUI();



    if (frame != null) {
      frame.setVisible(false);
    }
    frame = new JFrame();
    offscreenImage = new BufferedImage(2 * width, 2 * height, BufferedImage.TYPE_INT_ARGB);
    onscreenImage = new BufferedImage(2 * width, 2 * height, BufferedImage.TYPE_INT_ARGB);
    offscreen = offscreenImage.createGraphics();
    onscreen = onscreenImage.createGraphics();
    offscreen.scale(2.0, 2.0);  // since we made it 2x as big

    setXscale();
    setYscale();
    offscreen.setColor(Draw.WHITE);
    offscreen.fillRect(0, 0, width, height);
    setPenColor();
    setPenRadius();
    setFont();
    clear();

    // initialize keystroke buffers
    keysTyped = new LinkedList<Character>();
    keysDown = new TreeSet<Integer>();

    // add antialiasing
    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    offscreen.addRenderingHints(hints);

    frame.addKeyListener(drawInstance);    // JLabel cannot get keyboard focus
    frame.setFocusTraversalKeysEnabled(false);  // allow VK_TAB with isKeyPressed()
    frame.setResizable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
    // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
    frame.setTitle(titleString);
    frame.setJMenuBar(createMenuBar());
    frame.pack();
    frame.requestFocusInWindow();



    frame.setVisible(true);
  }
}

