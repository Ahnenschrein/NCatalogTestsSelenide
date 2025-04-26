package helpers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Класс тестовых данных
 */
public class TestRunData {

    /**
     * Класс данных для поиска
     */
    public static class SearchTestData implements ArgumentsProvider {

        public String query = "n-catalog";
        public String source = "n-Katalog: Новый каталог товаров и цен в интернет ...";
        public String section = "Гаджеты";
        public String subSection = "Мобильные";

        private HashMap<String, String> allBrands(){
            HashMap<String, String> Manufacturers = new HashMap<>();
            Manufacturers.put("Apple", "Iphone");
            Manufacturers.put("Google", "Pixel");
            Manufacturers.put("Honor", "Honor");
            Manufacturers.put("Huawei", "Huawei");
            Manufacturers.put("Nokia", "Nokia");
            Manufacturers.put("OnePlus", "OnePlus");
            Manufacturers.put("OPPO", "OPPO");
            Manufacturers.put("Realme", "Realme");
            Manufacturers.put("Samsung", "Galaxy");
            Manufacturers.put("Vivo", "Vivo");
            Manufacturers.put("Xiaomi", "Xiaomi");
            Manufacturers.put("ZTE", "ZTE");
            return Manufacturers;
        }

        public Pair <String, String> getBrand() {
            return new Pair<>("Apple", allBrands().get("Apple"));
        }

        @Override
        public String toString() {
            return  "section=" + section + " ● " +
                    "subsection=" + subSection + " ● " +
                    getBrand().toString();
        }

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(Arguments.of(new SearchTestData()));
        }
    }
}
