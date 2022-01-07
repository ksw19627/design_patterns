package hufs.cse.grimpan4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GrimPanModel {
	
	public static final int SHAPE_REGULAR = 0;
	public static final int SHAPE_OVAL = 1;
	public static final int SHAPE_POLYGON = 2;
	public static final int SHAPE_LINE = 3;
	public static final int SHAPE_PENCIL = 4;
	public static final int EDIT_MOVE = 5;
	
	private JFrame mainFrame = null;
	private int panWidth = GrimPanMain.INIT_WIDTH;
	private int panHeight = GrimPanMain.INIT_HEIGHT;
	private Stroke shapeStroke = null;
	private Color shapeColor = null;
	private boolean shapeFill = false;
	private int editState = SHAPE_LINE;
	
	public ArrayList<GrimShape> shapeList = null;
	
	private Point mousePosition = null;
	private Point clickedMousePosition = null;
	private Point lastMousePosition = null;
	
	public Shape curDrawShape = null;
	public ArrayList<Point> polygonPoints = null;
	private int selectedShape = -1;
	
	private int nPolygon = 3;
	
	private File saveFile = null;
	private File svgFile = null;

	public GrimPanModel(JFrame frame){
		this.mainFrame = frame;
		this.shapeList = new ArrayList<GrimShape>();
		this.shapeStroke = new BasicStroke();
		this.shapeColor = Color.BLACK;
		this.polygonPoints = new ArrayList<Point>();
	}

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
		//System.out.println(shapeFill);
		this.shapeFill = shapeFill;
	}

	public int getEditState() {
		return editState;
	}

	public void setEditState(int editState) {
		this.editState = editState;
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
		setSaveFile(saveFile);
		try {
			ObjectInputStream input =
				new ObjectInputStream(new FileInputStream(this.saveFile));
			Object inObj = input.readObject();
			if (inObj instanceof Integer){
				this.panWidth = (Integer)inObj;
				this.panHeight = (Integer)input.readObject();
			}
			this.shapeList = (ArrayList<GrimShape>) input.readObject();
			input.close();

		} catch (ClassNotFoundException e) {
			System.err.println("Class not Found");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void readShapeFromSVGFile(File svgFile){
		setSvgFile(svgFile);
		//System.out.println("SVG File="+svgFile);
		Document doc = null;
		Element svg = null;
		
		try {
			// Parse the input svg file into a Document.
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);

			doc = f.createDocument(svgFile.toURI().toString());			
			svg = doc.getDocumentElement();

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		

		String swidth = svg.getAttribute("width");
		if (swidth.equals("")) swidth = "400";
		String sheight = svg.getAttribute("height");
		if (sheight.equals("")) sheight ="400";
		int ipx = swidth.indexOf("px");
		if (ipx >= 0){
			swidth = swidth.substring(0, ipx);
		}
		ipx = sheight.indexOf("px");
		if (ipx >= 0){
			sheight = sheight.substring(0, ipx);
		}
		setPanWidth(Integer.parseInt(swidth));
		setPanHeight(Integer.parseInt(sheight));
		//System.out.println("svgW="+getPanWidth()+" svgH="+getPanHeight());

		Stack<Node> parNodeStack = new Stack<Node>();
		parNodeStack.push(svg);
		while (!parNodeStack.empty()){
			Node parNode = parNodeStack.pop();
			NodeList childlist = parNode.getChildNodes();

			for (int i=0; i<childlist.getLength(); ++i){
				Node gnode = childlist.item(i);
				// Process only when Element node
				if (gnode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				String nodeName = gnode.getNodeName();
				Element gelem = (Element)gnode;
				if (nodeName.equals("g")){
					parNodeStack.push(gelem);
				}
				else if (nodeName.equals("path")){
					GrimShape gs = SVG2GrimShapeTranslator.translateSVG2Shape(gelem);
					if (gs != null){
						shapeList.add(gs);
					}
				}
				// else do nothing
				// skip when <g id="image..."> or <symbol...> or others

			}
		}
			
	}
	
	public void saveGrimPanData(File saveFile){
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(new FileOutputStream(saveFile));
			output.writeObject(new Integer(panWidth));
			output.writeObject(new Integer(panHeight));
			output.writeObject(this.shapeList);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return the saveFile
	 */
	public File getSaveFile() {
		return saveFile;
	}
	public File getSVGFile() {
		return svgFile;
	}

	/**
	 * @param saveFile the saveFile to set
	 */
	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
		mainFrame.setTitle("그림판 - "+saveFile.getPath());
	}
	public void setSvgFile(File svgFile) {
		this.svgFile = svgFile;
		mainFrame.setTitle("그림판 - "+svgFile.getPath());
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

	public int getPanWidth() {
		return panWidth;
	}

	public void setPanWidth(int panWidth) {
		this.panWidth = panWidth;
	}

	public int getPanHeight() {
		return panHeight;
	}

	public void setPanHeight(int panHeight) {
		this.panHeight = panHeight;
	}

	
}
