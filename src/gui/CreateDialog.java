package gui;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

public class CreateDialog extends JPanel {

	private JFormattedTextField XIn;
	private JFormattedTextField YIn;
	private JFormattedTextField ZIn;
	private JButton create;

	public int x, y, z;

	private LayerPanel panel;

	public CreateDialog(LayerPanel panel) {
		this.panel = panel;
		// setPreferredSize(new Dimension(200, 200));
		// setMinimumSize(new Dimension(200, 200));
		setLayout(new GridLayout(7, 0));
		XIn = new JFormattedTextField(NumberFormat.getNumberInstance());
		YIn = new JFormattedTextField(NumberFormat.getNumberInstance());
		ZIn = new JFormattedTextField(NumberFormat.getNumberInstance());

		create = new JButton("Create");
		ButtonListener buttonListener = new ButtonListener();
		create.addActionListener(buttonListener);
		XIn.setValue(new Integer(8));
		YIn.setValue(new Integer(8));
		ZIn.setValue(new Integer(8));
		XIn.addPropertyChangeListener(buttonListener);
		YIn.addPropertyChangeListener(buttonListener);
		ZIn.addPropertyChangeListener(buttonListener);
		add(new JLabel("Width:"));
		add(XIn);
		add(new JLabel("Height:"));
		add(YIn);
		add(new JLabel("Length:"));
		add(ZIn);
		add(create);
	}

	private class ButtonListener implements ActionListener, PropertyChangeListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			x = Integer.parseInt((String) XIn.getValue().toString());
			y = Integer.parseInt((String) YIn.getValue().toString());
			z = Integer.parseInt((String) ZIn.getValue().toString());

			Component c = getParent();
			while (c.getParent() != null) {
				c = c.getParent();
			}
			JFrame pFrame = (JFrame) c;
			pFrame.setVisible(false);

			panel.createNewTemplate(x, y, z);
		}

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			Object source = e.getSource();
			if (source == XIn) {
				x = ((Number) (XIn.getValue())).intValue();
			} else if (source == YIn) {
				y = ((Number) (YIn.getValue())).intValue();
			} else if (source == ZIn) {
				z = ((Number) (ZIn.getValue())).intValue();
			}
		}
	}

}
