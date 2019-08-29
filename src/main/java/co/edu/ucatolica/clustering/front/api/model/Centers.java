package co.edu.ucatolica.clustering.front.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Centers {

    @JsonProperty
    private String key;
    @JsonProperty
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
