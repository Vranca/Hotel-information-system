/***********************************************************************
 * Module:  SelectionView.java
 * Author:  User
 * Purpose: Defines the Class SelectionView
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

import listeners.CustomTableSelectionListener;
import model.ComponentModel;
import utility.Tuple;

public class TableSelectionView extends TableDecorator
{
	private JDialog dialog = null;
	private JPanel backgroundPanel = null;
	private Tuple<Object, Object> result = null;

	public TableSelectionView(TableDataView baseView, ComponentModel model)
	{
		super(baseView, model);

		model.addSubscriber(baseView);
		baseView.addSelectionListener(new CustomTableSelectionListener(model, baseView));

		dialog = new JDialog();
		backgroundPanel = new JPanel(new BorderLayout());
		backgroundPanel.add(baseView.getComponent(), BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton btnSelect = new JButton("Select");
		JButton btnCancel = new JButton("Cancel");
		btnSelect.addActionListener(e -> setSelection(true));
		btnCancel.addActionListener(e -> setSelection(false));
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnSelect);
		backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		dialog.add(backgroundPanel);
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
	}

	public JComponent getComponent()
	{
		return backgroundPanel;
	}

	public void update()
	{
		// TODO: implement
	}

	private void setSelection(boolean isSelected)
	{
		if (isSelected)
		{
			result = model.getTableDataModel().getPkTuple(model.getTableDataModel().getSelectedRow());
		}

		dialog.setVisible(false);
		dialog.dispose();
	}

	public Tuple<Object, Object> getSelection()
	{
		dialog.setVisible(true);
		return result;
	}

}