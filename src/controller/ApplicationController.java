/***********************************************************************
 * Module:  ApplicationController.java
 * Author:  User
 * Purpose: Defines the Class ApplicationController
 ***********************************************************************/

package controller;

import model.ApplicationModel;
import view.ApplicationView;

public class ApplicationController 
{
   private ApplicationModel applicationModel;
   private ApplicationView applicationView;
   
   public ApplicationController(ApplicationModel model, ApplicationView view)
   {
	   applicationModel = model;
	   applicationView = view;
	   applicationModel.addSubscriber(applicationView);
   }

}