package se.eriktorngren.aoc2019.aoc11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aoc11 {

	static List<Long> computer = new ArrayList<Long>();
	static int relativeBase = 0;
	static List<List<Integer>> map;
	static int x;
	static int y;
	static int direction;
	static Map<List<Integer>, Boolean> painted;
	
	static boolean PART_2 = true;

	public static void main(String[] args) {
		List<String> records = null;
		try (BufferedReader br = new BufferedReader(new FileReader("/Users/Erik/Documents/AOC2019/aoc2019/se/eriktorngren/aoc2019/aoc11/input.txt"))) {
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
		for (int x=0; x<mapSize; x++) {
			List<Integer> yCords = new ArrayList<>();
			for (int y=0; y<mapSize; y++) {
				yCords.add(0);
			}
			map.add(yCords);
		}

		//start, opposite of usual cord(x,y) = map.get(y).get(x)
		x = 50;
		y = 50;
		direction = 0;
		// part 2
		if (PART_2) {
			map.get(y).set(x, 1);
		}
		
		painted = new HashMap();
		runSequence();
		if (!PART_2) {
			System.out.println("Finished! Painted " + painted.keySet().size() + " unique bricks");//1686
		}
		
		if (PART_2) {
			for (List<Integer> yCords : map) {
				for (int y : yCords) {
					if (y==0) {
						System.out.print(" ");
					} else {
						System.out.print("X");
					}
				}
				System.out.println();
			}
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
				int input = map.get(y).get(x);
				computer.set(Math.toIntExact(getWriteMode(modeFirst, first)), (long) input);
				i += 2;
			} else if (opcode == 4) {
				if (iteration == 0) {
					map.get(y).set(x, Math.toIntExact(getReadMode(modeFirst, first)));
					iteration = 1;
					List paint = new ArrayList<>();
					paint.add(x);
					paint.add(y);
					painted.put(paint, true);
					
				} else if (iteration == 1) {
					if (Math.toIntExact(getReadMode(modeFirst, first))== 0) {
						direction--;
						direction = direction == -1 ? 3 : direction;
					} else if (Math.toIntExact(getReadMode(modeFirst, first))== 1) {
						direction++;
						direction = direction == 4 ? 0 : direction;
					}
					switch (direction) {
					case 0:
						y--;
						break;
					case 1:
						x++;
						break;
					case 2:
						y++;
						break;
					case 3:
						x--;
						break;
					}
					iteration = 0;
				}
				//System.out.println("Output: " + getReadMode(modeFirst, first));
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
