/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.strategy;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GrimPanModel {
	
	private GrimPanFrameMain mainFrame = null;
	private int editState = Util.SHAPE_LINE;

	public final ShapeBuilder[] SHAPE_BUILDERS = {
		new RegularShapeBuilder(this),
		new OvalShapeBuilder(this),
		new PolygonShapeBuilder(this),
		new LineShapeBuilder(this),
		new PencilShapeBuilder(this),
		new MoveShapeBuilder(this),
	};
	public ShapeBuilder sb = null;
	
	private float shapeStrokeWidth = 1f;
	private Color shapeStrokeColor = null;
	private boolean shapeFill = false;
	private Color shapeFillColor = null;
	
	public ArrayList<GrimShape> shapeList = null;
	
	private Point mousePosition = null;
	private Point clickedMousePosition = null;
	private Point lastMousePosition = null;
	
	public Shape curDrawShape = null;
	public ArrayList<Point> polygonPoints = null;
	private int selectedShape = -1;
	
	private int nPolygon = 3;
	
	private File saveFile = null;

	public GrimPanModel(GrimPanFrameMain frame){
		this.mainFrame = frame;
		this.shapeList = new ArrayList<GrimShape>();
		this.shapeStrokeColor = Color.BLACK;
		this.shapeFillColor = Color.BLACK;
		this.polygonPoints = new ArrayList<Point>();
	}

	public int getEditState() {
		return editState;
	}

	public void setEditState(int editState) {
		this.editState = editState;
		if (editState == Util.EDIT_MOVE){
			mainFrame.modeLBL.setText(String.format("Mode: %s  ", "?̵? "));
		}
		else {
			mainFrame.modeLBL.setText(String.format("Mode: %s  ", "?߰? "));
			mainFrame.shapeLbl.setText(String.format("Shape: %s  ", Util.SHAPE_NAME[this.getEditState()]));
		}
		this.sb = SHAPE_BUILDERS[this.getEditState()];
	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
	}
	public Point getLastMousePosition() {
		return lastMousePosition;
	}

	public void setLastMousePosition(Point mousePosition) {
		this.lastMousePosition = mousePosition;
	}

	public Point getClickedMousePosition() {
		return clickedMousePosition;
	}

	public void setClickedMousePosition(Point clickedMousePosition) {
		this.clickedMousePosition = clickedMousePosition;
	}
	public void readShapeFromSaveFile(File saveFile) {
		this.saveFile = saveFile;
		ObjectInputStream input;
		try {
			input =
				new ObjectInputStream(new FileInputStream(this.saveFile));
			this.shapeList = (ArrayList<GrimShape>) input.readObject();
			input.close();

		} catch (ClassNotFoundException e) {
			System.err.println("Class not Found");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		}
	}
	public void saveGrimPanData(File saveFile){
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(new FileOutputStream(saveFile));
			output.writeObject(this.shapeList);
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		}
	}
	/**
	 * @return the saveFile
	 */
	public File getSaveFile() {
		return saveFile;
	}

	/**
	 * @param saveFile the saveFile to set
	 */
	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
		mainFrame.setTitle("?׸??? - "+saveFile.getPath());
	}
	/**
	 * @return the nPolygon
	 */
	public int getNPolygon() {
		return nPolygon;
	}

	/**
	 * @param nPolygon the nPolygon to set
	 */
	public void setNPolygon(int nPolygon) {
		this.nPolygon = nPolygon;
	}

	/**
	 * @return the selectedShape
	 */
	public int getSelectedShape() {
		return selectedShape;
	}

	/**
	 * @param selectedShape the selectedShape to set
	 */
	public void setSelectedShape(int selectedShape) {
		this.selectedShape = selectedShape;
	}

	/**
	 * @return the shapeStrokeColor
	 */
	public Color getShapeStrokeColor() {
		return shapeStrokeColor;
	}

	/**
	 * @param shapeStrokeColor the shapeStrokeColor to set
	 */
	public void setShapeStrokeColor(Color shapeStrokeColor) {
		this.shapeStrokeColor = shapeStrokeColor;
	}

	/**
	 * @return the shapeFill
	 */
	public boolean isShapeFill() {
		return shapeFill;
	}

	/**
	 * @param shapeFill the shapeFill to set
	 */
	public void setShapeFill(boolean shapeFill) {
		this.shapeFill = shapeFill;
	}

	/**
	 * @return the shapeFillColor
	 */
	public Color getShapeFillColor() {
		return shapeFillColor;
	}

	/**
	 * @param shapeFillColor the shapeFillColor to set
	 */
	public void setShapeFillColor(Color shapeFillColor) {
		this.shapeFillColor = shapeFillColor;
	}

	/**
	 * @return the shapeStrokeWidth
	 */
	public float getShapeStrokeWidth() {
		return shapeStrokeWidth;
	}

	/**
	 * @param shapeStrokeWidth the shapeStrokeWidth to set
	 */
	public void setShapeStrokeWidth(float shapeStrokeWidth) {
		this.shapeStrokeWidth = shapeStrokeWidth;
	}

	
}
