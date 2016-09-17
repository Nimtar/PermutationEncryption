package ru.vsu.amm.infosecurity;

import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);
    static int n = 2, m = 3;

    public static void main (String[] args) {
        String input = in.next();
        if (input.length() == 0) {
            System.out.println("Please type non-zero length string");
            return;
        }

        setNM(input.length());
        String result = encrypt(input);

        decrypt(result);
    }

    private static void setNM (int strLen) {
        n = (int)Math.sqrt(strLen);
        m = (int)Math.ceil(strLen / n);
    }

    private static String encrypt (String input) {
        int[] lineOrder = new int[n];
        int[] columnOrder = new int[m];
        for (int i = 0; i < n; i++) {
            lineOrder[i] = in.nextInt() - 1;
        }
        for (int i = 0; i < m; i++) {
            columnOrder[i] = in.nextInt() - 1;
        }

        char[][] matrix = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[lineOrder[i]][j] = (i * m + j < input.length())
                    ? input.charAt(i * m + j)
                    : ' ';
            }
        }

        show(matrix);
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                result.append(matrix[i][columnOrder[j]]);
            }
        }

        System.out.println(result.toString());
        return result.toString();
    }

    private static String decrypt (String input) {
        int[] lineOrder = new int[n];
        int[] columnOrder = new int[m];

        for (int i = 0; i < n; i++) {
            lineOrder[i] = in.nextInt() - 1;
        }
        for (int i = 0; i < m; i++) {
            columnOrder[i] = in.nextInt() - 1;
        }

        char[][] matrix = new char[n][m];
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                matrix[i][columnOrder[j]] = ((i + j * n) < input.length())
                    ? input.charAt(i + j * n)
                    : ' ';
            }
        }

        show(matrix);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.append(matrix[lineOrder[i]][j]);
            }
        }
        System.out.println(result.toString());
        return result.toString();
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
