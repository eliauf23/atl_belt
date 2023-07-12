package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Aligns functionality of closing window with red X in top corner and quitting
 * from the file menu.
 */
class CloseAction extends AbstractAction {
    private final JFrame mainFrame;


    /**
     * Todo: insert javadoc comment.
     *
     * @param mainFrame
     */
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
