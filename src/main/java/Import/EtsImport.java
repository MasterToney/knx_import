package Import;

import Models.GroupAddress;
import Models.ImportException;
import org.w3c.dom.Document;

import java.util.List;

public interface EtsImport {

    /**
     * Reads the content of a given ets project file and returns the xml document containing the knx addresses
     * @param filePathString path of the file to import
     * @return Document containing the knx addresses
     * @throws ImportException if any kind of error occurred, e.g. the file could not be found etc.
     */
    Document ImportEtsFile(String filePathString) throws ImportException;

    /**
     * Extracts all group addresses from the given Document.
     * @param source document of the ets project containing all
     * @return a list with all found group addresses
     */
    List<GroupAddress> ExtractGroupAddresses(Document source);

}
