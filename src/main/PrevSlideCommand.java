public class PrevSlideCommand extends Command {
    public PrevSlideCommand(Presentation presentation) {
        super(presentation);
    }

    @Override
    public void execute() {
        presentation.prevSlide();
    }
}