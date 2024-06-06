package com.nhlstenden.Slide;

import com.nhlstenden.JabberPoint.Factory.BitmapItemFactory;
import com.nhlstenden.JabberPoint.Factory.TextItemFactory;
import com.nhlstenden.JabberPoint.Slide.Slide;
import com.nhlstenden.JabberPoint.Slide.SlideItem;
import com.nhlstenden.JabberPoint.Slide.TextItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SlideTest {

    private Slide slide;
    private SlideItem textItem;

    @BeforeEach
    void setUp() {
        slide = new Slide("text");
        textItem = new TextItem(1, "testText");
    }

    @Test
    void testConstructor_WithValidFactoryType_SetsFactory() {
        try {
            Slide bitmapSlide = new Slide("bitmap");
            Field factoryField = Slide.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            Object factory = factoryField.get(bitmapSlide);
            assertTrue(factory instanceof BitmapItemFactory);

            Slide textSlide = new Slide("text");
            factory = factoryField.get(textSlide);
            assertTrue(factory instanceof TextItemFactory);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    void testConstructor_WithInvalidFactoryType_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Slide("invalidType");
        });
    }

    @Test
    void testAppend_WithSlideItem_AddsItem() {
        slide.append(textItem);
        assertEquals(1, slide.getSize());
        assertEquals(textItem, slide.getSlideItem(0));
    }

    @Test
    void testGetTitle_WhenCalled_ReturnsTitle() {
        String title = "Test Title";
        slide.setTitle(title);
        assertEquals(title, slide.getTitle());
    }

    @Test
    void testSetTitle_WithValidTitle_SetsTitle() {
        String title = "New Title";
        slide.setTitle(title);
        assertEquals(title, slide.getTitle());
    }

    @Test
    void testAppend_WithLevelAndMessage_CreatesAndAddsSlideItem() {
        slide.append(1, "New Message");
        assertEquals(1, slide.getSize());
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals("New Message", ((TextItem) item).getText());
    }

    @Test
    void testGetSlideItem_WithValidIndex_ReturnsSlideItem() {
        slide.append(textItem);
        SlideItem item = slide.getSlideItem(0);
        assertEquals(textItem, item);
    }

    @Test
    void testGetSlideItem_WithInvalidIndex_ReturnsNull() {
        SlideItem item = slide.getSlideItem(0);
        assertNull(item);
    }

    @Test
    void testGetAllSlideItems_WhenCalled_ReturnsAllSlideItems() {
        slide.append(textItem);
        assertEquals(1, slide.getAllSlideItems().size());
        assertEquals(textItem, slide.getAllSlideItems().get(0));
    }

    @Test
    void testGetSize_WhenCalled_ReturnsNumberOfSlideItems() {
        assertEquals(0, slide.getSize());
        slide.append(textItem);
        assertEquals(1, slide.getSize());
    }
}
