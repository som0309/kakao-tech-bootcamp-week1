package crop;

public class Vegetable extends Crop {

    protected static final int seedPrice = 500;
    protected int freshness;

    public static int getSeedPrice() {
        return seedPrice;
    }

    public int setFreshness() {
        freshness = random.nextInt(100) + 1;

        return freshness;
    }

    @Override
    public int calculateSellingPrice() {
        sellingPrice = cost + (cost * freshness / 100);

        return sellingPrice;
    }
}