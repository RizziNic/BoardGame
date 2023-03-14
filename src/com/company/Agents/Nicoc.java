package com.company.Agents;

import com.company.Agents.Agent;
import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;
import java.util.Arrays;

public class Nicoc extends Agent {

    boolean cheats = false;

    public Nicoc(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "Nicoc";
    }

    public Nicoc(int teamId){
        super( teamId);
        this.type = "Nicoc";

    }
    public void trucchi(){
        this.health = 100;
        this.minDamage = 50;
        this.maxDamage = 100;
    }

    public int[] findWeekestEnemy(Agent[] agents){
        int vita = 1000000000, y=0;
        int[] posWeekEnemy = new int[2];

        for (int i = 0; i < agents.length; i++) {
            if (agents[i] != null && agents[i].getStatus() != 0 && vita > agents[i].health && agents[i].getTeamId() != teamId){
                vita = agents[i].health;
                y = i;
            }
            if (agents[y] != null && agents[y].getStatus() != 0 && i == agents.length-1){
                posWeekEnemy[0] = agents[y].getPosX();
                posWeekEnemy[1] = agents[y].getPosY();
            }
        }
        return posWeekEnemy;
    }

    public char TargetPositionToDirection(int []target){
        char action = ' ';
        // attack
        if (posX < target[0]){
            action = 'e';
        } else if (posX > target[0]){
            action = 'o';
        }
        if (posY < target[1]){
            action = 'n';
        } else if (posY > target[1]){
            action = 's';
        }
        ////System.out.println("Action : "+action);

        return action;

    }

    public char Strategy(Agent[] agents){
        if(cheats)
            trucchi();

        int target[] = Sensor.findRisorsa(this);

        if (this.mood == Behaviour.EXPANSIVE){
            // risorsa pià vicina
            target = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.BERZERKER){
            // risorsa pià vicina
            target = findWeekestEnemy(agents);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.DEFENSIVE){
            // risorsa pià vicina
            target = Sensor.findEnemy(agents, this.teamId);
            return TargetPositionToDirection(target);
        }

        return ' ';
    }

    public Agent.Behaviour ComputeCurrentState(){

        int []risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());

        if (risorse[this.teamId] == 0)
            return Behaviour.EXPANSIVE;
        else if (risorse[this.teamId] > 3){
            return Behaviour.EXPANSIVE;
        }
        else if (risorse[this.teamId] > 6){
            return Behaviour.BERZERKER;
        }
        return Behaviour.BERZERKER;
    }

    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        //System.out.println("Nicoc MOVING");
        this.mood = ComputeCurrentState();
        return  Strategy(agents);
        //return 'o';
    }

}
