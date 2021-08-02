package io.github.ijnews.parser.headers;

public enum RSSItemHeader {
    ITEM("item"),
    TITLE("title"),
    LINK("link"),
    DESCRIPTION("description"),
    AUTHOR("author"),
    CATEGORY("category"),
    COMMENTS("comments"),
    PUB_DATE("pubDate"),
    UNKNOWN("UNKNOWN");

    private final String nodeName;

    RSSItemHeader(String nodeName) {
        this.nodeName = nodeName;
    }

    public static RSSItemHeader valueOfNodeName(String nodeName) {
        for (RSSItemHeader header : values()) {
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
