import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class TransformationThread extends Thread {
	
	int column;
	int [] array = null;
	
	public TransformationThread(int [] arr) {
		this.array = arr;
	}
	
	public TransformationThread(int column) {
		//matrix = matrixIn;
		this.column = column;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(array != null)
				transform(array);
		else transform2D(column);
		
	}	
	private void transform(int [] arr) {	

		int half = arr.length/2;
		
		//FileWriter fileWriter = new FileWriter("checker.out");			
		//BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);		
		
		//bufferedWriter.write("Length is: " + arr.length + Arrays.toString(arr) + "");
		//bufferedWriter.newLine();

		while(half > 1) {
			int [] a = new int[half];
			int [] d = new int[half];
				
			for(int i = 0; i < half; i++) {	
				a[i] = (arr[i * 2] + arr[i * 2 + 1])/2;
				d[i] = (arr[i * 2] - arr[i * 2 + 1])/2;
			}			
			for(int i = 0; i < half; i++) {
				arr[i] = a[i];
				arr[half + i] = d[i];
			}			
			half = half / 2;
			
			//bufferedWriter.write("Length is: " + arr.length + Arrays.toString(arr) + "");
			//bufferedWriter.newLine();
		}			
		//bufferedWriter.close();
	}	
	private void transform2D(int column) {		
		int half = HaarWavelets.SIZE/2;

		while(half > 1) {
			int [] a = new int[half];
			int [] d = new int[half];
			
			for(int i = 0; i < half; i++) {	
				a[i] = (HaarWavelets.matrix[i * 2][column] + HaarWavelets.matrix[i * 2 + 1][column])/2;
				d[i] = (HaarWavelets.matrix[i * 2][column] - HaarWavelets.matrix[i * 2 + 1][column])/2;
			}			
			for(int i = 0; i < half; i++) {
				HaarWavelets.matrix[i][column] = a[i];
				HaarWavelets.matrix[half + i][column] = d[i];
			}
			half = half / 2;
		}
	}
}
