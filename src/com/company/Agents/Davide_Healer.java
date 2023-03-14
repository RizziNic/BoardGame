package com.company.Agents;

import com.company.Cell;

import java.awt.*;

public class Davide_Healer extends Davide implements Healer{
    public Davide_Healer(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "Destex_Healer";
        canHeal=true;

    }

    public Davide_Healer(int teamId){
        super( teamId);
        this.type = "Destex_Healer";
        canHeal=true;

    }


    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        if (false /* è il caso di curare */) {
            return takeCare();
        }
        else return super.move(theBoard, agents);


    }


    @Override
    public char takeCare() {
        if (rnd.nextBoolean()){
            return 'h'; /* ---> lesione minima (-5 a me , + 10 a te) */

        }
        else {
            return 'H';  /* ---> lesione massima (-10 a me , + 20 a te) */

        }
    }
}
