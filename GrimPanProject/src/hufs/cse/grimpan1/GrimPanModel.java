package hufs.cse.grimpan1;

import java.awt.Color;
import java.awt.Stroke;

public class GrimPanModel {

	public static final int SHAPE_REGULAR = 0;
	public static final int SHAPE_OVAL = 1;
	public static final int SHAPE_POLYGON = 2;
	public static final int SHAPE_LINE = 3;
	public static final int SHAPE_PENCIL = 4;
	
	private Stroke shapeStroke = null;
	private Color shapeColor = null;
	private boolean shapeFill = false;
	private int editState = SHAPE_LINE; // Default Value
	
	public Stroke getShapeStroke() {
		return shapeStroke;
	}
	public void setShapeStroke(Stroke shapeStroke) {
		this.shapeStroke = shapeStroke;
	}
	public Color getShapeColor() {
		return shapeColor;
	}
	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	public boolean isShapeFill() {
		return shapeFill;
	}
	public void setShapeFill(boolean shapeFill) {
		this.shapeFill = shapeFill;
	}
	public int getEditState() {
		return editState;
	}
	public void setEditState(int editState) {
		this.editState = editState;
	}
	
}
