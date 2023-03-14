package com.company.Agents;

import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;

public class IsoBot_Healer extends IsoBot implements Healer{


    public IsoBot_Healer(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "IsoBot_Healer";
        canHeal=true;

    }

    public IsoBot_Healer(int teamId){
        super(teamId);
        this.type = "IsoBot_Healer";
        color = new Color(255, 0, 0);
        canHeal=true;
    }


    @Override
    public char move(Cell[][] theBoard, Agent[] agents) {
        if (canHeal) {
            return takeCare();
        }
        else return super.move(theBoard, agents);


    }


    @Override
    public char takeCare() {
        Agent toHeal;
        for (int i = 0; i < Board.getAgents().length; i++) {

            toHeal = Sensor.findAllies(Board.getAgents(), this.teamId);
            if (Math.abs(this.posX - toHeal.getPosX()) <= 1 && Math.abs(this.posY - toHeal.getPosY()) <= 1) {
                if (toHeal.getHealth() >= 50) {
                    return 'h';

                } else {
                    return 'H';
                }

            }

        }
        return ' ';

    }
}
