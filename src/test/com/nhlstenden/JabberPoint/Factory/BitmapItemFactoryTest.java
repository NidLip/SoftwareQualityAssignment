package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Factory.BitmapItemFactory;
import com.nhlstenden.JabberPoint.Slide.BitmapItem;
import com.nhlstenden.JabberPoint.Slide.SlideItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitmapItemFactoryTest {

    private final BitmapItemFactory factory = new BitmapItemFactory();

    @Test
    void testCreateSlideItem_WithValidType_ReturnsBitmapItem() {
        SlideItem item = factory.createSlideItem("Bitmap", 1, "JabberPoint.jpg");
        assertNotNull(item);
        assertTrue(item instanceof BitmapItem);
        assertEquals(1, item.getLevel());
        assertEquals("JabberPoint.jpg", ((BitmapItem) item).getName());
    }

    @Test
    void testCreateSlideItem_WithInvalidType_ThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            factory.createSlideItem("InvalidType", 1, "testText");
        });
    }
}
