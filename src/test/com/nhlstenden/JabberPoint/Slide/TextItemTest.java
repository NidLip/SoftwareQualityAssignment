package com.nhlstenden.JabberPoint.Slide;

import com.nhlstenden.JabberPoint.Slide.TextItem;
import com.nhlstenden.Style.Style;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import static org.junit.jupiter.api.Assertions.*;

public class TextItemTest {

    @Test
    public void testTextItem_compareLevelAndText_Valid() {
        int level = 1;
        String text = "testText";
        TextItem textItem = new TextItem(level, text);

        assertEquals(level, textItem.getLevel());
        assertEquals(text, textItem.getText());
    }

    @Test
    public void testTextItem_compareLevelAndText_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            int level = -1;
            String text = "testText";
            new TextItem(level, text);
        });
    }

    @Test
    public void testGetText_WithValidText_ReturnsText() {
        String text = "testText";
        TextItem textItem = new TextItem(1, text);

        assertEquals(text, textItem.getText());
    }

    @Test
    public void testGetText_WithNullText_ReturnsEmptyString() {
        TextItem textItem = new TextItem(1, null);

        assertEquals("No Text Given", textItem.getText());
    }

    @Test
    public void testSetText_WithValidText_ChangesText() {
        TextItem textItem = new TextItem(1, "initialText");
        String newText = "newText";
        textItem.setText(newText);

        assertEquals(newText, textItem.getText());
    }

    @Test
    public void testGetAttributedString_WithValidStyle_ReturnsAttributedString() {
        TextItem textItem = new TextItem(1, "testText");
        Style style = new Style(10, Color.BLACK, 12, 10);
        float scale = 1.0f;

        AttributedString attributedString = textItem.getAttributedString(style, scale);

        assertNotNull(attributedString);
        AttributedCharacterIterator iterator = attributedString.getIterator();
        assertEquals(style.getFont(scale), iterator.getAttribute(TextAttribute.FONT));
    }

    @Test
    public void testGetBoundingBox_WithValidGraphicsAndStyle_ReturnsRectangle() {
        TextItem textItem = new TextItem(1, "testText");
        Style style = new Style(10, Color.BLACK, 12, 10);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        float scale = 1.0f;

        Rectangle boundingBox = textItem.getBoundingBox(graphics, null, scale, style);

        assertNotNull(boundingBox);
        assertTrue(boundingBox.width > 0);
        assertTrue(boundingBox.height > 0);
    }

    @Test
    public void testSetText_WithEmptyText_SetsNoTextGiven() {
        TextItem textItem = new TextItem(1, "");
        assertEquals("No Text Given", textItem.getText());
    }

    @Test
    public void testGetAttributedString_WithEmptyText_ReturnsAttributedStringWithDefaultText() {
        TextItem textItem = new TextItem(1, "");
        Style style = new Style(10, Color.BLACK, 12, 10);
        float scale = 1.0f;

        AttributedString attributedString = textItem.getAttributedString(style, scale);

        assertNotNull(attributedString);
        AttributedCharacterIterator iterator = attributedString.getIterator();
        int endIndex = iterator.getEndIndex();
        StringBuilder resultText = new StringBuilder();
        for (char c = iterator.first(); c != AttributedCharacterIterator.DONE; c = iterator.next()) {
            resultText.append(c);
        }
        assertEquals("No Text Given", resultText.toString());
    }
}
