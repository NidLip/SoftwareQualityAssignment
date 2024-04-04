public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(int level, String text) {
        return new BitmapItem(level,"example.jpg");
    }
}