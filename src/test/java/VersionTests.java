import dev.cadindie.SMML;
import dev.cadindie.sources.Source;
import org.junit.jupiter.api.Test;

public class VersionTests {
    @Test
    public void testModrinthSodiumVersion() {
        SMML.getProjectVersions("AANobbMI", Source.MODRINTH).thenAccept(result -> {
            int i = 1;
            for (var version : result) {
                if (i++ > 5) break;
                StringBuilder sb = new StringBuilder();
                sb.append("Version: ").append(version.version())
                        .append(", MC Versions: ").append(String.join(", ", version.mcVersions())).append(", Loaders: ");

                for (var loader : version.loaders()) {
                    sb.append(loader.name()).append(", ");
                }

                System.out.println(sb);
            }
        });
    }

    @Test
    public void testCurseForgeSodiumVersion() {
        SMML.getProjectVersions("394468", Source.CURSEFORGE).thenAccept(result -> {
            int i = 1;
            for (var version : result) {
                if (i++ > 5) break;
                StringBuilder sb = new StringBuilder();
                sb.append("Version: ").append(version.version())
                        .append(", MC Versions: ").append(String.join(", ", version.mcVersions())).append(", Loaders: ");

                for (var loader : version.loaders()) {
                    sb.append(loader.name()).append(", ");
                }

                System.out.println(sb);
            }
        });
    }
}
