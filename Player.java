import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player {
    private final static Image[] images = Utils.getFaeImages();
    private final static int MAX_HEALTH_POINTS = 100;
    private final static double MOVE_SIZE = 18;
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;
    final static int DAMAGE_POINTS = 100;

    private final static int HEALTH_X = 20;
    private final static int HEALTH_Y = 25;
    private final static int FONT_SIZE = 30;
    private final Font FONT = new Font("res/frostbite.ttf", FONT_SIZE);

    private final static DrawOptions COLOUR = new DrawOptions();

    private Point position;
    private Point prevPosition;
    private int healthPoints;
    private Image currentImage;
    private boolean facingRight;
    private boolean isInvincible;
    private int invincibleTime;
    private int attackTime;
    private int idleTime;
    private boolean isAttack;

    public Player(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.healthPoints = MAX_HEALTH_POINTS;
        this.currentImage = images[Utils.RIGHT];
        this.facingRight = true;
        COLOUR.setBlendColour(Utils.GREEN);
    }

    /**
     * Method that performs state update
     */
    public void update(Input input, ShadowDimension gameObject) {
        attackTime--;
        invincibleTime--;
        idleTime--;
        if (attackTime < 0 && isAttack) {
            isAttack = false;
        }
        if (invincibleTime < 0) {
            isInvincible = false;
        }
        if (input.isDown(Keys.UP)) {
            setPrevPosition();
            move(0, -MOVE_SIZE);
        } else if (input.isDown(Keys.DOWN)) {
            setPrevPosition();
            move(0, MOVE_SIZE);
        } else if (input.isDown(Keys.LEFT)) {
            setPrevPosition();
            move(-MOVE_SIZE, 0);
            if (facingRight) {
                facingRight = !facingRight;
            }
        } else if (input.isDown(Keys.RIGHT)) {
            setPrevPosition();
            move(MOVE_SIZE, 0);
            if (!facingRight) {
                facingRight = !facingRight;
            }
        }
        if (input.isDown(Keys.A) && idleTime < 0) {
            isAttack = true;
            attackTime = 60;
            idleTime = 120;

        }
        if (isAttack()) {
            if (facingRight) {
                this.currentImage = images[Utils.AttackRight];
            } else {
                this.currentImage = images[Utils.AttackLeft];
            }
        } else {
            if (facingRight) {
                this.currentImage = images[Utils.RIGHT];
            } else {
                this.currentImage = images[Utils.LEFT];
            }
        }
        this.currentImage.drawFromTopLeft(position.x, position.y);
        gameObject.checkCollisions(this);
        renderHealthPoints();
        gameObject.checkOutOfBounds(this);
    }

    /**
     * Method that stores Fae's previous position
     */
    private void setPrevPosition() {
        this.prevPosition = new Point(position.x, position.y);
    }

    /**
     * Method that moves Fae back to previous position
     */
    public void moveBack() {
        this.position = prevPosition;
    }

    /**
     * Method that moves Fae given the direction
     */
    private void move(double xMove, double yMove) {
        double newX = position.x + xMove;
        double newY = position.y + yMove;
        this.position = new Point(newX, newY);
    }

    /**
     * Method that renders the current health as a percentage on screen
     */
    private void renderHealthPoints() {
        double percentageHP = ((double) healthPoints / MAX_HEALTH_POINTS) * 100;
        if (percentageHP <= Utils.RED_BOUNDARY) {
            COLOUR.setBlendColour(Utils.RED);
        } else if (percentageHP <= Utils.ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(Utils.ORANGE);
        }
        FONT.drawString(Math.round(percentageHP) + "%", HEALTH_X, HEALTH_Y, COLOUR);
    }

    /**
     * Method that checks if Fae's health has depleted
     */
    public boolean isDead() {
        return healthPoints <= 0;
    }

    /**
     * Method that checks if Fae has found the gate
     */
    public boolean reachedGate() {
        return (this.position.x >= WIN_X) && (this.position.y >= WIN_Y);
    }

    public Point getPosition() {
        return position;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public static int getMaxHealthPoints() {
        return MAX_HEALTH_POINTS;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(position, currentImage.getWidth(), currentImage.getHeight());
    }

    public boolean canDamage() {
        return !isDead() && !isInvincible();
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
        if (invincible) {
            invincibleTime = 180;
        }
    }

    public boolean isAttack() {
        return isAttack;
    }
}