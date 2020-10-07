package model;

public class UserDataModel extends TableDataModel
{
	private static final long serialVersionUID = 1L;

	private TableDataModel privilegesDataModel = null;

	public UserDataModel()
	{
		super();
		privilegesDataModel = new TableDataModel();
	}

	public void getPrivileges()
	{
		TreeElement.Table privilegeTable = this.xmlModel.getTable("Dozvole");
		privilegesDataModel.executeStoredProcedure(privilegeTable, "usp_DozvoleReadAll(?)", getRealValueAt(0, 4));
	}

	public final TableDataModel getPrivilegesDataModel()
	{
		return privilegesDataModel;
	}

	public boolean checkPrivilege(String privilege)
	{
		for (int i = 0; i < privilegesDataModel.getRowCount(); i++)
		{
			if (privilege.equalsIgnoreCase(privilegesDataModel.getValueAt(i, 3).toString()))
			{
				return true;
			}
		}

		return false;
	}

}
