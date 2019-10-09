import Import.EtsImport;
import Import.Importer;
import Models.ImportException;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1) {

            System.out.println("USAGE: Main etsproject.knxproj");
            System.exit(0);
        }

        var importPath = args[0];

        EtsImport etsImporter = new Importer();

        try {
            var doc = etsImporter.ImportEtsFile(importPath);

            XPath xPath =  XPathFactory.newInstance().newXPath();

            String expression = "//GroupAddress";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

            System.out.println(String.format("Xpath \"%s\" returned \"%d\" elements", expression, nodeList.getLength()));


        } catch (ImportException | XPathExpressionException e) {
            e.printStackTrace();
        }

    }
}
