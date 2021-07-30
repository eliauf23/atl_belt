/*
package com.company;

import static com.company.Measurement.displayResults;
import static com.company.Part.calcSumOfWidths;
import static com.company.Part.convertFileToPartList;
import static com.company.Part.drawPartsFromList;
import static com.company.Part.drawShaft;

import com.company.Resources.myColors;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

*/
/**
 * This class draws the roller spec & processes input and output from keyboard and mouse.
 * Based on StdDraw class from princeton intro CS course website & standard library.
 *//*

public class Draw1 implements ActionListener, MouseListener, MouseMotionListener, KeyListener {


  final private static int SCALE = 100;
  final private static double Y_CENTER = SCALE / 2.0;
  private static double shaft_len = 0.0;
  // default canvas size is square w/ side len. = DEFAULT_SIZE
  private static final int DEFAULT_SIZE = 2048;
  // default pen radius
  private static final double DEFAULT_PEN_RADIUS = 0.0025;

  // boundary of drawing canvas, 5% border
  private static final double BORDER = 0.05;
  private static final double DEFAULT_XMIN = 0.0;
  private static final double DEFAULT_XMAX = 1.0;
  private static final double DEFAULT_YMIN = 0.0;
  private static final double DEFAULT_YMAX = 1.0;

  // default font
  private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);

  //singleton for callbacks: avoids generation of extra .class files
  private static final Draw1 std = new Draw1();
  // mouse state
  protected static String titleString = "Atlantic Belt - Roller Spec. Application";
  private static Color penColor;
  private static double penRadius;
  private static int width = DEFAULT_SIZE;
  private static int height = DEFAULT_SIZE;
  private static Font font;
  // show we draw immediately or wait until next time show() is called?
  private static boolean defer = false; //enableDoubleBuffer() will set defer = true
  private static double xmin;
  private static double ymin;
  private static double xmax;
  private static double ymax;

  private static LinkedList<Character> keysTyped;
  // set of key codes currently pressed down
  private static TreeSet<Integer> keysDown;

  // double-buffered graphics (can animate drawing - if desired)
  private static BufferedImage offscreenImage;
  private static BufferedImage onscreenImage;


  private static Graphics2D offscreen;
  private static Graphics2D onscreen;

  // the frame for drawing to the screen
  private static JFrame frame;

  // static initializer
  static {
    init();
  }

  */
/* Constructors *//*

  Draw1() {
  }

  // init
  private static void init() {


    //set look and feel for user interface
    try {
      UIManager.setLookAndFeel(new FlatDarculaLaf());
    } catch (Exception e) {
      System.err.println("Failed to initialize LaF");
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
    offscreen.setColor(myColors.WHITE);
    offscreen.fillRect(0, 0, width, height);
    setPenColor();
    setPenRadius();
    setFont();
    clear();

    // initialize keystroke buffers
    keysTyped = new LinkedList<>();
    keysDown = new TreeSet<>();


    // add antialiasing
    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    offscreen.addRenderingHints(hints);

    // frame stuff
    ImageIcon icon = new ImageIcon(onscreenImage);
    JLabel draw = new JLabel(icon);

    draw.addMouseListener(std);

    frame.setContentPane(draw);

    frame.addKeyListener(std);    // JLabel cannot get keyboard focus
    frame.setFocusTraversalKeysEnabled(false);  // allow VK_TAB with isKeyPressed()
    frame.setResizable(true);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
    // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
    frame.setTitle(titleString);

    //setup close operations when you create menu bar


    frame.setJMenuBar(createMenuBar());
    frame.pack();
    frame.requestFocusInWindow();
    frame.setVisible(true);
  }

  */
/**
   * Sets canvas to be size = specified Width * specified Height.
   *
   * @param canvasWidth
   * @param canvasHeight
   *//*

  public static void setCanvasSize(int canvasWidth, int canvasHeight) {
    if (canvasWidth <= 0) {
      throw new IllegalArgumentException("width must be positive");
    }
    if (canvasHeight <= 0) {
      throw new IllegalArgumentException("height must be positive");
    }
    width = canvasWidth;
    height = canvasHeight;
    init();
  }

  */
