
public class Demon extends Enemy {

    public Demon(int startX, int startY) {
        super(startX, startY, Utils.getDemonImages(), Utils.DEMON_MAX_HEALTH, Utils.getRandom().nextBoolean());
    }
}
