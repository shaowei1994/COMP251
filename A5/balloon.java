
import java.io.*;
import java.util.*;

public class balloon{

	private static int numberOfProblems;
	
	public static int findNumberOfArrows(int[] balloons) {
		int balloonsRemaining = balloons.length;
		int arrowCount = 0;

		while (balloonsRemaining > 0) {		
			arrowCount++;
			int startingIndex = getMaxIndex(balloons);
			int arrowHeight = balloons[startingIndex];
			
			for (int i = startingIndex; i < balloons.length; i++) {
				if (balloons[i] == arrowHeight) {
					balloonsRemaining--;
					balloons[i] = Integer.MIN_VALUE; 
					arrowHeight--; // Balloon popped, decrease the height of arrow by 1
				}
			}
		}
		
		return arrowCount;
	}
	
	public static int getMaxIndex(int[] balloons) {
		int maxValueIndex = -1;
		int maxValue = 0;
		
		for (int i = 0; i < balloons.length; i++) {
			if (maxValue < balloons[i]) {
				maxValue = Math.max(maxValue, balloons[i]);
				maxValueIndex = i;
			}
		}
		
		return maxValueIndex;
	}

	public static void readFile(String file) throws RuntimeException {
		try {
			Scanner f = new Scanner(new File(file));
			numberOfProblems = Integer.parseInt(f.nextLine());			
			String[] numberOfBallonPerQuestion = f.nextLine().split(" "); /*first line number of problem*/

			for(int n = 0; n < numberOfProblems; n++) {

				int[] ballons = new int[Integer.parseInt(numberOfBallonPerQuestion[n])];
				String[] ballonsHeight = f.nextLine().split(" ");
				
				for(int i = 0; i < ballonsHeight.length; i++) {
					ballons[i] = Integer.parseInt(ballonsHeight[i]);
				}

				int numberOfArrows = findNumberOfArrows(ballons);
				writeAnswer(new File(file).getAbsolutePath().replace("testBalloons.txt","") + "testBalloons_solution.txt", String.valueOf(numberOfArrows));
			}

			f.close();

		} catch (FileNotFoundException e){
			System.out.println("File not found!");
			System.exit(1);
		}
	}
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesn't exists, then create it

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(line+"\n");	
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args){
		String file = "testBalloons.txt";
		readFile(file);
	}
}