/**
   * Sets canvas to be size = specified Width * specified Height.
   *//*

  public static void setCanvasSize() {
    width = DEFAULT_SIZE;
    height = DEFAULT_SIZE;
    init();
  }

  */
/**
   * Todo: insert javadoc comment.
   *
   * @param canvasWidth
   * @param canvasHeight
   * @param scale
   *//*

  public static void setup(int canvasWidth, int canvasHeight, int scale) {
    //Initialize canvas size, scale
    enableDoubleBuffering();
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\eliau\\IdeaProjects\\atl_belt\\input\\icons8-drawing-24.png"));
    frame.setVisible(true);
    setCanvasSize(canvasWidth, canvasHeight);
    setScale(0, scale); //i.e. x and y range from 0, 100 w/ (0,0) in bottom left corner
    setPenRadius(0.005);
  }

  public static void setup() {
    //Initialize canvas size, scale
    enableDoubleBuffering();
    frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\eliau\\IdeaProjects\\atl_belt\\input\\icons8-drawing-24.png"));
    setCanvasSize();
    setScale(0, SCALE); //i.e. x and y range from 0, 100 w/ (0,0) in bottom left corner
    setPenRadius(0.005);
  }


  // create the menu bar (changed to protected)
  protected static JMenuBar createMenuBar() {
    JMenuBar menuBar;

    menuBar = new JMenuBar();

    frame.setJMenuBar(menuBar);

    final CloseAction closeAction = new CloseAction(frame);

    JPanel panel = new JPanel();
    panel.add(new JButton(closeAction));


    frame.addWindowListener(new WindowAdapter() {
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

    frame.add(panel);
    String name, itemName;
    int keyEvent = 0;
    JMenu newMenu;
    JMenuItem newMenuItem;
    JCheckBoxMenuItem newCheckBoxItem;


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

    newMenu.addSeparator();

    itemName = " Exit     ";
    keyEvent = KeyEvent.VK_Q;
    newMenuItem = createNewMenuItem(itemName, keyEvent);
    newMenuItem.setAction(closeAction);
    newMenu.add(newMenuItem);


    //add edit menu
    name = "Edit";
    keyEvent = KeyEvent.VK_E;
    newMenu = createNewMenu(name, keyEvent);
    menuBar.add(newMenu);

    //add edit menu items
    itemName = "Cut";
    keyEvent = KeyEvent.VK_X;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    itemName = "Copy";
    keyEvent = KeyEvent.VK_C;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    itemName = "Paste";
    keyEvent = KeyEvent.VK_V;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    itemName = "Undo";
    keyEvent = KeyEvent.VK_Z;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    itemName = "Redo";
    keyEvent = KeyEvent.VK_R;
    newMenu.add(createNewMenuItem(itemName, keyEvent));

    //add measure menu

    name = "Measure";
    keyEvent = KeyEvent.VK_M;
    newMenu = createNewMenu(name, keyEvent);
    menuBar.add(newMenu);

    //create new draw measurement object to pass into create checkboxitem method
    DrawMeasurement dm = new DrawMeasurement();
    itemName = "Shaft Length";
    keyEvent = KeyEvent.VK_L;
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName, keyEvent, false, dm));
    itemName = "Outside-Outside";
    keyEvent = KeyEvent.VK_0; //zero, not O!!!!!!!!!!!!!!!!!
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName, keyEvent, false, dm));
    itemName = "Inside-Inside";
    keyEvent = KeyEvent.VK_I;
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName, keyEvent, false, dm));

    name = "Display Options";
    keyEvent = KeyEvent.VK_D;
    newMenu = createNewMenu(name, keyEvent);
    menuBar.add(newMenu);

    itemName = "Show Bill of Materials";
    keyEvent = KeyEvent.VK_B;
    newMenu.add(createNewJMenuCheckBoxMenuItem(itemName, keyEvent, false, dm));

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



  */
