/**
 * Class used to define the movement speed of the enemies.
 * Each changes in timescale makes the movement speed of
 * the enemies decrease or increase by 50%.
 * Operate it when it is in the Level 1.
 */
public class TimeScaleControl {
    private int timeScale = 0;


    public void increase() {
        if (timeScale < 3) {
            timeScale++;
            System.out.println("Sped up, Speed: " + timeScale);
        }
    }

    public void decrease() {
        if (timeScale > -3) {
            timeScale--;
            System.out.println("Slowed down, Speed: " + timeScale);
        }
    }

    public double getSpeed(double moveSize) {
        if (timeScale < 0) {
            for (int i = 0; i < Math.abs(timeScale); i++) {
                moveSize *= 0.5;
            }
        } else if (timeScale > 0) {
            for (int i = 0; i < Math.abs(timeScale); i++) {
                moveSize *= 1.5;
            }
        }
        return moveSize;
    }
}
