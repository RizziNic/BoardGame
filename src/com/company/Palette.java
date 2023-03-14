package com.company;

import java.awt.*;

public class Palette {

    static Color[] palette = null;

    public static Color getColor(Cell.CellType type){
        return palette[(type.ordinal())];
    }

    public static Color[] getPalette(){
        return palette;
    }

    public static void generateGamePallette(){
        palette = new Color[8];
        palette[0] = new Color(45,183,192);
        palette[1] = new Color(111,244,244);
        palette[2] = new Color(76,28,12);
        palette[3] = new Color(175, 120, 86, 253);
        palette[4] = new Color(170, 187, 204, 255);
        palette[5] = new Color(187,160,133);
        palette[6] = new Color(220, 189, 20);

    }

}
