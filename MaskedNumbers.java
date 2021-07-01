import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.*;

public class Main {
    public static LinkedList revealStrRecursion(LinkedList combinationsList) {
        int currentListSize = combinationsList.size();

        //base case: stop recursion when no masked signs are found
        if (!combinationsList.get(0).toString().contains("*")) {
            return combinationsList;
        }

        for (int i = 0; i < currentListSize; i++) {
            String temp = combinationsList.get(i).toString();
            combinationsList.set(i, temp.replaceFirst("\\*", "0"));
            combinationsList.add(temp.replaceFirst("\\*", "1"));
        }

        revealStrRecursion(combinationsList); //recursion

        return combinationsList;
    }

    public static Stack<String> revealStrIteration(String input) {
        int numMaskedNumbers = input.length() - input.replace("*", "").length();
        Stack<String> firstTemp = new Stack<String>();
        Stack<String> secondTemp = new Stack<String>();

        firstTemp.push(input);

        for (int i = 0; i < numMaskedNumbers; i++) {
            if (i%2 == 0) { //even number
                while(!firstTemp.empty()) {
                    String maskedNumber = (String) firstTemp.pop();

                    secondTemp.push(maskedNumber.replaceFirst("\\*", "0"));
                    secondTemp.push(maskedNumber.replaceFirst("\\*", "1"));
                }
            } else { //odd number
                while(!secondTemp.empty()) {
                    String maskedNumber = (String) secondTemp.pop();
                    firstTemp.push(maskedNumber.replaceFirst("\\*", "0"));
                    firstTemp.push(maskedNumber.replaceFirst("\\*", "1"));
                }
            }
        }

        return (numMaskedNumbers%2 == 0) ? firstTemp : secondTemp;
    }

    public static void main(String[] args) {
        //Initialisations
        Scanner keyboard = new Scanner(System.in);
        boolean workingInput = false;
        LinkedList<String> recursiveCombinations = new LinkedList<String>();
        Stack<String> iterativeCombinations = new Stack<String>();

        System.out.println("Enter input: ");
        String input = keyboard.next();

        //Validate input
        while (!input.matches("^[01*]*$")) {
            System.out.println("\nInvalid input. Please try again: ");
            input = keyboard.next();
        }

        long startTime = System.nanoTime();
        recursiveCombinations.add(input); //first node
        revealStrRecursion(recursiveCombinations);
        long endTime = System.nanoTime();

        System.out.println("\nTime taken to compute recursive function: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        iterativeCombinations = revealStrIteration(input);
        endTime = System.nanoTime();

        System.out.println("Time taken to compute iterative function: " + (endTime - startTime) + " ns");

        //Writing to file
        try {
            FileWriter outputRecursion = new FileWriter("outputRecursion.txt");

            for (String combination: recursiveCombinations) {
                outputRecursion.write(combination + "\n");
            }

            outputRecursion.close();
            System.out.println("\nRecursive output successfully written to file.");
        } catch (IOException e) {
            System.out.println("An error occurred during the recursion output.");
            e.printStackTrace();
        }

        try {
            FileWriter outputIteration = new FileWriter("outputIteration.txt");

            for (String combination: iterativeCombinations) {
                outputIteration.write(combination + "\n");
            }

            outputIteration.close();
            System.out.println("Iteration output successfully written to file.");
        } catch (IOException e) {
            System.out.println("An error occurred during the iteration output.");
            e.printStackTrace();
        }
    }
}
