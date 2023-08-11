package edu.vsu.putin_p_a.springbootapp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelativeUrl {
    private String path;
    private Map<String, Object> getParams;

    public RelativeUrl(String path) {
        this.path = path;
        this.getParams = new HashMap<>();
    }

    public RelativeUrl addGetParam(String name, Object value) {
        getParams.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        String result = path;
        if (!getParams.isEmpty()) {
            result += "?";
            List<String> params = getParams.keySet().stream()
                    .map(key -> key + "=" + getParams.get(key).toString())
                    .toList();
            result += String.join("&", params);
        }
        return result;
    }
}
