package Export;

import Models.OpenHAB.KnxControl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public final class FileExporter {

    private FileExporter() { }

    public static void WriteToFile(Path file, String content) {


        try {
            Files.write(file, content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void WriteImport(Path confDirectoryPath, List<KnxControl> knxControls) {

        WriteItemsFile(confDirectoryPath, knxControls);
        WriteThingsFile(confDirectoryPath, knxControls);
        WriteSitemapFile(confDirectoryPath, knxControls);

    }

    private static void WriteSitemapFile(Path confDirectoryPath, List<KnxControl> knxControls) {

        var itemsFilePath = Paths.get(confDirectoryPath.toString(), "sitemaps", "knx-import.sitemap");

        WriteToFile(itemsFilePath, SitemapExporter.ExportSitemap(knxControls));
    }

    private static void WriteItemsFile(Path confDirectoryPath, List<KnxControl> knxControls) {

        var itemsFilePath = Paths.get(confDirectoryPath.toString(), "items", "knx-import.items");

        WriteToFile(itemsFilePath, ItemExporter.ExportItems(knxControls));
    }

    private static void WriteThingsFile(Path confDirectoryPath, List<KnxControl> knxControls) {

        var itemsFilePath = Paths.get(confDirectoryPath.toString(), "things", "knx-import.things");


        WriteToFile(itemsFilePath, ThingExporter.ExportThings(knxControls));
    }

}
