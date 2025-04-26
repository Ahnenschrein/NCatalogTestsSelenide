package pages.ncatalog;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.page;

/**
 * Абстрактный класс общего функционала для всех PageObjects сайта N-Catalog
 */

public abstract class NCBasePage {

    /**
     * Разделы сайта
     */
    protected final ElementsCollection sections = $$x("//li[@class='mainmenu-item']");

    /**
     * Подразделы сайта
     */
    protected final ElementsCollection subsections = $$x("//a[@class='mainmenu-subitem']");

    /**
     * Метод для перехода по разделам и подразделам сайта
     */
    public <T extends NCBasePage> T goToChapter(String title, String subtitle, Class<T> typeNextPage) {
        for (SelenideElement section : sections) {
            if (section.getText().equals(title)) {
                section.click();
                break;
            }
        }
        for (SelenideElement subsection : subsections) {
            if (subsection.getText().equals(subtitle)) {
                subsection.click();
                break;
            }
        }
        return typeNextPage.cast(page(typeNextPage));
    }
}


