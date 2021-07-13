package com.company;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;


public class Draw implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

  //List of colors available for 2D Graphics
  public static final Color BLACK = Color.BLACK;
  public static final Color BLUE = Color.BLUE;
  public static final Color CYAN = Color.CYAN;
  public static final Color DARK_GRAY = Color.DARK_GRAY;
  public static final Color GRAY = Color.GRAY;
  public static final Color GREEN = Color.GREEN;
  public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
  public static final Color MAGENTA = Color.MAGENTA;
  public static final Color ORANGE = Color.ORANGE;
  public static final Color PINK = Color.PINK;
  public static final Color RED = Color.RED;
  public static final Color WHITE = Color.WHITE;
  public static final Color YELLOW = Color.YELLOW;
  public static final Color MEDIUM_BLUE = new Color(9, 90, 166);
  public static final Color AQUA = new Color(103, 198, 243);
  public static final Color BOOK_RED = new Color(150, 35, 31);
  public static final Color PRINCETON_ORANGE = new Color(245, 128, 37);
  public static final Color MEDIUM_GRAY = new Color(88, 88, 88);
  public static final Color NAVY_BLUE = new Color(4, 22, 56);


  // default colors
  private static final Color DEFAULT_PEN_COLOR = BLACK;
  private static final Color DEFAULT_CLEAR_COLOR = WHITE;
  private static final Color DEFAULT_PART_COLOR = Color.DARK_GRAY;
  private static final Color DEFAULT_SHAFT_COLOR = Color.LIGHT_GRAY;


  // default canvas size is square w/ side len. = DEFAULT_SIZE
  private static final int DEFAULT_SIZE = 512;

  // default pen radius

  private static final double DEFAULT_PEN_RADIUS = 0.002;

  // boundary of drawing canvas, 5% border
  private static final double BORDER = 0.05;
  private static final double DEFAULT_XMIN = 0.0;
  private static final double DEFAULT_XMAX = 1.0;
  private static final double DEFAULT_YMIN = 0.0;
  private static final double DEFAULT_YMAX = 1.0;

  // default font
  private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);

  //modifiable properties - pen color, canvas width & height, pen radius, current buffer status
  //currently not using next two lines TODO: remove if unnecessary for rendering
  private static final Object mouseLock = new Object();
  private static final Object keyLock = new Object();
  //TODO: revisit this decision - might be able to eliminate this & Draw constructor
  //singleton for callbacks: avoids generation of extra .class files
  private static final Draw std = new Draw();
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
  // double-buffered graphics (can animate drawing - if desired)
  private static BufferedImage offscreenImage;
  private static BufferedImage onscreenImage;
  private static Graphics2D offscreen;
  private static Graphics2D onscreen;
  // the frame for drawing to the screen
  private static JFrame frame;
  // mouse state
  private static boolean isMousePressed = false;
  private static double mouseX = 0;
  private static double mouseY = 0;
  // queue of typed key characters
  private static LinkedList<Character> keysTyped;
  // set of key codes currently pressed down
  private static TreeSet<Integer> keysDown;

  protected static String titleString = "Atlantic Belt - Roller Spec. Application";

  // static initializer
  static {
    init();
  }

  /* Constructors */
  Draw() {
  }

  /**
   * Initialization functions
   */

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


  // init
  private static void init(){

    try {
      UIManager.setLookAndFeel(new FlatDarculaLaf());
    } catch(Exception e) {
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
    offscreen.setColor(DEFAULT_CLEAR_COLOR);
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

    // frame stuff
    ImageIcon icon = new ImageIcon(onscreenImage);
    JLabel draw = new JLabel(icon);

    draw.addMouseListener(std);
    draw.addMouseMotionListener(std);

    frame.setContentPane(draw);
    frame.addKeyListener(std);    // JLabel cannot get keyboard focus
    frame.setFocusTraversalKeysEnabled(false);  // allow VK_TAB with isKeyPressed()
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
    // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
    frame.setTitle(titleString);
    frame.setJMenuBar(createMenuBar());
    frame.pack();
    frame.requestFocusInWindow();

    frame.setVisible(true);
  }

  // create the menu bar (changed to private)
  protected static JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    //add File menu


    JMenu fileMenu = new JMenu("File");


    menuBar.add(fileMenu);
    //Add save functionality
    JMenuItem fileMenuItem1 = new JMenuItem(" Save...   ");
    fileMenuItem1.addActionListener(std);
    // Java 10+: replace getMenuShortcutKeyMask() with getMenuShortcutKeyMask()
    fileMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    fileMenu.add(fileMenuItem1);
    //TODO: Add "open text file functionality" DIRECTLY FROM FILE MENU


    //Add exit functionality
    JMenuItem fileMenuItem2 = new JMenuItem("Exit   ");
    fileMenuItem1.addActionListener(std);
    fileMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

    fileMenu.add(fileMenuItem2);

    //Add a new menu
    JMenu editMenu = new JMenu("Help");


    menuBar.add(editMenu);
    JMenuItem editMenuItem1 = new JMenuItem("FAQ");
    editMenu.add(editMenuItem1);

    return menuBar;
  }

  /***************************************************************************
   *  User and screen coordinate systems.
   ***************************************************************************/

  // throw an IllegalArgumentException if x is NaN or infinite
  private static void validate(double x, String name) {
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


  /**
   * Sets the <em>x</em>-scale to be the default (between 0.0 and 1.0).
   */
  public static void setXscale() {
    setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
  }

  /**
   * Sets the <em>y</em>-scale to be the default (between 0.0 and 1.0).
   */
  public static void setYscale() {
    setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
  }

  /**
   * Sets the <em>x</em>-scale and <em>y</em>-scale to be the default
   * (between 0.0 and 1.0).
   */
  public static void setScale() {
    setXscale();
    setYscale();
  }

  /**
   * Sets the <em>x</em>-scale to the specified range.
   *
   * @param min the minimum value of the <em>x</em>-scale
   * @param max the maximum value of the <em>x</em>-scale
   * @throws IllegalArgumentException if {@code (max == min)}
   * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
   */
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

  /**
   * Sets the <em>y</em>-scale to the specified range.
   *
   * @param min the minimum value of the <em>y</em>-scale
   * @param max the maximum value of the <em>y</em>-scale
   * @throws IllegalArgumentException if {@code (max == min)}
   * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
   */
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

  /**
   * Sets both the <em>x</em>-scale and <em>y</em>-scale to the (same) specified range.
   *
   * @param min the minimum value of the <em>x</em>- and <em>y</em>-scales
   * @param max the maximum value of the <em>x</em>- and <em>y</em>-scales
   * @throws IllegalArgumentException if {@code (max == min)}
   * @throws IllegalArgumentException if either {@code min} or {@code max} is either NaN or infinite
   */
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

  // helper functions that scale from user coordinates to screen coordinates and back
  private static double scaleX(double x) {
    return width * (x - xmin) / (xmax - xmin);
  }

  private static double scaleY(double y) {
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


  /**
   * Clears the screen to the default color (white).
   */
  public static void clear() {
    clear(DEFAULT_CLEAR_COLOR);
  }

  /**
   * Clears the screen to the specified color.
   *
   * @param color the color to make the background
   * @throws IllegalArgumentException if {@code color} is {@code null}
   */
  public static void clear(Color color) {
    validateNotNull(color, "color");
    offscreen.setColor(color);
    offscreen.fillRect(0, 0, width, height);
    offscreen.setColor(penColor);
    draw();
  }

  /**
   * Returns the current pen radius.
   *
   * @return the current value of the pen radius
   */
  public static double getPenRadius() {
    return penRadius;
  }

  /**
   * Sets the radius of the pen to the specified size.
   * The pen is circular, so that lines have rounded ends, and when you set the
   * pen radius and draw a point, you get a circle of the specified radius.
   * The pen radius is not affected by coordinate scaling.
   *
   * @param radius the radius of the pen
   * @throws IllegalArgumentException if {@code radius} is negative, NaN, or infinite
   */
  public static void setPenRadius(double radius) {
    validate(radius, "pen radius");
    validateNonnegative(radius, "pen radius");

    penRadius = radius;
    float scaledPenRadius = (float) (radius * DEFAULT_SIZE);
    BasicStroke stroke = new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    // BasicStroke stroke = new BasicStroke(scaledPenRadius);
    offscreen.setStroke(stroke);
  }

  /**
   * Sets the pen size to the default size (0.002).
   * The pen is circular, so that lines have rounded ends, and when you set the
   * pen radius and draw a point, you get a circle of the specified radius.
   * The pen radius is not affected by coordinate scaling.
   */
  public static void setPenRadius() {
    setPenRadius(DEFAULT_PEN_RADIUS);
  }

  /**
   * Returns the current pen color.
   *
   * @return the current pen color
   */
  public static Color getPenColor() {
    return penColor;
  }

  /**
   * Sets the pen color to the specified color.
   * <p>
   * The predefined pen colors are
   * {@code Draw.BLACK}, {@code Draw.BLUE}, {@code Draw.CYAN},
   * {@code Draw.DARK_GRAY}, {@code Draw.GRAY}, {@code Draw.GREEN},
   * {@code Draw.LIGHT_GRAY}, {@code Draw.MAGENTA}, {@code Draw.ORANGE},
   * {@code Draw.PINK}, {@code Draw.RED}, {@code Draw.WHITE}, and
   * {@code Draw.YELLOW}.
   *
   * @param color the color to make the pen
   * @throws IllegalArgumentException if {@code color} is {@code null}
   */
  public static void setPenColor(Color color) {
    validateNotNull(color, "color");
    penColor = color;
    offscreen.setColor(penColor);
  }

  /**
   * Sets the pen color to the default color (black).
   */
  public static void setPenColor() {
    setPenColor(DEFAULT_PEN_COLOR);
  }

  /**
   * Sets the pen color to the specified RGB color.
   *
   * @param red   the amount of red (between 0 and 255)
   * @param green the amount of green (between 0 and 255)
   * @param blue  the amount of blue (between 0 and 255)
   * @throws IllegalArgumentException if {@code red}, {@code green},
   *                                  or {@code blue} is outside its prescribed range
   */
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

  /**
   * Returns the current font.
   *
   * @return the current font
   */
  public static Font getFont() {
    return font;
  }

  /**
   * Sets the font to the specified value.
   *
   * @param font the font
   * @throws IllegalArgumentException if {@code font} is {@code null}
   */
  public static void setFont(Font font) {
    validateNotNull(font, "font");
    Draw.font = font;
  }

  /**
   * Sets the font to the default font (sans serif, 16 point).
   */
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

  /***************************************************************************
   *  Drawing text.
   ***************************************************************************/

  /**
   * Writes the given text string in the current font, centered at (<em>x</em>, <em>y</em>).
   *
   * @param x    the center <em>x</em>-coordinate of the text
   * @param y    the center <em>y</em>-coordinate of the text
   * @param text the text to write
   * @throws IllegalArgumentException if {@code text} is {@code null}
   * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
   */
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

  /**
   * Writes the given text string in the current font, centered at (<em>x</em>, <em>y</em>) and
   * rotated by the specified number of degrees.
   *
   * @param x       the center <em>x</em>-coordinate of the text
   * @param y       the center <em>y</em>-coordinate of the text
   * @param text    the text to write
   * @param degrees is the number of degrees to rotate counterclockwise
   * @throws IllegalArgumentException if {@code text} is {@code null}
   * @throws IllegalArgumentException if {@code x}, {@code y}, or {@code degrees} is either NaN or infinite
   */
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


  /**
   * Writes the given text string in the current font, left-aligned at (<em>x</em>, <em>y</em>).
   *
   * @param x    the <em>x</em>-coordinate of the text
   * @param y    the <em>y</em>-coordinate of the text
   * @param text the text
   * @throws IllegalArgumentException if {@code text} is {@code null}
   * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
   */
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

  /**
   * Writes the given text string in the current font, right-aligned at (<em>x</em>, <em>y</em>).
   *
   * @param x    the <em>x</em>-coordinate of the text
   * @param y    the <em>y</em>-coordinate of the text
   * @param text the text to write
   * @throws IllegalArgumentException if {@code text} is {@code null}
   * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
   */
  public static void textRight(double x, double y, String text) {
    validate(x, "x");
    validate(y, "y");
    validateNotNull(text, "text");

    offscreen.setFont(font);
    FontMetrics metrics = offscreen.getFontMetrics();
    double xs = scaleX(x);
    double ys = scaleY(y);
    int ws = metrics.stringWidth(text);
    int hs = metrics.getDescent();
    offscreen.drawString(text, (float) (xs - ws), (float) (ys + hs));
    draw();
  }


  public static void pause(int t) {
    validateNonnegative(t, "t");
    try {
      Thread.sleep(t);
    } catch (InterruptedException e) {
      System.out.println("Error sleeping");
    }
  }

  /**
   * Enables double buffering. All subsequent calls to
   * drawing methods such as {@code line()}, {@code circle()},
   * and {@code square()} will be deferred until the next call
   * to show(). Useful for animations.
   */
  public static void enableDoubleBuffering() {
    defer = true;
  }

  /**
   * Disables double buffering. All subsequent calls to
   * drawing methods such as {@code line()}, {@code circle()},
   * and {@code square()} will be displayed on screen when called.
   * This is the default.
   */
  public static void disableDoubleBuffering() {
    defer = false;
  }


  /**********************
   * Draw basic geometry: rectangles, lines, filled-rectangle (will add more later
   */

  //TODO: determine if I need to add any additional functionality (like drawing geometric objects, text?)
  private static void pixel(double x, double y) {
    validate(x, "x");
    validate(y, "y");
    offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
  }

  public static void line(double x0, double y0, double x1, double y1) {
    validate(x0, "x0");
    validate(y0, "y0");
    validate(x1, "x1");
    validate(y1, "y1");
    offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
    draw();
  }

  /**
   * Draws one pixel at (<em>x</em>, <em>y</em>).
   * This method is private because pixels depend on the display.
   * To achieve the same effect, set the pen radius to 0 and call {@code point()}.
   *
   * @param x the <em>x</em>-coordinate of the pixel
   * @param y the <em>y</em>-coordinate of the pixel
   * @throws IllegalArgumentException if {@code x} or {@code y} is either NaN or infinite
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
   */
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

  /**
   * Draws a rectangle of the specified size, centered at (<em>x</em>, <em>y</em>).
   *
   * @param x          the <em>x</em>-coordinate of the center of the rectangle
   * @param y          the <em>y</em>-coordinate of the center of the rectangle
   * @param halfWidth  one half the width of the rectangle
   * @param halfHeight one half the height of the rectangle
   * @throws IllegalArgumentException if either {@code halfWidth} or {@code halfHeight} is negative
   * @throws IllegalArgumentException if any argument is either NaN or infinite
   */
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


  /***************************************************************************
   *  Save drawing to a file.
   ***************************************************************************/

  /**
   * Saves the drawing to using the specified filename.
   * The supported image formats are JPEG and PNG;
   * the filename fileType must be {@code .jpg} or {@code .png}.
   *
   * @param filename the name of the file with one of the required fileTypees
   * @throws IllegalArgumentException if {@code filename} is {@code null}
   */
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

  public static boolean saveToPNG(String filename) {
    boolean isSuccess = false;
    validateNotNull(filename, "filename");
    File file = new File(filename);
    String fileType = filename.substring(filename.lastIndexOf('.') + 1);
    if (fileType.equalsIgnoreCase(fileType)) {
      try {
        ImageIO.write(onscreenImage, fileType, file);
        isSuccess = true;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return isSuccess;
  }

  public static void main(String[] args) {
    System.out.println("draw class test");
  }


  /**
   * This method cannot be called directly.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    FileDialog chooser = new FileDialog(Draw.frame, "Use a .png or .jpg extension", FileDialog.SAVE);
    chooser.setVisible(true);
    String filename = chooser.getFile();
    if (filename != null) {
      Draw.save(chooser.getDirectory() + File.separator + chooser.getFile());
    }
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    // this body is intentionally left empty
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    // this body is intentionally left empty
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void mouseExited(MouseEvent e) {
    // this body is intentionally left empty
  }


  /***************************************************************************
   *  Keyboard interactions.
   ***************************************************************************/

  /**
   * This method cannot be called directly.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    synchronized (mouseLock) {
      mouseX = Draw.userX(e.getX());
      mouseY = Draw.userY(e.getY());
      isMousePressed = true;
    }
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    synchronized (mouseLock) {
      isMousePressed = false;
    }
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void mouseDragged(MouseEvent e) {
    synchronized (mouseLock) {
      mouseX = Draw.userX(e.getX());
      mouseY = Draw.userY(e.getY());
    }
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void mouseMoved(MouseEvent e) {
    synchronized (mouseLock) {
      mouseX = Draw.userX(e.getX());
      mouseY = Draw.userY(e.getY());
    }
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    synchronized (keyLock) {
      keysTyped.addFirst(e.getKeyChar());
    }
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    synchronized (keyLock) {
      keysDown.add(e.getKeyCode());
    }
  }

  /**
   * This method cannot be called directly.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    synchronized (keyLock) {
      keysDown.remove(e.getKeyCode());
    }
  }


  /***************************************************************************
   *  For improved resolution on Mac Retina displays.
   ***************************************************************************/

  private static class RetinaImageIcon extends ImageIcon {

    public RetinaImageIcon(Image image) {
      super(image);
    }

    public int getIconWidth() {
      return super.getIconWidth() / 2;
    }

    /**
     * Gets the height of the icon.
     *
     * @return the height in pixels of this icon
     */
    public int getIconHeight() {
      return super.getIconHeight() / 2;
    }

    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.scale(0.5, 0.5);
      super.paintIcon(c, g2, x * 2, y * 2);
      g2.dispose();
    }
  }
}