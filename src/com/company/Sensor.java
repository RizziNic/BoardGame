package com.company;

import com.company.Agents.Agent;

import java.util.Random;

public class Sensor {
    static Random rnd;

    //calcolo di cloni del chiamante
    public static int getNumCloni(Agent me, Agent agents[]){
        int cloni = 0;
        for (int i = 0; i < agents.length; i++){
            if (agents[i].getTeamId() == me.getTeamId()){
                cloni++;
            }
        }
        return cloni;
    }

    public static int[] findRisorsa(Agent me){
        /*
        questo metodo trova la riorsa più vicina all'utente
         */
        int[] pos = new int[2];
        int dist;
        int dist1 = -1;
        /*
        l'array pos serve a salvare le coordinate del punto trovato
        dist e dist1 servono a confrontare le distanze di due coordinate diverse
        dist1 viene inizializato a -1 per salvare nel ciclo le prime coordinate che il programma trova
         */
        for (int x = 0; x < Board.width; x++ ){
            for (int y = 0; y < Board.height; y++ ){
                if ((Board.theBoard[x][y].type == Cell.CellType.RISORSA && Board.theBoard[x][y].owner == null)
                        || (Board.theBoard[x][y].type == Cell.CellType.RISORSA &&  Board.theBoard[x][y].owner.getTeamId()!= me.getTeamId())){
                    if (dist1 == -1){
                        if (y < me.getPosY()){
                            dist1 = me.getPosY() - y;
                        } else {
                            dist1 = y - me.getPosY();
                        }
                        if (x < me.getPosX()){
                            dist1 = dist1 + (me.getPosX() - x);
                        } else {
                            dist1 = dist1 + (x - me.getPosX());
                        }
                        pos[1]= y;
                        pos[0] = x;
                        /*
                        con dist1 a -1 salva le coordinate della prima risorsa salvando anche
                        la distanza (dist1) sommando la distanza della y della risorsa dalla y dell'Agente con
                        la distanza della x della risorsa dalla x dell'Agente
                         */
                    } else {
                        if (y < me.getPosY()){
                            dist = me.getPosY() - y;
                        } else {
                            dist = y - me.getPosY();
                        }
                        if (x < me.getPosX()){
                            dist = dist + (me.getPosX() - x);
                        } else {
                            dist = dist + (x - me.getPosX());
                        }
                        if (dist < dist1){
                            dist1 = dist;
                            pos[1]= y;
                            pos[0] = x;
                        }
                        /*
                        qui calcola la seconda distanza (dist) per poi confrontarla con la distanza più piccola trovata (dist1)
                        nel caso in cui la distanza calcolata (dist) sia più piccola della distanza salvata (dist1) prenderà
                        il suo posto (dist1 = dist) salvando le cordinate nell'array
                         */
                    }
                }
            }
        }
        return pos;
    }

    public static int[] findMyRisorsa(Agent me){
        /*
        questo metodo trova la riorsa più vicina all'utente
         */
        int[] pos = new int[2];
        int dist;
        int dist1 = -1;
        /*
        l'array pos serve a salvare le coordinate del punto trovato
        dist e dist1 servono a confrontare le distanze di due coordinate diverse
        dist1 viene inizializato a -1 per salvare nel ciclo le prime coordinate che il programma trova
         */
        for (int x = 0; x < Board.width; x++ ){
            for (int y = 0; y < Board.height; y++ ){
                if (Board.theBoard[x][y].type == Cell.CellType.RISORSA && Board.theBoard[x][y].owner == me){
                    if (dist1 == -1){
                        if (y < me.getPosY()){
                            dist1 = me.getPosY() - y;
                        } else {
                            dist1 = y - me.getPosY();
                        }
                        if (x < me.getPosX()){
                            dist1 = dist1 + (me.getPosX() - x);
                        } else {
                            dist1 = dist1 + (x - me.getPosX());
                        }
                        pos[1]= y;
                        pos[0] = x;
                        /*
                        con dist1 a -1 salva le coordinate della prima risorsa salvando anche
                        la distanza (dist1) sommando la distanza della y della risorsa dalla y dell'Agente con
                        la distanza della x della risorsa dalla x dell'Agente
                         */
                    } else {
                        if (y < me.getPosY()){
                            dist = me.getPosY() - y;
                        } else {
                            dist = y - me.getPosY();
                        }
                        if (x < me.getPosX()){
                            dist = dist + (me.getPosX() - x);
                        } else {
                            dist = dist + (x - me.getPosX());
                        }
                        if (dist < dist1){
                            dist1 = dist;
                            pos[1]= y;
                            pos[0] = x;
                        }
                        /*
                        qui calcola la seconda distanza (dist) per poi confrontarla con la distanza più piccola trovata (dist1)
                        nel caso in cui la distanza calcolata (dist) sia più piccola della distanza salvata (dist1) prenderà
                        il suo posto (dist1 = dist) salvando le cordinate nell'array
                         */
                    }
                }
            }
        }
        return pos;
    }

