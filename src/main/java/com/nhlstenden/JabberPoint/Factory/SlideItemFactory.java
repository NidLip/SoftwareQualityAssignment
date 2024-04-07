package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Slide.SlideItem;

public interface SlideItemFactory {
    SlideItem createTextItem(int level, String text);
    SlideItem createBitmapItem(int level, String text);
}

