package com.company.Agents;

import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;
import java.util.Random;

public class Davide extends Agent{
    public Davide(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "Davide";

    }

    public Davide(int teamId){
        super( teamId);
        this.type = "Davide";

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
        //System.out.println("Action : "+action);
        return action;

    }

    public char Strategy(){
        Random rnd = new Random();
        int x = rnd.nextInt() * 10;
        int y = rnd.nextInt();
        if (this.mood == Behaviour.EXPANSIVE){
            int[] target = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.BERZERKER){
            // risorsa pià vicina
            int []target = Sensor.findEnemy(Board.getAgents(), this.teamId);
            assert target != null;
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.DEFENSIVE){
            // SCAPPA VIA
            double dens = Sensor.playerDens(this);
            if (dens <= 1) {
                return Sensor.direzioneVerso(this, x, y);
            }
        }
        if(this.mood == Behaviour.CAMPER){
            
            int[] target = Sensor.findRisorsa(this);
            TargetPositionToDirection(target);
            return ' ';
        }
        return ' ';
    }

    public Agent.Behaviour ComputeCurrentState(){

        int []risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());

        if (risorse[this.teamId] < (Board.getResources() / 2))
            return Behaviour.EXPANSIVE;
        else if (this.health > 25 && this.health < 50){
            return Behaviour.DEFENSIVE;
        }
        else if (risorse[this.teamId] == (Board.getResources() / 2)){
            return Behaviour.BERZERKER;
        }
        else if (this.health < 10){
            return Behaviour.CAMPER;
        }
        return Behaviour.EXPANSIVE;
    }



    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        this.mood = ComputeCurrentState();
        return  Strategy();
    }
}
