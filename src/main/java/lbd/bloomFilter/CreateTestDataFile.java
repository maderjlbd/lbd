package lbd.bloomFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CreateTestDataFile {
	private static final String hh = System.getProperties().getProperty("line.separator");
	private final static String[] arr = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
			"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	public static void main(String[] args) throws IOException {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 9900000; i++) {
			buf.append( arr[ rand( 0, 35 ) ] + arr[ rand( 0, 35 ) ] + arr[ rand( 0, 35 ) ] + arr[ rand( 0, 35 ) ] + hh );
		}
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( new File( "/LBDConfig/bloomFilterBase.txt" ) )));
		bw.write( buf.toString() );
		bw.close();
		System.out.println( buf.toString() );
	}

	/**
	 * 生成 “min <= 随机数 <= max ” 的随机数
	 * @return 
	 */
	public static int rand( int min, int max ){
		return min + (int)(Math.random() * (max-min+1));
	}
}
