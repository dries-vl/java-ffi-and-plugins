package plugin4j;

import org.pf4j.Extension;

@Extension
public class WelcomeGreeting implements Greeting {

    public String getGreeting() {
        return "Welcome Again!!!";
    }

}
