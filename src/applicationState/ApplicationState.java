/***********************************************************************
 * Module:  ApplicationState.java
 * Author:  User
 * Purpose: Defines the Class ApplicationState
 ***********************************************************************/

package applicationState;

import view.ApplicationView;

public abstract class ApplicationState
{
	ApplicationView applicationView;

	public ApplicationState()
	{
		// TODO Auto-generated constructor stub
	}

	public ApplicationState(ApplicationView applicationView)
	{
		this.applicationView = applicationView;
	}

	/** @param applicationView */
	public abstract void setToolbar(ApplicationView applicationView);

	/** @param applicationView */
	public abstract void setStatusBar(ApplicationView applicationView);

	/** @param applicationView */
	public abstract void setMenuBar(ApplicationView applicationView);

	/** @param applicationView */
	public abstract void setToolbar();

	/** @param applicationView */
	public abstract void setStatusBar();

	/** @param applicationView */
	public abstract void setMenuBar();

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "";
	}
}