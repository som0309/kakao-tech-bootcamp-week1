package game;

public class FarmTime implements Runnable{

    private final FarmGame farmGame;
    private int day = 1;

    public FarmTime(FarmGame farmGame) {
        this.farmGame = farmGame;
    }

    @Override
    public synchronized void run() {

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (farmGame.getRunning()) {
            while (farmGame.getIsWaitingForInput()) ;

            if (farmGame.getRunning()) {
                day++;
                farmGame.growAllCrops();
                System.out.println("----------------------------------------");
                System.out.printf("[Day %d] ", day);
                farmGame.printStatus();
                System.out.println("----------------------------------------\n");
            }

            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
