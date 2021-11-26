package com.company;

import com.company.controller.FileController;
import com.company.logic.AppLogic;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {


    public static void main(String[] args)
    {

        AppLogic.setPathToLotsFile("GameCases.txt");

        System.out.println("Hello!\n");
        System.out.println("There are all commands you can use:");

        String input = "help";
        Scanner scanner = new Scanner(System.in);
        while(AppLogic.executeCommand(input))
        {
            System.out.print("command>");
            input = scanner.nextLine();
        }

    }
}
