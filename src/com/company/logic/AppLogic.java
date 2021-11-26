package com.company.logic;

import com.company.controller.FileController;
import com.company.entities.Command;
import com.company.entities.GameCase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppLogic
{

    private static String pathToLotsFile;

    private static ArrayList<GameCase> gameCases;

    private static FileController fileController;

    private static boolean gameCasesUpdated;

    private static long gameCasesGeneralCount;

    private static ArrayList<Command> commands;

    static
    {
        pathToLotsFile = "";
        gameCases = new ArrayList<>();
        fileController = new FileController();
        gameCasesUpdated = false;
        gameCasesGeneralCount = 0;

        commands = new ArrayList<>();
        commands.add(new Command("update_cases", "upload cases from file GameCases.txt"));
        commands.add(new Command("print_cases", "print uploaded cases"));
        commands.add(new Command("get_case_info <id>", "get information about case by id(int) e.g. \"get_case_info 1\""));
        commands.add(new Command("spin", "start to spin roulette"));
        commands.add(new Command("exit", "exit the app"));
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

        System.out.println("Waiting for update...");
        while(!AppLogic.gameCasesIsUpdated()) {}

        gameCases.sort((gc1, gc2) -> gc1.getCount() - gc2.getCount());
        gameCasesGeneralCount = 0;
        for(var gc : gameCases)
            gameCasesGeneralCount += gc.getCount();

        System.out.println("Updated!");

    }

    public static void spin()
    {
        GameCase prize = getRandomCase();
        if(prize != null)
            System.out.println("You've won: " + prize);
        else
            System.out.println("Please, upload cases and try again!");
    }

    private static GameCase getRandomCase()
    {
        if(gameCasesGeneralCount == 0)
            return null;

        long number = new Random().nextLong(gameCasesGeneralCount) + 1;
        long bound = 0;
        for(var gc : gameCases)
        {
            bound += gc.getCount();
            if(number <= bound)
                return gc;
        }
        return null;
    }

    private static void getCaseByIdCommandWrapper(String parameter)
    {
        int id;
        GameCase gameCase = null;
        try
        {
            id = Integer.parseInt(parameter);
            gameCase = getCaseById(id);
            System.out.println(getCaseInfo(gameCase));
        }
        catch(RuntimeException exception)
        {
            System.out.println("Bad input: " + exception.getMessage());
        }
    }

    private static GameCase getCaseById(int id) throws ArrayIndexOutOfBoundsException
    {
        return gameCases.get(id);
    }

    private static String getCaseInfo(GameCase gameCase)
    {
        return  gameCase + " chance = " +
                new BigDecimal((double) gameCase.getCount() / gameCasesGeneralCount).setScale(4, RoundingMode.UP).doubleValue() * 100 + "%";
    }

    public static boolean executeCommand(String command)
    {
        String[] commandParameters = command.split(" ");

        if(command.replaceAll("\\s","") == "")
            return true;

        System.out.println();
        switch(commandParameters[0])
        {
            case "help":
                printCommands();
                break;
            case "update_cases":
                updateGameCases();
                break;
            case "print_cases":
                printGameCases();
                break;
            case "get_case_info":
                getCaseByIdCommandWrapper(commandParameters[1]);
                break;
            case "spin":
                spin();
                break;
            case "exit":
                return false;
            default:
                System.out.println("Unknown command");
                break;
        }
        System.out.println();

        return true;
    }

    public static void printCommands()
    {
        for(var c : commands)
            System.out.println(c.getName() + " - " + c.getDescription());
    }

    public static boolean gameCasesIsUpdated()
    {
        if(fileController.fileIsProcessed())
            gameCasesUpdated = true;
        return gameCasesUpdated;
    }

    public static void printGameCases()
    {
        if(gameCases.isEmpty())
            System.out.println("No cases");
        else
            for(int i = 0; i < gameCases.size(); ++i)
                System.out.println(gameCases.get(i) + " id = " + i);
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

    public static void setGameCasesGeneralCount(long count)
    {
        gameCasesGeneralCount = count;
    }

    public static long getGameCasesGeneralCount()
    {
        return gameCasesGeneralCount;
    }

}
