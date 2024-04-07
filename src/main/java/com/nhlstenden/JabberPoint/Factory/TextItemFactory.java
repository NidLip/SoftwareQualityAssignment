package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.SlideItem;
import com.nhlstenden.JabberPoint.Slide.TextItem;

public class TextItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createTextItem(int level, String text) {
        return new TextItem(level,text);
    }
    @Override
    public SlideItem createBitmapItem(int level, String text) {
        throw new UnsupportedOperationException("BitmapItemFactory can't create TextItem");
    }
}
