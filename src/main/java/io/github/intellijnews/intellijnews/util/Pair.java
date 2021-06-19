package io.github.intellijnews.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Pair<U, V> {
    private final U first;
    private final V second;
}
