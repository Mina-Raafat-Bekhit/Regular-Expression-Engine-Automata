/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.toc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mega Store
 */
public class State {
    
    public int id ;
    public boolean isfinal = false ;
    
    Map<Character, Set<State>> transitions = new HashMap<>();
    
    public State(int id) {
        this.id = id;
    }
    
    public void addTransition(char symbol, State toState) {
        transitions.computeIfAbsent(symbol, k -> new HashSet<>()).add(toState);
    }
}
