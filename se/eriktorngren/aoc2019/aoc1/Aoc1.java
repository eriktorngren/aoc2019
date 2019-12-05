package se.eriktorngren.aoc2019.aoc1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.floor;

public class Aoc1 {

	public static void main(String[] args) throws IOException {
		
		double totalFuel = 0;
		
		File file = new File("C:\\Users\\Erik\\Documents\\aoc2019\\aoc2019\\se\\eriktorngren\\aoc2019\\aoc1\\input.txt"); 

		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String st; 
		while ((st = br.readLine()) != null) {		
			totalFuel += calculateFuel(Double.valueOf(st));
		}
		System.out.println((int) totalFuel);
	}

	private static double calculateFuel(double mass) {	
        double fuel = floor(mass / 3) - 2;
        return fuel < 1.0 ? 0.0 : fuel + calculateFuel(fuel);
	} 
}





