package se.eriktorngren.aoc2019.aoc10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aoc10 {

	static int xMax;
	static int yMax;
	static List<List<String>> map;

	public static void main(String[] args) {
		map = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc10\\input.txt"))) {
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
		for (int y = 0; y<yMax; y++) {
			for (int x = 0; x<xMax; x++) {
				if (map.get(x).get(y).equals("#")) {
					List<Integer> asteroid = new ArrayList<>();
					asteroid.add(y);
					asteroid.add(x);
					asteroids.add(asteroid);
				}
			}
		}
		int best = 0;
		List<Integer> bestAsteroid = null;
		for (List<Integer> asteroid : asteroids) {
			best = best > detect(asteroid, asteroids).size() ? best : detect(asteroid, asteroids).size();
			bestAsteroid = best > detect(asteroid, asteroids).size() ? bestAsteroid : asteroid;
		}
		System.out.println("Best asteroid: " + bestAsteroid + " with " + best + " visible asteroids");
		int examine = 200;
		List<Double> lastVaporized = vaporize(bestAsteroid, asteroids, examine);
		System.out.println("Vaporized as number " + examine + ": " + lastVaporized.get(2) + ", " + lastVaporized.get(3));
	}

	private static List<Double> vaporize(List<Integer> bestAsteroid, List<List<Integer>> asteroids, int last) {
		Map<List<Integer>, List<Integer>> closest = detect(bestAsteroid, asteroids);
		List<List<Double>> metrics = new ArrayList<>();
		for (List<Integer> asteroid : closest.values()) {
			List<Double> geometry = new ArrayList<>();
			geometry.add(getAngle(bestAsteroid, asteroid));
			geometry.add(getDistance(bestAsteroid, asteroid));
			geometry.add((double) asteroid.get(0));
			geometry.add((double) asteroid.get(1));
			metrics.add(geometry);
		}
		metrics = metrics.stream().sorted((o1,o2) -> {
            for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
                 int c = o1.get(i).compareTo(o2.get(i));
                 if (c != 0) {
                   return c;
                 }
               }
               return Integer.compare(o1.size(), o2.size());
        }).collect(Collectors.toList());
		return metrics.get(metrics.size()-last);
	}
	

	private static double getDistance(List<Integer> bestAsteroid, List<Integer> asteroid) {
		return Math.sqrt(Math.pow((asteroid.get(1) - bestAsteroid.get(1)), 2)  + Math.pow((asteroid.get(0) - bestAsteroid.get(0)), 2));
	}

	private static double getAngle(List<Integer> bestAsteroid, List<Integer> asteroid) {
		return Math.toDegrees(Math.atan2(asteroid.get(0)-bestAsteroid.get(0), asteroid.get(1)-bestAsteroid.get(1)));		
	}

	private static Map<List<Integer>, List<Integer>> detect(List<Integer> currentAsteroid, List<List<Integer>> asteroids) {
		//int detected = 0;
		int currentX = currentAsteroid.get(0);
		int currentY = currentAsteroid.get(1);
		Map<List<Integer>, List<Integer>> inSight = new HashMap<>();
		for (List<Integer> asteroid : asteroids) {
			List<Integer> detected = new ArrayList<>();
			int x = asteroid.get(0);
			int y = asteroid.get(1);
			int gcd = BigInteger.valueOf(currentX-x).gcd(BigInteger.valueOf(currentY-y)).intValue();

			if (gcd != 0) {
				detected.add((currentX-x)/gcd);
				detected.add((currentY-y)/gcd);
				if (!inSight.containsKey(detected)) {
					inSight.put(detected, asteroid);
				} else if (inSight.containsKey(detected) && ( currentX-x>0 || currentY-y > 0 ) ) {
					inSight.put(detected, asteroid);
				}		
			}
		}
		return inSight;
	}

	class ListComparator<T extends Comparable<T>> implements Comparator<List<T>> {

		  @Override
		  public int compare(List<T> o1, List<T> o2) {
		    for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
		      int c = o1.get(i).compareTo(o2.get(i));
		      if (c != 0) {
		        return c;
		      }
		    }
		    return Integer.compare(o1.size(), o2.size());
		  }

		}

}
