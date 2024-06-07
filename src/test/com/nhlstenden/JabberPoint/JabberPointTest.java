package com.nhlstenden.JabberPoint;

import com.nhlstenden.JabberPoint.Presentation.Presentation;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class JabberPointTest
{

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp()
    {
        System.setErr(new PrintStream(errContent));
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown()
    {
        System.setErr(originalErr);
        System.setOut(originalOut);
    }

    @Test
    void testMain_NoArguments_LoadsDemoPresentation() throws IOException
    {
        // Run the main method without arguments
        JabberPoint.main(new String[]{});

        // Validate that the demo presentation is loaded
        Presentation presentation = Presentation.getInstance();
        assertNotNull(presentation);
        assertEquals(0, presentation.getSlideNumber());
    }

    @Test
    void testMain_WithArguments_LoadsFile() throws IOException
    {
        // Create a temporary XML file for testing
        String xml = "<presentation><showtitle>Test Presentation</showtitle><slide><title>Slide Title</title><item level=\"1\" kind=\"text\">Item Text</item></slide></presentation>";
        java.io.File tempFile = java.nio.file.Files.createTempFile("test", ".xml").toFile();
        try (java.io.PrintWriter out = new java.io.PrintWriter(tempFile))
        {
            out.println(xml);
        }

        // Run the main method with the file path argument
        JabberPoint.main(new String[]{tempFile.getAbsolutePath()});

        // Validate that the presentation is loaded from the file
        Presentation presentation = Presentation.getInstance();
        assertNotNull(presentation);
        assertEquals("Test Presentation", presentation.getTitle());
        assertEquals(0, presentation.getSlideNumber());

        // Delete the temporary file
        tempFile.delete();
    }
}
