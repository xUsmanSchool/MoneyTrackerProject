package Events;

public abstract class Event {
    private final String eventName;
    private boolean hasIcon;
    private String icon;

    public Event(String name) {
        this.eventName = name;
    }

    public String getEventName() {
        return this.eventName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.hasIcon = icon.length() == 0;
        this.icon = icon;
    }

    public boolean getHasIcon() {
        return hasIcon;
    }
}
