package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import data.BlockNames;
import data.RuinTemplate;
import data.RuinTemplateRule;
import data.RuleColors;

public class RulePanel extends JPanel {

	private RuinTemplate template;
	private JList ruleList;
	private JList blockIDs;

	private LocaleRenderer coloredRender;

	private DefaultListModel ruleModel = new DefaultListModel();
	private DefaultListModel blockModel = new DefaultListModel();
	private JRadioButton condButton1;
	private JRadioButton condButton2;

	public void setCurrentSelection(int currentSelection) {
		this.currentSelection = currentSelection;
	}

	private JRadioButton condButton3;

	private JButton removeButton;
	private JButton addButton;

	private JButton addBlockButton;
	private JButton remBlockButton;

	private JScrollPane listScrollPane;
	private JScrollPane listScrollPane2;
	private JComboBox blockList;

	private JFormattedTextField metaInput;

	private int currentSelection = 0;
	private int currentBlockselect = 0;

	private RuleListener ruleListener;

	JSlider chanceSlider;

	public int getCurrentSelection() {
		return currentSelection;
	}

	public RulePanel(RuinTemplate aTemplate) {

		ruleListener = new RuleListener();
		template = aTemplate;
		setLayout(new BorderLayout());

		// temp array for the JComboBox

		ArrayList<String> blocknamelist = new ArrayList<String>();
		for (int i = 0; i < BlockNames.names.length; i++) {
			if (BlockNames.names[i] != null) {
				blocknamelist.add(BlockNames.names[i]);
			}

		}

		chanceSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, template.getRules().get(currentSelection).getChance());
		chanceSlider.addChangeListener(ruleListener);
		chanceSlider.setPreferredSize(new Dimension(150, 50));
		chanceSlider.setMaximumSize(new Dimension(150, 50));
		metaInput = new JFormattedTextField(NumberFormat.getNumberInstance());

		blockList = new JComboBox(blocknamelist.toArray());

		removeButton = new JButton("remove");
		removeButton.addActionListener(ruleListener);

		addButton = new JButton("add");
		addButton.addActionListener(ruleListener);

		addBlockButton = new JButton("add block");
		addBlockButton.addActionListener(ruleListener);

		remBlockButton = new JButton("remove block");
		remBlockButton.addActionListener(ruleListener);

		condButton1 = new JRadioButton("No special conditions");
		condButton1.setMnemonic(KeyEvent.VK_1);
		condButton1.setSelected(true);
		condButton1.setActionCommand("b1");
		condButton1.addActionListener(ruleListener);

		condButton2 = new JRadioButton("Only place next to a block");
		condButton2.setMnemonic(KeyEvent.VK_2);
		condButton2.setActionCommand("b2");
		condButton2.addActionListener(ruleListener);

		condButton3 = new JRadioButton("Only place on a block");
		condButton3.setMnemonic(KeyEvent.VK_3);
		condButton3.setActionCommand("b3");
		condButton3.addActionListener(ruleListener);

		metaInput.addActionListener(ruleListener);
		metaInput.setActionCommand("metadata input");

		ButtonGroup group = new ButtonGroup();
		group.add(condButton1);
		group.add(condButton2);
		group.add(condButton3);

		coloredRender = new LocaleRenderer();
		ruleList = new JList(ruleModel);
		blockIDs = new JList(blockModel);
		ruleList.setCellRenderer(coloredRender);
		ruleList.addListSelectionListener(ruleListener);
		blockIDs.addListSelectionListener(ruleListener);

		reload();

		ruleList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ruleList.setLayoutOrientation(JList.VERTICAL_WRAP);
		ruleList.setLayoutOrientation(JList.VERTICAL);
		ruleList.setMaximumSize(new Dimension(150, 10000));
		blockList.setMaximumSize(new Dimension(150, 25));

		listScrollPane = new JScrollPane(ruleList);
		listScrollPane.setPreferredSize(new Dimension(240, 300));

