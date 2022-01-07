/**
 * Created on Nov 12, 2014
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.cse.grimpan4;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author cskim
 *
 */
public class GrimPanMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int topX = 100;
	static final int topY = 100;
	static final int INIT_WIDTH = 400;
	static final int INIT_HEIGHT = 400;
	private GrimPanMain thisClass = this;
	private JPanel contentPane;
	private DrawPanel drawPanel = null;
	private GrimPanModel model = null;

	public JCheckBoxMenuItem jcmiFill = null;
	private JFileChooser jFileChooser1 = null;
	private JFileChooser jFileChooser2 = null;
	private String defaultDir = "/home/cskim/temp/";

	JMenuBar panMenuBar;	
	JMenu fileMenu;
	JMenuItem jmiNew;
	JMenuItem jmiOpen;
	JMenuItem jmiSave;
	JMenuItem jmiSaveAs;
	JMenuItem jmiSaveAsSvg;
	JMenuItem jmiExit;
	JMenu shapeMenu;
	JRadioButtonMenuItem rdbtnmntmLine;
	JRadioButtonMenuItem rdbtnmntmPen;	
	JRadioButtonMenuItem rdbtnmntmPoly;
	JRadioButtonMenuItem rdbtnmntmRegular;
	JRadioButtonMenuItem rdbtnmntmOval;
	JMenu editMenu;	
	JMenuItem jmiRemove;
	JMenuItem jmiMove;
	JMenu settingMenu;
	JMenuItem jmiStroke;
	JMenuItem jmiColor;	
	JMenu helpMenu;	
	JMenuItem jmiAbout;

	ButtonGroup btnGroup = new ButtonGroup();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrimPanMain frame = new GrimPanMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GrimPanMain() {
		setTitle("그림판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(topX, topY, INIT_WIDTH, INIT_HEIGHT);

		panMenuBar = new JMenuBar();
		setJMenuBar(panMenuBar);

		fileMenu = new JMenu("File  ");
		panMenuBar.add(fileMenu);

		jmiNew = new JMenuItem("New  ");
		jmiNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.shapeList.clear();
				model.curDrawShape = null;
				model.polygonPoints.clear();
				//model.setShapeColor(Color.BLACK);
				//model.setShapeStroke(new BasicStroke());
				//model.setShapeFill(false);
				//jcmiFill.setState(false);
				drawPanel.repaint();
			}
		});
		fileMenu.add(jmiNew);

		jmiOpen = new JMenuItem("Open ");
		jmiOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openAction();
			}
		});
		fileMenu.add(jmiOpen);

		jmiSave = new JMenuItem("Save ");
		jmiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAction();
			}
		});
		fileMenu.add(jmiSave);

		jmiSaveAs = new JMenuItem("Save  As...");
		jmiSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAsAction();
			}
		});
		fileMenu.add(jmiSaveAs);

		jmiSaveAsSvg = new JMenuItem("Save As SVG");
		jmiSaveAsSvg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAsSVGAction();
			}
		});
		fileMenu.add(jmiSaveAsSvg);

		jmiExit = new JMenuItem("Exit");
		jmiExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(jmiExit);

		shapeMenu = new JMenu("Shape  ");
		panMenuBar.add(shapeMenu);

		rdbtnmntmLine = new JRadioButtonMenuItem("선분");
		rdbtnmntmLine.setSelected(true);
		rdbtnmntmLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.DESELECTED) return;
				model.setEditState(GrimPanModel.SHAPE_LINE);
			}
		});
		shapeMenu.add(rdbtnmntmLine);

		rdbtnmntmPen = new JRadioButtonMenuItem("연필");
		rdbtnmntmPen.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.DESELECTED) return;
				model.setEditState(GrimPanModel.SHAPE_PENCIL);

			}
		});
		shapeMenu.add(rdbtnmntmPen);

		rdbtnmntmPoly = new JRadioButtonMenuItem("다각형");
		rdbtnmntmPoly.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.DESELECTED) return;
				model.setEditState(GrimPanModel.SHAPE_POLYGON);
			}
		});
		shapeMenu.add(rdbtnmntmPoly);

		rdbtnmntmRegular = new JRadioButtonMenuItem("정다각형");
		rdbtnmntmRegular.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.DESELECTED) return;
				
				model.setEditState(GrimPanModel.SHAPE_REGULAR);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStroke(),
							model.getShapeColor(), model.isShapeFill()));
					model.curDrawShape = null;
				}
				Object[] possibleValues = { 
						"3", "4", "5", "6", "7",
						"8", "9", "10", "11", "12"
				};
				Object selectedValue = JOptionPane.showInputDialog(null,
						"Choose one", "Input",
						JOptionPane.INFORMATION_MESSAGE, null,
						possibleValues, possibleValues[0]);
				if (selectedValue!=null) {
					model.setNPolygon(Integer.parseInt((String)selectedValue));
				}

				drawPanel.repaint();
			}
		});
		shapeMenu.add(rdbtnmntmRegular);

		rdbtnmntmOval = new JRadioButtonMenuItem("타원형");
		rdbtnmntmOval.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange()==ItemEvent.DESELECTED) return;
				model.setEditState(GrimPanModel.SHAPE_OVAL);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStroke(),
							model.getShapeColor(), model.isShapeFill()));
					model.curDrawShape = null;
				}
			}
		});
		shapeMenu.add(rdbtnmntmOval);

		btnGroup.add(rdbtnmntmLine);
		btnGroup.add(rdbtnmntmPen);
		btnGroup.add(rdbtnmntmPoly);
		btnGroup.add(rdbtnmntmRegular);
		btnGroup.add(rdbtnmntmOval);

		editMenu = new JMenu("Edit  ");
		panMenuBar.add(editMenu);

		jmiRemove = new JMenuItem("제거");
		editMenu.add(jmiRemove);

		jmiMove = new JMenuItem("이동");
		jmiMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setEditState(GrimPanModel.EDIT_MOVE);
				if (model.curDrawShape != null){
					model.shapeList
					.add(new GrimShape(model.curDrawShape, model.getShapeStroke(),
							model.getShapeColor(), model.isShapeFill()));
					model.curDrawShape = null;
				}
				drawPanel.repaint();
			}
		});
		editMenu.add(jmiMove);

		settingMenu = new JMenu("Setting");
		panMenuBar.add(settingMenu);

		jmiStroke = new JMenuItem("선");
		jmiStroke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputVal = JOptionPane.showInputDialog("선두께", "1");
				if (inputVal!=null){
					model.setShapeStroke(new BasicStroke(Integer.parseInt(inputVal)));
					drawPanel.repaint();
				}
			}
		});
		settingMenu.add(jmiStroke);

		jmiColor = new JMenuItem("색");
		jmiColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = 
						JColorChooser.showDialog(thisClass, 
								"Choose a color",
								Color.black);					
				model.setShapeColor(color);
				drawPanel.repaint();
			}
		});
		settingMenu.add(jmiColor);

		jcmiFill = new JCheckBoxMenuItem("Fill");
		jcmiFill.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				boolean fillState = jcmiFill.getState();
				model.setShapeFill(fillState);
				drawPanel.repaint();
			}
		});
		settingMenu.add(jcmiFill);

		helpMenu = new JMenu("Help  ");
		panMenuBar.add(helpMenu);

		jmiAbout = new JMenuItem("About");
		jmiAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(thisClass,
						"GrimPan Ver0.2.2 \nProgrammed by cskim, cse, hufs.ac.kr", 
						"About", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		helpMenu.add(jmiAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		model = new GrimPanModel(this);
		drawPanel = new DrawPanel(model);
		contentPane.add(drawPanel, BorderLayout.CENTER);

		jFileChooser1 = new JFileChooser(defaultDir);
		jFileChooser1.setDialogTitle("Open Saved GrimPan");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("GrimPan & SVG Images", "grm", "svg");
		jFileChooser1.setFileFilter(filter);

		jFileChooser2 = new JFileChooser(defaultDir);
		jFileChooser2.setDialogType(JFileChooser.SAVE_DIALOG);
		jFileChooser2.setDialogTitle("Save As ...");
		filter = new FileNameExtensionFilter("GrimPan Images", "grm");
		jFileChooser2.setFileFilter(filter);

	}
	private void openAction() {
		if (jFileChooser1.showOpenDialog(this) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = jFileChooser1.getSelectedFile();
			String fileName = jFileChooser1.getName(selFile);
			int dotIndex = fileName.lastIndexOf(".");
			if (fileName.substring(dotIndex+1).equals("svg")){
				model.readShapeFromSVGFile(selFile);
				setBounds(topX, topY, model.getPanWidth(), model.getPanHeight());
			}
			else {
				model.readShapeFromSaveFile(selFile);
				setBounds(topX, topY, model.getPanWidth(), model.getPanHeight());
			}
			drawPanel.repaint();
		}
	}
	private void saveAction() {
		if (model.getSaveFile()==null){
			model.setSaveFile(new File(defaultDir+"noname.grm"));
		}
		File selFile = model.getSaveFile();
		model.saveGrimPanData(selFile);	
	}
	private void saveAsAction() {
		if (jFileChooser2.showSaveDialog(this) ==
				JFileChooser.APPROVE_OPTION) {
			File selFile = jFileChooser2.getSelectedFile();
			String fileName = jFileChooser2.getName(selFile);
			String pathName = jFileChooser2.getCurrentDirectory().toString();
			int dotIndex = fileName.lastIndexOf(".");
			if (dotIndex == -1){ // no file extension
				fileName += ".grm";
			}
			else { // let file extension to be grm
				fileName = fileName.substring(0, dotIndex)+".grm";
			}
			selFile = new File(pathName,fileName);
			//System.out.println("selfile="+selFile);
			model.setSaveFile(selFile);
			model.saveGrimPanData(selFile);
		}
	}
	private void saveAsSVGAction() {
		File svgFile = model.getSVGFile();

		if (svgFile == null){
			if (model.getSaveFile()==null){
				svgFile = new File(defaultDir+"noname.svg");
			}
			else {
				String saveFileName = model.getSaveFile().getName();
				svgFile = new File(defaultDir+saveFileName.replace(".grm", ".svg"));
			}
		}
		
		PrintWriter svgout = null;
		try {
			svgout = new PrintWriter(svgFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		svgout.println("<?xml version='1.0' encoding='utf-8'?>");
		svgout.println("<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'>");
		svgout.print("<svg 	 width=");
		svgout.print("'"+this.getWidth()+"' ");
		svgout.print("height='"+this.getHeight()+"' ");
		svgout.println("overflow='visible' xml:space='preserve'>");
		for (GrimShape gs:model.shapeList){
			svgout.println("    "+GrimShape2SVGTranslator.translateShape2SVG(gs));
		}
		svgout.println("</svg>");
		svgout.close();

	}

}
