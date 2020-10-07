/***********************************************************************
 * Module:  IdleState.java
 * Author:  User
 * Purpose: Defines the Class IdleState
 ***********************************************************************/

package applicationState;

import view.ApplicationView;

public class IdleState extends ApplicationState
{
	public IdleState()
	{
		// TODO Auto-generated constructor stub
	}

	public IdleState(ApplicationView applicationView)
	{
		super(applicationView);
		// TODO Auto-generated constructor stub
	}

	/** @param applicationView */
	public void setToolbar(ApplicationView applicationView)
	{
		// TODO: implement
	}

	/** @param applicationView */
	public void setStatusBar(ApplicationView applicationView)
	{
		// TODO: implement
	}

	/** @param applicationView */
	public void setMenuBar(ApplicationView applicationView)
	{
		// TODO: implement
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Stanje Mirovanja";
	}

	@Override
	public void setToolbar()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setStatusBar()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setMenuBar()
	{
		// TODO Auto-generated method stub

	}

}