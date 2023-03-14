package com.company.Agents;

import com.company.Agents.Agent;
import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;
import java.util.Arrays;

public class Nicoc extends Agent {

    public Nicoc(int teamId, Color color, int x, int y){
        super( teamId,  color,  x,  y);
        this.type = "Nicoc";
    }

    public Nicoc(int teamId){
        super( teamId);
        this.type = "Nicoc";

    }

}
