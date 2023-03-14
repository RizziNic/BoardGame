package com.company.Agents;

import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;
import java.util.Random;

public class Getal extends Agent {

    private static Random rnd = new Random();
    Behaviours moods;

    public Getal(int teamId, Color color, int x, int y) {
        super(teamId, color, x, y);
        this.type = "Getal";
    }

    public Getal(int teamId) {
        super(teamId);
        this.type = "Getal";
    }

    public enum Behaviours {
        DEFENSIVE,
        EXPANSIVE,
        BERZERKER,
        RANDOM
    }

    public char TargetPositionToDirection(int[] target) {
        // attack
        if (posX < target[0]) {
            return 'e';
        } else if (posX > target[0]) {
            return 'o';
        }
        if (posY < target[1]) {
            return 'n';
        } else if (posY > target[1]) {
            return 's';
        }
        return ' ';
    }

    public char Strategy() {
        if (this.moods == Behaviours.EXPANSIVE) {
            // risorsa pià vicina
            int target[] = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if (this.moods == Behaviours.BERZERKER) {
            // il primo nemico
            int target[] = findNearEnemy(Board.getAgents(),this);
            return TargetPositionToDirection(target);
        }
        if (this.moods == Behaviours.DEFENSIVE){
            int target[] = oppositeNearEnemy(Board.getAgents(),this);
            return TargetPositionToDirection(target);
        }
        if (this.moods == Behaviours.RANDOM){
            if (rnd.nextBoolean()){
                if (rnd.nextBoolean()){
                    return ' ';
                }else if (rnd.nextBoolean()){
                    return 'n';
                }else return 's';
            }else{
                if (rnd.nextBoolean()){
                    return 'o';
                }else return 'e';
            }
        }
        else return ' ';
    }

    public Behaviours ComputeCurrentState(){
        int[] risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());
        if (risorse[this.teamId] < Board.getResources() && this.health > 40) {
            return Behaviours.EXPANSIVE;
        }
        if (risorse[this.teamId] >= Board.getResources()/2 && this.health >= 40){
            return Behaviours.BERZERKER;
        }
        if (this.health < 40 && Board.getTeams()[this.teamId] < Board.getResources()/4 && risorse[this.teamId] > Board.getResources()/2){
            return Behaviours.DEFENSIVE;
        }
        if (this.health < 40 && Board.getTeams()[this.teamId] < Board.getResources()/4 && risorse[this.teamId] <= Board.getResources()/2){
            return Behaviours.EXPANSIVE;
        }
        if (this.health < 50){
            return Behaviours.EXPANSIVE;
        }else return Behaviours.EXPANSIVE;
    }

    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        this.moods = ComputeCurrentState();
        return Strategy();
    }

    public static int[] findNearEnemy(Agent[] fightingAgents, Agent me) {
        /*
        questo metodo trova il nemico più vicino all'utente
         */
        int[] pos = new int[2];
        int dist;
        int dist1 = -1;
        /*
        l'array pos serve a salvare le coordinate del punto trovato
        dist e dist1 servono a confrontare le distanze di due coordinate diverse
        dist1 viene inizializato a -1 per salvare nel ciclo le prime coordinate che il programma trova
         */
        for (int a = 0; a < fightingAgents.length; a++) {
            if (fightingAgents[a] != null && fightingAgents[a].getTeamId() != me.getTeamId() && fightingAgents[a].getStatus() != 0) {
                int[] posEnemy = new int[2];
                posEnemy[0] = fightingAgents[a].getPosX();
                posEnemy[1] = fightingAgents[a].getPosY();
                if (dist1 == -1) {
                    if (posEnemy[1] < me.getPosY()) {
                        dist1 = me.getPosY() - posEnemy[1];
                    } else {
                        dist1 = posEnemy[1] - me.getPosY();
                    }
                    if (posEnemy[0] < me.getPosX()) {
                        dist1 = dist1 + (me.getPosX() - posEnemy[0]);
                    } else {
                        dist1 = dist1 + (posEnemy[0] - me.getPosX());
                    }
                    pos[1] = posEnemy[1];
                    pos[0] = posEnemy[0];
                        /*
                        con dist1 a -1 salva le coordinate della prima risorsa salvando anche
                        la distanza (dist1) sommando la distanza della y della risorsa dalla y dell'Agente con
                        la distanza della x della risorsa dalla x dell'Agente
                         */
                } else {
                    if (posEnemy[1] < me.getPosY()) {
                        dist = me.getPosY() - posEnemy[0];
                    } else {
                        dist = posEnemy[1] - me.getPosY();
                    }
                    if (posEnemy[0] < me.getPosX()) {
                        dist = dist + (me.getPosX() - posEnemy[0]);
                    } else {
                        dist = dist + (posEnemy[0] - me.getPosX());
                    }
                    if (dist < dist1) {
                        dist1 = dist;
                        pos[1] = posEnemy[1];
                        pos[0] = posEnemy[0];
                    }
                        /*
                        qui calcola la seconda distanza (dist) per poi confrontarla con la distanza più piccola trovata (dist1)
                        nel caso in cui la distanza calcolata (dist) sia più piccola della distanza salvata (dist1) prenderà
                        il suo posto (dist1 = dist) salvando le cordinate nell'array
                         */
                }
            }
        }

        return pos;
    }

    public static int[] oppositeNearEnemy(Agent[] fightingAgents, Agent me){
        int[] pos = {me.getPosX(),me.getPosY()};
        int[] enemy = findNearEnemy(fightingAgents,me);
        int distX = me.getPosX() - enemy[0];
        int distY = me.getPosY() - enemy[1];
        if (me.getPosX() == enemy[0] && me.getPosY() == enemy[1]) {
            return pos;
        }
        if (distX < 0) {
            distX = distX * (-1);
        }
        if (distY < 0) {
            distY = distY * (-1);
        }
        if (distX - distY >= 0) {
            if (me.getPosX() - enemy[0] < 0) {
                pos[0]++;
                return pos;
            }
            pos[0]--;
            return pos;
        } else if (distX - distY <= 0) {
            if (me.getPosX() - enemy[1] < 0) {
                pos[1]++;
                return pos;
            }
            pos[1]--;
            return pos;
        } else {
            if (rnd.nextBoolean()) {
                if (me.getPosX() - enemy[0] < 0) {
                    pos[0]++;
                    return pos;
                }
                pos[0]--;
                return pos;
            } else if (me.getPosY() - enemy[1] < 0) {
                pos[1]++;
                return pos;
            }
            pos[1]--;
            return pos;
        }
    }

}
