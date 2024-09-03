import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Wall extends Entity {
    public Wall(int startX, int startY) {
        super(startX, startY, Utils.wall);
    }
}