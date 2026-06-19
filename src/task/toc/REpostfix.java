/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.toc;

import java.util.Stack;

/**
 *
 * @author Mega Store
 */
public class REpostfix {
    
    private static int priorty(char c) {
        switch (c) {
            case '+': return 4;
            case '*': return 3;
            case '.': return 2;
            case '|': return 1;
            default: return 0;
        }
    }

    public static String toPostfix(String regex) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> s = new Stack<>();
        
        String readyre = insertConcatDots(regex);

        for (char symbol : readyre.toCharArray()) {
            if (Character.isLetterOrDigit(symbol)) {
                postfix.append(symbol);
            } else if (symbol == '(') {
                s.push(symbol);
            } else if (symbol == ')') {
                while (!s.isEmpty() && s.peek() != '(') {
                    postfix.append(s.pop());
                }
                if (!s.isEmpty()) s.pop();
            } else {
                while (!s.isEmpty() && 
                       priorty(s.peek()) >= priorty(symbol)) {
                    postfix.append(s.pop());
                }
                s.push(symbol);
            }
        }
        while (!s.isEmpty()) {
            postfix.append(s.pop());
        }
        return postfix.toString();
    }
    public static String insertConcatDots(String regex) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < regex.length(); i++) {
        char c1 = regex.charAt(i);
        result.append(c1);

        if (i + 1 < regex.length()) {
            char c2 = regex.charAt(i + 1);
            if (isOperand(c1) || c1 == ')' || c1 == '*' || c1 == '+') {
                if (isOperand(c2) || c2 == '(') {
                    result.append('.');
                    }
                }
            }
        }
        return result.toString();
    }

    private static boolean isOperand(char c) {
        return Character.isLetterOrDigit(c);
    }
}
