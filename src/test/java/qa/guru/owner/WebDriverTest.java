package qa.guru.owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import qa.guru.owner.config.WebDriverProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebDriverTest extends TestBase{

    private WebDriver driver;

    @BeforeEach
    public void startDriver(){
        driver = new WebDriverProvider().get();
    }

   @Test
    public void testArcadia(){
        String title = driver.getTitle();
        assertEquals("Разработка программного обеспечения на заказ | Аркадия", title);
        driver.quit();
    }

    @AfterEach
    public void stopDriver(){
        driver.quit();
    }
 }
