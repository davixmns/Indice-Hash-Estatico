public class Tuple {
    private final String value;
    private final Integer key;

    public Tuple(String value, Integer key) {
        this.value = value;
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " " + value;
    }
}
