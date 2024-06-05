package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.SlideItem;

public interface SlideItemFactory {
    SlideItem createSlideItem(String type, int level, String text);
}
