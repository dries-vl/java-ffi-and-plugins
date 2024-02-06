import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import org.pf4j.update.DefaultUpdateRepository;
import org.pf4j.update.PluginInfo;
import org.pf4j.update.UpdateManager;
//import plugin4j.Greeting;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    static PluginManager pluginManager = new JarPluginManager(Path.of("plugins"));
    static UpdateManager updateManager = new UpdateManager(pluginManager);

    public static void main(String[] args) throws InterruptedException {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        while (true) {
            List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
            for (Greeting greeting : greetings) {
                System.out.println(">>> " + greeting.getGreeting());
            }
            reloadPlugin("welcome-plugin", "plugins/welcome-plugin-1.0-SNAPSHOT.jar");
        }
//        pluginManager.stopPlugins();
    }

    public static void reloadPlugin(String pluginId, String pluginPath) throws InterruptedException {
        Thread.sleep(3000);
        if (updateManager.hasUpdates()) {
            List<PluginInfo> updates = updateManager.getUpdates();
            System.out.printf("Found {} updates%n", updates.size());
            for (PluginInfo plugin : updates) {
                System.out.println(String.format("Found update for plugin '{}'", plugin.id));
                PluginInfo.PluginRelease lastRelease = updateManager.getLastPluginRelease(plugin.id);
                String lastVersion = lastRelease.version;
                String installedVersion = pluginManager.getPlugin(plugin.id).getDescriptor().getVersion();
                System.out.println(String.format("Update plugin '{}' from version {} to version {}", plugin.id, installedVersion, lastVersion));
                boolean updated = updateManager.updatePlugin(plugin.id, lastVersion);
                if (updated) {
                    System.out.println(String.format("Updated plugin '{}'", plugin.id));
                } else {
                    System.out.println(String.format("Cannot update plugin '{}'", plugin.id));
                }
            }
        } else {
            System.out.println("No updates found");
        }
    }
}
