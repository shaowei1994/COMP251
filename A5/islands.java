package A5;
import java.io.*;
import java.util.*;

public class islands{

	private static int numberOfProblems;
	private static int maxRow;
	private static int maxCol;
	private static char[][] ocean;

	public static int findNumberOfIsland(){
		int islandCount = 0;
		
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				if (ocean[i][j] == '-') {
					recurseGrid(i, j);
					islandCount++;
				}
			}
		}
		
		return islandCount;
	}
	
	public static void recurseGrid(int row, int col) {
		if (row < 0 || col < 0 || row >= maxRow || col >= maxCol) return;
		if (ocean[row][col] == '#') return;
		
		ocean[row][col] = '#';
		
		recurseGrid(row - 1, col);
		recurseGrid(row + 1, col);
		recurseGrid(row, col - 1);
		recurseGrid(row, col + 1);
	}

	public static void readFile(String file) throws RuntimeException {
		try {
			Scanner f = new Scanner(new File(file));
			numberOfProblems = Integer.parseInt(f.nextLine());

			for(int n = 0; n < numberOfProblems; n++) {				
				String[] ln = f.nextLine().split("\\s+"); /*first line number of problem*/
				maxRow = Integer.parseInt(ln[0]);
				maxCol = Integer.parseInt(ln[1]);
				ocean = new char[maxRow][maxCol];

				for (int i = 0; i < maxRow; i++) {
					ocean[i] = f.nextLine().toCharArray();
				}
				
				int numberOfIslands = findNumberOfIsland();
				writeAnswer(new File(file).getAbsolutePath().replace("testIslands.txt","") + "testIslands_solution.txt", String.valueOf(numberOfIslands));
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

		String file = "testIslands.txt";
		readFile(file);
	}
}