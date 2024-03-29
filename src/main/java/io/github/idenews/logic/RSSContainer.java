package io.github.idenews.logic;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RSSContainer {
    private final List<RSSChannel> channels;
}
