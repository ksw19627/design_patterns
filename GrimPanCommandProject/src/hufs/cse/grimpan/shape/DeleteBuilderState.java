package hufs.cse.grimpan.shape;
import hufs.cse.grimpan.core.GrimPanController;
import hufs.cse.grimpan.core.GrimPanModel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class DeleteBuilderState implements EditState{
	public static final int STATE_TYPE = 7;
	GrimPanModel model = null;
	GrimShape shape = null;
	
	public DeleteBuilderState(GrimPanModel model){
		this.model = model;
	}

	@Override
	public void performMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(SwingUtilities.isRightMouseButton(e)){
			Point p1 = e.getPoint();
			model.setMousePosition(p1);
			model.setClickedMousePosition(p1);
			
			getSelectedShape();
			Object YesNo = JOptionPane.showConfirmDialog(null, "Delete", "Delete", JOptionPane.YES_NO_OPTION);
			if(model.getSelectedShape()!=-1 && (int)(YesNo)==0){
				model.getController().deleteShapeAction();
				model.setSelectedShape(-1);
			}
			
		}
		
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStateType() {
		// TODO Auto-generated method stub
		return STATE_TYPE;
	}
	
	
	private void getSelectedShape(){
		int selIndex = -1;
		GrimShape shape = null;
		for (int i=model.shapeList.size()-1; i >= 0; --i){
			shape = model.shapeList.get(i);
			if (shape.contains(model.getMousePosition().getX(), model.getMousePosition().getY())){
				selIndex = i;
				break;
			}
		}
		if (selIndex != -1){
			model.setSavedPositionShape(model.shapeList.get(selIndex));
			model.setLastMousePosition(model.getClickedMousePosition());
			Color scolor = shape.getGrimStrokeColor();
			Color fcolor = shape.getGrimFillColor();
			shape.setGrimStrokeColor(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 127));
			shape.setGrimFillColor(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 127));
		}
		model.setSelectedShape(selIndex);
	}
}