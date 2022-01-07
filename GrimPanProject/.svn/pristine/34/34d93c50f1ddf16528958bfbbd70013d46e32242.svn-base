package hufs.cse.grimpan1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GrimPanMainFrame extends JFrame {

	private GrimPanMainFrame thisClass = this;
	private GrimPanModel model = null;
	
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnShape;
	private JMenu mnEdit;
	private JMenu mnSetting;
	private JMenu mnHelp;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmExit;
	private JRadioButtonMenuItem rdbtnmntmLine;
	private JRadioButtonMenuItem rdbtnmntmPencil;
	private JRadioButtonMenuItem rdbtnmntmPolygon;
	private JMenuItem mntmAbout;
	/**
	 * @wbp.nonvisual location=141,-6
	 */
	private ButtonGroup buttonGroup = new ButtonGroup();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrimPanMainFrame frame = new GrimPanMainFrame();
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
	public GrimPanMainFrame() {
		initialize();
		
	}
	
	private void initialize() {
		setTitle("그림판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 543, 392);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File  ");
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuItem("Save As...");
		mnFile.add(mntmSaveAs);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		mnShape = new JMenu("Shape ");
		menuBar.add(mnShape);
		
		rdbtnmntmLine = new JRadioButtonMenuItem("선분");
		rdbtnmntmLine.setSelected(true);
		rdbtnmntmLine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
				model.setEditState(GrimPanModel.SHAPE_LINE);
			}
		});
		mnShape.add(rdbtnmntmLine);
		
		rdbtnmntmPencil = new JRadioButtonMenuItem("연필");
		rdbtnmntmPencil.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				model.setEditState(GrimPanModel.SHAPE_PENCIL);
			}
		});
		mnShape.add(rdbtnmntmPencil);
		
		rdbtnmntmPolygon = new JRadioButtonMenuItem("다각형");
		rdbtnmntmPolygon.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				model.setEditState(GrimPanModel.SHAPE_POLYGON);
			}
		});
		mnShape.add(rdbtnmntmPolygon);
		
		buttonGroup.add(rdbtnmntmLine);
		buttonGroup.add(rdbtnmntmPencil);
		buttonGroup.add(rdbtnmntmPolygon);
		
		mnEdit = new JMenu("Edit  ");
		menuBar.add(mnEdit);
		
		mnSetting = new JMenu("Setting ");
		menuBar.add(mnSetting);
		
		mnHelp = new JMenu("Help  ");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JOptionPane.showMessageDialog(thisClass,
						"GrimPan Ver0.1 \n"
						+ "Programmed by cskim, cse, hufs.ac.kr\n"
						+ "2014.10.17\n"
						+ "Copy Right -- Free for Educational Purpose", 
						 "About", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		mnHelp.add(mntmAbout);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		model = new GrimPanModel();
		
	}

}
