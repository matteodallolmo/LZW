import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class LZWDecoder {
	
	private HashMap <String, Integer> dict;
	private int bitNum;
	private String binString;
	private byte [] byteArray;
	
	public LZWDecoder(File binFile, int bitNum, String binString) throws FileNotFoundException
	{
		this.dict = new HashMap <String, Integer>();
		this.bitNum = bitNum;
		this.binString = "";
		
		readBinFile(binFile);
		fillDictionary();
		this.binString = binString;
	}
	
	public void readBinFile(File binFile) throws FileNotFoundException
	{
		FileInputStream is = new FileInputStream(binFile);
	}
	
	public void fillDictionary()
	{
		for (int x = 0; x<256; x++)
		{
			char ch = (char)x;
			dict.put(String.valueOf(ch), x);
		}
	}
	
	public void decode ()
	{
		String currString = "";
		String nextString = "";
		for (int x = 0; x< binString.length()/bitNum; x++)
		{
			currString = binString.substring (0,bitNum);
			binString= binString.substring(bitNum);
			
			
		}
	}

}

