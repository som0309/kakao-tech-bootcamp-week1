package crop;

public class Tomato extends Vegetable {

    public boolean isSauce() {
        return randomlyUpgrade();
    }

    public Tomato() {
        name = "토마토";
        growDays = 1;
        currentDays = 0;
        cost = 600;
        extraCost = 1500;
    }

    @Override
    public int calculateSellingPrice() {
        super.calculateSellingPrice();

        if (isSauce()) {
            isUpgrade = true;
            sellingPrice = sellingPrice + extraCost;
        }

        return sellingPrice;
    }
}
