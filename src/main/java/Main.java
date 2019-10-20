import Export.FileExporter;
import Import.EtsImport;
import Import.Importer;
import Matching.Implementations.DimmerMatcher;
import Matching.Implementations.RollerShutterMatcher;
import Matching.Implementations.SwitchMatcher;
import Models.ImportException;
import Models.OpenHAB.KnxControl;

import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {

            System.out.println("USAGE: Main etsproject.knxproj openhab/conf/");
            System.exit(0);
        }

        var importPath = args[0];
        var confDirectoryString = args[1];
        var confDirectory = Paths.get(confDirectoryString);

        EtsImport etsImporter = new Importer();

        try {
            var parsedDocument = etsImporter.ImportEtsFile(importPath);
            var groupAddressList = etsImporter.ExtractGroupAddresses(parsedDocument);

            var switchMatcher = SwitchMatcher.BuildSwitchMatcher("LI", "RM LI");
            var rollerShutterMatcher = RollerShutterMatcher.BuildRollershutterMatcher("LZ", "", "WE HÖ", "RM WE HÖ", "SP", "KZ");
            var dimmerMatcher = DimmerMatcher.BuildDimmerMatcher("LI", "RM LI", "WE", "RM WE", "DIM");

            var controls = new LinkedList<KnxControl>();

            System.out.printf("Group address count before dimmer extraction: %d\n", groupAddressList.size());
            var dimmer = dimmerMatcher.ExtractControls(groupAddressList);
            System.out.printf("Group address count after dimmer extraction: %d\n", groupAddressList.size());
            System.out.printf("Number of dimmer: %d\n", dimmer.size());


            System.out.printf("Group address count before switch extraction: %d\n", groupAddressList.size());
            var switches = switchMatcher.ExtractControls(groupAddressList);
            System.out.printf("Group address count after switch extraction: %d\n", groupAddressList.size());
            System.out.printf("Number of switches: %d\n", switches.size());


            System.out.printf("Group address count before rollerShutter extraction: %d\n", groupAddressList.size());
            var rollerShutters = rollerShutterMatcher.ExtractControls(groupAddressList);
            System.out.printf("Group address count after rollerShutter extraction: %d\n", groupAddressList.size());
            System.out.printf("Number of rollerShutters: %d\n", rollerShutters.size());


            controls.addAll(dimmer);

            controls.addAll(rollerShutters);

            controls.addAll(switches);

            FileExporter.WriteImportedConfiguration(confDirectory, controls, "knximport");

        } catch (ImportException e) {
            e.printStackTrace();
        }

    }


}
