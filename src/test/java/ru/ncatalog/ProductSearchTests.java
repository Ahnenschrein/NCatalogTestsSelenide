package ru.ncatalog;

import helpers.TestRunData;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import ru.ncatalog.steps.Steps;

public class ProductSearchTests extends WebdriverSetup {

    @ParameterizedTest(name = "[{arguments}]")
    @DisplayName("Проверка фильтров и результатов поиска на сайте n-catalog")
    @Epic("Проверка фильтров и результатов поиска")
    @ArgumentsSource(TestRunData.SearchTestData.class)
    public void smartphoneSearchTest(TestRunData.SearchTestData testData){
        new Steps()
                .openGoogle()
                .search(testData.query)
                .goToSource(testData.source)
                .goToChapter(testData.section, testData.subSection)
                .setFilters(testData.getBrand())
                .getResultsCount()
                .checkResults(testData.getBrand());
    }
}


