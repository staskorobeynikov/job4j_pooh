package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final Map<String, ConcurrentLinkedQueue<String>> storage = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp("Bad Request", 400);
        if ("GET".equals(req.method())) {
            ConcurrentLinkedQueue<String> strings = storage.get(req.queue());
            result = strings != null ? new Resp(strings.poll(), 200)
                    : new Resp("Storage is empty", 200);
        }
        if ("POST".equals(req.method())) {
            ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
            Map.Entry<String, String> content = req.getText();
            String text = content.getKey() + "=" + content.getValue();
            queue.add(text);
            storage.putIfAbsent(req.queue(), queue);
            result = new Resp(text, 200);
        }
        return result;
    }
}
