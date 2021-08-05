package io.github.idenews.parser.headers;

public enum RSSImageHeader {
    TITLE("title"),
    URL("url"),
    LINK("link"),
    WIDTH("width"),
    HEIGHT("height"),
    DESCRIPTION("description"),
    UNKNOWN("UNKNOWN");

    private final String nodeName;

    RSSImageHeader(String nodeName) {
        this.nodeName = nodeName;
    }

    public static RSSImageHeader valueOfNodeName(String nodeName) {
        for (RSSImageHeader header : values()) {
            if (header.nodeName.equals(nodeName)) {
                return header;
            }
        }
        return UNKNOWN;
    }

    public String getNodeName() {
        return nodeName;
    }
}
