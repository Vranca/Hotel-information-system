/***********************************************************************
 * Module:  XMLParser.java
 * Author:  User
 * Purpose: Defines the Class XMLParser
 ***********************************************************************/

package model;

public class XMLParser
{
	private ParsingStrategy strategy = null;

	public XMLParser()
	{
		// TODO Auto-generated constructor stub
	}

	public void parse(UserDataModel tableDataModel)
	{
		strategy.parse(tableDataModel);
	}

	public void parse()
	{
		strategy.parse();
	}

	public void changeStrategy(ParsingStrategy strategy)
	{
		this.strategy = strategy;
	}
}