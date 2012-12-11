package comix.bgteams.utils; 

import java.util.Random; 

public class Randomizer { 

	final public static int generate(int min, int max) {

		Random r = new Random();
		int i1 = r.nextInt(max - min + 1) + min;

		return i1;
	}	
}