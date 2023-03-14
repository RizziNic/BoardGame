package com.company;

public class Game {

    static boolean playing = true;
    static int turno=0;
    static int maxTurni = 5000;

    public static void initGame() {
        Board.reset();
        Board.init();
    }

    public static int RunGame() {
        turno = 0;
        Board.teams();
        while (Board.teamsAlive()) {
            Board.performTurno();
            //System.out.println(turno);
            if (Board.GRAPHICS) Board.printBoard();
            if (Board.DEBUG) Board.printAgentStatus();
            Board.teams();
            turno++;

            if (turno > maxTurni){
                return -1;
            }
        }
        return 0;
    }

    public static int getTurno() {
        return turno;
    }
}