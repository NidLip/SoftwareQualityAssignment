package main;

public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(int level) {
        return new BitmapItem(level,"example.jpg");
    }
}