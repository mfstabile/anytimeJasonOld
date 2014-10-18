/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringtestgenerator;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author MarcioNote
 */
public class StringTestGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        generatePerceptList(200, 200, 5);
    }

    public static void generatePerceptList(int amount, int maximumSize, int maxParameters) {
        for (int i = 0; i < amount; i++) {
            generatePercept(maximumSize, maxParameters);
        }
    }

    public static void generatePercept(int maximumSize, int maxParameters) {
        Random r = new Random();
        String percept = new BigInteger(r.nextInt(maximumSize) + 30, r).toString(32).replaceAll("[^A-Za-z]", "");

        int par = r.nextInt(maxParameters);
        if (par > 0) {
            percept += "(";
            for (; par > 0; par--) {
                percept += new BigInteger(r.nextInt(maximumSize) + 30, r).toString(32).replaceAll("[^A-Za-z]", "");
                if (par > 1) {
                    percept += ",";
                }
            }
            percept += ")";
        }
        percept += ".";

        System.out.println(percept);
    }

}
