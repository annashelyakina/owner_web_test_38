package qa.guru.owner.config;

import org.aeonbits.owner.Config;

import java.net.URL;

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

    @Key("webdriver.remote")
    String getRemoteURL();
}
