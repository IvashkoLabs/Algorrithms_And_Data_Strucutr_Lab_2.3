import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.String;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/*Завдання першого рівня
Виконати такі дії:
– визначити тип вибірки без повторювань комбінаторної задачі
Дано Кількість кімнат у студентському
гуртожитку з одним вільним місцем – 14
Обчислити Скількома способами можна розмістити чотирнадцять першокурсників на вільні місця в гуртожитку
– розв’язати задачу для заданих вхідних даних

Завдання другого рівня:
– визначити тип вибірки з повторюванням комбінаторної задачі
згідно з варіантом завдання
Цифри десяткової системи числення;
обчислити кількість різних шифрів, що складаються з двох букв та трьох
цифр, якщо букви та цифри можуть повторюватися
букви латинського алфавіту (f – p)
– розв’язати задачу для заданих вхідних даних

Завдання третього рівня
        – записати до файлу повний перелік перестановок, розміщень
        або поєднань, отриманих під час розв’язання задачі завдання першого
рівня, алгоритмом лексикографічного порядку.
        Методичні рекомендації

Для виконання завдання першого та другого рівнів вхідні дані
необхідно увести набором на клавіатурі.
Завдання всіх рівнів використовуються комбінаторні задачі с

трьома типами вибірок: розміщення, перестановка та поєднання. Зверніть увагу, що задачі першого рівня – вибірки без повторень, а другого рівня – з повтореннями.
*/

public class Main
{
enum State {
    Initial,SwapNoRep,PlaceNoRep,Combinations,SwapWithReps,PlaceWithReps,CombinationsWithReps
}

enum Event {
    Repeat, NoRepeat, Order, NoOrder, Equal, NotEquall
}
    static String [] YesWords={"yes","yeah","true","1","так","!no","!false","y"};
    private static final String FILE_NAME = "words_lvl3.txt";
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args)
    {
        //State task1Type = determineCombinatoricsType();
        //System.out.println("\nTask type" + task1Type);
        solveTask1();

        //State task2Type = determineCombinatoricsType();
        //System.out.println("\nTask type " + task2Type);
        solveTask2();
        scanner.close();
    }
