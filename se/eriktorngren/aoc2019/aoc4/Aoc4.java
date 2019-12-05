package se.eriktorngren.aoc2019.aoc4;

import java.util.ArrayList;
import java.util.List;

public class Aoc4 {

	public static void main(String[] args) {
		int lowRange = 134564;
		int highRange = 585159;
		
		List listOfPasswords = new ArrayList<>();
		
		for (int i = lowRange; i<= highRange; i++) {
			if (adjacentDigitsSame(String.valueOf(i)) && neverDecrease(String.valueOf(i)) && noLargerGroup(String.valueOf(i))) {
				listOfPasswords.add(i);
			}
		}
		//System.out.println(listOfPasswords);
		System.out.println(listOfPasswords.size());
	}

	private static boolean noLargerGroup(String password) {
		return false;
	}



	private static boolean neverDecrease(String password) {
		int min = 0;
		for (int i = 0; i < password.length(); i++){
		    if (Integer.valueOf(password.substring(i, i+1)) < min) {
		    	return false;
		    } else {
		    	min = Integer.valueOf(password.substring(i, i+1));
		    }		    
		}
		return true;
	}

	private static boolean adjacentDigitsSame(String password) {
		for (int i = 1; i < password.length(); i++){
			if (Integer.valueOf(password.charAt(i)) == Integer.valueOf(password.charAt(i-1))) {
				return true;
			}
		}
		return false;
	}

}
