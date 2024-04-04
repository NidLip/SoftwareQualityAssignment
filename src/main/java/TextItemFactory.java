public class TextItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(int level, String text) {
        return new TextItem(level,"example");
    }
}
