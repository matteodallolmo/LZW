import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws IOException {
        LZW compressor = new LZW(9, "TestFile");

        compressor.encode("lzw-file2.txt");
        
//        LZWDecoder expander = new LZWDecoder ("TestFile", 9, "TestFile2");
        
    }
}
