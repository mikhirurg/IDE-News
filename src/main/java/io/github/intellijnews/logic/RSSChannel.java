package io.github.intellijnews.logic;

import java.time.LocalDateTime;
import java.util.List;

public class RSSChannel {
    private String title;
    private String link;
    private String description;
    private String language;
    private String rating;
    private String copyright;
    private LocalDateTime pubDate;
    private LocalDateTime lastBuildDate;
    private String category;
    private String docs;
    private long ttl;
    private String managingEditor;
    private String webMaster;
    private RSSImage image;
    private List<RSSItem> items;
}
