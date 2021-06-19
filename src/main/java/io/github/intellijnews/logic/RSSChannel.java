package io.github.intellijnews.logic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class RSSChannel {
    private final String title;
    private final String link;
    private String rssUrl;
    private final String description;
    private final String language;
    private final String rating;
    private final String copyright;
    private final Date pubDate;
    private final Date lastBuildDate;
    private final List<String> category;
    private final String docs;
    private final long ttl;
    private final String managingEditor;
    private final String webMaster;
    private final RSSImage image;
    private final List<RSSItem> items;
}
