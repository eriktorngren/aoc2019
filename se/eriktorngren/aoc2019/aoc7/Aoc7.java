package se.eriktorngren.aoc2019.aoc7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Aoc7 {

	public static void main(String[] args) {
		List<String> records = null;
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc7\\input.txt"))) {
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
		System.out.println(computer);

		for(String s : records) computer.add(Integer.valueOf(s));
		List<Integer> phase = new ArrayList<Integer>();
		for (int i=5; i<10; i++) {
			phase.add(i);
		}
		List<List<Integer>> phaseSettings = generatePerm(phase);

		System.out.println(phaseSettings);
		System.out.println(phaseSettings.size());
		int highestOutput = 0;
		for (int i = 0; i<phaseSettings.size(); i++) {
			int output = 0;
			output = runSequence(computer, phaseSettings.get(i));
			highestOutput = output > highestOutput ? output : highestOutput;
		}
		
		//System.out.println(runSequence(computer, phaseSettings.get(119)));
		System.out.println(highestOutput);
	}


	//4 43 432 4321
	public static List<List<Integer>> generatePerm(List<Integer> original) {
		if (original.size() == 0) {
			List<List<Integer>> result = new ArrayList<List<Integer>>(); 
			result.add(new ArrayList<Integer>()); 
			return result; 
		}
		Integer firstElement = original.remove(0);
		List<List<Integer>> returnValue = new ArrayList<List<Integer>>();
		List<List<Integer>> permutations = generatePerm(original);
		for (List<Integer> smallerPermutated : permutations) {
			for (int index=0; index <= smallerPermutated.size(); index++) {
				List<Integer> temp = new ArrayList<Integer>(smallerPermutated);
				temp.add(index, firstElement);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}

	public static int runSequence(List<Integer> initialComputer, List<Integer> phaseSettings) {
		int[] rows = new int[10];
		List<List<Integer>> computers = new ArrayList<List<Integer>>();
		for (int j = 0; j<10; j++) {
			computers.add(new ArrayList<Integer>(initialComputer));
		}
		int opcode = 0;
		int modeFirst;
		int modeSecond;
		int firstGet;
		int secondGet;
		int first;
		int second;
		int index;
		int input;
		int output = 0;
		int lastOutput = 0;
		int i = 0;
		List<Integer> computer;
		while (true) {

			for (int setting : phaseSettings) {
				computer = computers.get(setting);
				i = rows[setting];
				String current = computer.get(i).toString();
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
						if (rows[setting] == 0) {
							input = setting;
						} else {
							input = output;
						}
						//System.out.println("Input: " + input);
						computer.set(first, input);
						i += 2;
						rows[setting] = i;
					} else if (opcode == 4) {
						//System.out.println("Output: " + firstGet);
						output= firstGet;
						if (setting == phaseSettings.get(4)) {
							lastOutput = output;
						}
						i += 2;
						rows[setting] = i;
						computers.set(setting, computer);
						break;
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
				if (Integer.valueOf(current) == 99) {
					return lastOutput;
				}
				computers.set(setting, computer);
			}

		}
	}

}
