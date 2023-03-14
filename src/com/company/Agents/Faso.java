package com.company.Agents;

import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;

public class Faso extends Agent{

    public static Color fasoColor = new Color(0xFF7F00);

    public Faso(int teamId, Color color, int x, int y){
        super(teamId,  fasoColor,  x,  y);
        this.type = "Faso";

    }
    public Faso(int teamId){
        super(teamId);
        this.type = "Faso";

    }

    public char TargetPositionToDirection(int []target){
        // attack
        if (posX < target[0]){
            return 'e';
        } else if (posX > target[0]){
            return 'o';
        }
        if (posY < target[1]){
            return 'n';
        } else if (posY > target[1]){
            return 's';
        }
        return ' ';
    }

    //target position al contrario, per scappare
    public char TargetPositionToDirectionReverse(int []target){
        // attack
        if (posX < target[0]){
            return 'o';
        } else if (posX > target[0]){
            return 'e';
        }
        if (posY < target[1]){
            return 's';
        } else if (posY > target[1]){
            return 'n';
        }
        return ' ';
    }

    public char Strategy(){
        if(this.mood == Behaviour.DEFENSIVE){
            int target[] = PosizioneNemicoVicino();/*Sensor.findRisorsa(this);*/
            return TargetPositionToDirectionReverse(target);
        }
        if(this.mood == Behaviour.EXPANSIVE){
            int target[] = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if(this.mood == Behaviour.BERZERKER) {
            int target[] = Attacca(Board.getAgents());
            return TargetPositionToDirection(target);
        }
        return ' ';
    }

    public Agent.Behaviour ComputeCurrentState(){
        int num = 0;
        int minRisorse = 3;
        int []risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());
        if (risorse[this.teamId] <= minRisorse )
            return Behaviour.EXPANSIVE;
        if (this.getHealth() <= 30){
            return Behaviour.DEFENSIVE;
        }
        if (this.getHealth() >= 60 && numAllies(Board.getAgents()) > 1){
            return Behaviour.BERZERKER;
        }
        for(int i = 0; i < Board.getAgents().length; i++){
            if(Board.getAgents()[i].getStatus() != 0 && Board.getAgents()[i].getTeamId() != this.teamId){
                num++;
            }
        }
        if(num == 1)
            return Behaviour.BERZERKER;


        return Behaviour.EXPANSIVE;
    }





    //trova il nemico più vicino all'agente pssato come parametro
    public int[] FindNearestEnemy(Agent [] agents){
        int [] pos = new int [2];
        int contX = 1, contY = 1;
        int enemyPosX, enemyPosY;
        int posX = this.getPosX(), posY = this.getPosY();
        for(int i = 0; i < Board.getAgents().length; i++){
            enemyPosX = Board.getAgents()[i].getPosX();
            enemyPosY = Board.getAgents()[i].getPosY();
            if( enemyPosX == (posX + contX) && enemyPosY == (posY + contY) && Board.getAgents()[i].teamId != this.teamId){
                pos[0] = Board.getAgents()[i].getPosX();
                pos[1] = Board.getAgents()[i].getPosY();
            }
            else if(enemyPosX > posX + contX )
                contY ++;
            else if(enemyPosY > posY + contY)
                contX ++;
            else{
                contX ++;
                contY ++;
            }
        }
        return pos;
    }


    public int[] PosizioneNemicoVicino() {
        //double dist[] = new double[Board.getAgents().length];
        int pos[] = new int[2];
        double dist;
        Agent[] agents = Board.getAgents();
        double currMinDist = Double.MAX_VALUE;
        int minIndex;

        for (int a = 0; a < Board.getAgents().length; a++) {
            if (this != agents[a]) {
                dist = this.posX - agents[a].getPosX();
                if (dist < currMinDist) {
                    currMinDist = dist;
                    minIndex = a;
                    pos[0] = agents[a].getPosX();
                    pos[1] = agents[a].getPosY();
                }

            } else {
                dist = Double.MAX_VALUE;
            }
        }
        return pos;
    }



    //ritorna la posizione del primo agente nella lista, che ha la vita minore dell'agente chiamante
    public int[] FindLowestEnemy(){
        int pos[] = new int [2];
        for(int i = 0; i < Board.getAgents().length; i++){
            Agent enemy = Board.getAgents()[i];
            if(enemy.getHealth() < this.getHealth()){
                pos[0] = enemy.getPosX();
                pos[1] = enemy.getPosY();
                break;
            }
        }

        return pos;
    }


    //attacco del faso
    public int[] Attacca(Agent[] fightingAgents){
        int pos [] = new int [2];
        for(int i = 0; i < fightingAgents.length; i++){
            if(fightingAgents[i].getStatus() != 0 && fightingAgents[i].getTeamId() != this.teamId ){
                pos[0] = fightingAgents[i].getPosX();
                pos[1] = fightingAgents[i].getPosY();
            }
        }
        return pos;
    }

    //ritorna il numero di alleati
    public int numAllies(Agent[]fightingAgent){
        int num = 0;
        for(int i = 0; i < fightingAgent.length; i++){
            if(fightingAgent[i].getTeamId() == this.teamId && fightingAgent[i].getStatus() != 0)
                num++;
        }
        return num;
    }

    public char move(Cell[][] theBoard, Agent[] agents) {
        this.mood = ComputeCurrentState();
        return  Strategy();
    }
}