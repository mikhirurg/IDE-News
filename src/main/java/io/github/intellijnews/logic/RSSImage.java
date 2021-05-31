package io.github.intellijnews.logic;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RSSImage {
    private final String title;
    private final String url;
    private final String link;
    private final long width;
    private final long height;
    private final String description;
}
