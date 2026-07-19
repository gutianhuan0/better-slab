package com.betterslab.util;







public enum PlacementMode {
    
    AUTO_H,
    
    AUTO_V,
    
    LEFT,
    
    RIGHT,
    
    FRONT,
    
    BACK,
    
    TOP,
    
    BOTTOM;

    public static PlacementMode fromId(int id) {
        if (id < 0 || id >= values().length) return AUTO_H;
        return values()[id];
    }

    public int getId() {
        return ordinal();
    }

    
    public boolean isSpecific() {
        return this != AUTO_H && this != AUTO_V;
    }

    
    public boolean isVertical() {
        return this == LEFT || this == RIGHT || this == FRONT || this == BACK;
    }
}
