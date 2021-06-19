package io.github.intellijnews.logic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class RSSItem {
    private final String title;
    private final String link;
    private final String description;
    private final String author;
    private final List<String> category;
    private final String comments;
    private final Date pubDate;
    private RSSChannel channel;
}
