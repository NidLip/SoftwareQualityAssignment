package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import com.nhlstenden.JabberPoint.Slide.SlideItem;

import java.io.FileNotFoundException;

public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createBitmapItem(int level, String text) {
        try
        {
            return new BitmapItem(level, text);
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
    @Override
    public SlideItem createTextItem(int level, String text) {
        throw new UnsupportedOperationException("BitmapItemFactory can't create TextItem");
    }
}