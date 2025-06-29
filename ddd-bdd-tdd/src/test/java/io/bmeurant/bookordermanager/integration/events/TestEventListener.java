package io.bmeurant.bookordermanager.integration.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class TestEventListener {

    private final List<ApplicationEvent> capturedEvents = new ArrayList<>();

    @EventListener
    public void handleEvent(ApplicationEvent event) {
        capturedEvents.add(event);
    }

    public void clearEvents() {
        capturedEvents.clear();
    }
}
