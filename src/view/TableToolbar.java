/***********************************************************************
 * Module:  TableToolbar.java
 * Author:  User
 * Purpose: Defines the Class TableToolbar
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import command.Command;
import model.ComponentModel;

public class TableToolbar extends TableDecorator
{
	private java.util.List<Command> commands = null;

	public TableToolbar(TableDataView baseView, ComponentModel model)
	{
		super(baseView, model);

		JPanel toolbar = new JPanel();
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.LINE_AXIS));
		toolbar.setBackground(Color.GRAY);
		toolbar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

		JButton btnFirst = new JButton();
		btnFirst.setToolTipText("First row");
		btnFirst.setIcon(new ImageIcon("icons/first.png"));
		btnFirst.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnFirst.setOpaque(false);
		btnFirst.setBackground(null);

		toolbar.add(Box.createHorizontalStrut(2));
		toolbar.add(btnFirst);
		toolbar.add(Box.createHorizontalStrut(7));

		JButton btnPrev = new JButton();
		btnPrev.setToolTipText("Previous row");
		btnPrev.setIcon(new ImageIcon("icons/previous.png"));
		btnPrev.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnPrev.setOpaque(false);
		btnPrev.setBackground(null);

		toolbar.add(btnPrev);
		toolbar.add(Box.createHorizontalStrut(7));

		JButton btnNext = new JButton();
		btnNext.setToolTipText("Next row");
		btnNext.setIcon(new ImageIcon("icons/next.png"));
		btnNext.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnNext.setOpaque(false);
		btnNext.setBackground(null);

		toolbar.add(btnNext);
		toolbar.add(Box.createHorizontalStrut(7));

		JButton btnLast = new JButton();
		btnLast.setToolTipText("Next");
		btnLast.setIcon(new ImageIcon("icons/last.png"));
		btnLast.setBorder(new EmptyBorder(2, 2, 2, 2));
		btnLast.setOpaque(false);
		btnLast.setBackground(null);

		toolbar.add(btnLast);
		toolbar.add(Box.createHorizontalStrut(7));

		toolbar.setBorder(BorderFactory.createRaisedSoftBevelBorder());

		this.panel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		this.panel.add(toolbar, BorderLayout.NORTH);

	}

	/** @pdGenerated default getter */
	public java.util.List<Command> getCommands()
	{
		if (commands == null)
			commands = new java.util.ArrayList<Command>();
		return commands;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator<Command> getIteratorCommands()
	{
		if (commands == null)
			commands = new java.util.ArrayList<Command>();
		return commands.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newCommands
	 */
	public void setCommands(java.util.List<Command> newCommands)
	{
		removeAllCommands();
		for (java.util.Iterator<Command> iter = newCommands.iterator(); iter.hasNext();)
			addCommands((Command) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newCommand
	 */
	public void addCommands(Command newCommand)
	{
		if (newCommand == null)
			return;
		if (this.commands == null)
			this.commands = new java.util.ArrayList<Command>();
		if (!this.commands.contains(newCommand))
			this.commands.add(newCommand);
	}

	/**
	 * @pdGenerated default remove
	 * @param oldCommand
	 */
	public void removeCommands(Command oldCommand)
	{
		if (oldCommand == null)
			return;
		if (this.commands != null)
			if (this.commands.contains(oldCommand))
				this.commands.remove(oldCommand);
	}

	/** @pdGenerated default removeAll */
	public void removeAllCommands()
	{
		if (commands != null)
			commands.clear();
	}

	public JComponent getComponent()
	{
		return this.panel;
	}

	public void update()
	{
		// TODO: implement
	}

}