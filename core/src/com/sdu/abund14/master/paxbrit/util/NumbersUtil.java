package com.sdu.abund14.master.paxbrit.util;

public class NumbersUtil {
    public static long toNanoSeconds(float timeInSeconds) {
        return (long) (timeInSeconds * 1000000000L);
    }
}
