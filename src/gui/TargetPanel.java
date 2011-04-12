package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import data.BlockNames;
import data.RuinTemplate;

public class TargetPanel extends JPanel {

	private JComboBox blockList;
	private JButton addButton;
	private JButton removeButton;
	private JList list;
	private DefaultListModel listModel;

	private RuinTemplate template;

	public TargetPanel(RuinTemplate template) {
		this.template = template;

		BListener bListener = new BListener();

		removeButton = new JButton("remove");
		addButton = new JButton("add");
		removeButton.addActionListener(bListener);
		addButton.addActionListener(bListener);
		listModel = new DefaultListModel();
		for (int i = 0; i < template.getTargets().length; i++) {
			listModel.addElement(BlockNames.names[template.getTargets()[i]]);
		}
		list = new JList(listModel);
		// list.setA
		ArrayList<String> blocknamelist = new ArrayList<String>();
		for (int i = 0; i < BlockNames.names.length; i++) {
			if (BlockNames.names[i] != null) {
				blocknamelist.add(BlockNames.names[i]);
			}

		}

		blockList = new JComboBox(blocknamelist.toArray());
		add(new JLabel("Target blocks:"));
		add(blockList);
		add(addButton);
		add(removeButton);
		add(list);
	}
	
	public void reload(){
		listModel.clear();
		for (int i = 0; i < template.getTargets().length; i++) {
			listModel.addElement(BlockNames.names[template.getTargets()[i]]);
		}
	}

	private class BListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("add")) {
				int[] target = template.getTargets();
				ArrayList<Integer> targetList = new ArrayList<Integer>();
				for (int i = 0; i < target.length; i++) {
					targetList.add(target[i]);
				}
				targetList.add(BlockNames.getIndex((String) blockList.getSelectedItem()));
				int[] targets2 = new int[targetList.size()];
				for (int i = 0; i < targets2.length; i++) {
					targets2[i] = targetList.get(i);
				}
				template.setTargets(targets2);
				listModel.clear();
				for (int i = 0; i < template.getTargets().length; i++) {
					listModel.addElement(BlockNames.names[template.getTargets()[i]]);
				}
				repaint();
			}
			if (e.getActionCommand().equals("remove")) {
				if (!list.isSelectionEmpty()) {
					int[] target = template.getTargets();
					ArrayList<Integer> targetList = new ArrayList<Integer>();
					for (int i = 0; i < target.length; i++) {
						targetList.add(target[i]);
					}
					targetList.remove(list.getSelectedIndex());
					int[] targets2 = new int[targetList.size()];
					for (int i = 0; i < targets2.length; i++) {
						targets2[i] = targetList.get(i);
					}
					template.setTargets(targets2);
					listModel.clear();
					for (int i = 0; i < template.getTargets().length; i++) {
						listModel.addElement(BlockNames.names[template.getTargets()[i]]);
					}
					repaint();
				}
			}
		}

	}

}
