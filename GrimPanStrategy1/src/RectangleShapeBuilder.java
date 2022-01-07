import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

public class RectangleShapeBuilder implements ShapeBuilder {
	GrimPanModel model = null;
	
	public RectangleShapeBuilder(GrimPanModel model)
	{
		this.model = model;
	}

	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		genRectangle2D();
	}

	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

		genRectangle2D();
		if (model.curDrawShape != null){
			model.shapeList
			.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
					model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
			model.curDrawShape = null;
		}
		
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Point p1 = e.getPoint();
		model.setLastMousePosition(model.getMousePosition());
		model.setMousePosition(p1);

		genRectangle2D();
	}
	
	private void genRectangle2D(){
		Point pi = model.getMousePosition();
		Point topleft = model.getClickedMousePosition();
		if (pi.distance(new Point2D.Double(topleft.x, topleft.y)) <= Util.MINPOLYDIST)
			return;
		Rectangle2D rectangle = new Rectangle2D.Double(
				topleft.x, topleft.y,
				pi.x-topleft.x, pi.y-topleft.y);
		model.curDrawShape = rectangle;
	}

}
