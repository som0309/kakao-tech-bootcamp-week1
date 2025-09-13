package crop;

public class Apple extends Fruit {

    public boolean isJam() {
        return randomlyUpgrade();
    }

    public Apple() {
        name = "사과";
        growDays = 5;
        currentDays = 0;
        cost = 1200;
        extraCost = 1500;
    }

    @Override
    public int calculateSellingPrice() {
        super.calculateSellingPrice();

        if (isJam()) {
            isUpgrade = true;
            sellingPrice = sellingPrice + extraCost;
        }

        return sellingPrice;
    }
}
