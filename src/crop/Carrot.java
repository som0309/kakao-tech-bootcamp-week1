package crop;

public class Carrot extends Vegetable {

    public boolean isJuice() {
        return randomlyUpgrade();
    }

    public Carrot() {
        name = "당근";
        growDays = 2;
        currentDays = 0;
        cost = 800;
        extraCost = 1000;
    }

    @Override
    public int calculateSellingPrice() {
        super.calculateSellingPrice();

        if (isJuice()) {
            isUpgrade = true;
            sellingPrice = sellingPrice + extraCost;
        }

        return sellingPrice;
    }
}
