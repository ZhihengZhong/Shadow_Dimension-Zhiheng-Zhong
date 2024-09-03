import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class Enemy extends Entity implements Movable {

    private Image[] images;
    Map<String, Integer> config = new HashMap<>();
    private Point prevPosition;
    private int maxHealth;
    private static double MOVE_SIZE = 2;
    private final static int FONT_SIZE = 15;
    private final Font FONT = new Font("res/frostbite.ttf", FONT_SIZE);
    private DrawOptions COLOUR = new DrawOptions();
    private Fire fire;
    private boolean isAggressive = true;
    private int direction = Utils.RIGHT_DIRECTION;
    private boolean isInvincible;
    private int invincibleTime;
    private int healthPoints;
    private Image currentImage;
    private boolean facingRight;
    private final double x = 0.2;
    private final double y = 0.7;


    public Enemy(int startX, int startY, Image[] images, int maxHealth, boolean isAggressive) {
        super(startX, startY, images[0]);
        this.images = images;
        this.config.put("Demon", 150);
        this.config.put("Navec", 200);
        this.config.put("NavecDamage", 20);
        this.config.put("DemonDamage", 10);
        this.MOVE_SIZE = x + Math.random() * y % (y - x + 1);
        this.fire = new Fire(0, 0, images[Utils.Fire]);
        this.maxHealth = maxHealth;
        this.healthPoints = maxHealth;
        this.COLOUR.setBlendColour(Utils.GREEN);
        this.isAggressive = isAggressive;
        this.facingRight = true;
        if (this.isAggressive) {
            this.direction = Utils.getRandom().nextInt(Utils.DOWN_DIRECTION);
        }
    }


    public void showFire(Player fae) {
        int distance = this.config.get(getClass().getSimpleName());
        if (fae.getBoundingBox().intersects(new Rectangle(getBoundingBox().centre().x
                - distance / 2, getBoundingBox().centre().y - distance / 2, distance, distance))) {
            double X = fae.getBoundingBox().centre().x - getBoundingBox().centre().x;
            double Y = fae.getBoundingBox().centre().y - getBoundingBox().centre().y;
            if (X <= 0 && Y <= 0) {
                fire.setPosition(new Point(getBoundingBox().topLeft().x - fire.getEntityImage().getWidth(), getBoundingBox().topLeft().y - fire.getEntityImage().getHeight()));
                fire.setLocation(Utils.TOP_LEFT);
            } else if (X <= 0 && Y > 0) {
                fire.setPosition(new Point(getBoundingBox().bottomLeft().x - fire.getEntityImage().getWidth(), getBoundingBox().bottomLeft().y));
                fire.setLocation(Utils.BOTTOM_LEFT);
            } else if (X > 0 && Y <= 0) {
                fire.setPosition(new Point(getBoundingBox().topRight().x, getBoundingBox().topRight().y - fire.getEntityImage().getHeight()));
                fire.setLocation(Utils.TOP_RIGHT);
            } else {
                fire.setPosition(getBoundingBox().bottomRight());
                fire.setLocation(Utils.BOTTOM_RIGHT);
            }
            if (fae.canDamage() && fire.getBoundingBox().intersects(fae.getBoundingBox())) {
                int damage = this.config.get(getClass().getSimpleName() + "Damage");
                fae.setHealthPoints(Math.max(fae.getHealthPoints() - damage, 0));
                fae.setInvincible(true);
                System.out.println(getClass().getSimpleName() + " inflicts " + damage + " damage points on Fae. " +
                        "Fae's current health: " + fae.getHealthPoints() + "/" + fae.getMaxHealthPoints());
            }
            fire.update();
        } else {

        }
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

    public void update(ShadowDimension gameObject) {
        if (!isDead()) {
            invincibleTime--;
            if (invincibleTime < 0) {
                isInvincible = false;
            }
            if (isAggressive) {
                if (prevPosition != null && prevPosition.equals(position)) {
                    direction = -direction;
                }
                setPrevPosition();
                double moveSize = gameObject.getTimeScaleControl().getSpeed(MOVE_SIZE);
                switch (direction) {
                    case Utils.RIGHT_DIRECTION:
                        move(moveSize, 0);
                        if (!facingRight) {
                            this.currentImage = images[Utils.RIGHT];
                            facingRight = !facingRight;
                        }
                        break;
                    case Utils.LEFT_DIRECTION:
                        move(-moveSize, 0);
                        if (facingRight) {
                            this.currentImage = images[Utils.LEFT];
                            facingRight = !facingRight;
                        }
                        break;
                    case Utils.DOWN_DIRECTION:
                        move(0, moveSize);
                        break;
                    case Utils.UP_DIRECTION:
                        move(0, -moveSize);
                        break;
                }
            }
            showFire(gameObject.getPlayer());
            gameObject.checkCollisionsAndOutOfBounds(this);
            if (prevPosition != null && prevPosition.equals(position)) {
                switch (direction) {
                    case Utils.RIGHT_DIRECTION:
                        direction = Utils.LEFT_DIRECTION;
                        facingRight = true;
                        break;
                    case Utils.LEFT_DIRECTION:
                        direction = Utils.RIGHT_DIRECTION;
                        facingRight = false;
                        break;
                    case Utils.DOWN_DIRECTION:
                        direction = Utils.UP_DIRECTION;
                        break;
                    case Utils.UP_DIRECTION:
                        direction = Utils.DOWN_DIRECTION;
                        break;
                }
            }
            if (isInvincible()) {
                if (facingRight) {
                    this.currentImage = images[Utils.InvincibleRight];
                } else {
                    this.currentImage = images[Utils.InvincibleLeft];
                }
            } else {
                if (facingRight) {
                    this.currentImage = images[Utils.RIGHT];
                } else {
                    this.currentImage = images[Utils.LEFT];
                }
            }
            setEntityImage(this.currentImage);
            super.update();
            renderHealthPoints();
        }
    }


    /**
     * Method that renders the current health as a percentage on screen
     */
    private void renderHealthPoints() {
        double percentageHP = ((double) healthPoints / maxHealth) * 100;
        if (percentageHP <= Utils.RED_BOUNDARY) {
            COLOUR.setBlendColour(Utils.RED);
        } else if (percentageHP <= Utils.ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(Utils.ORANGE);
        }
        FONT.drawString(Math.round(percentageHP) + "%", position.x, position.y - 6, COLOUR);
    }

    /**
     * Method that checks if Fae's health has depleted
     */
    public boolean isDead() {
        return healthPoints <= 0;
    }


    /**
     * Method that moves Fae given the direction
     */
    private void move(double xMove, double yMove) {
        double newX = position.x + xMove;
        double newY = position.y + yMove;
        this.position = new Point(newX, newY);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
        setInvincible(true);
        invincibleTime = 180;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;

    }

    public void setAggressive(boolean aggressive) {
        isAggressive = aggressive;
    }
}