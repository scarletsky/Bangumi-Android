package io.github.scarletsky.bangumi.events;

/**
 * Created by scarlex on 15-7-4.
 */
public class ClickNavigateIconEvent {

    private NavigateIconType iconType;

    public enum NavigateIconType {
        BACK,
        MENU
    }

    public ClickNavigateIconEvent(NavigateIconType iconType) {
        this.iconType = iconType;
    }

    public NavigateIconType getIconType() {
        return iconType;
    }
}
