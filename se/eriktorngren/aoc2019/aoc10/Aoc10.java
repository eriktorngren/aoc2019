package se.eriktorngren.aoc2019.aoc10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		for (int y = 0; y<xMax; y++) {
			for (int x = 0; x<yMax; x++) {
				if (map.get(x).get(y).equals("#")) {
					List<Integer> asteroid = new ArrayList<>();
					asteroid.add(y);
					asteroid.add(x);
					asteroids.add(asteroid);
				}
			}
		}
		int best = 0;
		System.out.println(asteroids);
		for (List<Integer> asteroid : asteroids) {
			int lineOfSight = detect(asteroid, asteroids);
			best = best > detect(asteroid, asteroids) ? best : detect(asteroid, asteroids);
		}
		System.out.println(best);
	}

	private static int detect(List<Integer> currentAsteroid, List<List<Integer>> asteroids) {
		//int detected = 0;
		int currentX = currentAsteroid.get(0);
		int currentY = currentAsteroid.get(1);
		Map<List<Integer>, Boolean> test = new HashMap<>();
		for (List<Integer> asteroid : asteroids) {
			List<Integer> detected = new ArrayList<>();
			int x = asteroid.get(0);
			int y = asteroid.get(1);
			int gcd = BigInteger.valueOf(currentX-x).gcd(BigInteger.valueOf(currentY-y)).intValue();

			if (gcd != 0) {
				detected.add((currentX-x)/gcd);
				detected.add((currentY-y)/gcd);
			} else {
				detected.add(currentX-x);
				detected.add(currentY-y);
			}
			test.put(detected, true);
		}
		return test.size()-1;
	}



}
