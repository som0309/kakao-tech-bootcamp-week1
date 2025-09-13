package crop;

public class Fruit extends Crop {

    protected static final int seedPrice = 800;
    protected int sweetness;

    public static int getSeedPrice() {
        return seedPrice;
    }

    public int setSweetness() {
        sweetness = random.nextInt(100) + 1;

        return sweetness;
    }

    @Override
    public int calculateSellingPrice() {
        sellingPrice = cost + (cost * sweetness / 100);

        return sellingPrice;
    }
}
