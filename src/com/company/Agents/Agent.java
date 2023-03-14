package com.company.Agents;

import com.company.Board;
import com.company.Cell;
import com.company.Sensor;

import java.awt.*;
import java.util.Random;

public class Agent {

    static Sensor sense;

    int teamId;
    int posX;
    int posY;

    boolean canHeal=false;
    String lastParams;

    Random rnd = new Random();
    Color color;
    Behaviour mood;

    String type;

    int status = 1;

    int minDamage = 5;
    int maxDamage = 10;

    int health = 100;
    int minHeal=5;
    int maxHeal=10;

    int age;
    int canUpgrade = 1;

    public enum Behaviour {
        DEFENSIVE,
        EXPANSIVE,
        BERZERKER,
        CAMPER,
        FUSION
    }


    public Agent(){
        // init with random position
        posX = (int) (rnd.nextFloat() * Board.getWidth());
        posY = (int) (rnd.nextFloat() * Board.getHeight());

        this.teamId = 0;
        //set random color
        color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());

        this.type = "Agent";
        this.age = 0;
    }

    public Agent(int teamId){
        // init with random position
        posX = (int) (rnd.nextFloat() * Board.getWidth());
        posY = (int) (rnd.nextFloat() * Board.getHeight());

        this.teamId = teamId;
        //set random color
        color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
        this.type = "Agent";
        this.age = 0;

    }


    public Agent(int teamId, Color color, int x, int y){
        // init with random position
        posX = x;
        posY = y;
        this.teamId = teamId;

        //set random color
        this.color = color;
        this.type = "Agent";
        this.age = 0;

    }

    public void printStatus(){
        System.out.println("Agent "+ this.color.toString() + " x: "+this.posX + " y: "+this.posY);
    }

    public int getAge(){
            return age;
    }

    public void canUpdate(){
        this.canUpgrade++;
    }

    public int getTeamId(){
        return teamId;
    }

    public int getStatus(){
        return status;
    }

    public int getHealth(){
        return health;
    }

    public int getPosX(){
        return posX;
    }

    public String getType(){
        return type;
    }

    public int getPosY(){
        return posY;
    }
    
    public int getCanUpagrade(){return canUpgrade;}

    public void setPosX(int newPos){
        this.posX = newPos;
    }
    public void setPosY(int newPos){
        this.posY = newPos;
    }

    public void setHealth(int newHealth){
        this.health = newHealth;
    }

    public void setStatus(int newState){
        this.status = newState;
    }

    public Color getColor(){
        return this.color;
    }

    public int getMinDamage(){
        return this.minDamage;
    }

    public int getMaxDamage(){
        return this.maxDamage;
    }

    public void ageAgent(){
        this.age++;
    }

    public boolean getCanHeal() {
        return canHeal;
    }

    public int getMinHeal() {
        return minHeal;
    }

    public int getMaxHeal() {
        return maxHeal;
    }

    public char move(Cell[][] theBoard, Agent[] agents) {
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
}
