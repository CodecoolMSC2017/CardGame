package com.codecool.game;

import com.codecool.api.network.Client;
import com.codecool.api.network.Host;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Host server = null;
        Client clinet = null;

        // create new Game object. This is where most of the game will happen actually.
        Game newGame = new Game();


        // Display main menu and request basic input data (player names).
        // After that Game is called passing two player names
        while (true) {
            System.out.print("\033[H\033[2J");
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
                System.out.println("Please provide name of the first player:");
                String name1 = sc.nextLine();
                System.out.println("Please provide name of the second player:");
                String name2 = sc.nextLine();
                newGame.startGame(name1, name2);
            }
            else if (select == 2) {
                if (server == null) {
                    server = new Host();
                } else {
                    System.out.println("Cannot do that, there is already a server running");
                }
            }
            else if (select == 3) {
                if (clinet == null) {
                    clinet = new Client();
                    clinet.run();
                } else {
                    System.out.println("Cannot do that, maximum player number is only two");
                }
            }
            else{
                System.exit(0);

            }
        }
    }
}
