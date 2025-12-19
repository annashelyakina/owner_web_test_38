package qa.guru.owner.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${launch}.properties",
        "classpath:local.properties"
})

public interface WebDriverConfig extends Config {

    @Key("webdriver.baseUrl")
    String getBaseURL();

    @Key("webdriver.browser")
    Browser getBrowser();

    @Key("webdriver.browserVersion")
    String getBrowserVersion();

    @DefaultValue("")
    @Key("webdriver.remote")
    String getRemoteURLAsString();
}
