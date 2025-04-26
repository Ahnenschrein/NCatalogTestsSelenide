package ru.ncatalog.steps;

import com.codeborne.selenide.Selenide;
import helpers.Pair;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pages.google.GSearchMainPage;
import pages.google.GSearchResultsPage;
import pages.ncatalog.NCMainPage;
import pages.ncatalog.NCProductSelectionPage;

public class Steps {

    private GSearchMainPage gSearchMainPage;
    private GSearchResultsPage gSearchResultsPage;
    private NCMainPage ncMainPage;
    private NCProductSelectionPage ncProductSelectionPage;

    public Steps() {
    }

    @Step("Открываем сайт Google.com")
    public Steps openGoogle(){
        gSearchMainPage = Selenide.open("https://www.google.com/", GSearchMainPage.class);
        return this;
    }

    @Step("Поиск {query} в Google")
    public Steps search(String query){
        gSearchResultsPage = gSearchMainPage.search(query);
        return this;
    }

    @Step ("Переходим на сайт N-Catalog")
    public Steps goToSource(String source){
        ncMainPage = gSearchResultsPage.goToSource(source, NCMainPage.class);
        return this;
    }
    @Step("Переход в раздел {title}, подраздел {subtitle}")
    public Steps goToChapter(String title, String subtitle){
        ncProductSelectionPage = ncMainPage.goToChapter(title, subtitle, NCProductSelectionPage.class);
        return this;
    }

    @Step("Установка фильтров brands: {brands}")
    public Steps setFilters(Pair<String, String> brands){
        ncProductSelectionPage.setFilters(brands);
        return this;
    }

    @Step("Проверяем, что количество результатов поиска на странице не превышает 10")
    public Steps getResultsCount() {
        Assertions.assertTrue(ncProductSelectionPage.getResultsCount() <= ncProductSelectionPage.RESULTS_LIMIT,
                "Количество результатов поиска на странице превышает 10");
        return this;
    }
    @Step("Проверяем фильтры")
    @SuppressWarnings("UnusedReturnValue")
    public Steps checkResults(Pair<String, String> brands){
        ncProductSelectionPage.checkResults(brands);
        return this;
    }
}
