package io.github.ijnews.parser.headers;

public enum RSSChannelHeader {
    CHANNEL("channel"),
    TITLE("title"),
    LINK("link"),
    DESCRIPTION("description"),
    LANGUAGE("language"),
    RATING("rating"),
    COPYRIGHT("copyright"),
    PUB_DATE("pubDate"),
    LAST_BUILD_DATE("lastBuildDate"),
    CATEGORY("category"),
    DOCS("docs"),
    TTL("ttl"),
    MANAGING_EDITOR("managingEditor"),
    WEB_MASTER("webMaster"),
    IMAGE("image"),
    ITEM("item"),
    UNKNOWN("UNKNOWN");

    private final String nodeName;

    RSSChannelHeader(String nodeName) {
        this.nodeName = nodeName;
    }

    public static RSSChannelHeader valueOfNodeName(String nodeName) {
        for (RSSChannelHeader header : values()) {
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