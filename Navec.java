/**
 * Class used to define Navec.
 * Enable Navec to appear accurately on the screen.
 * Draw it when it is in the Level 1.
 */
public class Navec extends Enemy {


    public Navec(int startX, int startY) {
        super(startX, startY, Utils.getNavecImages(), Utils.NAVEC_MAX_HEALTH, true);
    }

}
