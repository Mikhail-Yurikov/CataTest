package catatest;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final char[] roman = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
    private static final int[] arabian = {1, 5, 10, 50, 100, 500, 1000};

    public static void main(String[] args) throws Exception {
        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc(String input) throws Exception {
        String[] digitAndAction = input.split(" ");
        int result = 0;
        if (digitAndAction.length > 3) {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else if (digitAndAction.length < 3) {
            throw new Exception("строка не является математической операцией");
        } else if (!digitAndAction[1].matches("(.*)[*\\-+/](.*)")) {
            throw new Exception("Такую операцию я делать не умею");
        } else {
            if (digitAndAction[0].matches("(.*)[1-9](.*)") && digitAndAction[2].matches("(.*)[1-9](.*)")) {
                int a = Integer.parseInt(digitAndAction[0]);
                int b = Integer.parseInt(digitAndAction[2]);
                if ((a > 10 || a < 1) || (b > 10 || b < 1)) {
                    throw new Exception("числа должны быть от 1 до 10 включительно");
                } else
                    switch (digitAndAction[1]) {
                        case "-":
                            result = a - b;
                            break;
                        case "+":
                            result = a + b;
                            break;
                        case "/":
                            result = a / b;
                            break;
                        case "*":
                            result = a * b;
                            break;
                    }
                return Integer.toString(result);

            } else if (digitAndAction[0].matches("(.*)[IVXLCDM](.*)") && digitAndAction[2].matches("(.*)[IVXLCDM](.*)")) {
                int c = romanToArabic(digitAndAction[0]);
                int d = romanToArabic(digitAndAction[2]);
                switch (digitAndAction[1]) {
                    case "-":
                        result = c - d;
                        break;
                    case "+":
                        result = c + d;
                        break;
                    case "/":
                        result = c / d;
                        break;
                    case "*":
                        result = c * d;
                        break;
                }
                if (result <= 0) {
                    throw new Exception("в римской системе нет отрицательных чисел");
                } else {
                    return arabicToRoman(result);
                }

            } else {
                throw new Exception("используются одновременно разные системы счисления");
            }
        }
    }


    public static int romanToArabic(String input) throws Exception {
        int result = 0;
        int buf = 0;
        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < roman.length; j++) {
                if (input.charAt(i) == roman[j]) {
                    if (arabian[j] > buf) {
                        result += arabian[j] - 2 * buf;
                        buf = arabian[j];
                        break;
                    } else {
                        result += arabian[j];
                        buf = arabian[j];
                        break;
                    }
                }
            }
        }
        if (result > 10 || result < 0) {
            throw new Exception("числа должны быть от 1 до 10 включительно");
        } else {
            return result;
        }
    }

    public static String arabicToRoman(int n) {
        int thousands = n / 1000;
        int halfThosands = (n % 1000) / 500;
        int hundreds = (n % 500) / 100;
        int halfHudreds = (n % 100) / 50;
        int dozens = (n % 50) / 10;
        int unitsFive = (n % 10) / 5;
        int units = n % 5;
        String romanicus = "";
        if (thousands > 0) {
            for (int i = 0; i < thousands; i++) {
                romanicus += 'M';
            }
        }
        if (halfThosands != 0 && hundreds != 4) {
            romanicus += 'D';
        }
        switch ((n % 1000) / 100) {
            case 4:
                romanicus += 'C';
                romanicus += 'D';
                break;
            case 9:
                romanicus += 'C';
                romanicus += 'M';
                break;

            default:
                for (int i = 0; i < hundreds; i++) {
                    romanicus += 'C';
                }
                break;
        }
        if (halfHudreds != 0 && dozens != 4) {
            romanicus += 'L';
        }
        switch ((n % 100) / 10) {
            case 9:
                romanicus += 'X';
                romanicus += 'C';
                break;
            case 4:
                romanicus += 'X';
                romanicus += 'L';
                break;
            default:
                for (int i = 0; i < dozens; i++) {
                    romanicus += 'X';
                }
                break;
        }
        if (unitsFive != 0 && units != 4) {
            romanicus += 'V';
        }
        switch (n % 10) {
            case 9:
                romanicus += 'I';
                romanicus += 'X';
                break;
            case 4:
                romanicus += 'I';
                romanicus += 'V';
                break;
            default:
                for (int i = 0; i < units; i++) {
                    romanicus += 'I';
                }
                break;
        }
        return romanicus;
    }
}
