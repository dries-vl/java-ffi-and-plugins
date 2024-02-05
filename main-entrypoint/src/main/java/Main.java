import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import plugin4j.Greeting;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    static PluginManager pluginManager = new JarPluginManager(Path.of("plugins"));

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

    public static boolean reloadPlugin(String pluginId, String pluginPath) {
        try {
            pluginManager.stopPlugin(pluginId);
            pluginManager.unloadPlugin(pluginId);
            System.out.println("RELOAD NOW");
            Thread.sleep(5000);
            System.out.println("MISSED RELOAD WINDOW");
            pluginManager.loadPlugin(Paths.get(pluginPath));
            pluginManager.enablePlugin(pluginId);
            pluginManager.startPlugin(pluginId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