static boolean AskBool(String question)
{
    System.out.print(question);
    String input = scanner.nextLine().trim().toLowerCase();
    boolean variable=(Arrays.asList(YesWords).contains(input));
    System.out.print(variable ? "confirmed" : "Negative");
    return variable;
}

    static State determineCombinatoricsType()
    {
        boolean repeat = AskBool("Чи можливі повторення елементів ?  ");
        boolean order = AskBool("Чи важливий порядок ?  ");
        boolean equal = false;

        if (order)
            equal = AskBool("кількість елементів = кількість позицій ? ");


        if (!repeat)
        { // Без повторень
            if (order)
                return equal ? State.SwapNoRep : State.PlaceNoRep;
             else
                return State.Combinations;

        }
        else
        { // З повтореннями
            if (order)
                return equal ? State.SwapWithReps : State.PlaceWithReps;
             else
                return State.CombinationsWithReps;

        }

    }
    static void solveTask1()
    {

            System.out.println("\n 14 студентів розсіляють у 14 унікальних кімнат.");
            System.out.println("Студенти не можуть повторюватись, порядок заселення важливий, а n = k. має бути: SwapNoRep.");

        State type= determineCombinatoricsType();
                System.out.println("\nTask type" + type);
        System.out.print("\nAmount of elemetns n ( 14): ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        int k = 0;

        if (type == State.PlaceNoRep || type == State.Combinations) {
            System.out.print("amount of slots ");
            k = Integer.parseInt(scanner.nextLine().trim());
        }

        long result=0;
        String formula="";
        switch (type)
        {
            case SwapNoRep:
                result= factorial(n);
                formula=(" P(" + n + ") = " + n + "!");
                break;

            case PlaceNoRep:
                result = placementsNoRep(n, k);
                formula=("A(" + n + ", " + k + ") = " + n + "! / (" + n + "-" + k + ")!");

                break;

            case Combinations:
               result = combinationsNoRep(n, k);
                formula=("C(" + n + ", " + k + ") = " + n + "! / (" + k + "! * (" + n + "-" + k + ")!)");
                break;
            case  SwapWithReps:
                // Перестановки з повтореннями: P_n(n1, n2...) = n! / (n1! * n2! * ...)
                System.out.println("\n amount of unique elements ");
                int typesCount = Integer.parseInt(scanner.nextLine().trim());
                int[] counts = new int[typesCount];
                long denominator = 1;
                int totalElements = 0;
                StringBuilder countsStr = new StringBuilder();

                for (int i = 0; i < typesCount; i++) {
                    System.out.print("amount of elements of " + (i + 1) + "-th type ");
                    counts[i] = Integer.parseInt(scanner.nextLine().trim());
                    denominator *= factorial(counts[i]);
                    totalElements += counts[i];
                    countsStr.append(counts[i]).append(i < typesCount - 1 ? ", " : "");
                }

                result = factorial(totalElements) / denominator;
                formula = "P_" + totalElements + "(" + countsStr + ") = " + totalElements + "! / (" + countsStr.toString().replace(", ", "! * ") + "!)";
                break;
            case PlaceWithReps:
                // Розміщення з повтореннями: A_bar(n, k) = n^k
                System.out.print("length of selection");
                int kPlaceReps = Integer.parseInt(scanner.nextLine().trim());
                result = (long) Math.pow(n, kPlaceReps);
                formula = "A_bar(" + n + ", " + kPlaceReps + ") = " + n + "^" + kPlaceReps;
                break;
            case CombinationsWithReps:
                // Сполучення з повтореннями: C_bar(n, k) = C(n + k - 1, k)
                System.out.print("length of selection");
                int kCombReps = Integer.parseInt(scanner.nextLine().trim());
                result = combinationsNoRep(n + kCombReps - 1, kCombReps);
                formula = "C_bar(" + n + ", " + kCombReps + ") = (" + n + " + " + kCombReps + " - 1)! / (" + kCombReps + "! * (" + n + " - 1)!)";
                break;
            default:
                System.out.println("error");
                return;
        }
        System.out.println("Formula "+formula);
        System.out.println("Result " + result + " ways");
        System.out.print(" N for Narayana algorithm");
        int demoN = Integer.parseInt(scanner.nextLine().trim());
        generateLexicographicalPermutations(demoN, FILE_NAME);
    }

    static void solveTask2()
    {
            System.out.println("\n шифр з букв та цифр, що можуть повторюватися.");
            System.out.println("Символи можуть дублюватися, порядок важливий, елементів в алфавіті більше ніж позицій. Очікувався: PlaceWithReps.");
        State type= determineCombinatoricsType();
        System.out.println("\nTask type" + type);

        System.out.print("n1 size (11 for f-p ): ");
        int n1 = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("k1 length (2): ");
        int k1 = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("n2 size (10 for digits 0-9): ");
        int n2 = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("k2 length (3): ");
        int k2 = Integer.parseInt(scanner.nextLine().trim());

        long result = 0;
        if (type == State.PlaceWithReps || type == State.SwapWithReps)
        { // Якщо порядок важливий
            result = (long) (Math.pow(n1, k1) * Math.pow(n2, k2));
            System.out.println(" (n1^k1) * (n2^k2)");
        }
        else if (type == State.CombinationsWithReps)
        { // Якщо порядок не важливий
            long c1 = combinationsWithReps(n1, k1);
            long c2 = combinationsWithReps(n2, k2);
            result = c1 * c2;
            System.out.println("C_reps(n1,k1) * C_reps(n2,k2)");
        }
        else
        {
            System.out.println("вибірка з повтореннями required");
            return;
        }

        System.out.println("smpunt of combinations " + result);
    }
    private static long factorial(int n)
    {
        long res = 1;
        for (int i = 2; i <= n; i++) res *= i;
        return res;
    }
    private static long combinationsNoRep(int n, int k)
    {
        if (k > n) return 0;
        return factorial(n) / (factorial(k) * factorial(n - k));
    }

    private static long combinationsWithReps(int n, int k)
    {
        return combinationsNoRep(n + k - 1, k);
    }
    private static long placementsNoRep(int n, int k)
    {
        if (k > n) return 0;
        return factorial(n) / factorial(n - k);
    }
    // Алгоритм Нараяни Лексикографічний порядок

    private static void generateLexicographicalPermutations(int n, String fileName)
    {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            int count = 0;
            while (true)
            {
                writer.write(Arrays.toString(arr));
                writer.newLine();
                count++;

                int i = arr.length - 2;
                while (i >= 0 && arr[i] >= arr[i + 1]) i--;
                if (i < 0) break;

                int j = arr.length - 1;
                while (arr[j] <= arr[i]) j--;

                // Swap
                int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;

                // Reverse tail
                int start = i + 1, end = arr.length - 1;
                while (start < end) {
                    temp = arr[start]; arr[start] = arr[end]; arr[end] = temp;
                    start++; end--;
                }
            }
            System.out.println("created " + count + " sawps in file '" + fileName + "'.");
        }
        catch (IOException e)
        {
            System.err.println("error:" + e.getMessage());
        }
    }
}

