package pl.cba.gibcode.alabackend.brand.model;

public enum PriceRangeEnum {
    ALL,
    ZERO_FIFTY,
    FIFTY_HUNDRED,
    HUNDRED_FIVEHUNDRED,
    FIVEHUNDRED_MORE;


    private static PriceRangeEnum[] allValues = values();

    public static PriceRangeEnum fromOrdinal(int n) {
        if (n < 0 || n >= allValues.length) {
            throw new RuntimeException();
        } else {
            return allValues[n];
        }
    }
}