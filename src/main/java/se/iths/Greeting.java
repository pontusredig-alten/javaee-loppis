package se.iths;

import javax.inject.Named;

@Named
public class Greeting {

    public String getGreeting() {
        return "Hello JU20dec from JSF!";
    }

}
