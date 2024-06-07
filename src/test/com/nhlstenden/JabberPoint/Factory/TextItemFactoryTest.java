package com.nhlstenden.JabberPoint.Factory;

import com.nhlstenden.JabberPoint.Factory.TextItemFactory;
import com.nhlstenden.JabberPoint.Slide.SlideItem;
import com.nhlstenden.JabberPoint.Slide.TextItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextItemFactoryTest {

    private final TextItemFactory factory = new TextItemFactory();

    @Test
    void testCreateSlideItem_WithValidType_ReturnsTextItem() {
        SlideItem item = factory.createSlideItem("Text", 1, "testText");
        assertNotNull(item);
        assertTrue(item instanceof TextItem);
        assertEquals(1, item.getLevel());
        assertEquals("testText", ((TextItem) item).getText());
    }

    @Test
    void testCreateSlideItem_WithInvalidType_ThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            factory.createSlideItem("InvalidType", 1, "testText");
        });
    }
}
