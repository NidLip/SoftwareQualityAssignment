package Slide;

import com.nhlstenden.JabberPoint.Slide.TextItem;
import org.junit.jupiter.api.Test;
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
    public void testTextItem_compareLevelAndText_ThrowsIllegalArgumentException() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> {
            int level = -1;
            String text = "testText";
            TextItem textItem = new TextItem(level, text);
            textItem.getLevel();
        });
    }
}