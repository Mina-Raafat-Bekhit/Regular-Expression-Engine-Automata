/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.toc;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Mega Store
 */
public class TaskTOC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner sc = new Scanner(System.in);

        while (true) {
        System.out.print("Enter Regular Expression ('exit' to quit): ");
        String regex = sc.next();

        if (regex.equalsIgnoreCase("exit")) break;

        String postfix = REpostfix.toPostfix(regex);
        NFA nfa = buildNFAFromPostfix(postfix);
        nfa.endState.isfinal = true;
        System.out.println(" NFA Structure :-");
        Set<Integer> visited = new HashSet<>();
        algorithm.printtransions(nfa.startState, visited);

        Set<Character> charSet = new HashSet<>();
        for (char c : regex.toCharArray()) {
            if (Character.isLetterOrDigit(c)) charSet.add(c);
        }
        char[] alphabet = new char[charSet.size()];
        int i = 0;
        for (char c : charSet) alphabet[i++] = c;

        List<DFA> myDFA = DFA.build(nfa, alphabet);
        algorithm.printDFA(myDFA);

        while (true) {
            System.out.print("Enter string to test ('reset' for new RE, 'exit' to quit): ");
            String input = sc.next();

            if (input.equalsIgnoreCase("exit")) {
                return; 
            }
            if (input.equalsIgnoreCase("reset")) {
                break; 
            }
            boolean result = DFA.simulate(myDFA, input);
            if (result) {
                System.out.println("Accepted");
            } else {
                System.out.println("Rejected");
            }
        }
    }
    
    }
    public static NFA buildNFAFromPostfix(String postfix) {
        Stack<NFA> stack = new Stack<>();

        for (char c : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stack.push(algorithm.character(c));
            } else if (c == '*') {
                stack.push(algorithm.star(stack.pop()));
            }else if (c == '+') {
                stack.push(algorithm.plus(stack.pop()));
            } else if (c == '.') {
                NFA b = stack.pop();
                NFA a = stack.pop();
                stack.push(algorithm.concatenate(a, b));
            } else if (c == '|') {
                NFA b = stack.pop();
                NFA a = stack.pop();
                stack.push(algorithm.union(a, b));
            }
        }
        return stack.pop();
    }
    
}
