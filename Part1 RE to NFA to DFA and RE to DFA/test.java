package complier_project_part1;

import java.util.Scanner;



public class test {

	public static void main(String[] args) {
            
		Scanner in = new Scanner(System.in);
		System.out.println("input a regular expression");
		String re = in.nextLine();
		System.out.println("Regular Expression:" + re);
		NFA nfa = new NFA(re);
		nfa.add_join_symbol();
		nfa.postfix();
		nfa.re2nfa();
		nfa.print();
		
		DFA dfa = new DFA(nfa.getPair(),nfa.getLetter());
		dfa.createDFA();
		dfa.printDFA();

	}
}
