package plugin4j;

import org.pf4j.Extension;
import org.pf4j.Plugin;

public class WelcomePlugin extends Plugin {

    /// These one do reload; the plugin JAR is used
    @Override
    public void start() {
        System.out.println("WelcomePlugin.start() lifecycle stuff");
    }

    @Override
    public void stop() {
        System.out.println("WelcomePlugin.stop()");
    }

    @Override
    public void delete() {
        System.out.println("WelcomePlugin.delete()");
    }

    @Extension
    public static class WelcomeGreeting implements Greeting {

        /// This one doesn't reload; the jars JAR is used
        @Override
        public String getGreeting() {
            return "Something else";
        }

    }
}
