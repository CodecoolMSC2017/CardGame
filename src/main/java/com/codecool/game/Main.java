package com.codecool.game;

import com.codecool.client.*;
import com.codecool.host.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // for testing
        System.out.println("Start local server? (y)es");
        char serverStart = sc.nextLine().charAt(0);
        if (serverStart == 'y') {
            Host server = new Host();

        }
        else {
            Client client = new Client();
            client.run();
        }

        // end of test section


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
            }else if (select == 2) {
                break;
            }
        }
    }
}