/***************************************************************************
   *  User and screen coordinate systems.
   ***************************************************************************//*


  // throw an IllegalArgumentException if x is NaN or infinite
  static void validate(double x, String name) {
    if (Double.isNaN(x)) {
      throw new IllegalArgumentException(name + " is NaN");
    }
    if (Double.isInfinite(x)) {
      throw new IllegalArgumentException(name + " is infinite");
    }
  }

  // throw an IllegalArgumentException if s is null
  private static void validateNonnegative(double x, String name) {
    if (x < 0) {
      throw new IllegalArgumentException(name + " negative");
    }
  }

  // throw an IllegalArgumentException if s is null
  private static void validateNotNull(Object x, String name) {
    if (x == null) {
      throw new IllegalArgumentException(name + " is null");
    }
  }

  */
/**
   * Sets the <em>x</em>-scale and <em>y</em>-scale to be the default
   * (between 0.0 and 1.0).
   *//*

  public static void setScale() {
    setXscale();
    setYscale();
  }

  */
/**
   * Sets both the <em>x</em>-scale and <em>y</em>-scale to the (same) specified range.
   *
   * @param min the minimum value of the <em>x</em>- and <em>y</em>-scales
   * @param max the maximum value of the <em>x</em>- and <em>y</em>-scales
   * @throws IllegalArgumentException if {@code (max == min)}
   * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
   *//*

  public static void setScale(double min, double max) {
    validate(min, "min");
    validate(max, "max");
    double size = max - min;
    if (size == 0.0) {
      throw new IllegalArgumentException("the min and max are the same");
    }
    synchronized (mouseLock) {
      xmin = min - BORDER * size;
      xmax = max + BORDER * size;
      ymin = min - BORDER * size;
      ymax = max + BORDER * size;
    }
  }


  */
/**
   * Sets the <em>x</em>-scale to be the default (between 0.0 and 1.0).
   *//*

  public static void setXscale() {
    setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
  }


  */
/**
   * Sets the <em>x</em>-scale to the specified range.
   *
   * @param min the minimum value of the <em>x</em>-scale
   * @param max the maximum value of the <em>x</em>-scale
   * @throws IllegalArgumentException if {@code (max == min)}
   * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
   *//*

  public static void setXscale(double min, double max) {
    validate(min, "min");
    validate(max, "max");
    double size = max - min;
    if (size == 0.0) {
      throw new IllegalArgumentException("the min and max are the same");
    }
    synchronized (mouseLock) {
      xmin = min - BORDER * size;
      xmax = max + BORDER * size;
    }
  }

  */
/**
   * Sets the <em>y</em>-scale to be the default (between 0.0 and 1.0).
   *//*

  public static void setYscale() {
    setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
  }

  */
/**
   * Sets the <em>y</em>-scale to the specified range.
   *
   * @param min the minimum value of the <em>y</em>-scale
   * @param max the maximum value of the <em>y</em>-scale
   * @throws IllegalArgumentException if {@code (max == min)}
   * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
   *//*

  public static void setYscale(double min, double max) {
    validate(min, "min");
    validate(max, "max");
    double size = max - min;
    if (size == 0.0) {
      throw new IllegalArgumentException("the min and max are the same");
    }
    synchronized (mouseLock) {
      ymin = min - BORDER * size;
      ymax = max + BORDER * size;
    }
  }


  // helper functions that scale from user coordinates to screen coordinates and back
  static double scaleX(double x) {
    return width * (x - xmin) / (xmax - xmin);
  }

  static double scaleY(double y) {
    return height * (ymax - y) / (ymax - ymin);
  }

  private static double factorX(double w) {
    return w * width / Math.abs(xmax - xmin);
  }

  private static double factorY(double h) {
    return h * height / Math.abs(ymax - ymin);
  }

  private static double userX(double x) {
    return xmin + x * (xmax - xmin) / width;
  }

  private static double userY(double y) {
    return ymax - y * (ymax - ymin) / height;
  }


  */
