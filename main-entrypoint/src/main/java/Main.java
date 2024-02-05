import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import plugin4j.Greeting;

import java.nio.file.Paths;
import java.util.List;

public class Main {
    static PluginManager pluginManager = new JarPluginManager();

    public static void main(String[] args) throws InterruptedException {
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        while (true) {
            Thread.sleep(5000);
            List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
            for (Greeting greeting : greetings) {
                System.out.println(">>> " + greeting.getGreeting());
            }
            reloadPlugin("welcome-plugin", "plugins/welcome-plugin-1.0-SNAPSHOT.jar");
        }
//        pluginManager.stopPlugins();
    }

    public static boolean reloadPlugin(String pluginId, String pluginPath) {
        try {
            pluginManager.stopPlugin(pluginId);
            pluginManager.unloadPlugin(pluginId);
            pluginManager.loadPlugin(Paths.get(pluginPath));
            pluginManager.startPlugin(pluginId);

            return true; // Reload successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Reload failed
        }
    }
}
