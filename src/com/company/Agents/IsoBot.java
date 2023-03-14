package com.company.Agents;

import com.company.AgentFactory;
import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;




public class IsoBot extends Agent{
    int SeriesNumber=0;
    int prevhealth=health;
    boolean cheaty = false;

    public IsoBot(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "IsoBot";
        color = new Color(255, 0, 0);

    }

    public IsoBot(int teamId, int num){
        super( teamId);
        this.type = "IsoBot";
        this.SeriesNumber+=num;
        color = new Color(255, 0, 0);
    }

    public IsoBot(int teamId) {
        super( teamId);
        this.type = "IsoBot";
        color = new Color(255, 0, 0);
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
        return action;

    }

    public char TargetPositionToEscape(int []target){
        char action = ' ';
        // attack
        if (posX < target[0]){
            action = 'o';
        } else if (posX > target[0]){
            action = 'e';
        }
        if (posY < target[1]){
            action = 's';
        } else if (posY > target[1]){
            action = 'n';
        }
        return action;

    }

    public char Strategy(){
        if (this.mood == Behaviour.EXPANSIVE){
            // risorsa più vicina
            int target[] = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.BERZERKER){
            // risorsa più vicina
            int target[] = Sensor.findEnemy(Board.getAgents(), this.teamId);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.DEFENSIVE){
            boolean near = true;
            int[] toRun=Sensor.findEnemy(Board.getAgents(), this.teamId);
            if (Math.abs(this.posX - toRun[0]) <= 4 && Math.abs(this.posY - toRun[1]) <= 4 ){

                    return TargetPositionToEscape(toRun);


            }
        }
        if (this.mood == Behaviour.FUSION){
            int target[] = Sensor.findAlliesPos(Board.getAgents(), this.teamId);
            return TargetPositionToDirection(target);
        }


        return ' ';
    }

    public Agent.Behaviour cheat(){
        health = prevhealth;
        prevhealth = health;

        minDamage = 1000;
        maxDamage = 10000;

        return Behaviour.BERZERKER;
    }

    public Agent.Behaviour ComputeCurrentState(){

        if(cheaty)
            return cheat();

        int[] risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());

        if (risorse[this.teamId] > Board.getResources()/3 && this.health <= 20)
            return Behaviour.DEFENSIVE;

        if (risorse[this.teamId]<=Board.getResources()/2)
            return Behaviour.EXPANSIVE;

        if (risorse[this.teamId] > Board.getResources()/2)
            return Behaviour.BERZERKER;

        if (this.SeriesNumber > Board.getResources()/3*2 && Board.getAgents().length > 5)
            return Behaviour.FUSION;

        if (Sensor.findAllies(Board.getAgents(), this.teamId).mood == this.mood && this.mood == Behaviour.FUSION){
            if (Sensor.findAllies(Board.getAgents(), this.teamId).getPosY() == this.getPosY() && Sensor.findAllies(Board.getAgents(), this.teamId).getPosX() == this.getPosX()){
                return Behaviour.BERZERKER;

            }

        }


        return Behaviour.BERZERKER;
    }

    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        this.mood = ComputeCurrentState();
        return  Strategy();
        //return 'o';
    }

}

