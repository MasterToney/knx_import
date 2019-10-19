package Export;

import Models.OpenHAB.KnxControl;

import java.util.List;

public final class SitemapExporter {

    private SitemapExporter() {}

    public static String ExportSitemap(List<KnxControl> controls, String sitemapName) {
        var resultStringBuilder = new StringBuilder();

        resultStringBuilder.append("sitemap " + sitemapName + " label=\"KNX Import Sitemap\" {\n" +
                "  Frame label=\"Imported Items\" {\n");

        for (var control: controls) {
            if (!control.isValid()) {
                continue;
            }

            resultStringBuilder.append("    ");
            resultStringBuilder.append(control.toSitemapFormat());
            resultStringBuilder.append("\n");
        }

        resultStringBuilder.append("  }\n}");

        return resultStringBuilder.toString();
    }

}
