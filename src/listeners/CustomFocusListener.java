package listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomFocusListener implements FocusListener {

	@Override
	public void focusGained(FocusEvent e) {
		e.getComponent().repaint();
    }
    @Override public void focusLost(FocusEvent e) {
      e.getComponent().repaint();
    

	}

}
