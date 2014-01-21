package com.recruiters.web.helper;

/**
 * Object for using in language choosing menu
 */
public class LangChoose {
    private String url = null;
    private String language = null;
    private boolean selected = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
}
