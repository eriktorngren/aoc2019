package se.eriktorngren.aoc2019.aoc4;

import java.util.ArrayList;
import java.util.List;


public class Aoc4 {

	public static void main(String[] args) {
		int lowRange = 134564;
		int highRange = 585159;	
		List<Integer> listOfPasswords = new ArrayList<>();	
		for (int i = lowRange; i<= highRange; i++) {
			if (neverDecrease(String.valueOf(i)) && adjacentDigitsSame(String.valueOf(i))) {
				listOfPasswords.add(i);
			}
		}
		System.out.println(listOfPasswords.size());
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
		int digits=1;
		for (int i = 1; i < password.length(); i++){
			if (Integer.valueOf(password.charAt(i)) == Integer.valueOf(password.charAt(i-1))) {
				digits++;	
			}
		}
		if (digits > 5) {
			return false;
		} else if (digits > 2) {
			return control(password, digits);
		} else if (digits == 2) {
			return true;
		}
		return false;
	}


	private static boolean control(String password, int digits) {
		if (digits > 4) {
			if (password.charAt(1) == password.charAt(password.length()-1) || password.charAt(0) == password.charAt(password.length()-2)) {
				return false;
			}
		}
		if (digits == 4) {			
			long count = password.chars().filter(ch -> ch == password.charAt(2)).count();
			if (count ==4) {
				return false;
			}
			return true;
		}
		long occurances = 0;
		for (int i = 0; i < password.length(); i++){
			final int index = i;
			occurances = password.chars().filter(ch -> ch == password.charAt(index)).count() > occurances ? password.chars().filter(ch -> ch == password.charAt(index)).count() : occurances;
		}
		return occurances == 3 ? false : true;
	}

}
