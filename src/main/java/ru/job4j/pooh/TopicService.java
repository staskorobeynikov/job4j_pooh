package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> storage = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp("Bad request", 400);
        if ("GET".equals(req.method())) {
            String queueName = req.queue();
            storage.putIfAbsent(queueName, new ConcurrentHashMap<>());
            storage.get(queueName).putIfAbsent(req.param("userId"), new ConcurrentLinkedQueue<>());
            String content = storage.get(queueName).get(req.param("userId")).poll();
            result = content != null ? new Resp(content, 200)
                    : new Resp("", 200);
        }
        if ("POST".equals(req.method())) {
            Map.Entry<String, String> content = req.getText();
            String text = content.getKey() + "=" + content.getValue();
            storage.getOrDefault(req.queue(), new ConcurrentHashMap<>()).forEach((s, q) -> q.add(text));
        }
        return result;
    }
}
