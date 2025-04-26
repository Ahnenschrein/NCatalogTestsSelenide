package pages.google;

import static com.codeborne.selenide.Selenide.*;

/**
 * Класс PageObject главной страницы сайта Google.com
 */
public class GSearchMainPage {

    /**
     * Метод для поиска в Google запроса
     */
    public GSearchResultsPage search(String query){
        $x("//input[@name='q']").setValue(query).pressEnter();
        return page(GSearchResultsPage.class);
    }
}
