package org.cis.optur.engine;

public class Utils {
    public static int[][] getCopyOf(int[][] original){
        boolean a = original==null;
        boolean b = a;
        int[][] result = new int[original
                .length][original[0]
                .length];
        for (int i = 0; i < result.length; i++) {
            for (int i1 = 0; i1 < result[i].length; i1++) {
                result[i][i1] = original[i][i1];
            }
        }
        return result;
    }
}
