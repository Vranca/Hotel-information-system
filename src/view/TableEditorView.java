/***********************************************************************
 * Module:  TableEditorView.java
 * Author:  User
 * Purpose: Defines the Class TableEditorView
 ***********************************************************************/

package view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.ComponentModel;
import model.TreeElement.Column;
import model.TreeElement.Table;
import net.miginfocom.swing.MigLayout;
import utility.Tuple;
import utility.ValueField;

public class TableEditorView extends TableDecorator implements ActionListener
{
	private JDialog dialog = null;
	private JPanel backgroundPanel = null;

	private JButton btnApply = null;
	private JButton btnCancel = null;
	private boolean isApplied = false;

	private int pkCount;
	private ArrayList<ValueField<?>> valueFieldList = null;

	public TableEditorView(TableDataView baseView, ComponentModel model)
	{
		super(baseView, model);

		dialog = new JDialog();
		backgroundPanel = new JPanel(new BorderLayout());
		backgroundPanel.add(baseView.getComponent(), BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		btnApply = new JButton("Apply");
		btnCancel = new JButton("Cancel");
		btnApply.addActionListener(e -> applyAction());
		btnCancel.addActionListener(e -> dialog.dispose());
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnApply);
		backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		JPanel editorPanel = new JPanel(new MigLayout());
		pkCount = 0;
		valueFieldList = new ArrayList<>();
		ArrayList<JLabel> cellLabels = new ArrayList<JLabel>();
		ArrayList<JComponent> editingCells = new ArrayList<JComponent>();
		ArrayList<JButton> fkSelectButtons = new ArrayList<JButton>();

		int cellCount = 0;
		for (int i = 0; i < model.getTableDataModel().getColumnCount(); i++)
		{
			if (!model.getTableDataModel().isCellPK(0, i))
			{
				cellCount++;
				String label;
				if (model.getTableDataModel().getXmlTable().getColumns().get(i - 1).isNullable())
				{
					label = model.getTableDataModel().getColumnName(i) + " ";
				} else
				{
					label = model.getTableDataModel().getColumnName(i) + " * ";
				}

				ValueField<?> vf = new ValueField<>(model.getTableDataModel().getValueAt(0, i), model.getTableDataModel().getColumnClass(i),
						label, model.getTableDataModel().isCellFK(0, i), model.getTableDataModel().getRealValueAt(0, i - 1));

				cellLabels.add(vf.getValueName());
				editingCells.add(vf.getValueField());
				fkSelectButtons.add(vf.getValueButton());
				valueFieldList.add(vf);
			} else
			{
				pkCount++;
			}
		}
		// Smanjuje se jer se kolona redni broj vraca kao da jeste pk
		pkCount--;

		for (int i = 0; i < cellCount; i++)
		{
			JPanel tmpPanel = new JPanel(new MigLayout());
			tmpPanel.add(cellLabels.get(i), "align left, pushy");
			if (fkSelectButtons.get(i) == null)
			{
				tmpPanel.add(editingCells.get(i), "align right, growx, pushx");
			} else
			{
				tmpPanel.add(editingCells.get(i), "align right, growx, pushx");
				tmpPanel.add(fkSelectButtons.get(i), "align right");
				fkSelectButtons.get(i).addActionListener(this);
				fkSelectButtons.get(i).setActionCommand(String.valueOf(i));
			}
			if (i % 2 == 0)
				editorPanel.add(tmpPanel, "align left, pushy, pushx, growx");
			else
				editorPanel.add(tmpPanel, "align right, pushy, pushx, growx, wrap");
		}

		JScrollPane editJScrollPane = new JScrollPane();
		editJScrollPane.setViewportView(editorPanel);

		backgroundPanel.add(editJScrollPane, BorderLayout.CENTER);
		baseView.getComponent().setPreferredSize(new Dimension(baseView.getComponent().getWidth(), 100));

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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		int index = Integer.parseInt(e.getActionCommand());

		Column fkColumn = model.getTableDataModel().getXmlTable().getColumns().get(index + pkCount);
		Table procTable = model.getTableDataModel().getFkTable(fkColumn);

		ComponentModel cm = new ComponentModel(model.getAppModel(), procTable, procTable.getRetrieveAllSProc(),
				model.getAppModel().getSystemId());
		TableDataView tdv = new TableDataView(cm);

		Tuple<Object, Object> result = (new TableSelectionView(tdv, cm)).getSelection();
		if (result != null)
		{
			JButton pressedButton = (JButton) e.getSource();
			for (int i = 0; i < valueFieldList.size(); i++)
			{
				if (pressedButton == valueFieldList.get(i).getValueButton())
				{
					valueFieldList.get(i).setValue(result.getSecondValue());
					valueFieldList.get(i).setFkValue(result.getFirstValue());
					dialog.repaint();
				}
			}
		}
	}

	private void applyAction()
	{
		List<Object> realValues = model.getTableDataModel().getResultCells();
		Object[] params = new Object[model.getTableDataModel().getColumnCount() - 1];

		for (int i = 0; i < model.getTableDataModel().getColumnCount() - 1; i++)
		{
			if (model.getTableDataModel().isCellPK(0, i + 1))
			{
				params[i] = realValues.get(i);
			} else
			{
				if (valueFieldList.get(i - pkCount).isFk())
					params[i] = valueFieldList.get(i - pkCount).getFkValue();
				else
					params[i] = valueFieldList.get(i - pkCount).getRealValue();
			}
		}

		model.executeUpdateProcedure(params);
		isApplied = true;
		dialog.dispose();
	}

	public boolean isUpdated()
	{
		dialog.setVisible(true);
		return isApplied;
	}
}