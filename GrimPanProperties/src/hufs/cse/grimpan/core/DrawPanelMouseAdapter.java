/**
 * Created on 2015. 4. 13.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.core;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

/**
 * @author cskim
 *
 */
public class DrawPanelMouseAdapter extends MouseInputAdapter {

	private GrimPanModel model = null;
	private DrawPanelView drawView = null;
	
	public DrawPanelMouseAdapter(GrimPanModel model, DrawPanelView drawView){
		this.model = model;
		this.drawView = drawView;
	}
	public void mousePressed(MouseEvent ev) {

		if (SwingUtilities.isLeftMouseButton(ev)){
			model.editState.performMousePressed(ev);
		}
		drawView.repaint();
	}

	public void mouseReleased(MouseEvent ev) {

		if (SwingUtilities.isLeftMouseButton(ev)){
			model.editState.performMouseReleased(ev);
		}
		drawView.repaint();

	}

	public void mouseDragged(MouseEvent ev) {
		
		if (SwingUtilities.isLeftMouseButton(ev)){
			model.editState.performMouseDragged(ev);
		}
		drawView.repaint();

	}

}