    public static int[]  findEnemy(Agent[] fightingAgents, int teamId) {

        for (int a = 0; a < fightingAgents.length; a++) {
            if (fightingAgents[a] != null && fightingAgents[a].getTeamId() != teamId && fightingAgents[a].getStatus() != 0) {
                int[] posEnemy = new int[2];
                posEnemy[0] = fightingAgents[a].getPosX();
                posEnemy[1] = fightingAgents[a].getPosY();
                return posEnemy;
            }
        }
        return null;
    }

    public static Agent findAllies(Agent[] fightingAgents, int teamId) {

        for (int a = 0; a < fightingAgents.length; a++) {
            if (fightingAgents[a] != null && fightingAgents[a].getTeamId() == teamId && fightingAgents[a].getStatus() != 0) {

                return fightingAgents[a];
            }
        }
        return null;
    }

    public static int[] findAlliesPos(Agent[] fightingAgents, int teamId) {

        for (int a = 0; a < fightingAgents.length; a++) {
            int[] posAllies=new int[2];
            if (fightingAgents[a] != null && fightingAgents[a].getTeamId() == teamId && fightingAgents[a].getStatus() != 0) {
                posAllies[0]=fightingAgents[a].getPosX();
                posAllies[1]=fightingAgents[a].getPosY();
                return posAllies;
            }
        }
        return null;
    }

    public static char direzioneVerso(Agent me, int x, int y){
        int distX = me.getPosX() - x;
        int distY = me.getPosY() - y;
        if (me.getPosX()==x && me.getPosY()==y){
            return ' ';
        }
        if (distX<0){
            distX = distX * (-1);
        }
        if (distY<0){
            distY = distY * (-1);
        }
        if (distX-distY >= 0){
            if (me.getPosX() - x <0)
                return 'e';
            return 'o';
        }else if (distX-distY <= 0){
            if (me.getPosX() - y <0)
                return 'n';
            return 's';
        }else{
            if (rnd.nextBoolean()){
                if (me.getPosX() - x < 0)
                    return 'e';
                return 'o';
            }else
            if (me.getPosY() - y < 0)
                return 'n';
            return 's';
        }
    }

    // Calcola la densità degli Agent rispetto all'Agent Corrente
    public static double playerDens(Agent me){
        int x = 0;
        int y = 0;
        double dist = 0.0;
        double media = 0.0;
        for (int i = 0; i < Board.agents.length; i++){ // Calcolo della distanza media tra l'agente corrente e il resto degli agenti
            x = me.getPosX() - Board.agents[i].getPosX();      // tra l'agente corrente e il resto degli agenti
            y = me.getPosY() - Board.agents[i].getPosY();
            dist = Math.sqrt((Math.pow(x,2)) + (Math.pow(y, 2)));
            media = media + dist;
        }
        media = media/Board.agents.length;
        return media;
    }
    //calcolo risorse possedute dagli agenti, incluso il chiamante
    public static int[] Risorse(Agent agents[], Cell[][] theBoard) {
        int[] arr = new int[agents.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
        for (int i = 0; i < Board.width; i++) {
            for (int j = 0; j < Board.height; j++) {
                if (theBoard[i][j].getType() == Cell.CellType.RISORSA && theBoard[i][j].owner != null && theBoard[i][j].ownerAge > 0) {
                    arr[theBoard[i][j].owner.getTeamId()]++;
                    // System.out.println(" RES (" + i + ", " + j + ")");
                }
            }
        }
        return arr;
    }

    public static int vitaMedia (Agent me, Agent [] agents){
        int media=0;
        for (int i=0; i<agents.length; i++){
            if (agents[i]!=me) {
                media = agents[i].getHealth() + media;
            }
        }
        return media/agents.length-1;
    }

    //trova le risorse di tutti escluso il chiamante
    public static int RisorseTot(Agent[] agents, Cell[][] theBoard, Agent me) {
        int ris = 0;
        for (int i = 0; i < Board.width; i++) {
            for (int j = 0; j < Board.height; j++) {
                if (theBoard[i][j].getType() == Cell.CellType.RISORSA && theBoard[i][j].owner != null && theBoard[i][j].ownerAge > 0 && theBoard[i][j].teamOwner != me.getTeamId()) {
                    ris++;
                }
            }
        }
        return ris;
    }

}
