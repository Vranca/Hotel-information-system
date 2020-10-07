package model;

import java.util.ArrayList;
import java.util.Vector;

public abstract class TreeElement
{

	protected String code = null;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	protected String name = null;

	private Vector<TreeElement> treeElements = new Vector<TreeElement>();

	public final Vector<TreeElement> getTreeElements()
	{
		return treeElements;
	}

	public final void setTreeElements(Vector<TreeElement> treeElements)
	{
		this.treeElements = treeElements;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return name;
	}

	public int getIndexOfElement(TreeElement element)
	{
		return treeElements.indexOf(element);
	}

	public void addElement(TreeElement element)
	{

		treeElements.add(element);
	}

	public Vector<TreeElement> getAllElements()
	{
		return treeElements;
	}

	public TreeElement getElementAt(int index)
	{
		return treeElements.elementAt(index);
	}

	public static class Package extends TreeElement
	{
		public Package()
		{

		}

		public Package(String naziv)
		{
			this.name = naziv;

		}

	}

	public static class Table extends TreeElement
	{
		private ArrayList<Table> reference = new ArrayList<Table>();
		private Vector<Column> columns = new Vector<TreeElement.Column>();

		public Vector<Column> getColumns()
		{
			return columns;
		}

		private String createSProc = null;
		private String retrieveSProc = null;
		private String retrieveAllSProc = null;
		private String retreiveRefValue = null;
		private String updateSProc = null;
		private String deleteSProc = null;

		public Table()
		{
			// TODO Auto-generated constructor stub
		}

		public void addReference(Table referenca)
		{
			reference.add(referenca);
		}

		public ArrayList<Table> getAllReferences()
		{
			return reference;
		}

		public Table getRefTabelaAt(int index)
		{
			return reference.get(index);

		}

		public String getCreateSProc()
		{
			return createSProc;
		}

		public void setCreateSProc(String createSProc)
		{
			this.createSProc = createSProc;
		}

		public String getRetrieveSProc()
		{
			return retrieveSProc;
		}

		public void setRetrieveSProc(String retrieveSProc)
		{
			this.retrieveSProc = retrieveSProc;
		}

		public String getRetrieveAllSProc()
		{
			return retrieveAllSProc;
		}

		public void setRetrieveAllSProc(String retrieveAllSProc)
		{
			this.retrieveAllSProc = retrieveAllSProc;
		}

		public final String getRetreiveRefValueSProc()
		{
			return retreiveRefValue;
		}

		public final void setRetreiveRefValueSProc(String retreiveRefValue)
		{
			this.retreiveRefValue = retreiveRefValue;
		}

		public String getUpdateSProc()
		{
			return updateSProc;
		}

		public void setUpdateSProc(String updateSProc)
		{
			this.updateSProc = updateSProc;
		}

		public String getDeleteSProc()
		{
			return deleteSProc;
		}

		public void setDeleteSProc(String deleteSProc)
		{
			this.deleteSProc = deleteSProc;
		}

	}

	public static class Column extends TreeElement
	{
		private Boolean nullable = false;
		private Boolean primary = false;
		private Vector<Column> refrences = new Vector<TreeElement.Column>();

		public Column()
		{
		}

		public Boolean isNullable()
		{
			return nullable;
		}

		public void setNullable(Boolean nullable)
		{
			this.nullable = nullable;
		}

		public void setPrimary(Boolean primary)
		{
			this.primary = primary;
		}

		public Boolean isPrimary()
		{
			return primary;
		}

		@Override
		public String toString()
		{
			// TODO Auto-generated method stub
			return this.name;
		}

		public void addRefrence(Column column)
		{
			refrences.add(column);
		}

		public Vector<Column> getRefrences()
		{
			return refrences;
		}

	}

	public static class LoginData
	{
		private static String tableCode = null;
		private static String loginProcedure = null;

		public LoginData()
		{
			// TODO Auto-generated constructor stub
		}

		public static String getTableCode()
		{
			return tableCode;
		}

		public final void setTableCode(String tableCode)
		{
			this.tableCode = tableCode;
		}

		public static String getLoginProcedure()
		{
			return loginProcedure;
		}

		public final void setLoginProcedure(String loginProcedure)
		{
			this.loginProcedure = loginProcedure;
		}

	}

	public static class Privileges
	{
		private static String tableCode = null;
		private static String privilegesProcedure = null;

		public Privileges()
		{
			// TODO Auto-generated constructor stub
		}

		public static String getTableCode()
		{
			return tableCode;
		}

		public void setTableCode(String tableCode1)
		{
			tableCode = tableCode1;
		}

		public static String getPrivilegesProcedure()
		{
			return privilegesProcedure;
		}

		public void setPrivilegesProcedure(String privilegesProcedure1)
		{
			privilegesProcedure = privilegesProcedure1;
		}
	}

}
