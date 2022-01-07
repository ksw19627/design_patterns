/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.shape;

import hufs.cse.grimpan.core.GrimPanController;
import hufs.cse.grimpan.core.GrimPanModel;
import hufs.cse.grimpan.core.Util;
import java.awt.geom.Rectangle2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * @author cskim
 *
 */
public class RectangleBuilderState implements EditState {

	public static final int STATE_TYPE = 6;
	GrimPanModel model = null;
	
	public RectangleBuilderState(GrimPanModel model){
		this.model = model;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMousePressed(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);
		model.setClickedMousePosition(p1);

		genRectangle2D();
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent e) {
		Point p1 = e.getPoint();
		model.setMousePosition(p1);

		genRectangle2D();
		if(model.curDrawShape != null){
			model.shapeList.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
					model.getShapeStrokeColor(),model.isShapeFill(),model.getShapeFillColor()));
			model.curDrawShape = null;
					
		}
		this.model.ObserverChange();
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseDragged(MouseEvent e) {
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
	@Override
	public int getStateType() {
		// TODO Auto-generated method stub
		return STATE_TYPE;
	}

}
