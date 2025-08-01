package com.devnmarki.engine.physics;

public class LayerCollision {
    private static final int MAX_LAYERS = 32;
    private static final boolean[][] matrix = new boolean[MAX_LAYERS][MAX_LAYERS];

    static {
        for (int i = 0; i < MAX_LAYERS; i++) {
            for (int j = 0; j < MAX_LAYERS; j++) {
                matrix[i][j] = true;
            }
        }
    }

    public static void setCollision(int layerA, int layerB, boolean collide) {
        matrix[layerA][layerB] = collide;
        matrix[layerB][layerA] = collide;
    }

    public static boolean canCollide(int layerA, int layerB) {
        return matrix[layerA][layerB];
    }
}