		listScrollPane2 = new JScrollPane(blockIDs);
		listScrollPane2.setPreferredSize(new Dimension(240, 300));

		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(500, 500));
		panel1.setPreferredSize(new Dimension(250, 500));

		JPanel panel10 = new JPanel();
		panel10.setLayout(new GridLayout(4, 0));
		panel10.setPreferredSize(new Dimension(220, 100));
		panel10.setMaximumSize(new Dimension(220, 70));

		panel10.add(new JLabel("Block conditions"));
		panel10.add(condButton1);
		panel10.add(condButton2);
		panel10.add(condButton3);

		JPanel panel11 = new JPanel();
		// panel11.setLayout(new GridLayout(0, 2));
		panel11.setPreferredSize(new Dimension(250, 50));
		panel11.setMaximumSize(new Dimension(250, 50));
		panel11.add(removeButton);
		panel11.add(addButton);

		panel1.add(listScrollPane);
		panel1.add(panel11);

		add(panel1, BorderLayout.EAST);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel2.add(new JLabel("Possible block IDs"));
		panel2.add(blockList);

		JPanel panel13 = new JPanel();
		panel13.setLayout(new GridLayout(0, 2));
		panel13.setPreferredSize(new Dimension(250, 40));
		panel13.setMaximumSize(new Dimension(250, 40));
		panel13.setLayout(new GridLayout(0, 2));
		panel13.add(addBlockButton);
		panel13.add(remBlockButton);

		panel2.add(panel13);
		panel2.add(listScrollPane2);

		JPanel panelwhat = new JPanel();

		metaInput.setMaximumSize(new Dimension(60, 20));
		metaInput.setPreferredSize(new Dimension(60, 20));
		metaInput.setMinimumSize(new Dimension(60, 20));
		panelwhat.add(new JLabel("Meta data:"));
		panelwhat.add(metaInput);
		panel2.add(panelwhat);
		panel2.add(panel10);
		panel2.add(new JLabel("Chance:"));
		panel2.add(chanceSlider);

		add(panel2, BorderLayout.WEST);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void reload() {

		ruleModel.clear();
		for (int i = 0; i < template.getRules().size(); i++) {

			ruleModel.addElement("Rule " + i + " " + template.getRules().get(i).getNames());
		}

		ruleList.setSelectedIndex(currentSelection);
	}

	public void reloadCheckboxes() {
		chanceSlider.setValue(template.getRules().get(currentSelection).getChance());
		blockModel.clear();
		if (template.getBlockIDs(currentSelection).length != 0) {
			for (int i = 0; i < template.getBlockIDs(currentSelection).length; i++) {
				blockModel.addElement(BlockNames.names[template.getBlockIDs(currentSelection)[i]]
						+ (template.getBlockMDs(currentSelection)[i] != 0 ? ":" + template.getBlockMDs(currentSelection)[i] : ""));
			}
		}

		switch (template.getCondition(currentSelection)) {
		case (0):
			condButton1.setSelected(true);
			break;
		case (1):
			condButton2.setSelected(true);
			break;
		case (2):
			condButton3.setSelected(true);
			break;
		}
	}

	public void select() {

	}

	private class RuleListener implements ActionListener, ListSelectionListener, ChangeListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("add")) {
				template.getRules().add(new RuinTemplateRule());
				reload();
			}

			if (currentSelection == 0)
				return;
			if (e.getActionCommand().equals("b1")) {
				template.setCondition(currentSelection, 0);
			}
			if (e.getActionCommand().equals("b2")) {
				template.setCondition(currentSelection, 1);
			}
			if (e.getActionCommand().equals("b3")) {
				template.setCondition(currentSelection, 2);
			}

			if (e.getActionCommand().equals("remove")) {
				if (currentSelection == 0)
					return;
				if (template.getRules().size() == 1)
					return;
				template.getRules().remove(currentSelection);
				if (currentSelection > 0)
					currentSelection--;
				reload();
			}

			if (e.getActionCommand().equals("metadata input")) {
				if (blockModel.size() == 0)
					return;
				int[] tMDs = template.getBlockMDs(currentSelection);
				int remIndex = 0;
				if (!blockIDs.isSelectionEmpty()) {
					remIndex = blockIDs.getSelectedIndex();
				} else {
					return;
				}
				tMDs[remIndex] = Integer.parseInt(metaInput.getValue().toString());
				template.setBlockMDs(currentSelection, tMDs);
				reload();
			}

			if (e.getActionCommand().equals("add block")) {
				int[] tIDs = template.getBlockIDs(currentSelection);
				int[] tMDs = template.getBlockMDs(currentSelection);

				ArrayList<Integer> tMDList = new ArrayList<Integer>();
				ArrayList<Integer> tIDList = new ArrayList<Integer>();
				for (int i = 0; i < tIDs.length; i++) {
					tIDList.add(tIDs[i]);
					tMDList.add(tMDs[i]);
				}

				tIDList.add(BlockNames.getIndex((String) blockList.getSelectedItem()));
				tMDList.add(0);
				int[] tIDs2 = new int[tIDList.size()];
				int[] tMDs2 = new int[tMDList.size()];
				for (int i = 0; i < tIDs2.length; i++) {
					tIDs2[i] = tIDList.get(i);
					tMDs2[i] = tMDList.get(i);
				}
				template.setBlockMDs(currentSelection, tMDs2);
				template.setBlockIDs(tIDs2, currentSelection);
				reload();
			}

			if (e.getActionCommand().equals("remove block")) {
				if (blockModel.size() == 0)
					return;
				int[] tIDs = template.getBlockIDs(currentSelection);
				int[] tMDs = template.getBlockMDs(currentSelection);

				ArrayList<Integer> tMDList = new ArrayList<Integer>();
				ArrayList<Integer> tIDList = new ArrayList<Integer>();
				for (int i = 0; i < tIDs.length; i++) {
					tIDList.add(tIDs[i]);
					tMDList.add(tMDs[i]);
				}
				int remIndex = 0;
				if (!blockIDs.isSelectionEmpty()) {
					remIndex = blockIDs.getSelectedIndex();
				}

				tIDList.remove(BlockNames.getIndex((String) blockList.getItemAt(remIndex)));
				tMDList.add(0);
				int[] tIDs2 = new int[tIDList.size()];
				int[] tMDs2 = new int[tMDList.size()];
				for (int i = 0; i < tIDs2.length; i++) {
					tIDs2[i] = tIDList.get(i);
					tMDs2[i] = tMDList.get(i);
				}
				template.setBlockMDs(currentSelection, tMDs2);
				template.setBlockIDs(tIDs2, currentSelection);
				reload();

			}

		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource().equals(ruleList)) {
				if (!ruleList.isSelectionEmpty())
					currentSelection = ruleList.getSelectedIndex();
				reloadCheckboxes();
			} else if (e.getSource().equals(blockIDs)) {
				if (!blockIDs.isSelectionEmpty()) {
					currentBlockselect = blockIDs.getSelectedIndex();
					metaInput.setValue(template.getBlockMDs(currentSelection)[currentBlockselect]);
				}
			}
			repaint();
		}

		@Override
		public void stateChanged(ChangeEvent e) {

			JSlider source = (JSlider) e.getSource();

			if (!source.getValueIsAdjusting()) {
				if (currentSelection == 0) {
					source.setValue(template.getChance(0));
					return;
				}
				template.setChance(currentSelection, source.getValue());
			}
		}

	}

	@SuppressWarnings("serial")
	private class LocaleRenderer extends DefaultListCellRenderer {

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			this.setBackground(RuleColors.colors[index]);
			if (isSelected)
				this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1), BorderFactory.createLoweredBevelBorder()));
			return this;
		}
	}

}
