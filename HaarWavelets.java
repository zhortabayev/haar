import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HaarWavelets {

	public static int COLUMN;
	public static int ROW;
	
	public static int [][] matrix;
	
	public static void main(String []args) {
		
		long startTime = System.currentTimeMillis();

		if(args.length != 1) {
			System.out.println("Usage: <input>");
			return;
		}				
		String input = args[0];
		String output = "image.out";
		
		byte[] bytes = null;
		
		try {
			bytes = Files.readAllBytes(Paths.get(input));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//ROW = ByteBuffer.wrap(bytes).getInt();
		ROW = (int) Math.sqrt(bytes.length / 4 - 2);
		COLUMN = ROW;

		matrix = new int[ROW][COLUMN];
			
		int c = 0, r = 0;
		for(int i = 8; i < bytes.length; i = i + 4) {
			matrix[r][c] = ByteBuffer.wrap(bytes, i, 4).getInt();			
			c++;			
			if(c == COLUMN) {
				r++; c = 0;
			}
		}		
		bytes = null;
		System.gc();
		
		//first transform by row	
		System.out.println("Thransforming by row...");
		int counter = 0;		
		while(counter < ROW){			
			TransformationThread tt = new TransformationThread(matrix[counter]);
			tt.start();	
		}
	
		while(Thread.activeCount() != 1 ) {}		
		System.gc();
		
		System.out.println("Thransforming by column...");
		for(int i = 0; i < COLUMN; i++) {
			TransformationThread tt = new TransformationThread(i);
			tt.start();
		}
		
		while(Thread.activeCount() != 1 ) {}		
		System.gc();		
		
		System.out.println("Writing to a file...");
		try {			
			FileOutputStream fos = new FileOutputStream(output, true);
			fos.write(int2ByteArray(ROW));
			fos.write(int2ByteArray(0));

			for(int i = 0; i < ROW; i++) 
				for(int j = 0; j < COLUMN; j++) 
					fos.write(int2ByteArray(matrix[i][j]));
			
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("The time spent is:	 "  + elapsedTime / 1000 + "s");    
	}	
	public static byte [] int2ByteArray (int value)
	{  
	     return ByteBuffer.allocate(4).putInt(value).array();
	}
}