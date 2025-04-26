package helpers;

import java.util.Objects;

/**
 * Парный класс для получения значения производитель/бренд из коллекции
 */
public class Pair<M, B> {

    public M manufacturer;
    public B brand;

    public Pair(M manufacturer, B brand) {
        this.manufacturer = manufacturer;
        this.brand = brand;
    }

    @Override
    public String toString(){
        return "manufacturer=" + manufacturer + " ● " + "brand=" + brand;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(manufacturer, other.manufacturer) &&
                Objects.equals(brand, other.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturer, brand);
    }
}
