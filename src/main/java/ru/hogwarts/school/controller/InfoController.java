package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("info")
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/getPort")
    public int getPort() {
        return serverPort;
    }

    @GetMapping("/sum")
    public int generatedSum() {
        List<Integer> limit = Stream.iterate(1, a -> a + 1)
                .limit(100_000_100)
                .collect(Collectors.toList());
        long start = System.currentTimeMillis();
        int sum = limit.stream()
                .parallel()
                .mapToInt(it -> it)
                .sum();
        return (int) (System.currentTimeMillis() - start);
    }
}

