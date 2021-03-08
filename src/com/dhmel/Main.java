package com.dhmel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //Приём выражения с очисткой от пробелов
        System.out.print("Введите выражение: ");
        Scanner console = new Scanner(System.in);
        String str = console.nextLine().replace(" ", "");
        while (str.equals("")) {
            System.err.print("Вы ничего не ввели. Введите выражение: ");
            str = console.nextLine().replace(" ", "");
        }

        //Подсчёт количества операторов во введённом выражении
        int operatorsCount = 0;
        String[] operators = {"+", "-", "*", "/"};
        for (String operator1 : operators) {
            if (!str.contains(operator1)) continue;
            if (str.indexOf(operator1) == 0) {
                System.err.println("Ошибка: Строка не может начинаться с оператора");
                System.exit(1);
            }
            if (str.indexOf(operator1) == str.length() - 1) {
                System.err.println("Ошибка: Строка не может оканчиваться оператором");
                System.exit(2);
            }
            operatorsCount = operatorsCount + str.split("\\" + operator1).length - 1;
        }

        //Проверка количества операторов в выражении
        if (operatorsCount > 1) {
            System.err.println("Ошибка: В строке не может быть более одного оператора");
            System.exit(3);
        } else if (operatorsCount == 0) {
            System.err.println("Ошибка: В строке нет операторов");
            System.exit(4);
        }

        //Разбиение строки на операнды
        Elements leftOperand = new Elements();
        Elements rightOperand = new Elements();

        String operator = "";

        for (String operator1 : operators) {
            if (!str.contains(operator1)) continue;

            leftOperand.content = str.split("\\" + operator1)[0];
            leftOperand.elementCheck();

            rightOperand.content = str.split("\\" + operator1)[1];
            rightOperand.elementCheck();

            operator = operator1;
        }

        if (leftOperand.arabic != rightOperand.arabic) {
            System.err.println("Ошибка: Один операнд введён арабскими цифрами, а другой римскими");
            System.exit(6);
        }

        //Вычисление результата
        Elements result = new Elements();
        result.value = result.operationResult(leftOperand.value, rightOperand.value, operator);
        result.arabic = leftOperand.arabic;

        //Если исходные данные были введены римскими цифрами, то конвертация результата в римскую запись
        if (result.arabic) {
            result.content = String.valueOf(result.value);
        } else result.content = result.arabicToRoman(result.value);

        //Вывод результата
        System.out.println(result.content);
    }
}

class Elements {
    String content;
    Boolean arabic;
    int value;

    void elementCheck() {
        try {
            value = Integer.valueOf(content);
            arabic = true;
        } catch (NumberFormatException e) {
            value = romanToArabic(content);
            arabic = false;
        }

        if (value < 1 || value > 10) {
            System.err.println("Ошибка: " + content + " - неверный операнд");
            System.exit(5);
        }
    }

    int operationResult(int left, int right, String operator) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                return left / right;
        }
        return 1000; //Среда разработки требует return
    }

    int romanToArabic(String Operand) {
        switch (Operand) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
            case "VII":
                return 7;
            case "VIII":
                return 8;
            case "IX":
                return 9;
            case "X":
                return 10;
            default:
                return -1;
        }
    }

    String arabicToRoman(int number) {
        String result = "";
        if (number == 0) {
            return "Результат равен 0, но среди римских цифр нет нуля!";
        } else if (number < 0) {
            result = "-";
            number = Math.abs(number);
        }

        int[] numbers = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < numbers.length; i++) {
            while (number / numbers[i] > 0) {
                result = result + romans[i];
                number = number - numbers[i];
            }
        }
        return result;
    }
}