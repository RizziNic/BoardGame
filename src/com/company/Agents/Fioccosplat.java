package com.company.Agents;

import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;
import java.util.Random;

import static com.company.Agents.Agent.Behaviour.EXPANSIVE;

public class Fioccosplat extends Agent {

    public Fioccosplat(int teamId, Color color, int x, int y) {
        super(teamId, color, x, y);
        this.type = "Fioccosplat";

    }

    public Fioccosplat(int teamId) {
        super(teamId);
        this.type = "Fioccosplat";

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
        Random rnd = new Random();
        int x = rnd.nextInt() * 10;
        int y = rnd.nextInt();
        if (this.mood == Behaviour.EXPANSIVE) {
            // risorsa pià vicina
            int[] target = Sensor.findRisorsa(this);
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.BERZERKER) {
            // risorsa pià vicina
            int[] target = Sensor.findEnemy(Board.getAgents(), this.teamId);
            if (target == null) {
                return ' ';
            }
            return TargetPositionToDirection(target);
        }
        if (this.mood == Behaviour.DEFENSIVE) {
            // corri via
            double dens = Sensor.playerDens(this);
            if (dens <= 10) {
                return Sensor.direzioneVerso(this, x, y);
            }
        }
        return ' ';
    }


    public Agent.Behaviour ComputeCurrentState() {
        int divRisorse = 0;

        if (Board.getResources() <= 16)
            divRisorse = 2;
        if (Board.getResources() <= 32 && Board.getResources() > 16)
            divRisorse = 3;
        if (Board.getResources() <= 64 && Board.getResources() > 32)
            divRisorse = 4;

        int[] risorse = Sensor.Risorse(Board.getAgents(), Board.getBoard());

        if (risorse[this.teamId] < (Board.getResources() / divRisorse))
            return Behaviour.EXPANSIVE;
        else if (this.health <= 60) {
            return Behaviour.DEFENSIVE;
        } else if (risorse[this.teamId] >= (Board.getResources() / divRisorse)) {
            return Behaviour.BERZERKER;
        }
        return Behaviour.EXPANSIVE;
    }


    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        this.mood = ComputeCurrentState();
        return Strategy();
    }
}