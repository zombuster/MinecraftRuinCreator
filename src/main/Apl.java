package main;

import gui.LayerPanel;
import gui.RulePanel;

import java.awt.Dimension;

import javax.swing.JFrame;

import data.RuinTemplate;

public class Apl {

	private RulePanel rulePanel;
	private LayerPanel layerPanel;
	private RuinTemplate template;

	public void run() {
		template = new RuinTemplate(8,8,8);
		JFrame frame = new JFrame("Ruin Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println("lol");
		
		layerPanel = new LayerPanel(template);
		template.addObserver(layerPanel);
		
		frame.setContentPane(layerPanel);
		frame.setPreferredSize(new Dimension(1000, 660));
		frame.setResizable(false);

		frame.pack();
		frame.setVisible(true);

	}

}
