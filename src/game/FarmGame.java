package game;

import crop.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FarmGame implements Runnable{

    private Thread farmTimeThread;
    Scanner scanner = new Scanner(System.in);
    private boolean running;
    volatile private boolean isWaitingForInput;

    private List<Crop> crops = new ArrayList<>();
    private int money;

    public void setFarmTimeThread(Thread farmTimeThread) {
        this.farmTimeThread = farmTimeThread;
    }

    public boolean getRunning() {
        return running;
    }

    public boolean getIsWaitingForInput() {
        return isWaitingForInput;
    }

    public void start() {
        running = true;
        isWaitingForInput = false;
        money = 10000;
        System.out.println("----------------------------------------");
        System.out.println("[Day 1] 농장 생활이 시작됩니다!");
        printStatus();
        System.out.println("----------------------------------------\n");
    }

    public void exit() {
        running = false;
        money = 0;
        crops.clear();
    }

    public void printStatus() {
        System.out.println("농장 상태: ");

        if (crops.isEmpty()) {
            System.out.println("심은 작물이 없습니다.");
        }
        else {
            for (Crop crop : crops) {
                System.out.printf("- %s : 성장 %d/%d ", crop.getName(), crop.getCurrentDays(), crop.getGrowDays());
                if (crop.isReadyToHarvest()) {
                    System.out.println("=> 수확!");
                }
                else {
                    System.out.println();
                }
            }
            System.out.println();
            harvestAllCrops();
        }

        System.out.printf("현재 자산: %d원\n", money);
    }

    public int plant(Crop crop) {
        int seedPrice;
        if (crop instanceof Vegetable) {
            seedPrice = Vegetable.getSeedPrice();
        } else if (crop instanceof Fruit) {
            seedPrice = Fruit.getSeedPrice();
        }
        else {
            throw new IllegalArgumentException("지원하지 않는 객체 타입");
        }

        money -= seedPrice;
        crops.add(crop);

        return money;
    }

    public void growAllCrops() {
        for (Crop crop : crops) {
            crop.grow();
        }
    }

    public int harvestAllCrops() {

        Iterator<Crop> iterCrops = crops.iterator();
        while (iterCrops.hasNext()) {
            Crop crop = iterCrops.next();
            if (crop.isReadyToHarvest()) {
                int finalPrice = 0;

                if (crop instanceof Vegetable veg) {
                    int freshness = veg.setFreshness();
                    finalPrice = crop.calculateSellingPrice();
                    System.out.printf("%s를 수확했습니다! (신선도: %d)\n", crop.getName(), freshness);
                    if (crop.getIsUpgrade()) {
                        System.out.printf("-> 작물 가공에 성공하였습니다! (판매가: %d)\n", finalPrice);
                    }
                    else {
                        System.out.printf("-> 작물 가공에 실패하였습니다. (판매가: %d)\n", finalPrice);
                    }
                }
                else if (crop instanceof Fruit fru) {
                    int sweetness = fru.setSweetness();
                    finalPrice = crop.calculateSellingPrice();
                    System.out.printf("%s를 수확했습니다! (당도: %d)\n", crop.getName(), sweetness);
                    if (crop.getIsUpgrade()) {
                        System.out.printf("-> 작물 가공에 성공하였습니다! (판매가: %d)\n", finalPrice);
                    }
                    else {
                        System.out.printf("-> 작물 가공에 실패하였습니다. (판매가: %d)\n", finalPrice);
                    }
                }
                else {
                    throw new IllegalArgumentException("지원하지 않는 객체 타입");
                }

                money += finalPrice;
                iterCrops.remove();
            }
        }

        return money;
    }

    @Override
    public synchronized void run() {

        while (running) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            isWaitingForInput = true;
            System.out.println("---------------------------------------");
            System.out.println("심을 수 있는 작물 목록");
            System.out.println("---------------------------------------");
            System.out.printf("1. 토마토 (씨앗 가격: %d원, 성장일수: 1일)\n", Vegetable.getSeedPrice());
            System.out.printf("2. 당근 (씨앗 가격: %d원, 성장일수: 2일)\n", Vegetable.getSeedPrice());
            System.out.printf("3. 사과 (씨앗 가격: %d원, 성장일수: 5일)\n", Fruit.getSeedPrice());
            System.out.printf("4. 바나나 (씨앗 가격: %d원, 성장일수: 4일)\n", Fruit.getSeedPrice());
            System.out.println("---------------------------------------\n");
            System.out.print("심고 싶은 작물 번호를 선택하세요 (작물을 심지 않으려면 0, 게임을 종료하려면 -1): ");
            String number = scanner.next();
            System.out.println();

            switch (number) {
                case "0":
                    System.out.println("-> 작물을 심지 않았습니다.");
                    System.out.printf("현재 자산: %d원\n\n", money);
                    break;
                case "1":
                    Crop.Tomato tomato = new Crop.Tomato();
                    plant(tomato);
                    System.out.printf("-> 토마토 씨앗을 심었습니다! (-%d원)\n", Vegetable.getSeedPrice());
                    System.out.printf("현재 자산: %d원\n\n", money);
                    break;
                case "2":
                    Carrot carrot = new Carrot();
                    plant(carrot);
                    System.out.printf("-> 당근 씨앗을 심었습니다! (-%d원)\n", Vegetable.getSeedPrice());
                    System.out.printf("현재 자산: %d원\n\n", money);
                    break;
                case "3":
                    Apple apple = new Apple();
                    plant(apple);
                    System.out.printf("-> 사과 씨앗을 심었습니다! (-%d원)\n", Fruit.getSeedPrice());
                    System.out.printf("현재 자산: %d원\n\n", money);
                    break;
                case "4":
                    Banana banana = new Banana();
                    plant(banana);
                    System.out.printf("-> 바나나 씨앗을 심었습니다! (-%d원)\n", Fruit.getSeedPrice());
                    System.out.printf("현재 자산: %d원\n\n", money);
                    break;
                case "-1":
                    exit();
                    farmTimeThread.interrupt();
                    break;
                default:
                    throw new IllegalArgumentException("잘못된 입력");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            isWaitingForInput = false;
        }
    }
}
