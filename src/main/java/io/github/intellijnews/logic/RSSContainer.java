package io.github.intellijnews.logic;

import io.github.intellijnews.logic.RSSChannel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RSSContainer {
    private final List<RSSChannel> channels;
}
