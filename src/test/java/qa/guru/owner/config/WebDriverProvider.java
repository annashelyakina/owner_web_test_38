package qa.guru.owner.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.URL;
import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    @Override
    public WebDriver get() {
        WebDriver driver = createDriver();
        driver.get(config.getBaseURL());
        return driver;
    }

    public WebDriver createDriver() {

        if (config.getRemoteURL().isEmpty()) {
            // Локальный запуск теста
            switch (config.getBrowser()) {
                case CHROME: {
                    WebDriverManager.chromedriver()
                            .browserVersion(config.getBrowserVersion())
                            .setup();
                    return new ChromeDriver();
                }
                case FIREFOX: {
                    WebDriverManager.firefoxdriver()
                            .browserVersion(config.getBrowserVersion())
                            .setup();
                    return new FirefoxDriver();
                }
                default: {
                    throw new RuntimeException("No such driver");
                }
            }
        }
        else {
            // удалённый запуск теста
            switch (config.getBrowser()) {
                case CHROME: {
                    WebDriverManager.chromedriver()
                            .browserVersion(config.getBrowserVersion())
                            .setup();
                    return new RemoteWebDriver(new URL(config.getRemoteURL()), new ChromeOptions());
                }
                case FIREFOX: {
                    WebDriverManager.firefoxdriver()
                            .browserVersion(config.getBrowserVersion())
                            .setup();
                    return new RemoteWebDriver(new URL(config.getRemoteURL()), new FirefoxOptions());
                }
                default: {
                    throw new RuntimeException("No such driver");
                }
            }
        }
        throw new RuntimeException("No such browser");
    }
}
