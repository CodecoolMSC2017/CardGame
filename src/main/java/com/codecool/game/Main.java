package com.codecool.game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game newGame = new Game();
        Scanner sc = new Scanner(System.in)
        while (true) {
            System.out.flush();
            int i = 1;
            System.out.println("Welcome to Medieval Wars!\nPlease choose from the menu:\n");

            for (MainMenu menu : MainMenu.values()) {
                System.out.println(Integer.toString(i) + " " + menu.getValue());
                i++;
            }

            int select = sc.nextInt();
            String empty = sc.nextLine();

            if (select == 1) {
                newGame.startGame();
            }else if (select == 2) {
                break;
            }
        }
    }
}
