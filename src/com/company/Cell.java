package com.company;

import com.company.Agents.Agent;

public class Cell {
    int id;
    CellType type;
    boolean walkable;

    Agent owner;
    int teamOwner;
    int ownerAge;


    public enum CellType {
        ALTO_MARE,
        MARE,
        PIANURA,
        PIANURA1,
        PIANURA2,
        MONTAGNA,
        RISORSA
    };

    public Cell(int id, CellType type){
        this.id = id;
        this.type = type;
        this.ownerAge = 0;

    }

    public void setOwner(Agent agente){
        if (this.type == CellType.RISORSA)
            this.owner = agente;

    }

    public CellType getType(){
        return this.type;
    }


}
