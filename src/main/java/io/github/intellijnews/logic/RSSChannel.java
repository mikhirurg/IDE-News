package io.github.intellijnews.logic;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class RSSChannel {
    private final String title;
    private final String link;
    private final String description;
    private final String language;
    private final String rating;
    private final String copyright;
    private final LocalDateTime pubDate;
    private final LocalDateTime lastBuildDate;
    private final String category;
    private final String docs;
    private final long ttl;
    private final String managingEditor;
    private final String webMaster;
    private final RSSImage image;
    private final List<RSSItem> items;
}
