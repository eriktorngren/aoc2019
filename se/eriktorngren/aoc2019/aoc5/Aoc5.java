package se.eriktorngren.aoc2019.aoc5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Aoc5 {

	public static void main(String[] args) {
		List<String> records = null;
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc5\\input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records = Arrays.asList(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Integer> computer = new ArrayList<Integer>();
		for(String s : records) computer.add(Integer.valueOf(s));
		runSequence(computer);
		System.out.println("Program finished");
	}

	private static void runSequence(List<Integer> computer) {
		int i = 0;
		String current = computer.get(0).toString();
		int opcode = 0;
		int modeFirst;
		int modeSecond;
		int firstGet;
		int secondGet;
		int first;
		int second;
		int index;

		Scanner in = new Scanner(System.in);
		while (Integer.valueOf(current) != 99) {
			String modes = String.format("%05d", Integer.valueOf(current));
			opcode = Integer.parseInt(modes.substring(3));
			modeFirst = Character.getNumericValue(modes.charAt(2));
			modeSecond = Character.getNumericValue(modes.charAt(1));
			first = computer.get(i+1);
			firstGet = modeFirst == 0 ? computer.get(first) : first;
			if (opcode == 1) {	
				second = computer.get(i+2);
				index = computer.get(i+3);
				secondGet = modeSecond == 0 ? computer.get(second) : second;
				computer.set(index, firstGet+secondGet);
				i += 4;
			} else if (opcode == 2) {	
				second = computer.get(i+2);
				index = computer.get(i+3);		
				secondGet = modeSecond == 0 ? computer.get(second) : second;
				computer.set(index, firstGet*secondGet);
				i += 4;
			} else if (opcode == 3) {
				System.out.println("Give input:");
				computer.set(first, Integer.valueOf(in.nextLine()));
				i += 2;
			} else if (opcode == 4) {
				System.out.println("Output: " + firstGet);
				i += 2;
			} else if (opcode == 5) {
				if (firstGet != 0) {
					second = computer.get(i+2);
					i = modeSecond == 0 ? computer.get(second) : second;
				} else {
					i += 3;
				}
			}  else if (opcode == 6) {
				if (firstGet == 0) {
					second = computer.get(i+2);
					i = modeSecond == 0 ? computer.get(second) : second;
				} else {
					i += 3;
				}
			} else if (opcode == 7) {
				second = computer.get(i+2);
				secondGet = modeSecond == 0 ? computer.get(second) : second;
				if (firstGet < secondGet) {
					computer.set(computer.get(i+3), 1);
				} else {
					computer.set(computer.get(i+3), 0);
				}
				i += 4;
			} else if (opcode == 8) {
				second = computer.get(i+2);
				secondGet = modeSecond == 0 ? computer.get(second) : second;
				if (firstGet == secondGet) {
					computer.set(computer.get(i+3), 1);
				} else {
					computer.set(computer.get(i+3), 0);
				}
				i += 4;
			} else {
				break;
			}
			current =  computer.get(i).toString();
		}
	}
}
