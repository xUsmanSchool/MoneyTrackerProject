package Events;

public abstract class Event {
    private String eventName;

    public Event(String name) {
        this.eventName = name;
    }

    public String getEventName() {
        return this.eventName;
    }
}
