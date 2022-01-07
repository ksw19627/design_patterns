/**
 * Created on 2015. 3. 8.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.core;

import hufs.cse.grimpan.shape.DeleteBuilderState;
import hufs.cse.grimpan.shape.EditState;
import hufs.cse.grimpan.shape.GrimShape;
import hufs.cse.grimpan.shape.LineBuilderState;
import hufs.cse.grimpan.shape.MoveBuilderState;
import hufs.cse.grimpan.shape.OvalBuilderState;
import hufs.cse.grimpan.shape.PencilBuilderState;
import hufs.cse.grimpan.shape.PolygonBuilderState;
import hufs.cse.grimpan.shape.RectangleBuilderState;
import hufs.cse.grimpan.shape.RegularBuilderState;
import hufs.cse.grimpan.command.Command;

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
import java.util.Properties;
import java.util.Stack;
import java.util.Observable;

public class GrimPanModel extends Observable {
	
	private volatile static GrimPanModel uniqueModelInstance;
	
	private GrimPanFrameView frameView = null;
	private GrimPanController controller = null;
	
	private String defaultDir = "C:/home/cskim/temp/";
	
	public EditState editstate = null;
	public EditState savedAddState = null;
	public final EditState STATE_RECTANGLE = new RectangleBuilderState(this);
	public final EditState STATE_REGULAR = new RegularBuilderState(this);
	public final EditState STATE_OVAL = new OvalBuilderState(this);
	public final EditState STATE_POLYGON = new PolygonBuilderState(this);
	public final EditState STATE_LINE = new LineBuilderState(this);
	public final EditState STATE_PENCIL = new PencilBuilderState(this);
	public final EditState STATE_MOVE = new MoveBuilderState(this);
	public final EditState STATE_DELETE = new DeleteBuilderState(this);
	
	//public EditState sb = null;
	
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
	private GrimShape savedPositionShape = null;
	
	private int nPolygon = 3;
	
	private File saveFile = null;
	private File recoverFile = null;
	public Stack<Command> undoCommandStack = null;
	
	public PropertyManager grimpanPM = null;
	
	public EditState getEdit(String a){
		switch(a){
		case "STATE_PENCIL":
			return STATE_PENCIL;
		case "STATE_LINE":
			return STATE_LINE;
		case "STATE_POLYGON":
			return STATE_POLYGON;
		case "STATE_REGULAR":
			return STATE_REGULAR;
		case "STATE_OVAL":
			return STATE_OVAL;
		case "STATE_RECTANGLE" :
			return STATE_RECTANGLE;
		case "STATE_MOVE":
			return STATE_MOVE;
		}
		return STATE_LINE;
	}
	
	private GrimPanModel(){
		this.shapeList = new ArrayList<GrimShape>();
		this.shapeStrokeColor = Color.BLACK;
		this.shapeFillColor = Color.BLACK;
		this.polygonPoints = new ArrayList<Point>();
		this.recoverFile = new File(defaultDir+"noname.rcv");
		this.undoCommandStack = new Stack<Command>();
		
		this.grimpanPM = new PropertyManager("resources/grimpan.properties");
		this.shapeStrokeWidth = Float.parseFloat(grimpanPM.getPanProperties().getProperty("default.stroke.width"));//선 두께
		this.shapeStrokeColor = new Color(Integer.parseInt(grimpanPM.getPanProperties().getProperty("default.stroke.color"),16));//선 색깔
		this.shapeFillColor = new Color(Integer.parseInt(grimpanPM.getPanProperties().getProperty("default.stroke.fill"),16));//색채우는 것
		this.shapeFill = this.parseboolean(); //색채우는 것을 true/false로 구분한다
		String a = grimpanPM.getPanProperties().getProperty("default.SHAPE"); //모양의 디폴트값을 문자열로 받기위해 만들어준것
		this.editstate = getEdit(a); //상태를 문자열로 받는다
		this.savedAddState = this.editstate;
	}
	public static GrimPanModel getInstance() {
		if (uniqueModelInstance == null) {
			synchronized (GrimPanModel.class) {
				if (uniqueModelInstance == null) {
					uniqueModelInstance = new GrimPanModel();
				}
			}
		}
		return uniqueModelInstance;
	}
	
	
	public boolean getShapeFill()
	{
		return this.shapeFill;
	}
	/**
	 * @return the mainFrame
	 */
	public boolean parseboolean()
	{
		if (this.grimpanPM.getPanProperties().getProperty("default.shpae.fill").equals("true"))
			return true;
		else
			return false;
		
		
	}
	
	public String getString(EditState a)
	{
		if(a == STATE_LINE){
			return "STATE_LINE";
		}
		else if(a == STATE_PENCIL){
			return "STATE_PENCIL";
		}
		else if(a == STATE_POLYGON){
			return "STATE_POLYGON";
		}
		else if(a == STATE_REGULAR){
			return "STATE_REGULAR";
		}
		else if(a == STATE_OVAL){
			return "STATE_OVAL";
		}
		else if(a == STATE_RECTANGLE){
			return "STATE_RECTANGLE";
		}
		else{
			return "STATE_MOVE";
		}
		
	}
	public GrimPanFrameView getFrameView() {
		return frameView;
	}
	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setFrameView(GrimPanFrameView mainFrame) {
		this.frameView = mainFrame;
	}
	public EditState getEditState() {
		return editstate;
	}
	
	public void ObserverChange() {
		setChanged();
		notifyObservers();
	}

	public void setEditState(EditState editState) {
		this.editstate = editState;
		if (editState.getStateType() == Util.EDIT_MOVE){
			this.editstate = editState;
			ObserverChange();
			//frameView.modeLBL.setText(String.format("Mode: %s  ", "이동 "));
		}
		else {
			this.savedAddState = editState;
			setChanged();
			notifyObservers();
			this.savedAddState = editstate;
			//frameView.modeLBL.setText(String.format("Mode: %s  ", "추가 "));
			//frameView.shapeLbl.setText(String.format("Shape: %s  ", Util.SHAPE_NAME[editState.getStateType()]));
		}
		this.grimpanPM.updateProperty("default.SHAPE", getString(this.editstate));
		//this.sb = editState;
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
		frameView.setTitle("그림판 - "+saveFile.getPath());
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
		
		if (shapeFill)
			this.grimpanPM.updateProperty("default.shpae.fill", "true");
		else
			this.grimpanPM.updateProperty("default.shpae.fill", "false");
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
	/**
	 * @return the defaultDir
	 */
	public String getDefaultDir() {
		return defaultDir;
	}
	/**
	 * @param defaultDir the defaultDir to set
	 */
	public void setDefaultDir(String defaultDir) {
		this.defaultDir = defaultDir;
	}
	/**
	 * @return the controller
	 */
	public GrimPanController getController() {
		return controller;
	}
	/**
	 * @param controller the controller to set
	 */
	public void setController(GrimPanController controller) {
		this.controller = controller;
	}
	/**
	 * @return the recoverFile
	 */
	public File getRecoverFile() {
		return recoverFile;
	}
	/**
	 * @param recoverFile the recoverFile to set
	 */
	public void setRecoverFile(File recoverFile) {
		this.recoverFile = recoverFile;
	}
	/**
	 * @param grimShape
	 */
	public void setSavedPositionShape(GrimShape grimShape) {
		savedPositionShape = grimShape.clone();		
	}
	public GrimShape getSavedPositionShape() {
		return savedPositionShape;		
	}

	
}
