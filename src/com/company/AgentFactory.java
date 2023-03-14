package com.company;

import com.company.Agents.Agent;
import com.company.Agents.*;

import java.awt.*;

public class AgentFactory {
    public static String [] warriors = {"Destex", "Davide", "Nicoc"}; //"Faso",  "Getal", "Fioccosplat", "IsoBot", "Telex", "ATX303"};

    // public Agent(int teamId, Color color, int x, int y)
    public static Agent buildAgent(String name,int teamId, Color color, int x, int y){
        switch (name){
            case "Destex":
                return new Destex(teamId,  color,  x,  y);
            case "Agent":
                return new Agent(teamId, color, x, y);
            case "Davide":
                return new Davide(teamId,  color,  x,  y);
            /*
            case "Fioccosplat":
                return new Fioccosplat(teamId,  color,  x,  y);
            case "Getal":
                return new Getal(teamId,  color,  x,  y);
            case "IsoBot":
                return new IsoBot(teamId,  color,  x,  y);
            case "Faso":
                return new Faso(teamId,  color,  x,  y);
            case "Telex":
                return new Telex(teamId,  color,  x,  y);
            case "ATX303":
                return new ATX303(teamId,  color,  x,  y);

             */
            case "Nicoc" :
                return new Nicoc(teamId, color, x, y);
        }

        return null;
    }

    public static Agent buildAgent(String name,int teamId){
        switch (name){
            case "Destex":
                return new Destex(teamId);
            case "Agent":
                return new Agent(teamId);
            case "Davide":
                return new Davide(teamId);
            /*
            case "Getal":
                return new Getal(teamId);
            case "Fioccosplat":
                return new Fioccosplat(teamId);
            case "IsoBot":
                return new IsoBot(teamId);
            case "Faso":
                return new Faso(teamId);
            case "Telex":
                return new Telex(teamId);
            case "ATX303":
                return new ATX303(teamId);

             */
            case "Nicoc" :
                return new Nicoc(teamId);
        }

        return null;
    }

    static Agent buildShooter(String name, int teamId, Color color, int x, int y, int health, int age){
        switch (name){
            case "Davide_Shooter":
                return new Davide_Shooter( teamId);
        }
        return null;
    }

    static Agent buildHealer(String name, int teamId, Color color, int x, int y, int health, int age){
        switch (name) {
            case "Davide_Healer":
                return new Davide_Healer( teamId);
        }
        return null;
    }


    public String [] getWarriors(){
        return warriors;
    }
}
