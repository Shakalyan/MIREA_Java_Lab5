package com.company.entities;

public class Command
{
    String name;
    String description;

    public Command(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

}
