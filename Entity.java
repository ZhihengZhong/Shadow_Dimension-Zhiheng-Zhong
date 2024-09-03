import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Entity {
    private Image entityImage;
    Point position;

    public Entity(int startX, int startY, Image entityImage) {
        this.position = new Point(startX, startY);
        this.entityImage = entityImage;
    }

    /**
     * Method that performs state update
     */
    public void update() {
        entityImage.drawFromTopLeft(this.position.x, this.position.y);
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(position, entityImage.getWidth(), entityImage.getHeight());
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setEntityImage(Image entityImage) {
        this.entityImage = entityImage;
    }

    public Image getEntityImage() {
        return entityImage;
    }

    public Point getPosition() {
        return position;
    }
}