package com.company.Agents;

import com.company.AgentFactory;
import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;

public class Telex extends Agent{
    public Telex(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "Telex";

    }

    public Telex(int teamId){
        super( teamId);
        this.type = "Telex";

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
            target = Sensor.findEnemy(Board.getAgents(), this.teamId);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.DEFENSIVE){
            // risorsa pià vicina
            target = escape(Board.getAgents());
            return TargetPositionToDirection(target);
        }

        return ' ';
    }
    public int[] escape(Agent[] agents){
        int[] target = new int[2];
        for (int i = 0; i < agents.length; i++){
            if (agents[i].teamId != teamId && agents[i].status != 0) {
                if (agents[i].posY == posY) {
                    if (agents[i].posX == posX++) {
                        target[0] = posX--;
                        target[1] = posY;
                        break;
                    }
                    if (agents[i].posX == posX--) {
                        target[0] = posX++;
                        target[1] = posY;
                        break;
                    }
                }
                if (agents[i].posX == posX) {
                    if (agents[i].posY == posY++) {
                        target[0] = posX;
                        target[1] = posY--;
                        break;
                    }
                    if (agents[i].posY == posY--) {
                        target[0] = posX;
                        target[1] = posY++;
                        break;
                    }
                }
                if (agents[i].posX == posX && agents[i].posY == posY){
                    target[0] = posX;
                    target[1] = posY++;
                    break;
                }
            }
        }
        return target;
    }

    public Agent.Behaviour ComputeCurrentState(){

        int []risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());

        if (risorse[this.teamId] <= Sensor.RisorseTot(Board.getAgents(), Board.getBoard(), this)) {
            if (this.health <= 30 && Sensor.getNumCloni(this, Board.getAgents()) == 1) {
                return Behaviour.DEFENSIVE;
            }
            return Behaviour.EXPANSIVE;
        }
        if (this.health <= 30  && Board.getAgents().length > 2){
            return Behaviour.DEFENSIVE;
        }
        if (this.health > 30 && Sensor.getNumCloni(this, Board.getAgents()) > 1){
            return Behaviour.BERZERKER;
        }
        return Behaviour.EXPANSIVE;
    }

    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        this.mood = ComputeCurrentState();
        return  Strategy();
        //return 'o';
    }
}
