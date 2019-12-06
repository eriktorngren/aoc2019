package se.eriktorngren.aoc2019.aoc6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aoc6 {

	static Map<String, String> relations = new HashMap<String, String>();
	
	public static void main(String[] args) {
		List<List<String>> orbits = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc6\\input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String values = line;
				orbits.add(Arrays.asList(values));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (List<String> orbit: orbits) {
			String[] relation = orbit.get(0).split("\\)");
			relations.put(relation[1], relation[0]);
		}
		firstSolution();
		secondSolution();	
	}
	
	private static void secondSolution() {
		List<String> youVisited = goOrbit("YOU");
		List<String> santaVisited = goOrbit("SAN");
		int distance = Integer.MAX_VALUE;
		for (String visit : youVisited) {
			if (santaVisited.contains(visit)) {
				distance = distance > youVisited.indexOf(visit) + santaVisited.indexOf(visit) ? youVisited.indexOf(visit) + santaVisited.indexOf(visit):distance;
			}
		}
		System.out.println(distance);
	}

	private static List<String> goOrbit(String who) {
		List<String> visited = new ArrayList<String>();
		String next = relations.get(who);
		while (relations.containsKey(next)) {
			visited.add(next);
			next = getNext(next);			
		}
		visited.add(next);
		return visited;
	}

	private static void firstSolution() {
		int count =0;
		String next;
		for (String first : relations.keySet()) {
			next = relations.get(first);
			while (relations.containsKey(next)) {
				count++;
				next = getNext(next);			
			}
			count++;
		}
		System.out.println(count);
	}

	public static String getNext(String key) {
		return relations.get(key);	
	}
	
}
