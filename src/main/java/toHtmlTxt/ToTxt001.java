package toHtmlTxt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ToTxt001 {
	private static final String hh = System.getProperties().getProperty("line.separator");
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream( new File( "/toHtmlTxt/source.txt" ) ), "utf-8" ) );
		String line = null;
		StringBuffer buf = new StringBuffer();
		while((line = br.readLine())!=null){
			System.out.println( line );
			line = line.
					replace( "\t", "&nbsp;&nbsp;&nbsp;" ).
					replace( " ", "&nbsp;" ).
					replace( "<", "&lt;" ).
					replace( ">", "&gt;" ).
					replace( "|||||", "<red>" ).
					replace( "||||", "</red>" );
			buf.append( line + "<br>" + hh );
		}
		
		br.close();
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( new File( "/toHtmlTxt/out.txt" ) ), "utf-8" ));
		bw.write( buf.toString() );
		bw.close();
		System.out.println( buf.toString() );
	}

}
