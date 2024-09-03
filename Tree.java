import bagel.Image;
/**
 * Class used to define Tree.
 * Enable Tree to appear accurately on the screen.
 * Draw it when it is in the Level 1, Fae cannot go through this picture.
 */
public class Tree extends Entity {
    public Tree(int startX, int startY) {
        super(startX, startY, Utils.tree);
    }
}