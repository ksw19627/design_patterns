/**
 * Created on 2015. 4. 3.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan.core;

import hufs.cse.grimpan.command.AddCommand;
import hufs.cse.grimpan.command.Command;
import hufs.cse.grimpan.command.MoveCommand;
import hufs.cse.grimpan.shape.GrimShape;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * @author cskim
 *
 */
public class GrimPanController {
	
	private GrimPanModel model = GrimPanModel.getInstance();
	private GrimPanFrameView frameView = null;
	private DrawPanelView drawPanelView = null;

	public GrimPanController(){
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createGrimPanFrameView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void createGrimPanFrameView(){
		frameView = new GrimPanFrameView(this, model);
		frameView.setVisible(true);
		model.setController(this);
		
	}
	public void openAction() {
		if (frameView.jFileChooser1.showOpenDialog(frameView) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = frameView.jFileChooser1.getSelectedFile();
			readShapeFromSaveFile(selFile);
			model.setSaveFile(selFile);
			drawPanelView.repaint();
		}
	}
	public void saveAction() {
		if (model.getSaveFile()==null){
			model.setSaveFile(new File(model.getDefaultDir()+"noname.grm"));
		}
		File selFile = model.getSaveFile();
		saveGrimPanData(selFile);	
	}
	public void saveAsAction() {
		if (frameView.jFileChooser2.showSaveDialog(frameView) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = frameView.jFileChooser2.getSelectedFile();
			model.setSaveFile(selFile);
			saveGrimPanData(selFile);
		}
	}
	public void readShapeFromSaveFile(File saveFile) {
		model.setSaveFile(saveFile);
		ObjectInputStream input;
		try {
			input =
				new ObjectInputStream(new FileInputStream(saveFile));
			model.shapeList = (ArrayList<GrimShape>) input.readObject();
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
			output.writeObject(model.shapeList);
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
	 * @return the drawPanelView
	 */
	public DrawPanelView getDrawPanelView() {
		return drawPanelView;
	}

	/**
	 * @param drawPanelView the drawPanelView to set
	 */
	public void setDrawPanelView(DrawPanelView drawPanelView) {
		this.drawPanelView = drawPanelView;
	}

	/**
	 * 
	 */
	public void clearAllShape() {
		model.shapeList.clear();
		model.curDrawShape = null;
		model.polygonPoints.clear();
		drawPanelView.repaint();
	}

	/**
	 * 
	 */
	public void setMoveShapeState() {
		model.setEditState(Util.EDIT_MOVE);
		if (model.curDrawShape != null){
			model.shapeList
			.add(new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
					model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
			model.curDrawShape = null;
		}
		drawPanelView.repaint();
	}

	/**
	 * 
	 */
	public void setStrokeWithAction() {
		String inputVal = JOptionPane.showInputDialog("??????", "1");
		if (inputVal!=null){
			model.setShapeStrokeWidth(Float.parseFloat(inputVal));
		}
		else {
			model.setShapeStrokeWidth(1f);
		}
		
	}

	/**
	 * 
	 */
	public void setBoundaryColorAction() {
		Color color = 
				JColorChooser.showDialog(frameView, 
						"Choose a color",
						Color.black);					
		if (color!=null){
			model.setShapeStrokeColor(color);
		}
		else {
			model.setShapeStrokeColor(Color.black);
		}
	}

	/**
	 * 
	 */
	public void setFillColorAction() {
		Color color = 
				JColorChooser.showDialog(frameView, 
						"Choose a color",
						Color.black);					
		if (color!=null){
			model.setShapeFillColor(color);
		}
		else {
			model.setShapeFillColor(Color.black);
		}
	}

	/**
	 * 
	 */
	public void addShapeAction() {
		Command addCommand = new AddCommand(model, new GrimShape(model.curDrawShape, model.getShapeStrokeWidth(),
				model.getShapeStrokeColor(), model.isShapeFill(), model.getShapeFillColor()));
		model.undoCommandStack.push(addCommand);// save for undo
		addCommand.execute();

	}

	/**
	 * 
	 */
	public void moveShapeAction() {
		Command moveCommand = new MoveCommand(model, model.getSavedPositionShape());
		model.undoCommandStack.push(moveCommand);// save for undo
		moveCommand.execute();
	}
	/**
	 * 
	 */
	public void recoveryAction() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	public void undoAction() {
		Command comm = model.undoCommandStack.pop();
		comm.undo();
		drawPanelView.repaint();
	}



}
