package ru.vsu.amm.infosecurity;

import java.util.Scanner;

public class Main {

    private static int[] lineOrder;
    private static int[] columnOrder;
    private static String input;
    private static Scanner in = new Scanner(System.in);
    private static int n, m;

    public static void main (String[] args) {
        System.out.println("First of all type \"E\" if you want to encrypt "
            + "text or type \"D\" if you want do decrypt encrypted text");

        boolean isEncryption = setEncryption();

        initialize();

        String result;
        if (isEncryption) {
            result = encrypt(input);
        } else {
            result = decrypt(input);
        }

        System.out.println(
            (isEncryption ? "Encrypted " : "Decrypted ") + " " + "string is\n "
                + result);
    }

    private static boolean setEncryption () {
        String s;
        do {
            s = in.next();
            s = s.toUpperCase();
        } while (!"E".equals(s) && !"D".equals(s));

        return "E".equals(s);
    }

    private static void initialize () {
        promptForUserString();
        do {
            input = in.nextLine();
        } while (input.length() == 0);

        setNM(input.length());

        lineOrder = new int[n];
        columnOrder = new int[m];
        promptForNumbers();
        do {
            initUserSequences();
        } while (!validateSequences());
    }

    private static String encrypt (String input) {
        char[][] matrix = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[lineOrder[i]][j] = (i * m + j < input.length())
                    ? input.charAt(i * m + j)
                    : ' ';
            }
        }

        StringBuilder result = new StringBuilder();
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                result.append(matrix[i][columnOrder[j]]);
            }
        }

        return result.toString();
    }

    private static String decrypt (String input) {
        char[][] matrix = new char[n][m];
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                matrix[i][columnOrder[j]] = ((i + j * n) < input.length())
                    ? input.charAt(i + j * n)
                    : ' ';
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.append(matrix[lineOrder[i]][j]);
            }
        }

        return result.toString();
    }

    private static void promptForUserString () {
        System.out.print("Please type non-zero length string\n");
    }

    private static void setNM (int strLen) {
        n = (int)Math.sqrt(strLen);
        m = (int)Math.ceil(strLen / (double)n);
    }

    private static void promptForNumbers () {
        System.out.println("Please input " + n + " numbers from 1 to " + n +
            " in arbitrary order" +
            "\n" + " and " + m + " numbers from 1 to " + m + ""
            + " in arbitrary order");
    }

    private static void initUserSequences () {
        for (int i = 0; i < n; i++) {
            lineOrder[i] = in.nextInt() - 1;
        }
        for (int i = 0; i < m; i++) {
            columnOrder[i] = in.nextInt() - 1;
        }
    }

    private static boolean validateSequences () {
        return validateLineOrder() && validateColumnOrder();
    }

    private static boolean validateLineOrder () {
        int[] test = new int[n];
        for (int aLineOrder : lineOrder) {
            if (0 <= aLineOrder && aLineOrder < n) {
                test[aLineOrder]++;
            }
        }
        for (int b : test) {
            if (b != 1) {
                promptForNumbers();
                return false;
            }
        }

        return true;
    }

    private static boolean validateColumnOrder () {
        int[] test = new int[m];
        for (int aColumnOrder : columnOrder) {
            if (0 <= aColumnOrder && aColumnOrder < m) {
                test[aColumnOrder]++;
            }
        }
        for (int b : test) {
            if (b != 1) {
                System.out.println("Please input only numbers from 1 to " + m);
                promptForNumbers();
                return false;
            }
        }
        return true;
    }

    private static void show (char[][] matrix) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
