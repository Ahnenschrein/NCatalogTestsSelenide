package ru.ncatalog;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.CustomAllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Класс настройки веб-драйвера
 */
public class WebdriverSetup {

    protected static DesiredCapabilities capabilities = new DesiredCapabilities();
    @SuppressWarnings("FieldMayBeFinal")
    private static ChromeOptions options = new ChromeOptions();

    @BeforeAll
    public static void driverSetup(){
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.browserCapabilities = capabilities;
        Configuration.browserSize = "1910x1030";
        Configuration.browserPosition = "0x0";
        Configuration.timeout = 60000;
        SelenideLogger.addListener("AllureSelenide", new CustomAllureSelenide().screenshots(true)
                .savePageSource(true));
    }
}
