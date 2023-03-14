package com.company.Agents;

import com.company.Agents.Agent;
import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;
import java.util.Arrays;

/* Agente di base del gioco ZucRoyale */

public class Destex extends Agent {

    public Destex(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "Destex";

    }

    public Destex(int teamId){
        super( teamId);
        this.type = "Destex";

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

    public char Strategy(){
        int target[] = Sensor.findRisorsa(this);

        if (this.mood == Behaviour.EXPANSIVE){
                // risorsa pià vicina
             target = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.BERZERKER){
            // risorsa pià vicina
             target = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.DEFENSIVE){
            // risorsa pià vicina
            target = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }

        return ' ';
    }

    public Agent.Behaviour ComputeCurrentState(){

        int []risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());

        if (risorse[this.teamId] == 0)
            return Behaviour.EXPANSIVE;
        else if (risorse[this.teamId] > 3){
            return Behaviour.DEFENSIVE;
        }
        else if (risorse[this.teamId] > 6){
            return Behaviour.BERZERKER;
        }
        return Behaviour.BERZERKER;
    }

    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        //System.out.println("DESTEX MOVING");
        this.mood = ComputeCurrentState();
        return  Strategy();
        //return 'o';
    }
}
