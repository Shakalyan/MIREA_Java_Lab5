package com.company.controller;

import com.company.entities.GameCase;
import com.company.logic.AppLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class FileController implements Runnable
{
    private AtomicBoolean fileProcessed = new AtomicBoolean();

    @Override
    public void run()
    {
        fileProcessed.set(false);
        String path = AppLogic.getPathToLotsFile();
        ArrayList<GameCase> initialGameCases = new ArrayList<GameCase>(AppLogic.getGameCases());
        AppLogic.getGameCases().clear();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path)))
        {
            StringBuffer sb = new StringBuffer();

            String currentLine = bufferedReader.readLine();
            while(currentLine != null)
            {
                GameCase gameCase = buildGameCase(currentLine);
                AppLogic.addGameCase(gameCase);
                currentLine = bufferedReader.readLine();
            }
        }
        catch(IOException exception)
        {
            System.out.println(exception);
            AppLogic.setGameCases(initialGameCases);
        }
        fileProcessed.set(true);
    }

    private GameCase buildGameCase(String line) throws IOException
    {
        String[] params = line.split(" ");

        String name = params[0];
        String rarity = params[1];
        int cost;
        int count;

        try
        {
            cost = Integer.parseInt(params[2]);
            count = Integer.parseInt(params[3]);
        }
        catch(NumberFormatException exception)
        {
            throw new IOException("Bad input : " + exception.getMessage());
        }

        return new GameCase(name, rarity, cost, count);
    }

    public boolean fileIsProcessed()
    {
        if(fileProcessed.get())
        {
            fileProcessed.set(false);
            return true;
        }
        return false;
    }

}
