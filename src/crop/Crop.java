package crop;

import java.util.Random;

public class Crop {

    protected String name;
    protected int growDays;
    protected int currentDays;
    protected int cost; // 원가
    protected int sellingPrice; // 판매가
    protected int extraCost;
    protected boolean isUpgrade;

    Random random = new Random();

    public String getName() {
        return name;
    }

    public int getGrowDays() {
        return growDays;
    }

    public int getCurrentDays() {
        return currentDays;
    }

    public boolean getIsUpgrade() {
        return isUpgrade;
    }

    public void grow() {
        currentDays++;
    }

    public boolean isReadyToHarvest() {
        return currentDays >= growDays;
    }

    public boolean randomlyUpgrade() {
        return random.nextInt(2) == 1;
    }

    public int calculateSellingPrice() {
        sellingPrice = cost;

         return sellingPrice;
    }

    public static class Tomato extends Vegetable {

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
}
