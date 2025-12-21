package qa.guru.owner;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import qa.guru.owner.config.WebDriverConfig;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
        Configuration.baseUrl = config.getBaseURL();
        Configuration.browser = config.getBrowser().toString();
        Configuration.browserVersion = config.getBrowserVersion();
        Configuration.browserSize = System.getProperty("1920x1080");
        if (!config.getRemoteURL().isEmpty()) {
            Configuration.remote = config.getRemoteURL();
        }
    }
}
