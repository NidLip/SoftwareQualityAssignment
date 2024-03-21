package main;

public class TextItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(int level) {
        return new TextItem(level,"example");
    }
}
