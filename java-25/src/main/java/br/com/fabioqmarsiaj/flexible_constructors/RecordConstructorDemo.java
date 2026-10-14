package br.com.fabioqmarsiaj.flexible_constructors;

public class RecordConstructorDemo {
    void main() {
        var boilingPoint = new Temperature(100.0);
        IO.println("Boiling: " + boilingPoint);

        var bodyTemp = new Temperature(98.6, "F");
        IO.println("Body Temp: " + bodyTemp);

        var absolute = new Temperature(0, "K");
        IO.println("Absolute Zero: " + absolute);
    }

    record Temperature(double celsius) {
        Temperature {
            if (celsius < -273.15) {
                throw new IllegalArgumentException("Below absolute zero: " + celsius);
            }

        }

        Temperature(double value, String unit) {
            double converted = switch (unit.toUpperCase()) {
                case "C" -> value;
                case "F" -> (value - 32) * 5.0 / 9.0;
                case "K" -> value - 273.15;
                default -> throw new IllegalArgumentException("Unknown unit: " + unit);
            };
            this(converted);
        }
    }
}
