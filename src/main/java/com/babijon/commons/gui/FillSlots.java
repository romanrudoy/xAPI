package com.babijon.commons.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum FillSlots {

    SMALL(Arrays.asList(10,11,12,13,14,15,16)),
    MEDIUM(Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25)),
    BIG(Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34)),
    BIGGEST(Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43))

    ;

    private final @Getter List<Integer> slots;
}
