/***********************************************************************
 * Module:  TableDecorator.java
 * Author:  User
 * Purpose: Defines the Class TableDecorator
 ***********************************************************************/

package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import model.ComponentModel;

public class TableDecorator extends TableDataView
{
	private TableDataView tableView;

	public TableDecorator(TableDataView baseView, ComponentModel model)
	{
		super(model);
		tableView = baseView;

		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.panel.add(tableView.getComponent(), BorderLayout.CENTER);
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