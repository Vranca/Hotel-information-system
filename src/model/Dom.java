/***********************************************************************
 * Module:  Dom.java
 * Author:  User
 * Purpose: Defines the Class Dom
 ***********************************************************************/

package model;

import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.TreeElement.LoginData;
import model.TreeElement.Privileges;

public class Dom extends ParsingStrategy
{
	Document document = null;
	javax.xml.xpath.XPathExpression expression = null;
	XPath xpath = null;
	TreeElement rootPackage;

	public Dom(TreeElement rootPackage)
	{
		this.rootPackage = rootPackage;
	}

	private boolean isContainedInTree(TreeElement root, TreeElement node)
	{
		boolean isContainedInTree = false;
		Vector<TreeElement> children = root.getAllElements();
		if (children.contains(node))
		{
			isContainedInTree = true;
		}

		for (int i = 0; i < children.size(); i++)
		{
			isContainedInTree = isContainedInTree || isContainedInTree(children.get(i), node);
		}

		return isContainedInTree;
	}

	private void subPacks(NodeList packeges, TreeElement rootpackage, UserDataModel tableDataModel) throws XPathExpressionException
	{

		for (int i = 0; i < packeges.getLength(); i++)
		{

			TreeElement.Package pompackage = new TreeElement.Package();
			pompackage.code = null;
			pompackage.name = packeges.item(i).getAttributes().getNamedItem("name").getNodeValue();
			if (tableDataModel.checkPrivilege(pompackage.getName()))
			{
				if (isContainedInTree(this.rootPackage, rootpackage))
				{
					rootpackage.addElement(pompackage);
				} else
				{
					this.rootPackage.addElement(pompackage);
				}
			}
			expression = xpath.compile("package");
			NodeList subPackeges = (NodeList) expression.evaluate(packeges.item(i), XPathConstants.NODESET);

			subPacks(subPackeges, pompackage, tableDataModel);

			expression = xpath.compile("table");
			NodeList tabele = (NodeList) expression.evaluate(packeges.item(i), XPathConstants.NODESET);

			for (int j = 0; j < tabele.getLength(); j++)
			{
				TreeElement.Table pomtable = new TreeElement.Table();
				pompackage.addElement(pomtable);
				pomtable.code = tabele.item(j).getAttributes().getNamedItem("code").getNodeValue();
				pomtable.name = tabele.item(j).getAttributes().getNamedItem("name").getNodeValue();
				expression = xpath.compile("column");
				NodeList kolone = (NodeList) expression.evaluate(tabele.item(j), XPathConstants.NODESET);
				for (int k = 0; k < kolone.getLength(); k++)

				{
					TreeElement.Column pomcolumn = new TreeElement.Column();
					pomcolumn.code = kolone.item(k).getAttributes().getNamedItem("code").getNodeValue();
					pomcolumn.name = kolone.item(k).getAttributes().getNamedItem("name").getNodeValue();

					boolean pomBool = false;
					if (kolone.item(k).getAttributes().getNamedItem("nullable").getNodeValue().equals("true"))
					{
						pomBool = true;

					}
					pomcolumn.setNullable(pomBool);
					pomBool = false;
					if (kolone.item(k).getAttributes().getNamedItem("primary").getNodeValue().equals("true"))
					{
						pomBool = true;
					}
					pomcolumn.setPrimary(pomBool);

					expression = xpath.compile("references");
					NodeList reference = (NodeList) expression.evaluate(kolone.item(k), XPathConstants.NODESET);

					for (int m = 0; m < reference.getLength(); m++)

					{

						TreeElement.Table reftable = new TreeElement.Table();

						reftable.code = reference.item(m).getAttributes().getNamedItem("refTable").getNodeValue();

						pomtable.addReference(reftable);

						if (reftable.getCode() != pomtable.getCode())
							pomcolumn.setName(stringFormat(reftable.code));

						expression = xpath.compile("refColumn");

						TreeElement.Column refKolone = new TreeElement.Column();
						refKolone.code = reference.item(m).getAttributes().getNamedItem("refColumn").getNodeValue();

						reftable.addElement(refKolone);

						pomcolumn.addRefrence(refKolone);
					}
					if (reference.getLength() > 0)
					{
						TreeElement.Table referencedTable = new TreeElement.Table();
						referencedTable.setCode(reference.item(0).getAttributes().getNamedItem("refTable").getNodeValue());
						if (referencedTable.getCode() != pomtable.getCode())
							pomcolumn.setName(stringFormat(referencedTable.code));
					}

					pomtable.addElement(pomcolumn);
					pomtable.getColumns().add(pomcolumn);

				}

				expression = xpath.compile("crud");

				Node crud = (Node) expression.evaluate(tabele.item(j), XPathConstants.NODE);
				expression = xpath.compile("create");

				Node create = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (create.getAttributes().getLength() > 0)
					pomtable.setCreateSProc(create.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("retrieve");

				Node retrieve = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (retrieve.getAttributes().getLength() > 0)
					pomtable.setRetrieveSProc(retrieve.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("retrieveAll");

				Node retrieveAll = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (retrieveAll != null)
					if (retrieveAll.getAttributes().getLength() > 0)
						pomtable.setRetrieveAllSProc(retrieveAll.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("update");

				Node update = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (update.getAttributes().getLength() > 0)
					pomtable.setUpdateSProc(update.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("delete");

				Node delete = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (delete.getAttributes().getLength() > 0)
					pomtable.setDeleteSProc(delete.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("retrieveRefValue");

				Node retrieveRefValue = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (retrieveRefValue != null)
					if (retrieveRefValue.getAttributes().getLength() > 0)
						pomtable.setRetreiveRefValueSProc(retrieveRefValue.getAttributes().getNamedItem("name").getNodeValue());

			}

		}
	}

	@Override
	public void parse(UserDataModel tableDataModel)
	{

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		{
			DocumentBuilder documentBuilder;
			try
			{
				documentBuilder = builderFactory.newDocumentBuilder();
				document = documentBuilder.parse("xml\\xmlcic.xml"); // Bio xmlN.xml

				javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
				xpath = factory.newXPath();

				expression = xpath.compile("/database");
				Node database = (Node) expression.evaluate(document, XPathConstants.NODE);

				rootPackage.code = null;
				rootPackage.name = database.getAttributes().getNamedItem("name").getNodeValue();
				
				
				
				expression = xpath.compile("package");
				NodeList packages = (NodeList) expression.evaluate(database, XPathConstants.NODESET);

				subPacks(packages, rootPackage, tableDataModel);
			} catch (ParserConfigurationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void checkReference()
	{

	}

	public String stringFormat(String s)
	{
		String returnString = s.toLowerCase();
		if (returnString.contains("_"))
			returnString = returnString.replace("_", " ");
		returnString = returnString.substring(0, 1).toUpperCase() + returnString.substring(1);
		return returnString;
	}

	@Override
	public void parse()
	{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

		{
			DocumentBuilder documentBuilder;
			try
			{
				documentBuilder = builderFactory.newDocumentBuilder();
				document = documentBuilder.parse("xml\\xmlcic.xml"); // Bio xmlN.xml

				javax.xml.xpath.XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
				xpath = factory.newXPath();

				expression = xpath.compile("/database");
				Node database = (Node) expression.evaluate(document, XPathConstants.NODE);
				
				expression=xpath.compile("login");
				Node login=(Node) expression.evaluate(database,XPathConstants.NODE);
				TreeElement.LoginData loginData=new LoginData();
				loginData.setTableCode(login.getAttributes().getNamedItem("tablecode").getNodeValue());
				loginData.setLoginProcedure(login.getAttributes().getNamedItem("loginProcedure").getNodeValue());

				expression=xpath.compile("privileges");
				Node privileges=(Node) expression.evaluate(database,XPathConstants.NODE);
				TreeElement.Privileges pomPrivileges=new Privileges();
				pomPrivileges.setTableCode(privileges.getAttributes().getNamedItem("tablecode").getNodeValue());
				pomPrivileges.setPrivilegesProcedure(privileges.getAttributes().getNamedItem("privilegesProcedure").getNodeValue());

				
				rootPackage.code = null;
				rootPackage.name = database.getAttributes().getNamedItem("name").getNodeValue();

				expression = xpath.compile("package");
				NodeList packages = (NodeList) expression.evaluate(database, XPathConstants.NODESET);

				subPacks(packages, rootPackage);
			} catch (ParserConfigurationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void subPacks(NodeList packeges, TreeElement rootpackage) throws XPathExpressionException
	{

		for (int i = 0; i < packeges.getLength(); i++)
		{

			TreeElement.Package pompackage = new TreeElement.Package();
			pompackage.code = null;
			pompackage.name = packeges.item(i).getAttributes().getNamedItem("name").getNodeValue();

			rootpackage.addElement(pompackage);

			expression = xpath.compile("package");
			NodeList subPackeges = (NodeList) expression.evaluate(packeges.item(i), XPathConstants.NODESET);

			subPacks(subPackeges, pompackage);

			expression = xpath.compile("table");
			NodeList tabele = (NodeList) expression.evaluate(packeges.item(i), XPathConstants.NODESET);

			for (int j = 0; j < tabele.getLength(); j++)
			{
				TreeElement.Table pomtable = new TreeElement.Table();
				pompackage.addElement(pomtable);
				pomtable.code = tabele.item(j).getAttributes().getNamedItem("code").getNodeValue();
				pomtable.name = tabele.item(j).getAttributes().getNamedItem("name").getNodeValue();
				expression = xpath.compile("column");
				NodeList kolone = (NodeList) expression.evaluate(tabele.item(j), XPathConstants.NODESET);
				for (int k = 0; k < kolone.getLength(); k++)

				{
					TreeElement.Column pomcolumn = new TreeElement.Column();
					pomcolumn.code = kolone.item(k).getAttributes().getNamedItem("code").getNodeValue();
					pomcolumn.name = kolone.item(k).getAttributes().getNamedItem("name").getNodeValue();

					boolean pomBool = false;
					if (kolone.item(k).getAttributes().getNamedItem("nullable").getNodeValue().equals("true"))
					{
						pomBool = true;

					}
					pomcolumn.setNullable(pomBool);
					pomBool = false;
					if (kolone.item(k).getAttributes().getNamedItem("primary").getNodeValue().equals("true"))
					{
						pomBool = true;
					}
					pomcolumn.setPrimary(pomBool);

					expression = xpath.compile("references");
					NodeList reference = (NodeList) expression.evaluate(kolone.item(k), XPathConstants.NODESET);

					for (int m = 0; m < reference.getLength(); m++)

					{

						TreeElement.Table reftable = new TreeElement.Table();

						reftable.code = reference.item(m).getAttributes().getNamedItem("refTable").getNodeValue();

						pomtable.addReference(reftable);

						if (reftable.getCode() != pomtable.getCode())
							pomcolumn.setName(stringFormat(reftable.code));

						expression = xpath.compile("refColumn");

						TreeElement.Column refKolone = new TreeElement.Column();
						refKolone.code = reference.item(m).getAttributes().getNamedItem("refColumn").getNodeValue();

						reftable.addElement(refKolone);

						pomcolumn.addRefrence(refKolone);

//						for (int l = 0; l < refKolone.getLength(); l++)
//						{
//							TreeElement.Column refK = new TreeElement.Column();
//
//							refK.code = refKolone.item(l).getAttributes().getNamedItem("refColumn").getNodeValue();
//
//							reftable.addElement(refK);
//						}

					}
					if (reference.getLength() > 0)
					{
						TreeElement.Table referencedTable = new TreeElement.Table();
						referencedTable.setCode(reference.item(0).getAttributes().getNamedItem("refTable").getNodeValue());
						if (referencedTable.getCode() != pomtable.getCode())
							pomcolumn.setName(stringFormat(referencedTable.code));
					}

					pomtable.addElement(pomcolumn);
					pomtable.getColumns().add(pomcolumn);

				}

				expression = xpath.compile("crud");

				Node crud = (Node) expression.evaluate(tabele.item(j), XPathConstants.NODE);
				expression = xpath.compile("create");

				Node create = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (create.getAttributes().getLength() > 0)
					pomtable.setCreateSProc(create.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("retrieve");

				Node retrieve = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (retrieve.getAttributes().getLength() > 0)
					pomtable.setRetrieveSProc(retrieve.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("retrieveAll");

				Node retrieveAll = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (retrieveAll != null)
					if (retrieveAll.getAttributes().getLength() > 0)
						pomtable.setRetrieveAllSProc(retrieveAll.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("update");

				Node update = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (update.getAttributes().getLength() > 0)
					pomtable.setUpdateSProc(update.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("delete");

				Node delete = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (delete.getAttributes().getLength() > 0)
					pomtable.setDeleteSProc(delete.getAttributes().getNamedItem("name").getNodeValue());

				expression = xpath.compile("retrieveRefValue");

				Node retrieveRefValue = (Node) expression.evaluate(crud, XPathConstants.NODE);
				if (retrieveRefValue != null)
					if (retrieveRefValue.getAttributes().getLength() > 0)
						pomtable.setRetreiveRefValueSProc(retrieveRefValue.getAttributes().getNamedItem("name").getNodeValue());

			}

		}

	}

}