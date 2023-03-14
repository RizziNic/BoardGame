package com.company.Agents;

import com.company.Cell;

import java.awt.*;

public class Davide_Shooter extends Davide implements Shooter{
    public Davide_Shooter(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "Destex_Shooter";

    }

    public Davide_Shooter(int teamId){
        super( teamId);
        this.type = "Destex_Shooter";

    }

    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        if (false /* Eè il caso di colpire */) {
            return chooseProjectile();
        }
        else return super.move(theBoard, agents);
    }

    @Override
    public char chooseProjectile() {
        return 'p';
    }
}
