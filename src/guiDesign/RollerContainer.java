package guiDesign;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

public class RollerContainer extends JPanel {
  private static final long serialVersionUID = 1L;

  public Roller[] Roller = new Roller[5];
  public Roller[] total = new Roller[5];
  public RollerContainer depot;

  public RollerContainer(Roller[] d) {
    this.Roller = d;

    for (int i = 0; i < Roller.length; i++) {
      Roller[i] = new Roller(i, this);
      Roller[i].val = -1;

      total[i] = new Roller(i, null);
      total[i].val = -1;

      add(Roller[i]);
    }

    repaint();
  }

  public void paint(Graphics g) {
    super.paint(g);

    // turn on anti-alias mode
    Graphics2D antiAlias = (Graphics2D) g;
    antiAlias.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int arc = 15;
    int offset = 1;

    g.setColor(Color.decode("#555555"));
    g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

    g.setColor(Color.decode("#cccccc"));
    g.fillRoundRect(offset, offset, getWidth() - (offset * 2), getHeight() - (offset * 2), arc, arc);

    g.setColor(Color.WHITE);
    for (int i = 0; i < Roller.length; i++) {
      g.fillRoundRect(
          Roller[i].getX() - offset,
          Roller[i].getY() - offset,
          Roller[i].getWidth() + (offset * 2),
          Roller[i].getHeight() + (offset * 2),
          Roller[i].arc.width,
          Roller[i].arc.height
      );
    }

    super.paintChildren(g);
  }

  public void addRoller() {
    for (int i = 0; i < Roller.length; i++) {
      if (Roller[i].val == -1) {
        Roller[i].val = 0;
        return;
      }
    }
    return;
  }

  public boolean addRoller(int val) {
    for (int i = 0; i < Roller.length; i++) {
      if (Roller[i].val == -1) {
        Roller[i].val = val;
        return true;
      }
    }
    return false;
  }

  public void removeRoller(int pos) {
    if (Roller[pos].val != -1) {
      Roller[pos].val = -1;
    }
  }
}

