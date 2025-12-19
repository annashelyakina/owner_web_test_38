package qa.guru.owner.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    @Override
    public WebDriver get() {
        try {
            WebDriver driver = createDriver();
            driver.get(config.getBaseURL());
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize the web driver.", e); // Перебрасываем как runtime exception
        }
    }

    public WebDriver createDriver() throws MalformedURLException{
        String remoteUrlStr = config.getRemoteURLAsString(); // Получаем URL как строку

        if (!remoteUrlStr.isEmpty()) { // Проверяем наличие удалённого URL
            URL remoteUrl = new URL(remoteUrlStr); // Преобразовываем строку в URL здесь

            // Создаем объект настроек в зависимости от выбранного браузера
            switch (config.getBrowser()) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions(); // Объект для опций Chrome
                    chromeOptions.setCapability("browserName", config.getBrowser()); // Имя браузера
                    chromeOptions.setCapability("browserVersion", config.getBrowserVersion()); // Версия браузера

                    // Используем RemoteWebDriver для подключения к удаленному серверу
                    return new RemoteWebDriver(new URL(remoteUrl.toString()), chromeOptions);
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions(); // Объект для опций Firefox
                    firefoxOptions.setCapability("browserName", config.getBrowser()); // Имя браузера
                    firefoxOptions.setCapability("browserVersion", config.getBrowserVersion()); // Версия браузера

                    // Используем RemoteWebDriver для подключения к удаленному серверу
                    return new RemoteWebDriver(new URL(remoteUrl.toString()), firefoxOptions);
                default:
                    throw new IllegalArgumentException("Unsupported browser type.");
            }
        } else {
            // Локальное создание драйвера
            switch (config.getBrowser()) {
                case CHROME:
                    WebDriverManager.chromedriver()
                            .browserVersion(config.getBrowserVersion())
                            .setup();
                    return new ChromeDriver();
                case FIREFOX:
                    WebDriverManager.firefoxdriver()
                            .browserVersion(config.getBrowserVersion())
                            .setup();
                    return new FirefoxDriver();
                default:
                    throw new RuntimeException("Unsupported browser type.");
            }
        }
    }
}
