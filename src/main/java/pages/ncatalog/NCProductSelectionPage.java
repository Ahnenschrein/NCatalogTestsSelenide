package pages.ncatalog;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import helpers.Pair;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс PageObject для страницы подбора товара по фильтрам
 */
public class NCProductSelectionPage extends NCBasePage {

    /**
     * Максимальное количество результатов поиска
     */
    public final int RESULTS_LIMIT = 10;

    /**
     * Метод для установки фильтров по товарам
     */
    @SuppressWarnings("UnusedReturnValue")
    public NCProductSelectionPage setFilters(Pair<String, String> brands) {
        $x("//span[@id='br_all']/em").click();
        $x("//div[@id='manufacturers_presets']//label/a[text()='" + brands.manufacturer + "']/..").click();
        $x("//a[@class='show-models']").click();
        return this;
    }

    /**
     * Метод для получения количества результатов поиска на странице
     */
    public int getResultsCount() {
        return $$x("//div[@product]//a[@data-idgood]/span[@class='u']").size();
    }

    /**
     * Метод для проверки соответствия результатов поиска заданному фильтру по бренду
     */
    @Step("Проверяем соответствие результатов поиска заданному фильтру по бренду")
    @SuppressWarnings("UnusedReturnValue")
    public NCProductSelectionPage checkResults(Pair<String, String> brands) {
        int currentPage = 1;
        ElementsCollection searchResults = $$x("//div[@product]//a[@data-idgood]/span[@class='u']");
        SelenideElement nextPage;
        do {
            for (SelenideElement searchResult : searchResults) {
                Assertions.assertTrue(searchResult.getText().toLowerCase().contains(brands.brand.toLowerCase()),
                        "В результатах поиска содержится другой бренд");
                searchResult.hover();
            }
            if ((nextPage = $x("//a[@id='pager_" + (currentPage + 1) + "']")).exists()) {
                nextPage.hover();
                nextPage.click();
                currentPage++;
            }
        } while (nextPage.exists());
        return this;
    }
}
