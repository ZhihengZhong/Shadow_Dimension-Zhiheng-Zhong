import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Colour;

import java.util.Random;

public class Utils {

    static Image wall = new Image("res/wall.png");


    static Image tree = new Image("res/tree.png");
    static Image sinkhole = new Image("res/sinkhole.png");
    static Image navecFire = new Image("res/navec/navecFire.png");
    static Image navecInvincibleLeft = new Image("res/navec/navecInvincibleLeft.PNG");
    static Image navecInvincibleRight = new Image("res/navec/navecInvincibleRight.PNG");
    static Image navecLeft = new Image("res/navec/navecLeft.png");
    static Image navecRight = new Image("res/navec/navecRight.png");

    static int RIGHT = 0;
    static int LEFT = 2;
    static int Fire = 1;
    static int InvincibleLeft = 5;
    static int InvincibleRight = 4;
    static int AttackLeft = 3;
    static int AttackRight = 4;

    public static Image[] getNavecImages() {
        return new Image[]{navecRight, navecFire, navecLeft, navecRight, navecInvincibleLeft, navecInvincibleRight};
    }


    static Image faeRight = new Image("res/fae/faeRight.png");
    static Image faeLeft = new Image("res/fae/faeLeft.png");
    static Image faeAttackRight = new Image("res/fae/faeAttackRight.png");
    static Image faeAttackLeft = new Image("res/fae/faeAttackLeft.png");

    public static Image[] getFaeImages() {
        return new Image[]{faeRight, faeRight, faeLeft, faeAttackLeft, faeAttackRight};
    }

    static Image demonLeft = new Image("res/demon/demonLeft.png");
    static Image demonRight = new Image("res/demon/demonRight.png");
    static Image demonInvincibleRight = new Image("res/demon/demonInvincibleRight.PNG");
    static Image demonInvincibleLeft = new Image("res/demon/demonInvincibleLeft.PNG");
    static Image demonFire = new Image("res/demon/demonFire.png");

    public static Image[] getDemonImages() {
        return new Image[]{demonRight, demonFire, demonLeft, demonRight, demonInvincibleRight, demonInvincibleLeft};
    }


    final static int ORANGE_BOUNDARY = 65;
    final static int RED_BOUNDARY = 35;
    final static Colour GREEN = new Colour(0, 0.8, 0.2);
    final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    final static Colour RED = new Colour(1, 0, 0);

    final static int LEFT_DIRECTION = 0;
    final static int RIGHT_DIRECTION = 1;
    final static int UP_DIRECTION = 2;
    final static int DOWN_DIRECTION = 3;

    final static int TOP_LEFT = 1;
    final static int BOTTOM_LEFT = 2;
    final static int TOP_RIGHT = 3;
    final static int BOTTOM_RIGHT = 4;
    final static int DEMON_MAX_HEALTH = 40;
    final static int NAVEC_MAX_HEALTH = 80;

    static Random random = new Random();

    public static Random getRandom() {
        return random;
    }
}
