import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SWEN20003 Project 1, Semester 2, 2022
 *
 * @author Tharun Dharmawickrema
 *
 * SWEN20003 Project 2B, Semester 2, 2022
 *
 * @author Zhiheng Zhong
 */
public class ShadowDimension extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private String[] WORLD_FILES = new String[]{"res/level0.csv", "res/level1.csv"};
    private final Image BACKGROUND_IMAGE[] = {new Image("res/background0.png"), new Image("res/background1.png")};

    private final static int TITLE_FONT_SIZE = 75;
    private final static int INSTRUCTION_FONT_SIZE = 40;
    private final static int[] TITLE_X = new int[]{260, 350};
    private final static int[] TITLE_Y = new int[]{250, 350};
    private final static int INS_X_OFFSET = 90;
    private final static int INS_Y_OFFSET = 190;
    private final Font TITLE_FONT = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
    private final Font INSTRUCTION_FONT = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
    private final static String INSTRUCTION_MESSAGE[] = new String[]{"PRESS SPACE TO START\nUSE ARROW KEYS TO FIND GATE", "PRESS SPACE TO START\nPRESS A TO ATTACK\nDEFEAT NAVEC TO WIN"};
    private final static String END_MESSAGE = "GAME OVER!";
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private final static String LEVEL_COMPLETE_MESSAGE = "LEVEL COMPLETE!";

    private final static int WALL_ARRAY_SIZE = 52;
    private final static int S_HOLE_ARRAY_SIZE = 5;
    private final static List<Wall> walls = new ArrayList<>();
    private final static List<Sinkhole> sinkholes = new ArrayList<>();
    private final static List<Tree> trees = new ArrayList<>();
    private final static List<Demon> demons = new ArrayList<>();
    private static Navec navec;
    private Point topLeft;
    private Point bottomRight;
    private Player player;
    private boolean hasStarted;
    private boolean gameOver;
    private boolean playerWin;
    private int level = 0;
    private int leveUpTime = 180;
    private TimeScaleControl timeScaleControl;

    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV();
        hasStarted = false;
        gameOver = false;
        playerWin = false;
        timeScaleControl = new TimeScaleControl();
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(WORLD_FILES[level]))) {

            String line;
            walls.clear();
            sinkholes.clear();
            trees.clear();
            demons.clear();
            navec = null;
            while ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Fae":
                        player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "Wall":
                        walls.add(new Wall(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "Sinkhole":
                        sinkholes.add(new Sinkhole(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2])));
                        break;
                    case "Navec":
                        navec = new Navec(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        break;
                    case "Tree":
                        trees.add(new Tree(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2])));
                        break;
                    case "Demon":
                        demons.add(new Demon(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2])));
                        break;
                    case "TopLeft":
                        topLeft = new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "BottomRight":
                        bottomRight = new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (input.wasPressed(Keys.W) && level == 0) {
            level++;
            readCSV();
        }

        if (input.wasPressed(Keys.L)) {
            timeScaleControl.increase();
        }
        if (input.wasPressed(Keys.K)) {
            timeScaleControl.decrease();
        }


        if (!hasStarted) {
            drawStartScreen();
            if (input.wasPressed(Keys.SPACE)) {
                hasStarted = true;
            }
        }

        if (gameOver) {
            drawMessage(END_MESSAGE);
        } else if (playerWin) {
            if (level == 0) {
                level++;
                readCSV();
                playerWin = false;
                hasStarted = false;
            } else {
                drawMessage(WIN_MESSAGE);
            }
        }

        // game is running
        if (hasStarted && !gameOver && !playerWin) {
            BACKGROUND_IMAGE[level].draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

            for (Wall current : walls) {
                current.update();
            }
            for (Sinkhole current : sinkholes) {
                current.update();
            }
            for (Tree tree : trees) {
                tree.update();
            }

            for (Demon demon : demons) {
                demon.update(this);
            }

            if (navec != null) {
                navec.update(this);
            }
            player.update(input, this);

            if (player.isDead()) {
                gameOver = true;
            }

            checkWin();
        }
    }

    /**
     * Method that checks for collisions between Fae and the other entities, and performs
     * corresponding actions.
     */
    public void checkCollisions(Player player) {
        Rectangle faeBox = new Rectangle(player.getPosition(), player.getCurrentImage().getWidth(),
                player.getCurrentImage().getHeight());
        List<Entity> pauseEntity = new ArrayList<>();
        pauseEntity.addAll(walls);
        pauseEntity.addAll(trees);
        for (Entity current : pauseEntity) {
            Rectangle wallBox = current.getBoundingBox();
            if (faeBox.intersects(wallBox)) {
                player.moveBack();
            }
        }

        for (Sinkhole hole : sinkholes) {
            Rectangle holeBox = hole.getBoundingBox();
            if (hole.isActive() && faeBox.intersects(holeBox)) {
                player.setHealthPoints(Math.max(player.getHealthPoints() - hole.getDamagePoints(), 0));
                player.moveBack();
                hole.setActive(false);
                System.out.println("Sinkhole inflicts " + hole.getDamagePoints() + " damage points on Fae. " +
                        "Fae's current health: " + player.getHealthPoints() + "/" + Player.getMaxHealthPoints());
            }
        }
    }


    public void checkCollisionsAndOutOfBounds(Enemy enemy) {
        List<Entity> pauseEntity = new ArrayList<>();
        pauseEntity.addAll(walls);
        pauseEntity.addAll(trees);
        pauseEntity.addAll(sinkholes);
        for (Entity current : pauseEntity) {
            Rectangle wallBox = current.getBoundingBox();
            if (enemy.getBoundingBox().intersects(wallBox)) {
                enemy.moveBack();
            }
        }

        if (player.isAttack() && enemy.getBoundingBox().intersects(player.getBoundingBox())) {
            if (!enemy.isDead()) {
                if (player.isAttack() && !enemy.isInvincible()) {
                    enemy.setHealthPoints(Math.max(enemy.getHealthPoints() - player.DAMAGE_POINTS, 0));
                    System.out.println("Fae " + player.DAMAGE_POINTS + " damage points on " + enemy.getClass().getSimpleName() + ". " +
                            enemy.getClass().getSimpleName() + "'s current health: " + enemy.getHealthPoints() + "/" + enemy.getMaxHealth());
                }
            }
        }

        Point currentPosition = enemy.getPosition();
        if ((currentPosition.y > bottomRight.y) || (currentPosition.y < topLeft.y) || (currentPosition.x < topLeft.x)
                || (currentPosition.x > bottomRight.x)) {
            enemy.moveBack();
        }
    }

    /**
     * Method that checks if Fae has gone out-of-bounds and performs corresponding action
     */
    public void checkOutOfBounds(Player player) {
        Point currentPosition = player.getPosition();
        if ((currentPosition.y > bottomRight.y) || (currentPosition.y < topLeft.y) || (currentPosition.x < topLeft.x)
                || (currentPosition.x > bottomRight.x)) {
            player.moveBack();
        }
    }

    /**
     * Method used to draw the start screen title and instructions
     */
    private void drawStartScreen() {
        if (level == 0) {
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X[level], TITLE_Y[level]);
            INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE[level], TITLE_X[level] + INS_X_OFFSET, TITLE_Y[level] + INS_Y_OFFSET);
        } else {
            if (leveUpTime > 0) {
                leveUpTime--;
                drawMessage(LEVEL_COMPLETE_MESSAGE);
            } else {
                INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE[level], TITLE_X[level], TITLE_X[level]);
            }
        }
    }

    /**
     * Method used to draw end screen messages
     */
    private void drawMessage(String message) {
        TITLE_FONT.drawString(message, (Window.getWidth() / 2.0 - (TITLE_FONT.getWidth(message) / 2.0)),
                (Window.getHeight() / 2.0 + (TITLE_FONT_SIZE / 2.0)));
    }

    public Player getPlayer() {
        return player;
    }

    public void checkWin() {
        if (level == 0 && player.reachedGate()) {
            playerWin = true;
        } else if (level == 1 && navec.isDead()) {
            playerWin = true;
        }
    }

    public TimeScaleControl getTimeScaleControl() {
        return timeScaleControl;
    }

}