package ru.job4j.pooh;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Req {
    private final String method;
    private final String mode;
    private final String queue;
    private final Map<String, String> params;

    public Req(String method, String mode, String queue, Map<String, String> params) {
        this.method = method;
        this.mode = mode;
        this.queue = queue;
        this.params = params;
    }

    public static Req of(String content) {
        String[] method = content.split(" ", 3);
        String[] topics = method[1].replaceAll("/", " ").trim().split(" ");
        String[] strings = content.split("\\R");
        String[] param = strings[strings.length - 1].split("=");
        Map<String, String> params = new HashMap<>();
        params.put(param[0], param[param.length - 1]);
        if (topics.length > 2) {
            params.put("userId", topics[2]);
        }
        return new Req(method[0], topics[0],  topics[1], params);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String queue() {
        return queue;
    }

    public String param(String key) {
        return params.get(key);
    }

    public Map.Entry<String, String> getText() {
        if (params.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return params.entrySet().iterator().next();
    }
}
