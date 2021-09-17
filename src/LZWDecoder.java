import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class LZWDecoder {
	
	private HashMap <Integer, String> dict;
	private int bitNum;
	private String binString;
	private byte [] byteArray;
	private String finalOutput;
	
	public LZWDecoder(String binString, int bitNum, String outputFileName) throws IOException
	{
		this.dict = new HashMap <Integer, String>();
		this.bitNum = bitNum;
		this.binString = binString;
		this.finalOutput = "";
		
		
//		File binFile = new File (binFileName);
//		readBinFile(binFile);
		decode();
		writeToTxt(outputFileName);
		
	}
	
	//reads the binary file into a string. not working, so I've bypassed this method for now and just input a binary string into the constructor.
	public void readBinFile(File binFile) throws IOException
	{
		FileInputStream is = new FileInputStream(binFile);
		
		int currInt = is.read();
		while (currInt != -1)
		{
			binString+=currInt;
		}
		is.close();
	}
	

	
	public void decode ()
	{
		for (int x = 0; x<256; x++)
		{
			char ch = (char)x;
			dict.put(x, String.valueOf(ch));
		}

		String currBinString = binString.substring (0,bitNum);
		binString= binString.substring(bitNum);
		int currDecimal = Integer.parseInt(currBinString, 2);
		String currString = dict.get(currDecimal);
		finalOutput = currString;
		
		String nextBinString = "";
		int nextDecimal= 0;
		String nextString= "";
		
		String lastSymbolInDict = "";
		
		
		for (int x = 0; x< binString.length()/bitNum; x++)
		{
			nextBinString = binString.substring(0, bitNum);
			binString= binString.substring(bitNum);
			nextDecimal = Integer.parseInt(nextBinString, 2);
			nextString = dict.get(nextDecimal);
			
			if (nextString!= null)
			{
				dict.put(256+x, currString+ nextString.substring(0,1));
				lastSymbolInDict = currString+ nextString.substring(0,1);
				finalOutput += nextString;
				currString = nextString;



			}
			else
			{
				dict.put(256+x, lastSymbolInDict + lastSymbolInDict.substring(0,1));
				lastSymbolInDict = lastSymbolInDict + lastSymbolInDict.substring(0,1);
			}
			
			
		}
	}
	
	//writes the 
	public void writeToTxt(String outputFileName) throws FileNotFoundException
	{
		PrintWriter output = new PrintWriter(outputFileName);
		output.print (finalOutput);
		output.close();
	}

}

