package pages.google;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

/**
* Класс PageObject страницы с результатами поиска на сайте Google.com
*/
public class GSearchResultsPage {

    /**
     * Метод для перехода на сайт по результату поиска
     */
    public <T> T goToSource(String source, Class<T> typeNextPage) {
        ElementsCollection resultSearch = $$x("//h1[text()='Результаты поиска']/following-sibling::div/div");
        for (SelenideElement result : resultSearch) {
            if (result.getText().contains(source)) {
                result.$x(".//a[@href]").click();
                break;
            }
        }
        return typeNextPage.cast(page(typeNextPage));
    }
}
