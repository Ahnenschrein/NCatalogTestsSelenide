package helpers;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.SelenideLog;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import io.qameta.allure.util.ResultsUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Кастомизированный класс AllureSelenide для прикрепления скриншотов в Allure-отчет
 */
public class CustomAllureSelenide extends AllureSelenide {

        private static final Logger LOGGER = LoggerFactory.getLogger(CustomAllureSelenide.class);
        private boolean saveScreenshots;
        private boolean savePageHtml;
        private boolean includeSelenideLocatorsSteps;
        private final Map<LogType, Level> logTypesToSave;
        private final AllureLifecycle lifecycle;

        public CustomAllureSelenide() {
            this(Allure.getLifecycle());
        }

        public CustomAllureSelenide(AllureLifecycle lifecycle) {
            this.saveScreenshots = true;
            this.savePageHtml = true;
            this.includeSelenideLocatorsSteps = true;
            this.logTypesToSave = new HashMap<>();
            this.lifecycle = lifecycle;
        }

        public io.qameta.allure.selenide.AllureSelenide screenshots(boolean saveScreenshots) {
            this.saveScreenshots = saveScreenshots;
            return this;
        }

        public io.qameta.allure.selenide.AllureSelenide savePageSource(boolean savePageHtml) {
            this.savePageHtml = savePageHtml;
            return this;
        }

        public io.qameta.allure.selenide.AllureSelenide includeSelenideSteps(boolean includeSelenideSteps) {
            this.includeSelenideLocatorsSteps = includeSelenideSteps;
            return this;
        }

        public io.qameta.allure.selenide.AllureSelenide enableLogs(LogType logType, Level logLevel) {
            this.logTypesToSave.put(logType, logLevel);
            return this;
        }

        public io.qameta.allure.selenide.AllureSelenide disableLogs(LogType logType) {
            this.logTypesToSave.remove(logType);
            return this;
        }

        private static Optional<byte[]> getScreenshotBytes() {
            try {
                return WebDriverRunner.hasWebDriverStarted() ? Optional.of(((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES)) : Optional.empty();
            } catch (WebDriverException var1) {
                LOGGER.warn("Could not get screen shot", var1);
                return Optional.empty();
            }
        }

        private static Optional<byte[]> getPageSourceBytes() {
            try {
                return WebDriverRunner.hasWebDriverStarted() ? Optional.of(WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)) : Optional.empty();
            } catch (WebDriverException var1) {
                LOGGER.warn("Could not get page source", var1);
                return Optional.empty();
            }
        }

        private static String getBrowserLogs(LogType logType, Level level) {
            return String.join("\n\n", Selenide.getWebDriverLogs(logType.toString(), level));
        }

        public void beforeEvent(LogEvent event) {
            if (this.stepsShouldBeLogged(event)) {
                this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                    String uuid = UUID.randomUUID().toString();
                    this.lifecycle.startStep(parentUuid, uuid, (new StepResult()).setName(event.toString()));
                });
            }

        }

        public void afterEvent(LogEvent event) {
            if (event.getStatus().equals(LogEvent.EventStatus.FAIL) || event.getStatus().equals(LogEvent.EventStatus.PASS)) {
                this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                    if (this.saveScreenshots) {
                        getScreenshotBytes().ifPresent((bytes) -> this.lifecycle.addAttachment("Screenshot", "image/png", "png", bytes));
                    }

                    if (this.savePageHtml) {
                        getPageSourceBytes().ifPresent((bytes) -> this.lifecycle.addAttachment("Page source", "text/html", "html", bytes));
                    }

                    if (!this.logTypesToSave.isEmpty()) {
                        this.logTypesToSave.forEach((logType, level) -> {
                            byte[] content = getBrowserLogs(logType, level).getBytes(StandardCharsets.UTF_8);
                            this.lifecycle.addAttachment("Logs from: " + logType, "application/json", ".txt", content);
                        });
                    }

                });
            }

            if (this.stepsShouldBeLogged(event)) {
                this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                    switch (event.getStatus()) {
                        case PASS:
                            this.lifecycle.updateStep((step) -> step.setStatus(Status.PASSED));
                            break;
                        case FAIL:
                            this.lifecycle.updateStep((stepResult) -> {
                                stepResult.setStatus(ResultsUtils.getStatus(event.getError()).orElse(Status.BROKEN));
                                stepResult.setStatusDetails(ResultsUtils.getStatusDetails(event.getError()).orElse(new StatusDetails()));
                            });
                            break;
                        default:
                            LOGGER.warn("Step finished with unsupported status {}", event.getStatus());
                    }

                    this.lifecycle.stopStep();
                });
            }

        }

        private boolean stepsShouldBeLogged(LogEvent event) {
            return this.includeSelenideLocatorsSteps || !(event instanceof SelenideLog);
        }
    }