/**
   * Clears the screen to the default color (white).
   *//*

  public static void clear() {
    clear(DEFAULT_CLEAR_COLOR);
  }

  */
/**
   * Clears the screen to the specified color.
   *
   * @param color the color to make the background
   * @throws IllegalArgumentException if {@code color} is {@code null}
   *//*

  public static void clear(Color color) {
    validateNotNull(color, "color");
    offscreen.setColor(color);
    offscreen.fillRect(0, 0, width, height);
    offscreen.setColor(penColor);
    draw();
  }

  */
/**
   * Get shaft length (as specified in input file).
   *
   * @return shaft-length as a double var.
   *//*

  public static double getShaftLength() {
    return shaft_len;
  }

  */
/**
   * Set shaft length.
   *
   * @param shaft_len
   * @assert shaft_len > 0.
   *//*

  public static void setShaftLength(double shaft_len) {
    validateNonnegative(shaft_len, "shaft length");
    Draw1.shaft_len = shaft_len;
  }

  */
/**
   * Sets the radius of the pen to the specified size.
   * The pen is circular, so that lines have rounded ends, and when you set the
   * pen radius and draw a point, you get a circle of the specified radius.
   * The pen radius is not affected by coordinate scaling.
   *
   * @param radius the radius of the pen
   * @throws IllegalArgumentException if {@code radius} is negative,NaN,or inf.
   *//*

  public static void setPenRadius(double radius) {
    validate(radius, "pen radius");
    validateNonnegative(radius, "pen radius");

    penRadius = radius;
    float scaledPenRadius = (float) (radius * DEFAULT_SIZE);
    BasicStroke stroke = new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND,
        BasicStroke.JOIN_ROUND);
    // BasicStroke stroke = new BasicStroke(scaledPenRadius);
    offscreen.setStroke(stroke);
  }

  */
/**
   * Sets the pen size to the default size (0.002).
   * The pen is circular, so that lines have rounded ends, and when you set the
   * pen radius and draw a point, you get a circle of the specified radius.
   * The pen radius is not affected by coordinate scaling.
   *//*

  public static void setPenRadius() {
    setPenRadius(DEFAULT_PEN_RADIUS);
  }

  */
/**
   * Returns the current pen color.
   *
   * @return the current pen color
   *//*

  public static Color getPenColor() {
    return penColor;
  }

  */
/**
   * Sets the pen color to the specified color.
   * The predefined pen colors are
   *
   * @param color the color to make the pen
   * @throws IllegalArgumentException if {@code color} is {@code null}
   *//*

  public static void setPenColor(Color color) {
    validateNotNull(color, "color");
    penColor = color;
    offscreen.setColor(penColor);
  }

  */
/**
   * Sets the pen color to the default color (black).
   *//*

  public static void setPenColor() {
    setPenColor(Color.BLACK);
  }

  */
/**
   * Sets the pen color to the specified RGB color.
   *
   * @param red   the amount of red (between 0 and 255)
   * @param green the amount of green (between 0 and 255)
   * @param blue  the amount of blue (between 0 and 255)
   * @throws IllegalArgumentException if {@code red}, {@code green},
   *                                  or {@code blue} is outside its prescribed range
   *//*

  public static void setPenColor(int red, int green, int blue) {
    if (red < 0 || red >= 256) {
      throw new IllegalArgumentException("red must be between 0 and 255");
    }
    if (green < 0 || green >= 256) {
      throw new IllegalArgumentException("green must be between 0 and 255");
    }
    if (blue < 0 || blue >= 256) {
      throw new IllegalArgumentException("blue must be between 0 and 255");
    }
    setPenColor(new Color(red, green, blue));
  }

  */
/**
   * Returns the current font.
   *
   * @return the current font
   *//*

  public static Font getFont() {
    return font;
  }

  */
/**
   * Sets the font to the specified value.
   *
   * @param font the font
   * @throws IllegalArgumentException if {@code font} is {@code null}
   *//*

  public static void setFont(Font font) {
    validateNotNull(font, "font");
    Draw1.font = font;
  }

  */
