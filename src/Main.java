import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main
{
    static Integer tryParse(String value)
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    static Integer getSum(char operator, int a, int b)
    {
        int sum = 0;
        switch(operator)
        {
            case '+':
                sum = a + b;
                break;
            case '-':
                sum = a - b;
                break;
            case '*':
                sum = a * b;
                break;
            case '/':
                sum = a / b;
                break;
        }
        return sum;
    }

    static String outputRome(int number)
    {
        int tens = number / 10;
        number %= 10;
        int units = number;

        StringBuilder toReturn = new StringBuilder();

        if (tens != 0)
        {
            if (tens == 10) return "C";

            if (tens == 9) toReturn.append("XC");
            else if (tens == 6) toReturn.append("LX");
            else if (tens == 5) toReturn.append("L");
            else if (tens == 4) toReturn.append("XL");
            else toReturn.append("X".repeat(tens));
        }

        if (units != 0)
        {
            if (units == 9) toReturn.append("IX");
            else if (units == 6) toReturn.append("VI");
            else if (units == 5) toReturn.append("V");
            else if (units == 4) toReturn.append("IV");
            else toReturn.append("I".repeat(units));
        }


        return toReturn.toString();
    }

    static Integer inputRome(String input) throws Exception
    {
        HashMap<String, Integer> alphabet = new HashMap<String, Integer>();
        alphabet.put("I", 1);
        alphabet.put("II", 2);
        alphabet.put("III", 3);
        alphabet.put("IV", 4);
        alphabet.put("V", 5);
        alphabet.put("VI", 6);
        alphabet.put("VII", 7);
        alphabet.put("VIII", 8);
        alphabet.put("IX", 9);
        alphabet.put("X", 10);

        int toReturn = 0;
        try
        {
            toReturn = alphabet.get(input);
        }
        catch (Exception e)
        {
            throw new Exception("Число больше 10.");
        }
        return toReturn;
    }
    public static String calc(String input) throws Exception
    {

        char[] operators = new char[] {'+','-','*','/'};

        var symbols = input.toCharArray();
        boolean toContinue = true;
        int i = 0;
        Character operator = null;

        while (toContinue)
        {
            for (var op : operators)
            {
                if (symbols[i] == op)
                {
                    operator = symbols[i];
                    toContinue = false;
                }
            }
            if(i == symbols.length - 1) toContinue = false;
            i++;
        }

        if (operator == null)
        {
            throw new Exception("Пример введен неправильно.");
        }

        var operands = input.split(Pattern.quote(operator.toString()));
        if (operands.length > 2) throw new Exception("Пример может содержать лишь 2 операнда и 1 оператор.");
        int a;
        int b;

        if (tryParse(operands[0]) == null && tryParse(operands[1]) == null)
        {
            a = inputRome(operands[0]);
            b = inputRome(operands[1]);

            int sum = getSum(operator, a, b);
            if (sum < 1) throw new Exception("Римские числа не могут быть меньше 1.");

            return outputRome(sum);
        }
        else if (tryParse(operands[0]) != null && tryParse(operands[1]) != null)
        {
            a = Integer.parseInt(operands[0]);
            b = Integer.parseInt(operands[1]);
            if (a > 10 || a < 1 || b > 10 || b < 1) throw new Exception("Числа могут быть только от 1 до 10.");

            return Integer.toString(getSum(operator, a, b));
        }
        else
        {
            throw new Exception("Используйте либо арабскую, либо римскую систему исчисления.");
        }
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);



        System.out.println("Введите пример:");
        var input = in.nextLine().replaceAll("\\s","");
        try
        {
            System.out.println(calc(input));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}