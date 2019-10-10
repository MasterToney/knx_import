import Import.EtsImport;
import Import.Importer;
import Matching.Implementations.SwitchMatcher;
import Models.GroupAddress;
import Models.ImportException;
import Parser.GroupAddressFactory;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.LinkedList;

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

            var groupAddressList = new LinkedList<GroupAddress>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                var node = nodeList.item(i);

                var groupAddress = GroupAddressFactory.CreateGroupAddressFromNode(node);

                if (groupAddress != null) {
                    groupAddressList.add(groupAddress);
                }
            }

            System.out.println(String.format("Xpath \"%s\" returned \"%d\" elements", expression, nodeList.getLength()));
            System.out.println(String.format("Parsed \"%d\" nodes successfully", groupAddressList.size()));


            var matcher = new SwitchMatcher("LI", "RM LI");


            System.out.printf("Group address count before switch extraction: %d\n", groupAddressList.size());
            var result = matcher.ExtractControls(groupAddressList);
            System.out.printf("Group address count after switch extraction: %d\n", groupAddressList.size());
            System.out.printf("Number of switches: %d\n", result.size());



            for (var switchControl : result) {
                // System.out.println(switchControl);
                System.out.println(switchControl.toThingFormat());
            }


        } catch (ImportException | XPathExpressionException e) {
            e.printStackTrace();
        }

    }
}
