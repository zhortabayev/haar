
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
		else transform2D(HaarWavelets.matrix, column);
		
	}	
	private void transform(int [] arr) {	

		int half = arr.length/2;
		
		while(half > 0 && half%2 == 0) {
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
		}	
	}
	
	private void transform2D(int [][] arr, int column) {		
		int half = HaarWavelets.ROW/2;

		while(half > 0 && half%2 == 0) {
			int [] a = new int[half];
			int [] d = new int[half];
			
			for(int i = 0; i < half; i++) {	
				a[i] = (arr[i * 2][column] + arr[i * 2 + 1][column])/2;
				d[i] = (arr[i * 2][column] - arr[i * 2 + 1][column])/2;
			}			
			for(int i = 0; i < half; i++) {
				arr[i][column] = a[i];
				arr[half + i][column] = d[i];
			}
			half = half / 2;
		}
	}
}
