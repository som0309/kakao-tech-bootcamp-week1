package crop;

public class Banana extends Fruit {

    public boolean isSmoothie() {
        return randomlyUpgrade();
    }

    public Banana() {
        name = "바나나";
        growDays = 4;
        currentDays = 0;
        cost = 1000;
        extraCost = 3000;
    }

    @Override
    public int calculateSellingPrice() {
        super.calculateSellingPrice();

        if (isSmoothie()) {
            isUpgrade = true;
            sellingPrice = sellingPrice + extraCost;
        }

        return sellingPrice;
    }
}
