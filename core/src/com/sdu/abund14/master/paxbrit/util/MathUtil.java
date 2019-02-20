package com.sdu.abund14.master.paxbrit.util;


import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.Collections;

public class MathUtil {
    public static long toNanoSeconds(float timeInSeconds) {
        return (long) (timeInSeconds * 1000000000L);
    }

    public static double angleBetweenPoints(float x1, float y1, float x2, float y2) {
        final double deltaX = x2 - x1;
        final double deltaY = y2 - y1;
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return result < 0 ? 360d + result : result;
    }

    public static Vector2 unitVector(double angle) {
        Vector2 vector = new Vector2();
        vector.x = (float) Math.cos(Math.toRadians(angle));
        vector.y = (float) Math.sin(Math.toRadians(angle));
        return vector;
    }

    public static Vector2 offsetVector(float originX, float originY, double angle, float distance) {
        Vector2 offset = unitVector(angle);
        offset.x *= distance;
        offset.y *= distance;
        return new Vector2(originX, originY).add(offset);
    }

    public static int max(Integer... vals) {
        return Collections.max(Arrays.asList(vals));
    }
}
