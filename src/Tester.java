import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws IOException {
        LZW compressor = new LZW(9, "TestFile");

        String testString = compressor.encode("lzw-file1.txt");
        System.out.println (testString);
        
        LZWDecoder expander = new LZWDecoder (testString, 9, "TestFile2");
        
    }
}
