import java.io.Serializable;

public class Tuple implements Serializable {
    private String value;
    private Integer key;

    public Tuple(String value, Integer key) {
        this.value = value;
        this.key = key;
    }

    public void setValues(String value, Integer key) {
        this.value = value;
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public Integer getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "key: " + key + " value: " + value;
    }
}
