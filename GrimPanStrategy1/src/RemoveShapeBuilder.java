import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class RemoveShapeBuilder implements ShapeBuilder{
	
	GrimPanModel model = null;
	
	public RemoveShapeBuilder(GrimPanModel model){
		this.model = model;
	}

	@Override
	public void performMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);
		
		getSelectedShape();
		model.shapeList.remove(model.getSelectedShape());
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
			model.setLastMousePosition(model.getClickedMousePosition());
			Color scolor = shape.getGrimStrokeColor();
			Color fcolor = shape.getGrimFillColor();
			shape.setGrimStrokeColor(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 127));
			shape.setGrimFillColor(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 127));
		}
		model.setSelectedShape(selIndex);
	}


}
