package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.SlideItem;
import com.nhlstenden.JabberPoint.Slide.TextItem;

public class TextItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(String type, int level, String text) {
        if (type.equalsIgnoreCase("Text")) {
            return new TextItem(level, text);
        } else {
            throw new UnsupportedOperationException("TextItemFactory can't create " + type);
        }
    }
}
