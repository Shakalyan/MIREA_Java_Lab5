package com.company.entities;

public class GameCase
{

    private String name;
    private String rarity;
    private int cost;
    private int count;

    public GameCase(String name, String rarity, int cost, int count)
    {
        this.name = name;
        this.rarity = rarity;
        this.cost = cost;
        this.count = count;
    }

    public String getName()
    {
        return name;
    }

    public String getRarity()
    {
        return rarity;
    }

    public int getCost()
    {
        return cost;
    }

    public int getCount()
    {
        return count;
    }

    @Override
    public String toString()
    {
        return name + "(" + rarity + ", " + cost + "$, x" + count + ")";
    }

}
