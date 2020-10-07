package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import model.TreeElement.Package;
import model.TreeElement.Table;

public class XMLModel extends DefaultTreeModel
{
	private static final long serialVersionUID = 1L;

	private XMLParser parser = null;

	TreeElement rootPackage;

	public XMLModel(TreeNode root)
	{
		super(root);
		rootPackage = new TreeElement.Package();
		parser = new XMLParser();
		parser.changeStrategy(new Dom(rootPackage));
		parser.parse();
	}

	public XMLModel(TreeNode root, UserDataModel tableModel)
	{
		super(root);
		rootPackage = new TreeElement.Package();
		parser = new XMLParser();
		parser.changeStrategy(new Dom(rootPackage));
		parser.parse(tableModel);
	}

	@Override
	public Object getChild(Object parent, int index)
	{
		if (parent instanceof TreeElement.Package)
		{
			return ((TreeElement.Package) parent).getElementAt(index);
		} else if (parent instanceof TreeElement.Table)
		{
			return null;
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent)
	{
		if (parent instanceof TreeElement.Package)
		{
			return ((TreeElement.Package) parent).getAllElements().size();
		} else if (parent instanceof TreeElement.Table)
		{
			return 0;
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child)
	{
		if (parent instanceof TreeElement.Package)
		{
			return ((TreeElement.Package) parent).getIndexOfElement((TreeElement) child);
		} else if (parent instanceof TreeElement.Table)
		{
			return -1;
		}
		return -1;
	}

	public XMLParser getParser()
	{
		return parser;
	}

	public void setParser(XMLParser parser)
	{
		this.parser = parser;
	}

	@Override
	public Object getRoot()
	{
		return this.rootPackage;
	}

	@Override
	public boolean isLeaf(Object node)
	{
		if (node instanceof TreeElement.Table)
		{
			return true;
		}
		return false;
	}

	public TreeElement.Table getTable(String tableCode)
	{
		ArrayList<TreeElement.Table> allTables = new ArrayList<TreeElement.Table>();
		allTables = getTablesRecursive(rootPackage);

		for (int i = 0; i < allTables.size(); i++)
		{
			if (tableCode.equals(allTables.get(i).getCode()))
			{
				return allTables.get(i);
			}
		}

		return null;
	}

	private ArrayList<Table> getTablesRecursive(TreeElement root)
	{
		ArrayList<TreeElement.Table> allTables = new ArrayList<TreeElement.Table>();
		Vector<TreeElement> rootChildren = root.getAllElements();
		for (int i = 0; i < rootChildren.size(); i++)
		{
			if (rootChildren.get(i) instanceof Table)
			{
				allTables.add((Table) rootChildren.get(i));
			} else
			{
				allTables.addAll(getTablesRecursive(rootChildren.get(i)));
			}
		}

		return allTables;
	}

	public Package getPackage(TreeElement root, Table table)
	{
		Vector<TreeElement> rootChildren = root.getAllElements();
		Package parentPackage = null;

		for (int i = 0; i < rootChildren.size(); i++)
		{
			if (rootChildren.get(i) instanceof Table)
			{
				if (rootChildren.get(i).getCode().equals(table.getCode()))
				{
					return (Package) root;
				}
			} else
			{
				parentPackage = getPackage(rootChildren.get(i), table);
				if (parentPackage != null)
					return parentPackage;
			}
		}

		return parentPackage;
	}

}
