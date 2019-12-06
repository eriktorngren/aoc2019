package se.eriktorngren.aoc2019.aoc2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Aoc2 {
	static int noun = 0;
	static int verb = 0;
	
	public static void main(String[] args) {
		List<String> records = null;
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc2\\input.txt"))) {
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
		//restore the gravity assist program
		int output = 19690720;
		testProgram(output, computer);
		int answer = 100 * noun + verb;
		System.out.println(answer);
	}

	private static void testProgram(int output, List<Integer> computer) {
		int testOutput = 0;
		while (noun < 100) {
			while (verb < 100) {
				testOutput  = runSequence(noun, verb, computer);
				if (testOutput == output) {
					return;
				}
				verb++;
			}
			verb = 0;
			noun++;
		}
		if (noun ==100) {
			System.out.println("NOne found");
			return;
		}
	}

	private static int runSequence(int noun, int verb, List<Integer> computer) {
		List<Integer> testComputer = new ArrayList<Integer>(computer);
		testComputer.set(1, noun);
		testComputer.set(2, verb);
		int i = 0;
		int current = testComputer.get(0);
		int first;
		int second;
		int index;
		while (current != 99) {
			first = testComputer.get(i+1);
			second = testComputer.get(i+2);
			index = testComputer.get(i+3);
			if (current == 1) {
				testComputer.set(index, testComputer.get(first)+testComputer.get(second));
			} else if (current == 2) {
				testComputer.set(index, testComputer.get(first)*testComputer.get(second));
			} else {
				break;
			}
			i += 4;
			current =  testComputer.get(i);
		}
		return testComputer.get(0);
	}
}
