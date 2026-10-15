import dev.cadindie.SMML;
import dev.cadindie.SortMethod;
import dev.cadindie.SortOrder;
import dev.cadindie.sources.SearchRequest;
import dev.cadindie.sources.Source;
import org.junit.jupiter.api.Test;

public class SearchTests {
    @Test
    public void testAll() {
        SearchRequest request = new SearchRequest.Builder("Essentials").gameVersion("1.20.1")
                .sortMethod(SortMethod.DOWNLOADS).limit(5).build();

        System.out.println("Testing all sources - API Version: " + SMML.getVersion());
        SMML.search(request, Source.MODRINTH, Source.CURSEFORGE, Source.HANGAR, Source.SPIGET).thenAccept(results -> {
            int i = 1;
            for (var result : results) {
                System.out.println("Result " + i + ": " + result.name() + " - " + result.source());
                i++;
            }
        }).join();
    }

    @Test
    public void testModrinth() {
        SearchRequest request = new SearchRequest.Builder("Sodium").gameVersion("1.20.1")
                .sortMethod(SortMethod.DOWNLOADS).limit(5).build();

        System.out.println("Testing Modrinth - API Version: " + SMML.getSourceApiVersion(Source.MODRINTH));
        SMML.search(request, Source.MODRINTH).thenAccept(results -> {
            int i = 1;
            for (var result : results) {
                System.out.println("Result " + i + ": " + result.name());
                i++;
            }
        }).join();
    }

    @Test
    public void testCurseForge() {
        SearchRequest request = new SearchRequest.Builder("Sodium").gameVersion("1.20.1")
                .sortMethod(SortMethod.DOWNLOADS).sortOrder(SortOrder.DESCENDING).limit(5).build();

        System.out.println("Testing CurseForge - API Version: " + SMML.getSourceApiVersion(Source.CURSEFORGE));
        SMML.search(request, Source.CURSEFORGE).thenAccept(results -> {
            int i = 1;
            for (var result : results) {
                System.out.println("Result " + i + ": " + result.name() + " - " + result.id());
                i++;
            }
        }).join();
    }

    @Test
    public void testHangar() {
        SearchRequest request = new SearchRequest.Builder("Moderation").gameVersion("1.20.1")
                    .sortMethod(SortMethod.DOWNLOADS).limit(5).build();

        System.out.println("Testing Hangar - API Version: " + SMML.getSourceApiVersion(Source.HANGAR));
        SMML.search(request, Source.HANGAR).thenAccept(results -> {
            int i = 1;
            for (var result : results) {
                System.out.println("Result " + i + ": " + result.name());
                i++;
            }
        }).join();
    }

    @Test
    public void testSpiget() {
        SearchRequest request = new SearchRequest.Builder("Essentials").gameVersion("1.20.1")
                .sortMethod(SortMethod.DOWNLOADS).limit(5).build();

        System.out.println("Testing Spiget - API Version: " + SMML.getSourceApiVersion(Source.SPIGET));
        SMML.search(request, Source.SPIGET).thenAccept(results -> {
            int i = 1;
            for (var result : results) {
                System.out.println("Result " + i + ": " + result.name());
                i++;
            }
        }).join();
    }
}
