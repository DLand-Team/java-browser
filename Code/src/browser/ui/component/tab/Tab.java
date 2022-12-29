package browser.ui.component.tab;

public class Tab {

    private final TabCaption caption;
    private final TabContent content;

    public Tab(TabCaption caption, TabContent content) {
        this.caption = caption;
        this.content = content;
    }

    public TabCaption getCaption() {
        return caption;
    }

    public TabContent getContent() {
        return content;
    }

}
