package main;

import model.ApplicationModel;
import view.LoginView;

public class MainClass
{

	public static void main(String[] args)
	{

		ApplicationModel model = new ApplicationModel();
		new LoginView(model);

	}

}
