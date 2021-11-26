import javax.swing.JFrame;
import junit.framework.TestCase;

public class MenuTest extends TestCase {

  private JFrame testFrame;


  protected void tearDown() throws Exception {
    if (this.testFrame != null) {
      this.testFrame.dispose();
      this.testFrame = null;
    }
  }

  public JFrame getTestFrame() {
    if (this.testFrame == null) {
      this.testFrame = new JFrame("Test");
    }
    return this.testFrame;
  }
}
