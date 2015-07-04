package io.github.scarletsky.bangumi.events;

import io.github.scarletsky.bangumi.events.ClickNavigateIconEvent.NavigateIconType;
/**
 * Created by scarlex on 15-7-3.
 */
public class SetToolbarEvent {

    private String title;
    private NavigateIconType iconType;



    public SetToolbarEvent(String title) {
        this.title = title;
    }

    public SetToolbarEvent(String title, NavigateIconType iconType) {
        this.title = title;
        this.iconType = iconType;
    }

    public String getTitle() {
        return title;
    }

    public NavigateIconType getIconType() {
        return iconType;
    }
}
