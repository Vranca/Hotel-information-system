/***********************************************************************
 * Module:  MenuBarView.java
 * Author:  User
 * Purpose: Defines the Class MenuBarView
 ***********************************************************************/

package view;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.ComponentModel;

public class MenuBarView extends ViewComponent
{
	public JMenuBar menuBar = null;
	public JMenu file = null;
	public JMenu edit = null;

	public JMenu help = null;

	JMenuItem chooseXML = null;
	JMenuItem changeXML = null;
	JMenuItem exit = null;

	JMenu navigationMenu = null;
	JMenuItem first = null;
	JMenuItem next = null;
	JMenuItem previous = null;
	JMenuItem last = null;

	JMenu changeMenu = null;
	JMenuItem newMenuItem = null;
	JMenuItem editMenuItem = null;
	JMenuItem delete = null;

	JMenu confirmation = null;
	JMenuItem accept = null;
	JMenuItem cancel = null;
	JMenuItem about = null;

	JMenu menuReport = new JMenu("Report");
	JMenuItem itemPoslovniSistem = new JMenuItem("Generate \"Poslovni sistem\" report");
	JMenuItem itemRegistarKorisnika = new JMenuItem("Generate \"Registar korisnika\" report");

	public MenuBarView(ComponentModel model)
	{
		super(model);
		menuBar = new JMenuBar();

		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		chooseXML = new JMenuItem("Choose XML");
		chooseXML.setIcon(new ImageIcon(new ImageIcon("img/chooseDataBase.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		changeXML = new JMenuItem("Change XML");
		changeXML.setIcon(new ImageIcon(new ImageIcon("img/changeDataBase.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		exit = new JMenuItem("Exit");
		exit.setIcon(new ImageIcon(new ImageIcon("img/exit.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		exit.addActionListener(e -> ((JFrame) menuBar.getTopLevelAncestor()).dispose());

		file.add(chooseXML);
		file.add(changeXML);
		file.add(exit);

		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);

		navigationMenu = new JMenu("Navigation");
		first = new JMenuItem("First");
		first.setIcon(new ImageIcon(new ImageIcon("icons/up.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		first.setActionCommand("First");

		next = new JMenuItem("Next");
		next.setIcon(new ImageIcon(new ImageIcon("icons/next.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		next.setMnemonic(KeyEvent.VK_N);
		next.setActionCommand("Next");

		previous = new JMenuItem("Previous");
		previous.setIcon(new ImageIcon(new ImageIcon("icons/previous.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		previous.setActionCommand("Previous");

		last = new JMenuItem("Last");
		last.setIcon(new ImageIcon(new ImageIcon("icons/down.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		last.setActionCommand("Last");

		navigationMenu.add(first);
		navigationMenu.add(next);
		navigationMenu.add(previous);
		navigationMenu.add(last);

		changeMenu = new JMenu("Change");
		newMenuItem = new JMenuItem("New");
		newMenuItem.setIcon(new ImageIcon(new ImageIcon("img/addNewRow.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		newMenuItem.setActionCommand("New");

		editMenuItem = new JMenuItem("Edit");
		editMenuItem.setIcon(new ImageIcon(new ImageIcon("img/editDataBase.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		editMenuItem.setActionCommand("Edit");

		delete = new JMenuItem("Delete");
		delete.setIcon(new ImageIcon(new ImageIcon("img/deleteRow.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		delete.setActionCommand("Delete");

		changeMenu.add(newMenuItem);
		changeMenu.add(editMenuItem);
		changeMenu.add(delete);

		confirmation = new JMenu("Confirmation");
		accept = new JMenuItem("Accept");
		accept.setIcon(new ImageIcon(new ImageIcon("img/acceptInput.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		accept.setActionCommand("Accept");

		cancel = new JMenuItem("Cancel");
		cancel.setIcon(new ImageIcon(new ImageIcon("img/cancelInput.jpg").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		cancel.setActionCommand("Cancel");

		confirmation.add(accept);
		confirmation.add(cancel);

		edit.add(navigationMenu);
		edit.add(changeMenu);
		edit.add(confirmation);

		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		about = new JMenuItem("About");
		about.setIcon(new ImageIcon(new ImageIcon("img/about.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
		help.add(about);
		menuReport.add(itemPoslovniSistem);
		menuReport.add(itemRegistarKorisnika);

		itemRegistarKorisnika.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (Desktop.isDesktopSupported())
				{
					try
					{
						File myFile = new File("reports/Korisnici.pdf");
						Desktop.getDesktop().open(myFile);
					} catch (IOException ex)
					{
						JOptionPane.showMessageDialog(null, "Nije Moguce prikazati izvjestaj", "Hotelski informacioni sistem",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}

			}
		});

		itemPoslovniSistem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * JasperReport jasperReport; try { jasperReport = JasperCompileManager
				 * .compileReport("reports/Poslovni sistemi.jrxml"); // Parameters for report
				 * Map<String, Object> parameters = new HashMap<String, Object>();
				 * 
				 * // DataSource // This is simple example, no database. // then using empty
				 * datasource. JRDataSource dataSource = new JREmptyDataSource();
				 * 
				 * JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				 * parameters, dataSource);
				 * 
				 * 
				 * // Make sure the output directory exists. File outDir = new
				 * File("jasperoutput"); outDir.mkdirs();
				 * 
				 * // Export to PDF. JasperExportManager.exportReportToPdfFile(jasperPrint,
				 * "jasperoutput/Poslovni sistemi.pdf");
				 * 
				 * System.out.println("Done!"); } catch (JRException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace(); }
				 */

				if (Desktop.isDesktopSupported())
				{
					try
					{
						File myFile = new File("reports/Poslovni sistemi.pdf");
						Desktop.getDesktop().open(myFile);
					} catch (IOException ex)
					{
						JOptionPane.showMessageDialog(null, "Nije Moguce prikazati izvjestaj", "Hotelski informacioni sistem",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}

		});

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
		menuBar.add(menuReport);
	}

	public JComponent getComponent()
	{
		// TODO: implement
		return menuBar;
	}

	public void update()
	{
		model.getAppModel().getAppState().setMenuBar();
		menuBar.repaint();

	}

	@Override
	public void addEventListener(EventListener listener)
	{
		// changeXML.addActionListener((ActionListener) listener);
		// chooseXML.addActionListener((ActionListener) listener);
		// exit.addActionListener((ActionListener) listener);

		delete.addActionListener((ActionListener) listener);
		editMenuItem.addActionListener((ActionListener) listener);
		newMenuItem.addActionListener((ActionListener) listener);

		first.addActionListener((ActionListener) listener);
		last.addActionListener((ActionListener) listener);
		next.addActionListener((ActionListener) listener);
		previous.addActionListener((ActionListener) listener);

		about.addActionListener((ActionListener) listener);
		cancel.addActionListener((ActionListener) listener);
		accept.addActionListener((ActionListener) listener);

		// changeState.addActionListener((ActionListener) listener);

	}

	public final JMenuBar getMenuBar()
	{
		return menuBar;
	}

	public final JMenu getFile()
	{
		return file;
	}

	public final JMenu getEdit()
	{
		return edit;
	}

	public final JMenu getHelp()
	{
		return help;
	}

	public final JMenuItem getChooseXML()
	{
		return chooseXML;
	}

	public final JMenuItem getChangeXML()
	{
		return changeXML;
	}

	public final JMenuItem getExit()
	{
		return exit;
	}

	public final JMenu getNavigationMenu()
	{
		return navigationMenu;
	}

	public final JMenuItem getFirst()
	{
		return first;
	}

	public final JMenuItem getNext()
	{
		return next;
	}

	public final JMenuItem getPrevious()
	{
		return previous;
	}

	public final JMenuItem getLast()
	{
		return last;
	}

	public final JMenu getChangeState()
	{
		return changeMenu;
	}

	public final JMenuItem getNewMenuItem()
	{
		return newMenuItem;
	}

	public final JMenuItem getEditMenuItem()
	{
		return editMenuItem;
	}

	public final JMenuItem getDelete()
	{
		return delete;
	}

	public final JMenu getConfirmation()
	{
		return confirmation;
	}

	public final JMenuItem getAccept()
	{
		return accept;
	}

	public final JMenuItem getCancel()
	{
		return cancel;
	}

	public final JMenuItem getAbout()
	{
		return about;
	}

	public final JMenu getMenuReport()
	{
		return menuReport;
	}

	public final JMenuItem getItemPoslovniSistem()
	{
		return itemPoslovniSistem;
	}

	public final JMenuItem getItemRegistarKorisnika()
	{
		return itemRegistarKorisnika;
	}

}