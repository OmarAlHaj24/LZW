import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author: Omar Khaled Al Haj   20190351
 * @author: Alaa Mahmoud Ebrahim 20190105
 */

public class Main {
    public static void compress(String s) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(s));
        Scanner sc = new Scanner(br);
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.out"));
        String s1 = sc.nextLine();
        int counter = 128;
        HashMap<String, Integer> dict = new HashMap<String, Integer>();
        for (int i = 0; i < 128; i++) {
            dict.put("" + (char) i, i);
        }
        for (int i = 0; i < s1.length(); ) {
            String searchingFor = "";
            int idx = 0;
            for (int j = i; j < s1.length(); j++) {
                searchingFor += s1.charAt(j);
                if (dict.containsKey(searchingFor)) {
                    idx = dict.get(searchingFor);
                    if(j + 1 == s1.length()){
                        dict.put(searchingFor, counter++);
                        writer.write("<" + idx + ">\n");
                        i = j + 1;
                    }
                } else {
                    dict.put(searchingFor, counter++);
                    writer.write("<" + idx + ">\n");
                    i = j;
                    break;
                }
            }
        }
        writer.write(" ");
        writer.close();
    }

    public static void decompress(String s) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(s));
        Scanner sc = new Scanner(br);
        HashMap<Integer, String> dict = new HashMap<Integer, String>();
        StringBuilder result = new StringBuilder();
        int counter = 128;
        for (int i = 0; i < 128; i++) {
            dict.put(i, "" + (char) i);
        }
        int prev = -1;
        while(true){
            int idx = -1;
            String s1 = sc.nextLine();
            if(s1.equals(" ")) break;
            for(int i = 1; i < s1.length();){
                StringBuilder x = new StringBuilder();
                while(s1.charAt(i) != ',' && s1.charAt(i) != '>'){
                    x.append(s1.charAt(i));
                    i++;
                }
                i++;
                idx = Integer.parseInt(x.toString());
            }
            String temp = "";
            if(prev == -1){
                temp = dict.get(idx);
                prev = idx;
                result.append(temp);
                continue;
            }
            if(idx >= dict.size()){
                temp = dict.get(prev) + dict.get(prev).charAt(0);
                dict.put(counter++, temp);
                prev = idx;
                result.append(temp);
            }else{
                temp = dict.get(idx);
                dict.put(counter++, dict.get(prev) + temp.charAt(0));
                prev = idx;
                result.append(temp);
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        compress("input.in");
        decompress("output.out");
    }
}