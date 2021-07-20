package guiDesign;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;




class RollerButton extends JComponent implements MouseListener {
  private static final long serialVersionUID = 1L;
  public int pos;
  public int val;

  private int width;
  private int height;

  private Dimension size = new Dimension(36, 36);
  public Dimension dot = new Dimension((int) (size.width / 3), (int) (size.height / 3));
  public Dimension arc = new Dimension((int) Math.sqrt(size.width), (int) Math.sqrt(size.height));
  private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
  private boolean mouseEntered = false;
  private boolean mousePressed = false;

  private RollerContainer container;

  public RollerButton(int pos, RollerContainer container) {
    this(null, pos, container);
  }

  public RollerButton(ActionListener e, int pos, RollerContainer container) {
    super();

    this.container = container;

    enableInputMethods(true);
    addMouseListener(this);

    setSize(size.width, size.height);
    setFocusable(true);

    this.pos = pos;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (val != -1) {
      // turn on anti-alias mode
      Graphics2D antiAlias = (Graphics2D) g;
      antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      // draw white rectangle
      g.setColor(Color.WHITE);
      g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc.width, arc.height);

      // draw black border
      if (mouseEntered && val != -1) {
        g.setColor(Color.WHITE);
      } else {
        g.setColor(Color.BLACK);
      }
      g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc.width, arc.height);

      // draw inside light border
      g.setColor(Color.decode("#c0c0c0"));
      g.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc.width, arc.height);


    }
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
    mouseEntered = true;
    if (val != -1) {
      setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    repaint();
  }

  public void mouseExited(MouseEvent e) {
    mouseEntered = false;
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    repaint();
  }

  public void mousePressed(MouseEvent e) {
    notifyListeners(e);
    mousePressed = true;
    repaint();
  }

  public void mouseReleased(MouseEvent e) {
    mousePressed = false;
    if (val != 0) {
      container.depot.addRoller(val);
      container.depot.repaint();
      container.removeRoller(pos);
      container.repaint();
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    repaint();
  }

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  private void notifyListeners(MouseEvent e) {
    ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, new String(), e.getWhen(), e.getModifiers());
    synchronized (listeners) {
      for (int i = 0; i < listeners.size(); i++) {
        ActionListener tmp = listeners.get(i);
        tmp.actionPerformed(evt);
      }
    }
  }

  public Dimension getPreferredSize() {
    return new Dimension(getWidth(), getHeight());
  }

  public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  public Dimension getMaximumSize() {
    return getPreferredSize();
  }
}
