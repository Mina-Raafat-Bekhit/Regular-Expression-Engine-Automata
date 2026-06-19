/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.toc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Mega Store
 */
public class algorithm {
    
    static int counter = 0 ;
    
    static State createState() {
        return new State(counter++);
    }
    
    static NFA character(char symbol) {
        State start = createState();
        State end = createState();
        start.addTransition(symbol, end);
        return new NFA(start, end) ;
    }
    
    static NFA concatenate(NFA a , NFA b ) {
        a.endState.addTransition('#', b.startState);
        return new NFA(a.startState, b.endState);
    }
    
    static NFA union(NFA nfaA, NFA nfaB) {
        State start = createState(); 
        State end = createState();   

        start.addTransition('#', nfaA.startState);
        start.addTransition('#', nfaB.startState);

        nfaA.endState.addTransition('#', end);
        nfaB.endState.addTransition('#', end);

        return new NFA(start, end);
    }
    
    static NFA star(NFA nfaA) {
        State start = createState();
        State end = createState();
        start.isfinal = true;
        
        start.addTransition('#', end);
        start.addTransition('#', nfaA.startState);

        nfaA.endState.addTransition('#', nfaA.startState);
        nfaA.endState.addTransition('#', end);

        return new NFA(start, end);
    }
    static NFA plus(NFA nfaA) {
        State start = createState();
        State end = createState();
        
        start.addTransition('#', nfaA.startState);
        nfaA.endState.addTransition('#', nfaA.startState);
        nfaA.endState.addTransition('#', end);
        
        return new NFA(start, end);
    }
    
    static void printtransions(State state, Set<Integer> visited) {
    if (visited.contains(state.id)) return;
    visited.add(state.id);

    for (Map.Entry<Character, Set<State>> entry : state.transitions.entrySet()) {
        for (State next : entry.getValue()) {
            System.out.println("State " + state.id + " --" + entry.getKey() + "--> State " + next.id);
            printtransions(next, visited);
        }
    }}
    public static void printDFA(List<DFA> dfaStates) {
    System.out.println("DFA :-");
    System.out.printf("%-10s | %-25s | %-8s | %-20s\n", 
                      "DFA ID", "NFA States (The Set)", "Final?", "Transitions");

    for (DFA dfa : dfaStates) {
        List<Integer> ids = new ArrayList<>();
        for (State s : dfa.nfaStates) {
            ids.add(s.id);
        }
        String nfaSetString = ids.toString(); 
        
        StringBuilder transitions = new StringBuilder();
        for (Map.Entry<Character, DFA> entry : dfa.transitions.entrySet()) {
            transitions.append("'").append(entry.getKey())
                       .append("' -> S").append(entry.getValue().id).append("  ");
        }
        System.out.printf("%-10d | %-25s | %-8b | %-20s\n", 
                          dfa.id, 
                          nfaSetString, 
                          dfa.isFinal, 
                          transitions.toString().isEmpty() ? "None (Dead State)" : transitions.toString());
    }
}
    public static Set<State> epsilonClosure(State s) {
    Set<State> closure = new HashSet<>();
    Stack<State> stack = new Stack<>();

    stack.push(s);
    closure.add(s); 

    while (!stack.isEmpty()) {
        State current = stack.pop();
        Set<State> nextStates = current.transitions.get('#');
        if (nextStates != null) {
            for (State next : nextStates) {
               if (!closure.contains(next)) {
                    closure.add(next);
                    stack.push(next);
               }    
            }
        }
    }
    return closure;
    }
    public static Set<State> move(Set<State> states, char symbol) {
    Set<State> result = new HashSet<>();
    for (State s : states) {
        Set<State> reachableStates = s.transitions.get(symbol);
        
        if (reachableStates != null) {
            result.addAll(reachableStates);
        }
    }
    
    return result;
    }
}
