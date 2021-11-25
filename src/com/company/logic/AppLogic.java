package com.company.logic;

import com.company.controller.FileController;
import com.company.entities.GameCase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppLogic
{

    private static String pathToLotsFile;

    private static ArrayList<GameCase> gameCases;

    private static FileController fileController;

    private static boolean gameCasesUpdated;

    static
    {
        pathToLotsFile = "";
        gameCases = new ArrayList<>();
        fileController = new FileController();
        gameCasesUpdated = false;
    }

    public static void setPathToLotsFile(String path)
    {
        pathToLotsFile = path;
    }

    public static String getPathToLotsFile()
    {
        return pathToLotsFile;
    }

    public static void updateGameCases()
    {
        gameCasesUpdated = false;
        ExecutorService fileControllerES = Executors.newSingleThreadExecutor();
        fileControllerES.execute(fileController);
        fileControllerES.shutdown();
    }

    public static boolean gameCasesIsUpdated()
    {
        if(fileController.fileIsProcessed())
            gameCasesUpdated = true;
        return gameCasesUpdated;
    }

    public static void printGameCases()
    {
        for(var gameCase : gameCases)
            System.out.println(gameCase);
    }

    public static void addGameCase(GameCase gameCase)
    {
        gameCases.add(gameCase);
    }

    public static void setGameCases(ArrayList<GameCase> newGameCases)
    {
        gameCases = newGameCases;
    }

    public static ArrayList<GameCase> getGameCases()
    {
        return gameCases;
    }

}
