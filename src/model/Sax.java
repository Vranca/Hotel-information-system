/***********************************************************************
 * Module:  Sax.java
 * Author:  User
 * Purpose: Defines the Class Sax
 ***********************************************************************/

package model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Sax extends ParsingStrategy
{

	@Override
	public void parse()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void parse(UserDataModel tabDataModel)
	{
		// TODO Auto-generated method stub

	}

	public static class SaxHandler extends DefaultHandler
	{
		private String elementValue = null;
		private TreeElement base = null;
		private TreeElement.Package pomPackage = null;
		private TreeElement.Table pomTable = null;
		private TreeElement.Column pomColumn = null;

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			// TODO Auto-generated method stub
			elementValue = new String(ch, start, length);
		}

		@Override
		public void startDocument() throws SAXException
		{
			// TODO Auto-generated method stub
			base = new TreeElement.Package();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			// TODO Auto-generated method stub
			switch (qName)
			{
				case "package":
					pomPackage = new TreeElement.Package();
					pomPackage.name = attributes.getValue("name");
					pomPackage.code = attributes.getValue("code");
					base.addElement(pomPackage);
					break;
				case "table":
					pomTable = new TreeElement.Table();
					pomTable.name = attributes.getValue("name");
					pomTable.code = attributes.getValue("code");
					pomPackage.addElement(pomTable);
					break;
				case "column":
					pomColumn = new TreeElement.Column();
					pomColumn.name = attributes.getValue("name");
					pomColumn.code = attributes.getValue("code");
					pomTable.addElement(pomColumn);
					break;
				default:
					break;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			// TODO Auto-generated method stub
			switch (qName)
			{
				case "package":

					base.addElement(pomPackage);
					break;
				case "table":

					pomPackage.addElement(pomTable);
					break;
				case "column":

					pomTable.addElement(pomColumn);
					break;
				default:
					break;
			}
		}
	}
}