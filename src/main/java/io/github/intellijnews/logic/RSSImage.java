package io.github.intellijnews.logic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RSSImage {
    private final String title;
    private final String url;
    private final String link;
    private final long width;
    private final long height;
    private final String description;
}
