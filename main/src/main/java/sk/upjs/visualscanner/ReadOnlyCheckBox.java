package sk.upjs.visualscanner;

import java.awt.event.*;
import javax.swing.*;

/**
 * A simple JCheckBox that ignores all mouse and keyboard actions.
 */
class ReadOnlyCheckBox extends JCheckBox {

	private static final long serialVersionUID = -3332825412062260594L;

	public ReadOnlyCheckBox(String text) {
		super(text, false);
	}

	protected void processKeyEvent(KeyEvent e) {
	}

	protected void processMouseEvent(MouseEvent e) {

	}
}