package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import com.nhlstenden.JabberPoint.Slide.SlideItem;

public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(int level, String text) {
        return new BitmapItem(level,"example.jpg");
    }
}