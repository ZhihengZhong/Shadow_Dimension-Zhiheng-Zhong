import bagel.DrawOptions;
import bagel.Image;
public class Fire extends Entity {

    public Fire(int startX, int startY, Image entityImage) {
        super(startX, startY, entityImage);
    }

    int location;

    @Override
    public void update() {
        DrawOptions rotate = new DrawOptions();
        switch (location){
            case Utils.TOP_LEFT:
                break;
            case Utils.BOTTOM_LEFT :
                rotate.setRotation(3 * Math.PI / 2);
                break;
            case Utils.TOP_RIGHT:
                rotate.setRotation(Math.PI / 2);
                break;
            case Utils.BOTTOM_RIGHT :
                rotate.setRotation(Math.PI);
                break;
        }
        getEntityImage().drawFromTopLeft(position.x, position.y, rotate);

    }

    public void setLocation(int location) {
        this.location = location;
    }
}
