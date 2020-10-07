package view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class TableCreateView extends TableDecorator implements ActionListener
{
	private JDialog dialog = null;
	private JPanel backgroundPanel = null;

	private JButton btnApply = null;
	private JButton btnCancel = null;
	private boolean isApplied = false;

	private ArrayList<ValueField<?>> valueFieldList = null;
	private ArrayList<Integer> increments = null;
	private int increment;

	public TableCreateView(TableDataView baseView, ComponentModel model)
	{
		super(baseView, model);

		dialog = new JDialog();
		backgroundPanel = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		btnApply = new JButton("Apply");
		btnCancel = new JButton("Cancel");
		btnApply.addActionListener(e -> applyAction());
		btnCancel.addActionListener(e -> dialog.dispose());
		buttonPanel.add(btnCancel);
		buttonPanel.add(btnApply);
		backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

		JPanel editorPanel = new JPanel(new MigLayout());

		valueFieldList = new ArrayList<>();
		ArrayList<JLabel> cellLabels = new ArrayList<JLabel>();
		ArrayList<JComponent> editingCells = new ArrayList<JComponent>();
		ArrayList<JButton> fkSelectButtons = new ArrayList<JButton>();

		increments = new ArrayList<>();
		increment = 0;
		int cellCount = 0;
		for (int i = 1; i < model.getTableDataModel().getColumnCount(); i++)
		{
			Column column = model.getTableDataModel().getXmlTable().getColumns().get(i - 1);
			if (!model.getTableDataModel().isCellPK(0, i) || column.getRefrences().size() > 0)
			{
				cellCount++;
				String label;
				if (column.isNullable())
				{
					label = model.getTableDataModel().getColumnName(i) + " ";
				} else
				{
					label = model.getTableDataModel().getColumnName(i) + " * ";
				}

				ValueField<?> vf = new ValueField<>(null, model.getTableDataModel().getColumnClass(i), label,
						model.getTableDataModel().isCellFK(0, i), null);

				cellLabels.add(vf.getValueName());
				editingCells.add(vf.getValueField());
				fkSelectButtons.add(vf.getValueButton());
				valueFieldList.add(vf);
				increments.add(new Integer(increment));
			} else
			{
				if (model.getTableDataModel().isPkIdentity())
				{
					cellCount++;
					String label;
					if (column.isNullable())
					{
						label = model.getTableDataModel().getColumnName(i) + " ";
					} else
					{
						label = model.getTableDataModel().getColumnName(i) + " * ";
					}

					ValueField<?> vf = new ValueField<>(null, model.getTableDataModel().getColumnClass(i), label,
							model.getTableDataModel().isCellFK(0, i), null);

					cellLabels.add(vf.getValueName());
					editingCells.add(vf.getValueField());
					fkSelectButtons.add(vf.getValueButton());
					valueFieldList.add(vf);
					increments.add(new Integer(increment));

				} else
				{
					increment = 1;
				}
			}
		}

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

		Column fkColumn = model.getTableDataModel().getXmlTable().getColumns().get(index + increments.get(index));
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
		Object[] params = new Object[model.getTableDataModel().getColumnCount() - 1 - increment];

		for (int i = 0; i < model.getTableDataModel().getColumnCount() - 1 - increment; i++)
		{
			if (valueFieldList.get(i).isFk())
				params[i] = valueFieldList.get(i).getFkValue();
			else
				params[i] = valueFieldList.get(i).getRealValue();

		}
		model.executeCreateProcedure(params);
		isApplied = true;
		dialog.dispose();

	}

	public boolean isUpdated()
	{
		dialog.setVisible(true);
		return isApplied;
	}

}
