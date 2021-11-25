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

        AppLogic.setPathToLotsFile("GameLots.txt");

        AppLogic.updateGameCases();
        System.out.println("Waiting for update...");
        while(!AppLogic.gameCasesIsUpdated())
        {

        }
        System.out.println("Updated!");
        AppLogic.printGameCases();

    }
}
