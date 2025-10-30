package io.bmeurant.java.concurrency;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@RestController
public class ThreadComparisonController {

    private static final long BLOCKING_TIME_MS = 100;

    /**
     * Endpoint executed by the default embedded Tomcat thread pool (Platform Threads).
     * This pool has a finite size (e.g., 200).
     */
    @GetMapping("/platform-thread")
    public String platformThreadEndpoint() throws InterruptedException {
        String threadInfo = Thread.currentThread().toString();

        // Simulate a blocking I/O operation (e.g., database call, external service API call)
        TimeUnit.MILLISECONDS.sleep(BLOCKING_TIME_MS);

        // When many requests are made, the pool will be exhausted (starvation)
        return "Platform Thread processed in " + BLOCKING_TIME_MS + "ms on: " + threadInfo;
    }

    /**
     * Endpoint executed by a Virtual Thread (thanks to Spring Boot 3.2+ and JDK 21).
     * This is highly scalable for I/O-bound tasks.
     * Note: We must explicitly use the @EnableVirtualThreads annotation
     * or configure the Executor, but for a simple Spring Boot app with the property
     * set in pom.xml, the standard dispatcher will use them for this type of task.
     */
    @GetMapping("/virtual-thread")
    public String virtualThreadEndpoint() throws InterruptedException {
        String threadInfo = Thread.currentThread().toString();

        // Simulate a blocking I/O operation
        TimeUnit.MILLISECONDS.sleep(BLOCKING_TIME_MS);

        // Even with many concurrent requests, the JVM manages the carrier threads efficiently.
        return "Virtual Thread processed in " + BLOCKING_TIME_MS + "ms on: " + threadInfo;
    }
}