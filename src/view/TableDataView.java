/***********************************************************************
 * Module:  TableDataView.java
 * Author:  User
 * Purpose: Defines the Class TableDataView
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import model.ComponentModel;
import utility.CustomTable;
import utility.TableColumnAdjuster;

public class TableDataView extends ViewComponent
{
	protected JPanel panel = null;
	protected CustomTable table = null;
	private TableColumnAdjuster tca = null;

	public TableDataView(ComponentModel model)
	{
		super(model);

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		table = new CustomTable(this.model.getTableDataModel());

		tca = new TableColumnAdjuster(table);
		tca.adjustColumns();
		tca.setDynamicAdjustment(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		panel.add(scrollPane, BorderLayout.CENTER);

	}

	public JTable getTable()
	{
		return table;
	}

	public JComponent getComponent()
	{
		return panel;
	}

	public void update()
	{
		
		table.repaint();
	}

	public void addSelectionListener(ListSelectionListener listener)
	{
		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel.addListSelectionListener(listener);
	}

	@Override
	public void addEventListener(EventListener listener)
	{
		// TODO Auto-generated method stub

	}

}