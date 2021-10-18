package ru.job4j.pooh;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        var queueService = new QueueService();
        Map<String, String> params = new HashMap<>();
        params.put("temperature", "18");
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req("POST", "queue", "weather", params)
        );
        /* Забираем данные из очереди weather. Режим queue */
        var result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }
}