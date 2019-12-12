package se.eriktorngren.aoc2019.aoc12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aoc12 {
	
	static List<List<Integer>> positions;
	static List<List<Integer>> velocities;
	static int moons;
	static int dimensions;

	public static void main(String[] args) {
		List<List<String>> map = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc12\\input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				map.add(Arrays.asList(values));
			}
		} catch (IOException e) {

		}
		dimensions = map.get(0).size();
		moons = map.size();
		positions = new ArrayList<>();
		velocities = new ArrayList<>();
		for (List<String> moon : map) {
			List<Integer> position = new ArrayList<>();
			List<Integer> velocity = new ArrayList<>();
			for (int i = 0; i<dimensions; i++) {			
				int coord;
				if (i==dimensions-1) {
					coord = Integer.valueOf(moon.get(i).substring(moon.get(i).lastIndexOf('=')+1, moon.get(i).length()-1));
				} else {
					coord = Integer.valueOf(moon.get(i).substring(moon.get(i).lastIndexOf('=')+1));
				}
				position.add(coord);
				velocity.add(0);
			}
			positions.add(position);
			velocities.add(velocity);
		}		
		int timeSteps = 1000;
		for (int timeStep = 0; timeStep<timeSteps; timeStep++) {
			updateVelocity();
			updatePositions();
		}	
		System.out.println("Part 1: " + calculateEnergy());
		long[] repeating = new long[3];
		for (int i = 0; i<dimensions; i++) {
			repeating[i] = (long) calculateRepeat(i);
		}		
		System.out.println("Part 2: " + lcm(repeating));
	}
	
	private static long gcd(long x, long y) {
	    return (y == 0) ? x : gcd(y, x % y);
	}
	
	public static long lcm(long... numbers) {
	    return Arrays.stream(numbers).reduce(1, (x, y) ->  x * (y / gcd(x, y)));
	}

	private static int calculateRepeat(int i) {
		Map<String, Boolean> history = new HashMap();
		int step =0;
		while (true)  {		
			updateVelocity();
			updatePositions();	
			String state = positions.get(0).get(i).toString() + " " + positions.get(1).get(i).toString() + " " +positions.get(2).get(i).toString() +" " + positions.get(3).get(i).toString() + 
					" " + velocities.get(0).get(i).toString() + " " + velocities.get(1).get(i).toString() + " " +velocities.get(2).get(i).toString() +" " + velocities.get(3).get(i).toString();
			if (history.containsKey(state)) {
				return step;
			} else {
				history.put(state, true);
			}
			step++;
		}
	}

	private static int calculateEnergy() {
		List<Integer> potEnergies = new ArrayList<>();
		List<Integer> kinEnergies = new ArrayList<>();	
		for (List<Integer> position : positions) {
			int potEnergy = 0;
			for (int j = 0; j<dimensions; j++) {
				potEnergy += Math.abs(position.get(j));
			}
			potEnergies.add(potEnergy);
		}
		for (List<Integer> velocity : velocities) {
			int kinEnergy = 0;
			for (int j = 0; j<dimensions; j++) {
				kinEnergy += Math.abs(velocity.get(j));
			}
			kinEnergies.add(kinEnergy);
		}
		int totalEnergy = 0;
		for (int i = 0; i<moons; i++) {
			totalEnergy += potEnergies.get(i) * kinEnergies.get(i);
		}
		return totalEnergy;
	}

	private static void updatePositions() {
		for (int i = 0; i<moons; i++) {
			applyVelocity(i);
		}
	}

	private static void applyVelocity(int i) {
		for (int j = 0; j<dimensions; j++) {
			positions.get(i).set(j, positions.get(i).get(j)+velocities.get(i).get(j));
		}
	}

	private static void updateVelocity() {
		for (int i = 0; i<moons; i++) {
			applyGravity(i);
		}
	}

	private static void applyGravity(int order) {
		for (int i = order; i<moons; i++) {
			if (i != order) {
				for (int j = 0; j<dimensions; j++) {
					if (positions.get(order).get(j) < positions.get(i).get(j)) {
						velocities.get(order).set(j, velocities.get(order).get(j)+1);
						velocities.get(i).set(j, velocities.get(i).get(j)-1);
					} else if (positions.get(order).get(j) > positions.get(i).get(j)) {
						velocities.get(order).set(j, velocities.get(order).get(j)-1);
						velocities.get(i).set(j, velocities.get(i).get(j)+1);
					}
				}
			}
		}
	}
}
