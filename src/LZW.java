import java.io.*;
//little change;

import java.util.*;










public class LZW {

    private static final int BIT_0 = 1;

    /** Mask for bit 1 of a byte. */
    private static final int BIT_1 = 0x02;

    /** Mask for bit 2 of a byte. */
    private static final int BIT_2 = 0x04;

    /** Mask for bit 3 of a byte. */
    private static final int BIT_3 = 0x08;

    /** Mask for bit 4 of a byte. */
    private static final int BIT_4 = 0x10;

    /** Mask for bit 5 of a byte. */
    private static final int BIT_5 = 0x20;

    /** Mask for bit 6 of a byte. */
    private static final int BIT_6 = 0x40;

    /** Mask for bit 7 of a byte. */
    private static final int BIT_7 = 0x80;

    private static final int[] BITS = { BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7 };

    static int binaryLength;
    static String newFilename;
    HashMap<String, String> map;
    public LZW(int BinaryLength, String newFileName)
    {
        newFilename = newFileName;
        map = new HashMap<String, String>((int)Math.pow(2, binaryLength));
        binaryLength = BinaryLength;
        for(int i = 0; i < 256; i++)
        {
            String binString = intToBinary(i);
            String actualLetters = Character.toString((char)i);
            map.put(actualLetters, binString);
            //<"char", binary>
        }
    }

    public void encode(String filepath) throws IOException
    {
        int counter = 256;

        File file = new File(filepath);
        FileReader reader = new FileReader(file);

        BufferedReader br = new BufferedReader(reader);
        
        StringBuilder sb = new StringBuilder();

        int currInt = br.read();
        String currString = String.valueOf((char)currInt); //gets "char" from 9 digit binary
        int nextInt = br.read();
        String nextString = String.valueOf((char)nextInt); //gets "char" from 9 digit binary

        while(currInt != -1)
        {
            if(!map.containsKey(currString+nextString) && nextInt != -1)
            {
                sb.append(map.get(currString));
                map.put(currString+nextString, intToBinary(counter));

                currInt = nextInt; 
                currString = nextString;
                nextInt = br.read();
                nextString = String.valueOf((char)nextInt);
                counter++;
            }
            else if(nextInt == -1)
            {
                sb.append(map.get(currString));
                currInt = nextInt;
            }
            else
            {
                currString += nextString;
                nextInt = br.read();
                nextString = String.valueOf((char)nextInt);
            }
        }

        writeToBinary(sb.toString());

    }
    
    public static void writeToBinary(String s) throws IOException
    {
        char[] ascii = s.toCharArray();
        byte[] l_raw = new byte[ascii.length >> 3];
        /*
         * We decr index jj by 8 as we go along to not recompute indices using
         * multiplication every time inside the loop.
         */
        for (int ii = 0, jj = ascii.length - 1; ii < l_raw.length; ii++, jj -= 8) {
            for (int bits = 0; bits < BITS.length; ++bits) {
                if (ascii[jj - bits] == '1') {
                    l_raw[ii] |= BITS[bits];
                }
            }
        }
        
        FileOutputStream fos = new FileOutputStream(newFilename);
        fos.write(l_raw);
    }

    public static String intToBinary(int num)
    {
        String binString = Integer.toBinaryString(num);
        int l = binaryLength-binString.length();
        for(int j = 0; j < l; j++)
        {
            binString = "0"+binString;
        }
        return binString;
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        LZW lzw = new LZW(9, "output.bin");

        lzw.encode("lzw-file3.txt");
    }
}