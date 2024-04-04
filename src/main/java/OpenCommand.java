import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class OpenCommand extends Command {
    private Frame parent;
    protected static final String IOEX = "IO Exception: ";
    protected static final String LOADERR = "Load Error";
    protected static final String TESTFILE = "test.xml";


    public OpenCommand(Presentation presentation, Frame parent) {
        super(presentation);
        this.parent = parent;
    }

    @Override
    public void execute() {
        presentation.clear();
        Accessor xmlAccessor = new XMLAccessor();
        try {
            xmlAccessor.loadFile(presentation, TESTFILE);
            presentation.setSlideNumber(0);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IOEX + exc, 
            LOADERR, JOptionPane.ERROR_MESSAGE);
        }
        parent.repaint();
    }
}