/**
   * Sets the font to the default font (sans serif, 16 point).
   *//*

  public static void setFont() {
    setFont(DEFAULT_FONT);
  }


  public static void show() {
    onscreen.drawImage(offscreenImage, 0, 0, null);
    frame.repaint();
  }


  // draw onscreen if defer is false
  private static void draw() {
    if (!defer) {
      show();
    }
  }

  */
/**
   * Writes the given text string in the current font, centered at (<em>x</em>, <em>y</em>).
   *
   * @param x    the center <em>x</em>-coordinate of the text
   * @param y    the center <em>y</em>-coordinate of the text
   * @param text the text to write
   * @throws IllegalArgumentException if {@code text} is {@code null}
   * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
   *//*

  public static void text(double x, double y, String text) {
    validate(x, "x");
    validate(y, "y");
    validateNotNull(text, "text");

    offscreen.setFont(font);
    FontMetrics metrics = offscreen.getFontMetrics();
    double xs = scaleX(x);
    double ys = scaleY(y);
    int ws = metrics.stringWidth(text);
    int hs = metrics.getDescent();
    offscreen.drawString(text, (float) (xs - ws / 2.0), (float) (ys + hs));
    draw();
  }

  */
/**
   * Writes the given text string in the current font, centered at (<em>x</em>, <em>y</em>) and
   * rotated by the specified number of degrees.
   *
   * @param x       the center <em>x</em>-coordinate of the text
   * @param y       the center <em>y</em>-coordinate of the text
   * @param text    the text to write
   * @param degrees is the number of degrees to rotate counterclockwise
   * @throws IllegalArgumentException if {@code text} is {@code null}
   * @throws IllegalArgumentException if  x,  y, or degrees is either NaN or infinite
   *//*

  public static void text(double x, double y, String text, double degrees) {
    validate(x, "x");
    validate(y, "y");
    validate(degrees, "degrees");
    validateNotNull(text, "text");

    double xs = scaleX(x);
    double ys = scaleY(y);
    offscreen.rotate(Math.toRadians(-degrees), xs, ys);
    text(x, y, text);
    offscreen.rotate(Math.toRadians(+degrees), xs, ys);
  }


  */
/**
   * Writes the given text string in the current font, left-aligned at (<em>x</em>, <em>y</em>).
   *
   * @param x    the <em>x</em>-coordinate of the text
   * @param y    the <em>y</em>-coordinate of the text
   * @param text the text
   * @throws IllegalArgumentException if {@code text} is {@code null}
   * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
   *//*

  public static void textLeft(double x, double y, String text) {
    validate(x, "x");
    validate(y, "y");
    validateNotNull(text, "text");

    offscreen.setFont(font);
    FontMetrics metrics = offscreen.getFontMetrics();
    double xs = scaleX(x);
    double ys = scaleY(y);
    int hs = metrics.getDescent();
    offscreen.drawString(text, (float) xs, (float) (ys + hs));
    draw();
  }


  */
/**
   * Enables double buffering. All subsequent calls to
   * drawing methods such as {@code line()}, {@code circle()},
   * and {@code square()} will be deferred until the next call
   * to show(). Useful for animations.
   *//*

  public static void enableDoubleBuffering() {
    defer = true;
  }

  */
/* Draw basic geometry: rectangles, lines, filled-rectangle (will add more later) *//*


  */
/**
   * Draws one pixel at (<em>x</em>, <em>y</em>).
   * This method is private because pixels depend on the display.
   * To achieve the same effect, set the pen radius to 0 and call {@code point()}.
   *
   * @param x the <em>x</em>-coordinate of the pixel
   * @param y the <em>y</em>-coordinate of the pixel
   * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
   *//*

  private static void pixel(double x, double y) {
    validate(x, "x");
    validate(y, "y");
    offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
  }


  */
