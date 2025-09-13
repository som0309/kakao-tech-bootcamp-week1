import game.FarmGame;
import game.FarmTime;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("          Crop Growing Game!!!");
        System.out.println("========================================");

        Scanner scanner = new Scanner(System.in);
        System.out.print("게임을 시작하시겠습니까? (yes/no): ");
        String isStart = scanner.next();
        System.out.println();

        FarmGame farmGame = new FarmGame();
        FarmTime farmTime = new FarmTime(farmGame);

        if (isStart.equals("yes")) {
            farmGame.start();
            Thread thread1 = new Thread(farmGame);
            Thread thread2 = new Thread(farmTime);
            farmGame.setFarmTimeThread(thread2);

            thread1.start();
            thread2.start();
        }
        else if (isStart.equals("no")) {
            return;
        }
        else {
            return;
        }
    }
}