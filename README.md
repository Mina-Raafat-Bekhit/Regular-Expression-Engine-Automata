# Regular-Expression-Engine-Automata
A Java application that take Regular Expressions (RE) and constructs equivalent Non Deterministic Finite Automata (NFA).  Implemented algorithms to convert the generated NFA into a Deterministic Finite Automata (DFA).  Designed a string-matching that simulates the DFA execution to check the acceptability of user input string in this RE.
# How it works
take RE and turn it to postfix by Fn toPostfix(String regex) in class REpostfix. 
apply the propirtys of RE by the Fn buildNFAFromPostfix(String postfix) using class algorithm.
Build and print NFA structure uses printtransions(State state, Set<Integer> visited) in the algorithm class to perform a recursion over the NFA graph, printing all states and character transitions while avoiding infinite loops using a visited set.
store the alphabet in array.
Convert NFA to DFA uses build(NFA nfa, char[] alphabet) in the DFA class to convert the NFA into a DFA using the Subset Construction Algorithm by:
*Computing the epsilonClosure to determine the initial and subsequent DFA states.
*Utilizing a Queue (unprocessedStates) for tracking new states.
*Applying the move function across the extracted alphabet to map out and construct the final DFA transition table while correctly flagging final (accepting) states.
Check acceptability of the input strings uses simulate(List<DFA> dfaStates, String input) in the DFA class it processes the user input string character by character traversing the transition table if a character leads to an undefined transition (null), it rejects the string otherwise it returns true only if the execution halts on a designated Final (Accepting) State.