/**
   * Draws a line.
   *
   * @param x0 initial x-value.
   * @param y0 initial y-value.
   * @param x1 final x-value.
   * @param y1 final y-value.
   *//*


  public static void line(double x0, double y0, double x1, double y1) {
    validate(x0, "x0");
    validate(y0, "y0");
    validate(x1, "x1");
    validate(y1, "y1");
    offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
    draw();
  }


  */
/**
   * Draws a filled rectangle of the specified size, centered at (<em>x</em>, <em>y</em>).
   *
   * @param x          the <em>x</em>-coordinate of the center of the rectangle
   * @param y          the <em>y</em>-coordinate of the center of the rectangle
   * @param halfWidth  one half the width of the rectangle
   * @param halfHeight one half the height of the rectangle
   * @throws IllegalArgumentException if either {@code halfWidth} or {@code halfHeight} is negative
   * @throws IllegalArgumentException if any argument is either NaN or infinite
   *//*

  public static void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
    validate(x, "x");
    validate(y, "y");
    validate(halfWidth, "halfWidth");
    validate(halfHeight, "halfHeight");
    validateNonnegative(halfWidth, "half width");
    validateNonnegative(halfHeight, "half height");

    double xs = scaleX(x);
    double ys = scaleY(y);
    double ws = factorX(2 * halfWidth);
    double hs = factorY(2 * halfHeight);
    if (ws <= 1 && hs <= 1) {
      pixel(x, y);
    } else {
      offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
    }
    draw();
  }


  */
/**
   * Draws a rectangle of the specified size, centered at (<em>x</em>, <em>y</em>).
   *
   * @param x          the <em>x</em>-coordinate of the center of the rectangle
   * @param y          the <em>y</em>-coordinate of the center of the rectangle
   * @param halfWidth  one half the width of the rectangle
   * @param halfHeight one half the height of the rectangle
   * @throws IllegalArgumentException if either {@code halfWidth} or {@code halfHeight} is negative
   * @throws IllegalArgumentException if any argument is either NaN or infinite
   *//*

  public static void rectangle(double x, double y, double halfWidth, double halfHeight) {
    validate(x, "x");
    validate(y, "y");
    validate(halfWidth, "halfWidth");
    validate(halfHeight, "halfHeight");
    validateNonnegative(halfWidth, "half width");
    validateNonnegative(halfHeight, "half height");

    double xs = scaleX(x);
    double ys = scaleY(y);
    double ws = factorX(2 * halfWidth);
    double hs = factorY(2 * halfHeight);
    if (ws <= 1 && hs <= 1) {
      pixel(x, y);
    } else {
      offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
    }
    draw();
  }


  */
/* Save drawing to a file *//*


  */
/**
   * Saves the drawing to using the specified filename.
   * The supported image formats are JPEG and PNG;
   * the filename fileType must be {@code .jpg} or {@code .png}.
   *
   * @param filename the name of the file with one of the required fileTypees
   * @throws IllegalArgumentException if {@code filename} is {@code null}
   *//*

  public static void save(String filename) {
    validateNotNull(filename, "filename");
    File file = new File(filename);
    String fileType = filename.substring(filename.lastIndexOf('.') + 1);

    // png files
    if ("png".equalsIgnoreCase(fileType)) {
      try {
        ImageIO.write(onscreenImage, fileType, file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Invalid image file type: " + fileType);
    }
  }

  */
/**
   * Method launches window with file open dialogue.
   *
   * @return JFileChooser object created to open file specified by user.
   *//*

  public static JFileChooser launchFileOpenDialogue() {
    JFileChooser fileChooser = new JFileChooser((File) null);
    //fileChooser.addActionListener(e -> System.out.println());
    fileChooser.addActionListener(e -> System.out.println("File loading ..."));

    return fileChooser;
  }

  */
/**
   * Todo: insert javadoc comment.
   *
   * @return
   * @throws IOException
   *//*

  public static JFileChooser launchSaveFileDialogue() throws IOException {
    JFileChooser fileChooser = new JFileChooser((File) null);
    fileChooser.setDialogTitle("Save as a .png file");
    return fileChooser;

  }

  */
