package Slide;

import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
public class BitmapItemTest
{

    @Test
    public void testBitmapItem_CompareLevelAndImage_Valid() throws FileNotFoundException
    {
        int level = 1;
        String name = "JabberPoint.jpg";
        BitmapItem bitmapItem = new BitmapItem(level, name);

        assertEquals(1, bitmapItem.getLevel());
        assertEquals("JabberPoint.jpg", bitmapItem.getName());
    }

    @Test
    public void testBitmapItem_CompareImage_ThrowsFileNotFoundException() throws FileNotFoundException
    {
        int level = 1;
        String nonExistentImageName = "nonExistentImage.png";

        BitmapItem bitmapItem = new BitmapItem(level, nonExistentImageName);
        assertNull(bitmapItem.getBufferedImage());
    }
}
