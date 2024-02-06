package plugin4j;

public class WelcomePlugin {

    public void start() {
        System.out.println("WelcomePlugin.start() lifecycle stuff");
    }

    public void stop() {
        System.out.println("WelcomePlugin.stop()");
    }

    public void delete() {
        System.out.println("WelcomePlugin.delete()");
    }

}
