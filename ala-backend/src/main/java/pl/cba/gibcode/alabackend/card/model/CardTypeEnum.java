package pl.cba.gibcode.alabackend.card.model;

public enum CardTypeEnum {
    PHYSICAL,
    ELECTRONIC;

    private static CardTypeEnum[] allValues = values();

    public static CardTypeEnum fromOrdinal(int n) {
        if (n < 0 || n >= allValues.length) {
            throw new RuntimeException();
        } else {
            return allValues[n];
        }
    }
}