/**
   * method opens file using JFileChooser framework, and if able to successfully open
   * file & parse data -> converts to list & draws components.
   *//*

  public static void openFileAndDrawContents() throws IOException {
    File selectedFile;

    JFileChooser fileChooser = launchFileOpenDialogue();
    int status = fileChooser.showOpenDialog(null);

    if (status == JFileChooser.APPROVE_OPTION) {
      selectedFile = fileChooser.getSelectedFile();
      PartLibrary.partList = convertFileToPartList(selectedFile);
      HashMap<String, Integer> billOfMaterials = new HashMap<>();
      PartLibrary.createBillOfMaterials(billOfMaterials, PartLibrary.partList);
      double sumOfWidths = calcSumOfWidths(PartLibrary.partList);
      drawShaft(shaft_len);
      drawPartsFromList(PartLibrary.partList, sumOfWidths);
      //TODO: might need to put measurement drawing
      displayResults(sumOfWidths, billOfMaterials);


      show();
    } else if (status == JFileChooser.CANCEL_OPTION) {
      System.out.println("User cancelled open file dialogue.");
      //do nothing
      //throw new FileNotFoundException();
    } else {
      throw new FileNotFoundException();
    }
  }


  public static void main(String[] args) {
    System.out.println("draw class test");
  }

  */
/**
   * Invoked when an action occurs.
   *
   * @param e
   *//*

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  */
/**
   * Invoked when a key has been typed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key typed event.
   *
   * @param e
   *//*

  @Override
  public void keyTyped(KeyEvent e) {

  }

  */
/**
   * Invoked when a key has been pressed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key pressed event.
   *
   * @param e
   *//*

  @Override
  public void keyPressed(KeyEvent e) {

  }

  */
/**
   * Invoked when a key has been released.
   * See the class description for {@link KeyEvent} for a definition of
   * a key released event.
   *
   * @param e
   *//*

  @Override
  public void keyReleased(KeyEvent e) {

  }

  */
/**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   *
   * @param e
   *//*

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  */
/**
   * Invoked when a mouse button has been pressed on a component.
   *
   * @param e
   *//*

  @Override
  public void mousePressed(MouseEvent e) {

  }

  */
/**
   * Invoked when a mouse button has been released on a component.
   *
   * @param e
   *//*

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  */
/**
   * Invoked when the mouse enters a component.
   *
   * @param e
   *//*

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  */
/**
   * Invoked when the mouse exits a component.
   *
   * @param e
   *//*

  @Override
  public void mouseExited(MouseEvent e) {

  }

  */
/**
   * Invoked when a mouse button is pressed on a component and then
   * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
   * delivered to the component where the drag originated until the
   * mouse button is released (regardless of whether the mouse position
   * is within the bounds of the component).
   * <p>
   * Due to platform-dependent Drag&amp;Drop implementations,
   * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
   * Drag&amp;Drop operation.
   *
   * @param e
   *//*

  @Override
  public void mouseDragged(MouseEvent e) {

  }

  */
/**
   * Invoked when the mouse cursor has been moved onto a component
   * but no buttons have been pushed.
   *
   * @param e
   *//*

  @Override
  public void mouseMoved(MouseEvent e) {

  }
}


*/
/**
 * Aligns functionality of closing window with red X in top corner and quitting
 * from the file menu.
 *//*

class CloseAction extends AbstractAction {
  private final JFrame mainFrame;


  */
/**
   * Todo: insert javadoc comment.
   *
   * @param mainFrame
   *//*

  public CloseAction(JFrame mainFrame) {
    super("Exit");
    putValue(MNEMONIC_KEY, KeyEvent.VK_X);
    this.mainFrame = mainFrame;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    confirmClosing();
  }

  public void confirmClosing() {
    int confirmed = JOptionPane.showConfirmDialog(mainFrame,
        "Are you sure you want to quit?", "Confirm quit",
        JOptionPane.YES_NO_OPTION);
    if (confirmed == JOptionPane.YES_OPTION) {
      // clean up code
      System.exit(0);
    }
  }
}
*/
