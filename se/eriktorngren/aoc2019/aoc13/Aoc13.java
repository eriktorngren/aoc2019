package se.eriktorngren.aoc2019.aoc13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Aoc13 {

	static List<Long> computer = new ArrayList<Long>();
	static int relativeBase = 0;
	static int x;
	static int y;
	static List<List<Integer>> map;
	static boolean PART_1 = false;
	static int score;

	public static void main(String[] args) {
		List<String> records = null;
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc13\\input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records = Arrays.asList(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String s : records) computer.add(Long.valueOf(s));
		//System.out.println(computer);
		int computerSize = 10000;
		for (int i = 1; i<computerSize; i++ ) {
			computer.add((long) 0);
		}

		int mapSize = 100;
		map = new ArrayList<>();
		//start, opposite of usual cord(x,y) = map.get(y).get(x)
		for (int x=0; x<mapSize; x++) {
			List<Integer> yCords = new ArrayList<>();
			for (int y=0; y<mapSize; y++) {
				yCords.add(0);
			}
			map.add(yCords);
		}

		if (PART_1) {

			runSequence();
			int blockTiles = 0;
			for (List<Integer> yCords : map) {
				for (int tile : yCords) {
					if (tile == 2) {
						blockTiles++;
					}
				}
			}		
			System.out.println("There are " + blockTiles + " block tiles on the screen when the game exits");
		} else if (!PART_1) {
			computer.set(0, (long) 2);
			runSequence();
			System.out.println("You finshed the game with score: " + score);
		}
	}

	public static void runSequence() {
		int i = 0;
		String current = computer.get(0).toString();
		int opcode = 0;
		int modeFirst;
		int modeSecond;
		int modeIndex;
		long first;
		long second;
		int index;
		int iteration = 0;
		boolean prepareScore = false;
		int ball = 0;
		int paddle = 0;
		int input = 0;
		Scanner in = new Scanner(System.in);
		while (Integer.valueOf(current) != 99) {
			String modes = String.format("%05d", Integer.valueOf(current));
			//System.out.println(modes);
			opcode = Integer.parseInt(modes.substring(3));
			modeFirst = Character.getNumericValue(modes.charAt(2));
			modeSecond = Character.getNumericValue(modes.charAt(1));
			modeIndex = Character.getNumericValue(modes.charAt(0));
			first = i+1;
			second = i+2;
			index = i+3;
			if (opcode == 1) {
				computer.set(getWriteMode(modeIndex, index), getReadMode(modeFirst, first)+getReadMode(modeSecond, second));
				i += 4;
			} else if (opcode == 2) {
				computer.set(getWriteMode(modeIndex, index), getReadMode(modeFirst, first)*getReadMode(modeSecond, second));
				i += 4;
			} else if (opcode == 3) {
				//System.out.println("Give input:");
				if (paddle == ball) {
					input = 0;
				} else if (paddle < ball) {
					input = 1;
				} else if (paddle > ball) {
					input = -1;
				}
				computer.set(Math.toIntExact(getWriteMode(modeFirst, first)), (long) input);
				i += 2;
			} else if (opcode == 4) {
				//System.out.println("Output: " + getReadMode(modeFirst, first));
				if (iteration == 0) {
					if (Math.toIntExact(getReadMode(modeFirst, first)) != -1) {
						x = Math.toIntExact(getReadMode(modeFirst, first));
					} else {
						prepareScore = true;
					}		
					iteration = 1;
				} else if (iteration == 1) {
					if (!prepareScore) {
						y = Math.toIntExact(getReadMode(modeFirst, first));
					}		
					iteration = 2;
				} else if ( iteration == 2) {
					if (prepareScore) {
						score = Math.toIntExact(getReadMode(modeFirst, first));
						prepareScore = false;
					} else {
						map.get(y).set(x, Math.toIntExact(getReadMode(modeFirst, first)));
						if (Math.toIntExact(getReadMode(modeFirst, first)) == 4) {
							ball = x;
						} else if (Math.toIntExact(getReadMode(modeFirst, first)) == 3) {
							paddle = x;
						}
					}
					iteration = 0;
				}
				i += 2;
			} else if (opcode == 5) {
				if (getReadMode(modeFirst, first) != 0) {
					i = Math.toIntExact(getReadMode(modeSecond, second));
				} else {
					i += 3;
				}
			}  else if (opcode == 6) {
				if (getReadMode(modeFirst, first) == 0) {
					i = Math.toIntExact(getReadMode(modeSecond, second));
				} else {
					i += 3;
				}
			} else if (opcode == 7) {
				if (getReadMode(modeFirst, first) < getReadMode(modeSecond, second)) {
					computer.set(getWriteMode(modeIndex, index), (long) 1);
				} else {
					computer.set(getWriteMode(modeIndex, index), (long) 0);
				}
				i += 4;
			} else if (opcode == 8) {
				if (getReadMode(modeFirst, first) == getReadMode(modeSecond, second)) {
					computer.set(getWriteMode(modeIndex, index), (long) 1);
				} else {
					computer.set(getWriteMode(modeIndex, index), (long) 0);
				}
				i += 4;
			} else if (opcode == 9) {
				relativeBase += getReadMode(modeFirst, first);
				i += 2;
			} else {
				break;
			}
			current =  computer.get(i).toString();
		}
	}

	private static long getReadMode(int mode, long param) {
		if (mode == 0) {
			return computer.get(Math.toIntExact(computer.get(Math.toIntExact(param))));
		} else if (mode == 1) {
			return computer.get((int) param);
		} else {
			return (long) computer.get((int) (computer.get(Math.toIntExact(param))+relativeBase));
		}
	}

	private static int getWriteMode(int mode, long param) {
		if (mode == 0) {
			return Math.toIntExact(computer.get(Math.toIntExact(param)));
		} else if (mode == 1) {
			System.out.println("ERROR");
			return -1;
		} else {
			return  Math.toIntExact(computer.get(Math.toIntExact(param))+relativeBase);
		}
	}

}
