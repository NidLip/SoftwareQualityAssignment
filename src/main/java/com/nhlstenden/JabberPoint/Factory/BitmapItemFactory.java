package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import com.nhlstenden.JabberPoint.Slide.SlideItem;

import java.io.FileNotFoundException;

public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(String type, int level, String text) {
        if (type.equalsIgnoreCase("Bitmap")) {
            try {
                return new BitmapItem(level, text);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new UnsupportedOperationException("BitmapItemFactory can't create " + type);
        }
    }
}
