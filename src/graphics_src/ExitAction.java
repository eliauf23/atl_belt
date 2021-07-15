package graphics_src;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class ExitAction extends AbstractAction {
  public ExitAction() {
    super("Exit");
    putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_Q));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //  Find the active window before creating and dispatching the event

    Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();

    if (window != null) {
      WindowEvent windowClosing = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
      window.dispatchEvent(windowClosing);
    }
  }
}
