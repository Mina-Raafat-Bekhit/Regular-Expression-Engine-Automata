/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.toc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author Mega Store
 */
public class DFA {
    
    int id;
    Set<State> nfaStates; 
    Map<Character, DFA> transitions = new HashMap<>();
    boolean isFinal = false;

    public DFA(int id, Set<State> nfaStates) {
        this.id = id;
        this.nfaStates = nfaStates;
    }
    public static List<DFA> build(NFA nfa, char[] alphabet) {
    List<DFA> dfaStates = new ArrayList<>();
    Queue<DFA> unprocessedStates = new LinkedList<>();
    Map<Set<State>, DFA> stateMap = new HashMap<>();

    Set<State> startSet = algorithm.epsilonClosure(nfa.startState);
    DFA startDFA = new DFA(0, startSet);
    
    for (State ifaccept : startSet) {        
        if (ifaccept.isfinal) startDFA.isFinal = true;
    }

    dfaStates.add(startDFA);
    stateMap.put(startSet, startDFA);
    unprocessedStates.add(startDFA);

    int idCounter = 1;

    while (!unprocessedStates.isEmpty()) {
        DFA currentDFA = unprocessedStates.poll();

        for (char symbol : alphabet) {
            Set<State> moveRes = algorithm.move(currentDFA.nfaStates, symbol);
            if (moveRes.isEmpty()) continue;
            
            Set<State> closureRes = new HashSet<>();
            for (State s : moveRes) {
                closureRes.addAll(algorithm.epsilonClosure(s));
            }

            if (!stateMap.containsKey(closureRes)) {
                DFA newState = new DFA(idCounter++, closureRes);
                
                for (State s : closureRes) {
                    if (s.isfinal) {
                        newState.isFinal = true;
                        break;
                    }
                }
                
                dfaStates.add(newState);
                stateMap.put(closureRes, newState);
                unprocessedStates.add(newState);
            }
            
            currentDFA.transitions.put(symbol, stateMap.get(closureRes));
        }
    }
    return dfaStates;
}
    public static boolean simulate(List<DFA> dfaStates, String input) {
        
    DFA currentState = dfaStates.get(0); 

    for (char symbol : input.toCharArray()) {
        DFA nextState = currentState.transitions.get(symbol);
        if (nextState == null) {
            return false;
        }
        currentState = nextState;
    }
    return currentState.isFinal;
}
}
