package hufs.cse.grimpan.shape;


import java.awt.event.MouseEvent;

public interface EditState {
	
	public void performMousePressed(MouseEvent e);
	public void performMouseReleased(MouseEvent e);
	public void performMouseDragged(MouseEvent e);
	public int getStateType();

}
