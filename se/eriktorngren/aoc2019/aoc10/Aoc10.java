package se.eriktorngren.aoc2019.aoc10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Aoc10 {

	static int xMax;
	static int yMax;
	static List<List<String>> map;
	
	public static void main(String[] args) {
		map = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("/Users/Erik/Documents/AOC2019/aoc2019/se/eriktorngren/aoc2019/aoc10/input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("");
				map.add(Arrays.asList(values));
			}
		} catch (IOException e) {

		}

		xMax = map.size();
		yMax = map.size();

		List<List<Integer>> asteroids = new ArrayList<>();

		for (int x = 0; x<xMax; x++) {
			for (int y = 0; y<yMax; y++) {
				if (map.get(x).get(y).equals("#")) {
					List<Integer> asteroid = new ArrayList<>();
					asteroid.add(x);
					asteroid.add(y);
					asteroids.add(asteroid);
				}
			}
		}

		System.out.println(asteroids);
		for (List<Integer> asteroid : asteroids) {
			int lineOfSight = detect(asteroid);
			System.out.println(lineOfSight);
		}

	}

	private static int detect(List<Integer> currentAsteroid) {
		int detected = 0;
		for (int x = -xMax; x<xMax; x++) {
			for (int y = -yMax; y<yMax; y++) {
				if (visible(x, y, currentAsteroid)) {
					detected++;
				}
			}
		}
		return detected;
	}

	private static boolean visible(int x, int y, List<Integer> currentAsteroid) {
		int currentX = currentAsteroid.get(0) + x;
		int currentY = currentAsteroid.get(1) + y;
		
		if (currentX < 0 || currentX>=xMax || currentY < 0 || currentY>=yMax) {
			return false;
		}
		
		if (map.get(currentX).get(currentY).equals("#")) {
			return true;
		} else {
			return visible(currentX, currentY, currentAsteroid);
		}
	}



}
