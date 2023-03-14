package com.company;

import zuclib.GraficaSemplice;

public class Main {

    public static void main(String[] args) {
        int partite = 1000;
        int[] victoryTeams = new int[Board.teamNumber];
        runLoop(partite, victoryTeams);
        stampaStats(partite, victoryTeams);
    }

    public static void runLoop(int partite, int[] victoryTeams) {
        for (int i = 0; i < partite; i++) {
            Game.initGame();
            System.out.println(i);
            if (Game.RunGame() == 0)
                victoryTeams[Board.winner().getTeamId()]++;
        }
    }

    public static void stampaStats(int partite, int[] vincite) {
        double[] stats = new double[Board.teamNumber];
        for (int i = 0; i < vincite.length; i++) {
            stats[i] = ((double) vincite[i]) / ((double) partite) * 100.0;
        }
        for (int i = 0; i < stats.length; i++) {
            System.out.println("Team " + AgentFactory.warriors[i] + ": " + stats[i] + "%");
        }
    }
}