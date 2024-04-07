package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import com.nhlstenden.JabberPoint.Slide.SlideItem;

public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createBitmapItem(int level, String text) {
        return new BitmapItem(level, text);
    }
    @Override
    public SlideItem createTextItem(int level, String text) {
        throw new UnsupportedOperationException("BitmapItemFactory can't create TextItem");
    }
}