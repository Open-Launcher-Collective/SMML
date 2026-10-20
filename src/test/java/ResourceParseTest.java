import dev.cadindie.Resource;
import dev.cadindie.SMML;
import org.junit.jupiter.api.Test;

public class ResourceParseTest {
    @Test
    public void parseModsFolderTest() {
        for (Resource result : SMML.parseModsFolder("")) {
            System.out.println("Found resource: " + result.name() + " - " + result.id() + " - " + result.resourceType());
        }
    }
}
