package algorithms;
import java.io.*;
import java.util.*;

public class decryptVignere implements DecryptionAlgorithms {

    private static ArrayList<Integer> findclashes(ArrayList<Character> input){
        ArrayList<Integer> ans = new ArrayList<Integer>();
        for(int i=1;i<100;i++){
            int x = 0;
            for(int j=0;j<input.size()-i;j++){
                if(input.get(j).equals(input.get(j+i))) {
                    x++;
                }
            }
            ans.add(x);
        }
        return ans;
    }

    private static int findkeylength(ArrayList<Integer> clashes) {
        // assuming key length to be limited to size 4-8
        int maxavg = 0,ans = 0;
        for(int i=1;i<9;i++){
            for(int k=0;k<=i;k++){
                int sum = 0,count = 0;
                for(int j=k;j<clashes.size();j=j+i){
                    sum += clashes.get(j);
                    count++;
                }
                int avg = sum/count;
                if(avg>maxavg){
                    maxavg = avg;
                    ans = i;
                }
            }
        } 
        return ans;
    }

    ArrayList<Pair> truefreq = new ArrayList(26);

    public void decrypt(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        // br is used to read the original text
        BufferedWriter wr = new BufferedWriter(new FileWriter("./decipheredtext.txt"));
        // wr is used to write the encrypted text to a file
        int val = 0;
        ArrayList<Character> input = new ArrayList<Character>();
        while((val = br.read()) != -1){
            input.add((char)val);
            // System.out.print((char)val);
        }br.close();
        ArrayList<Integer> clashes = findclashes(input);
        // for(int i=0;i<clashes.size();i++){
        //     System.out.println(clashes.get(i));
        // }
        int keylength = findkeylength(clashes);
        System.out.println(keylength);
        ////////////////////////////////////
            truefreq.add(new Pair('A',0.08167));
            truefreq.add(new Pair('B',0.01492));
            truefreq.add(new Pair('C',0.02782));
            truefreq.add(new Pair('D',0.04253));
            truefreq.add(new Pair('E',0.12702));
            truefreq.add(new Pair('F',0.02228));
            truefreq.add(new Pair('G',0.02015));
            truefreq.add(new Pair('H',0.06094));
            truefreq.add(new Pair('I',0.06966));
            truefreq.add(new Pair('J',0.00153));
            truefreq.add(new Pair('K',0.00772));
            truefreq.add(new Pair('L',0.04025));
            truefreq.add(new Pair('M',0.02406));
            truefreq.add(new Pair('N',0.06749));
            truefreq.add(new Pair('O',0.07507));
            truefreq.add(new Pair('P',0.01929));
            truefreq.add(new Pair('Q',0.00095));
            truefreq.add(new Pair('R',0.05987));
            truefreq.add(new Pair('S',0.06327));
            truefreq.add(new Pair('T',0.09056));
            truefreq.add(new Pair('U',0.02758));
            truefreq.add(new Pair('V',0.00978));
            truefreq.add(new Pair('W',0.02360));
            truefreq.add(new Pair('X',0.00150));
            truefreq.add(new Pair('Y',0.01974));
            truefreq.add(new Pair('Z',0.00074));
        ////////////////////////////////////
        Collections.sort(truefreq);
        int key[] = new int[keylength];
        int k=0;
        ArrayList<Character> ans;
        for(int i=0;i<keylength;i++){
            // System.out.println("entered" + truefreq.size());
            double freq[] = new double[26];
            for(int j=0;j<26;j++)   freq[j] = 0.0;
            double sum = 0.0;
            for(int j=i;j<input.size();j=j+keylength){
                freq[input.get(j)-65] = freq[input.get(j)-65]+1.0;
            }
            for(int j=0;j<26;j++){
                sum += freq[j];
            }
            for(int j=0;j<26;j++){
                freq[j]/=sum;
            }
            for(int j=0;j<truefreq.size();j++){
                if(freq[j]>0.05 && freq[(j+4)%26]>0.095){
                    key[k++]=j;
                    System.out.println(key[k-1]);
                    break;
                }
            }
        }

        for(int i=0;i<input.size();i++){
            // System.out.print(((int)input.get(i)-key[i%keylength]+26)%26+);
            wr.write((char)(((int)input.get(i)-65-key[i%keylength]+26)%26+65));
        }
        wr.close();
    }
}