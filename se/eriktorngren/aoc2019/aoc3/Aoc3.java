package se.eriktorngren.aoc2019.aoc3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Aoc3 {
	static int MIN_DISTANCE = Integer.MAX_VALUE;
	static int MIN_STEPS = Integer.MAX_VALUE;
	static Map<Coords, Integer> map = new HashMap<Coords, Integer>();
	static int x = 0;
	static int y = 0;
	static int totalSteps = 0;
	public static void main(String[] args) {
		List<String> first = null;
		List<String> second = null;
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc3\\input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records.add(Arrays.asList(values));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		first = records.get(0);
		second = records.get(1);
		System.out.println(first);
		System.out.println(second);
		
		travel(first);
		Map<Coords, Integer> firstMap = new HashMap<Coords, Integer>(map);
		map = new HashMap<Coords, Integer>();
		x = 0;
		y = 0;
		totalSteps = 0;
		travel(second);
		Map<Coords, Integer> secondMap = new HashMap<Coords, Integer>(map);
		closestIntersect(firstMap, secondMap);
		System.out.println(MIN_DISTANCE);
		fastestIntersect(firstMap, secondMap);
		System.out.println(MIN_STEPS);
	}


	private static void fastestIntersect(Map<Coords, Integer> firstMap, Map<Coords, Integer> secondMap) {
		Set<Coords> coordinates = secondMap.keySet();
		for (Coords visited : coordinates) {
			if (firstMap.containsKey(visited)) {
				int steps = firstMap.get(visited) + secondMap.get(visited);			
				MIN_STEPS = steps < MIN_STEPS ? steps : MIN_STEPS;
			}
		}	
	}


	private static void closestIntersect(Map<Coords, Integer> firstMap, Map<Coords, Integer> secondMap) {
		Set<Coords> coordinates = secondMap.keySet();
		for (Coords visited : coordinates) {
			if (firstMap.containsKey(visited)) {
				int distance = manhattanDist(visited.getX(), visited.getY());
				MIN_DISTANCE = distance < MIN_DISTANCE ? distance : MIN_DISTANCE;
			}
		}
	}

	private static int manhattanDist(int x1, int y1) {
		return Math.abs(x1-0) + Math.abs(y1-0);
	}

	private static void travel(List<String> instructions) {
		for (String instruction : instructions) {
			go(instruction);
		}
		
	}

	private static void go(String instruction) {
		String direction = instruction.substring(0, 1);
		int step = Integer.valueOf(instruction.substring(1));
		if (direction.equals("R")) {
			for (int i = x+1; i<=x+step; i++) {
				map.put(new Coords(i, y), ++totalSteps);		
			}
			x += step;
		} else if (direction.equals("U")){
			for (int i = y+1; i<=y+step; i++) {
				map.put(new Coords(x, i), ++totalSteps);
			}
			y += step;
		} else if (direction.equals("L")){
			for (int i = x-1; i>=x-step; i--) {
				map.put(new Coords(i, y), ++totalSteps);
			}
			x -= step;
		} else if (direction.equals("D")){
			for (int i = y-1; i>=y-step; i--) {
				map.put(new Coords(x, i), ++totalSteps);
			}
			y -= step;
		}
	}
	
	static class Coords {
        int x;
        int y;

        @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coords other = (Coords) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		public Coords(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

//        public int hashCode() {
//            return new Integer(x + 31*y);
//        }
        
        public String toString() {
        	return "X: " + x + " Y: " + y;
        }
        
        public int getX() {
        	return x;
        }
        
        public int getY() {
        	return y;
        }
    }

}
