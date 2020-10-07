/***********************************************************************
 * Module:  ApplicationBrowser.java
 * Author:  User
 * Purpose: Defines the Class ApplicationBrowser
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionListener;

import listeners.CustomTreeSelectionListener;
import model.ComponentModel;
import model.XMLModel;
import utility.CustomTreeCellRenderer;
import utility.CustomTreeUI;

public class ApplicationBrowser extends ViewComponent
{
	JPanel panel = null;
	XMLModel xmlModel = null;
	private JTree tree = null;
	private JScrollPane scrollPane = null;

	public ApplicationBrowser(XMLModel model)
	{
		this.xmlModel = model;
		tree = new JTree();
		tree.setModel(new XMLModel(null));
		tree.setRootVisible(false);

		tree.setRootVisible(false);
		tree.setOpaque(false);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		scrollPane.getViewport().setBackground(Color.GRAY);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);

		panel = new JPanel(new BorderLayout());
		panel.setVisible(true);

		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(250, 500));

		Border border = BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(240, 240, 240));
		panel.setBorder(border);
		tree.setSize(new Dimension(panel.getSize()));
		CustomTreeCellRenderer renderer = (new CustomTreeCellRenderer());
		tree.setCellRenderer(renderer);

		panel.add(scrollPane, BorderLayout.CENTER);
		tree.setUI(new CustomTreeUI());
	}

	public ApplicationBrowser(ComponentModel model)
	{

		this.model = model;
		tree = new JTree();
		tree.setModel(this.model.getXmlModel());
		tree.setRootVisible(false);
		tree.setOpaque(false);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tree);
		scrollPane.getViewport().setBackground(Color.GRAY);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);

		panel = new JPanel(new BorderLayout());
		panel.setVisible(true);

		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(250, 500));

		Border border = BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(240, 240, 240));
		panel.setBorder(border);
		tree.setSize(new Dimension(panel.getSize()));
		CustomTreeCellRenderer renderer = (new CustomTreeCellRenderer());
		tree.setCellRenderer(renderer);

		panel.add(scrollPane, BorderLayout.CENTER);
		tree.setUI(new CustomTreeUI());
	}

	public JComponent getComponent()
	{
		return panel;
	}

	public void update()
	{
		// TODO: implement
	}

	public void addTreeListener(CustomTreeSelectionListener customTreeSelectionListener)
	{
		tree.addTreeSelectionListener((TreeSelectionListener) customTreeSelectionListener);
		tree.addMouseListener((MouseListener) customTreeSelectionListener);
	}

	@Override
	public void addEventListener(EventListener listener)
	{
		// TODO Auto-generated method stub

	}

}