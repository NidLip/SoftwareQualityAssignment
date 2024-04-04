public abstract class BaseDecorator extends Slide {
    protected Slide wrappe;

    public BaseDecorator(Slide wrappe) {
        super("text");
        this.wrappe = wrappe;
    }
}
