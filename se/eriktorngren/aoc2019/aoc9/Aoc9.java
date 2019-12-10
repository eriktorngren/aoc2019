package se.eriktorngren.aoc2019.aoc9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Aoc9 {

	public static void main(String[] args) {
		List<String> records = null;
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc9\\input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records = Arrays.asList(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Long> computer = new ArrayList<Long>();
		for(String s : records) computer.add(Long.valueOf(s));
		System.out.println(computer);
		int computerSize = 10000;
		for (int i = 1; i<computerSize; i++ ) {
			computer.add((long) 0);
		}
		runSequence(computer);
	}
	
	public static void runSequence(List<Long> computer) {
		int i = 0;
		int relativeBase = 0;
		String current = computer.get(0).toString();
		int opcode = 0;
		int modeFirst;
		int modeSecond;
		long firstGet;
		long secondGet;
		long first;
		long second;
		int index;
		Scanner in = new Scanner(System.in);
		while (Integer.valueOf(current) != 99) {
			String modes = String.format("%05d", Integer.valueOf(current));
			//System.out.println(modes);
			opcode = Integer.parseInt(modes.substring(3));
			modeFirst = Character.getNumericValue(modes.charAt(2));
			modeSecond = Character.getNumericValue(modes.charAt(1));
			first = computer.get(i+1);
			firstGet = getFromMode(modeFirst, first, relativeBase, computer);
			if (opcode == 1) {	
				second = computer.get(i+2);
				index = Math.toIntExact(computer.get(i+3));
				secondGet = getFromMode(modeSecond, second, relativeBase, computer);
				//System.out.println(index);
				computer.set(index, firstGet+secondGet);
				i += 4;
			} else if (opcode == 2) {	
				second = computer.get(i+2);
				index = Math.toIntExact(computer.get(i+3));		
				secondGet = getFromMode(modeSecond, second, relativeBase, computer);
				//System.out.println(index);
				computer.set(index, firstGet*secondGet);
				i += 4;
			} else if (opcode == 3) {
				System.out.println("Give input:");
				computer.set(Math.toIntExact(first), Long.valueOf(in.nextLine()));
				i += 2;
			} else if (opcode == 4) {
				System.out.println("Output: " + firstGet);
				i += 2;
			} else if (opcode == 5) {
				if (firstGet != 0) {
					second = computer.get(i+2);
					i = Math.toIntExact(getFromMode(modeSecond, second, relativeBase, computer));
				} else {
					i += 3;
				}
			}  else if (opcode == 6) {
				if (firstGet == 0) {
					second = computer.get(i+2);
					i = Math.toIntExact(getFromMode(modeSecond, second, relativeBase, computer));
				} else {
					i += 3;
				}
			} else if (opcode == 7) {
				second = computer.get(i+2);
				secondGet = getFromMode(modeSecond, second, relativeBase, computer);
				index = Math.toIntExact(computer.get(i+3));
				if (firstGet < secondGet) {
					computer.set(index, (long) 1);
				} else {
					computer.set(index, (long) 0);
				}
				i += 4;
			} else if (opcode == 8) {
				second = computer.get(i+2);
				secondGet = getFromMode(modeSecond, second, relativeBase, computer);
				index = Math.toIntExact(computer.get(i+3));
				if (firstGet == secondGet) {
					computer.set(index, (long) 1);
				} else {
					computer.set(index, (long) 0);
				}
				i += 4;
			} else if (opcode == 9) {
				relativeBase += computer.get(Math.toIntExact(firstGet));
				i += 2;
			} else {
				break;
			}
			current =  computer.get(i).toString();
		}
	}

	private static long getFromMode(int mode, long param, int relativeBase, List<Long> computer) {
		if (mode == 0) {
			return computer.get(Math.toIntExact(param));
		} else if (mode == 1) {
			return param;
		} else {
			return (long) computer.get(Math.toIntExact(param)+relativeBase);
		}
	}

}
