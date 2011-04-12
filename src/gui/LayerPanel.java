package gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import data.RuinTemplate;
import data.RuleColors;

@SuppressWarnings("serial")
public class LayerPanel extends JPanel implements Observer {

	private JMenuBar menuBar;
	private JMenu loadsaveMenu;
	private JMenuItem newTemplate;
	private JMenuItem save;
	private JMenuItem saveAs;
	private JMenuItem load;
	private JMenuItem exit;

	private TargetPanel targetPanel;

	private GridListener gridListener;
	private RuinTemplate template;

	private RulePanel rulePanel;

	private JLabel heightText;

	private JButton upButton;
	private JButton downButton;

	private JButton setAnchorLayer;

	private int currentLayer = 0;
	private LayerView layerView;

	private int cellsizex = 10;
	private int cellsizey = 10;

	private String fileName = "";

	private JTextField overhang;
	private JTextField weight;

	CreateDialog createDialog;
	JFrame frame;

	public LayerPanel(RuinTemplate aTemplate) {

		createDialog = new CreateDialog(this);
		frame = new JFrame();
		frame.setContentPane(createDialog);
		heightText = new JLabel("current layer:" + currentLayer);
		template = aTemplate;
		gridListener = new GridListener();
		rulePanel = new RulePanel(template);
		layerView = new LayerView();

		targetPanel = new TargetPanel(template);

		overhang = new JTextField(Integer.toString(template.getOverhang()));
		weight = new JTextField(Integer.toString(template.getWeight()));

		overhang.setPreferredSize(new Dimension(100, 20));
		overhang.setMaximumSize(new Dimension(100, 20));
		overhang.setActionCommand("overhang");
		weight.setActionCommand("weight");
		overhang.addActionListener(gridListener);
		weight.addActionListener(gridListener);

		weight.setPreferredSize(new Dimension(100, 20));
		weight.setMaximumSize(new Dimension(100, 20));
		menuBar = new JMenuBar();
		loadsaveMenu = new JMenu("File");
		loadsaveMenu.setMnemonic(KeyEvent.VK_F);
		newTemplate = new JMenuItem("New");
		newTemplate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAs = new JMenuItem("Save As");
		saveAs.setAccelerator(KeyStroke.getKeyStroke("shift ctrl S"));
		load = new JMenuItem("Load");
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		exit = new JMenuItem("Exit");
		loadsaveMenu.add(newTemplate);
		loadsaveMenu.add(load);
		loadsaveMenu.add(save);
		loadsaveMenu.add(saveAs);
		loadsaveMenu.add(exit);
		newTemplate.addActionListener(gridListener);
		load.addActionListener(gridListener);
		save.addActionListener(gridListener);
		saveAs.addActionListener(gridListener);
		exit.addActionListener(gridListener);

		menuBar.add(loadsaveMenu);
		upButton = new JButton("Up");
		upButton.setActionCommand("up");
		upButton.addActionListener(gridListener);

		setAnchorLayer = new JButton("Set Current level to ground level");
		setAnchorLayer.addActionListener(gridListener);
		downButton = new JButton("Down");
		downButton.setActionCommand("down");
		downButton.addActionListener(gridListener);

		setLayout(new BorderLayout());
		add(menuBar, BorderLayout.NORTH);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 2));
		panel1.add(rulePanel);
		JPanel subPanel1 = new JPanel();
		subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.Y_AXIS));
		subPanel1.add(layerView);
		JPanel subPanel2 = new JPanel();
		subPanel2.setLayout(new BorderLayout());
		subPanel2.setPreferredSize(new Dimension(400, 80));
		subPanel2.setMaximumSize(new Dimension(400, 80));
		subPanel2.add(upButton, BorderLayout.WEST);

		subPanel2.add(heightText, BorderLayout.CENTER);
		subPanel2.add(setAnchorLayer, BorderLayout.SOUTH);
		subPanel2.add(downButton, BorderLayout.EAST);
		subPanel1.add(subPanel2);
		JPanel subPanel3 = new JPanel();
		subPanel3.add(new JLabel("Overhang:"));
		subPanel3.add(overhang);
		subPanel3.add(new JLabel("Weight:"));
		subPanel3.add(weight);
		subPanel1.add(subPanel3);
		subPanel1.add(targetPanel);
		panel1.add(subPanel1);
		add(panel1, BorderLayout.CENTER);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		rulePanel.reload();

		repaint();

	}

	public void createNewTemplate(int x, int y, int z) {
		template.clear(x, z, y);
		fileName = "";
		layerView.refill();
		
		Component c = getParent();
		while (c.getParent() != null) {
			c = c.getParent();
		}
		JFrame pFrame = (JFrame) c;
		pFrame.setTitle("Ruin Creation Tool");
		
		rulePanel.setCurrentSelection(0);
		overhang.setText(Integer.toString(template.getOverhang()));
		weight.setText(Integer.toString(template.getWeight()));
		rulePanel.reload();
		targetPanel.reload();
		repaint();

	}

	public void createNew() {

		frame.setPreferredSize(new Dimension(200, 200));
		frame.setMinimumSize(new Dimension(200, 200));

		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void load() {
		JFileChooser fc = new JFileChooser();

		fc.setCurrentDirectory(new File("."));
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			fileName = file.getPath();

			try {
				template.load(fileName);
				Component c = getParent();
				while (c.getParent() != null) {
					c = c.getParent();
				}
				JFrame pFrame = (JFrame) c;
				pFrame.setTitle(fileName);
				overhang.setText(Integer.toString(template.getOverhang()));
				weight.setText(Integer.toString(template.getWeight()));
				layerView.refill();
				rulePanel.reload();
				targetPanel.reload();
				repaint();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void save() {
		if (fileName.equals("")) {
			saveAs();
			return;
		}
		
		try {
			template.setOverhang(Integer.parseInt(overhang.getText().toString()));
		} catch (NumberFormatException exception) {
			overhang.setText("" + template.getOverhang());
		}
		
		try {
			template.setWeight(Integer.parseInt(weight.getText().toString()));
		} catch (NumberFormatException exception) {
			weight.setText("" + template.getWeight());
		}
		
		if (!fileName.endsWith(".tml"))
			fileName += ".tml";
		template.saveToFile(fileName);
		Component c = getParent();
		while (c.getParent() != null) {
			c = c.getParent();
		}
		JFrame pFrame = (JFrame) c;
		pFrame.setTitle("Ruin Creation Tool" + fileName);
	}

	public void saveAs() {

		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			fileName = file.getPath();
			save();
		}
	}

	@SuppressWarnings("serial")
	private class LayerView extends JPanel {

		public LayerView() {
			this.addMouseMotionListener(gridListener);
			this.addMouseListener(gridListener);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			refill();
		}

		public void refill() {
			if (template.getWidth() > template.getHeight()) {
				cellsizex = 400 / template.getWidth();
				cellsizey = 400 / template.getWidth();
			} else {
				cellsizex = 400 / template.getLength();
				cellsizey = 400 / template.getLength();
			}
			this.setPreferredSize(new Dimension(cellsizex * template.getWidth(), cellsizey * template.getWidth()));
			this.setMinimumSize(new Dimension(cellsizex * template.getWidth(), cellsizey * template.getWidth()));
			this.setMaximumSize(new Dimension(cellsizex * template.getWidth(), cellsizey * template.getWidth()));
			heightText.setText("current layer:  " + currentLayer + ((currentLayer == template.getEmbed()) ? "   ground layer" : ""));
			revalidate();
			repaint();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			for (int x = 0; x < template.getWidth(); x++) {
				for (int z = 0; z < template.getLength(); z++) {
					if (currentLayer > 1) {
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
						g2.setColor(RuleColors.colors[template.getData(x, currentLayer - 2, z)]);
						g2.fillRect(x * cellsizex, z * cellsizey, cellsizex, cellsizey);
					}
					if (currentLayer > 0) {
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
						g2.setColor(RuleColors.colors[template.getData(x, currentLayer - 1, z)]);
						g2.fillRect(x * cellsizex, z * cellsizey, cellsizex, cellsizey);
					}
					if(template.getData(x, currentLayer, z) != 0){
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
					g2.setColor(RuleColors.colors[template.getData(x, currentLayer, z)]);
					g2.fillRect(x * cellsizex, z * cellsizey, cellsizex, cellsizey);
					}
					g.setColor(Color.BLACK);
					g.drawRect(x * cellsizex, z * cellsizey, cellsizex, cellsizey);
					if (cellsizex > 20 && cellsizey > 20)
						g.drawString(Integer.toString(template.getData(x, currentLayer, z)), x * cellsizex + 20, z * cellsizey + 20);
				}
			}

		}

	}

	private class GridListener implements MouseMotionListener, MouseListener, ActionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getX() < cellsizex * template.getWidth() && e.getY() < cellsizey * template.getLength())
				template.setData(e.getX() / cellsizex, currentLayer, e.getY() / cellsizey, rulePanel.getCurrentSelection());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getX() < cellsizex * template.getWidth() && e.getY() < cellsizey * template.getLength())
				template.setData(e.getX() / cellsizex, currentLayer, e.getY() / cellsizey, rulePanel.getCurrentSelection());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("overhang")) {
				try {
					template.setOverhang(Integer.parseInt(overhang.getText().toString()));
				} catch (NumberFormatException exception) {
					overhang.setText("" + template.getOverhang());
				}
			}
			if (e.getActionCommand().equals("weight")) {
				try {
					template.setWeight(Integer.parseInt(weight.getText().toString()));
				} catch (NumberFormatException exception) {
					weight.setText("" + template.getWeight());
				}
			}

			if (e.getActionCommand().equals("Set Current level to ground level")) {
				template.setEmbed(currentLayer);
			}

			if (e.getActionCommand().equals("New")) {
				createNew();
			}
			if (e.getActionCommand().equals("Load")) {
				load();
			}
			if (e.getActionCommand().equals("Save")) {
				save();
			}
			if (e.getActionCommand().equals("Save As")) {
				saveAs();
			}
			if (e.getActionCommand().equals("Exit")) {
				System.exit(0);
			}

			if (e.getActionCommand().equals("up")) {
				if (currentLayer < template.getHeight() - 1)
					currentLayer++;
			}

			if (e.getActionCommand().equals("down")) {
				if (currentLayer > 0)
					currentLayer--;
			}
			layerView.refill();
		}
	}

}
