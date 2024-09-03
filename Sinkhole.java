import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Sinkhole extends Entity {
    private final static int DAMAGE_POINTS = 30;
    private boolean isActive;

    public Sinkhole(int startX, int startY) {
        super(startX, startY, Utils.sinkhole);
        this.isActive = true;
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if (isActive) {
            super.update();
        }
    }

    public int getDamagePoints() {
        return DAMAGE_POINTS;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}