package Import;

import Models.GroupAddress;
import Models.ImportException;
import Parser.GroupAddressFactory;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Importer implements EtsImport {

    @Override
    public Document ImportEtsFile(String filePathString) throws ImportException {

        var filePath = Paths.get(filePathString);

        ValidateFileExists(filePath);

        var resultDoc = GetProjectDocument(filePath);

        resultDoc.getDocumentElement().normalize();

        return resultDoc;
    }

    @Override
    public List<GroupAddress> ExtractGroupAddresses(Document source) {

        var groupAddressList = new LinkedList<GroupAddress>();
        XPath xPath =  XPathFactory.newInstance().newXPath();

        String expression = "//GroupAddress";
        NodeList nodeList = null;
        try {
            nodeList = (NodeList) xPath.compile(expression).evaluate(source, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                var node = nodeList.item(i);

                var groupAddress = GroupAddressFactory.CreateGroupAddressFromNode(node);

                if (groupAddress != null) {
                    groupAddressList.add(groupAddress);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return groupAddressList;
    }

    private void ValidateFileExists(Path filePath) throws ImportException {

        if (!Files.exists(filePath)) {
            throw new ImportException(String.format("The file \"%s\" does not exist!", filePath));
        }

        if (Files.isDirectory(filePath)) {
            throw new ImportException(String.format("The file path \"%s\" points to a directory instead of an .knxproj file!", filePath));
        }
    }

    private Document GetProjectDocument(Path projectFile) throws ImportException {

        ZipFile zipFile = new ZipFile(projectFile.toFile());

        Document resultDocument = null;

        try {
            var knxProjectFilePath = getProjectFilePath(zipFile);

            var fileHeader = zipFile.getFileHeader(knxProjectFilePath);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            try(InputStream inputStream = zipFile.getInputStream(fileHeader)) {

                resultDocument = builder.parse(inputStream);

            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return resultDocument;
    }

    private String getProjectFilePath(ZipFile zipFile) throws ZipException, ImportException {

        // Get the list of file headers from the zip file
        var fileHeaderList = zipFile.getFileHeaders();

        for (var fileHeader: fileHeaderList) {
            if (fileHeader.getFileName().endsWith("/0.xml")) {
                return fileHeader.getFileName();
            }
        }

        throw new ImportException("Could not find the main ets project file \"0.xml\" inside the .knxproj file, maybe you have a different ets version.");
    }
}
