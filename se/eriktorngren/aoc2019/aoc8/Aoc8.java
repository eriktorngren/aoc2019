package se.eriktorngren.aoc2019.aoc8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Aoc8 {


	static int wide = 25;
	static int tall = 6;

	public static void main(String[] args) {
		List<String> records = null;
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc8\\input.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split("");
				records = Arrays.asList(values);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int layers = records.size()/(tall*wide);
		int layerSize = records.size()/layers;
		List<Integer> pixels = new ArrayList<Integer>();
		for(String s : records) pixels.add(Integer.valueOf(s));
		
		List<List<Integer>> listOfLayers = new ArrayList<List<Integer>>();
		for (int k = 0; k < layers; k++) {
			List layer = new ArrayList<Integer>();
			for (int i = 0; i < tall; i++) {
				for (int j = 0; j < wide; j++ ) {
					layer.add(pixels.get(layerSize*k+i*wide + j));
				}
			}
			listOfLayers.add(layer);
		}
		List occurrences = new ArrayList();
		for ( List layer : listOfLayers) {
			occurrences.add(Collections.frequency(layer, 0));
			//System.out.println(layer.get(0));
		}

		Integer min = (Integer) Collections.min(occurrences);
		int index = occurrences.indexOf(min);
		int ones = Collections.frequency(listOfLayers.get(index), 1);
		int twos = Collections.frequency(listOfLayers.get(index), 2);

		System.out.println(ones*twos);
		
		int firstLayer = 0;
			for (int i = 0; i < tall; i++) {
				for (int j = 0; j < wide; j++ ) {
					int color = setColor(listOfLayers, firstLayer, i, j);
					if (color == 0) {
						System.out.print(" ");
					} else if (color == 1) {	
						System.out.print("O");
					}
				}
				System.out.println("");
			}

			
	}

	private static int setColor(List<List<Integer>> listOfLayers, int layer, int row, int col) {
		int color = listOfLayers.get(layer).get(row*wide + col);
		if (color == 0) {
			return 0;
		} else if (color == 1) {
			return 1;
		} else {
			return setColor(listOfLayers, ++layer, row, col);
		}
	}
	

}
