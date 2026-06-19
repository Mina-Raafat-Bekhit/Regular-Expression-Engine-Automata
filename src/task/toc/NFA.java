/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.toc;

/**
 *
 * @author Mega Store
 */
public class NFA {
    
    State startState;
    State endState;
    
    public NFA(State start, State end) {
        this.startState = start;
        this.endState = end;
    }
    
}
