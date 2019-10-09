package Import;

import Models.ImportException;
import net.lingala.zip4j.ZipFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Importer implements EtsImport {

    @Override
    public Document ImportEtsFile(String filePathString) throws ImportException {

        var filePath = Paths.get(filePathString);

        ValidateFileExists(filePath);

        var resultDoc = GetProjectDocument(filePath, "P-0829/0.xml");

        resultDoc.getDocumentElement().normalize();

        return resultDoc;
    }

    private void ValidateFileExists(Path filePath) throws ImportException {

        if (!Files.exists(filePath)) {
            throw new ImportException(String.format("The file \"%s\" does not exist!", filePath));
        }

        if (Files.isDirectory(filePath)) {
            throw new ImportException(String.format("The file path \"%s\" points to a directory instead of an .knxproj file!", filePath));
        }
    }

    private Document GetProjectDocument(Path projectFile, String knxProjectFilePath) {

        ZipFile zipFile = new ZipFile(projectFile.toFile());

        Document resultDocument = null;

        try {
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
}
