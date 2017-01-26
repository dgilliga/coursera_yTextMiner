package edu.yonsei.test.main;

import java.io.FileReader;
import java.util.Scanner;

import edu.yonsei.util.Sentence;

public class NGramMain {

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(new FileReader("data/corpus/streamtweets.txt"));

        for (int i = 0; i < 5; i++) {
            {
                String text = s.nextLine();
                Sentence sent = new Sentence(text);
                sent.preprocess();

                int n2 = 2;
                int n5 = 5;
                System.out.println();
                System.out.println("Sentence " + i+1);
                System.out.println(sent.getSentence());
                System.out.println(n2 + "-grams");
                System.out.println(sent.getNGrams(n2));
                System.out.println(n5 + "-grams");
                System.out.println(sent.getNGrams(n5));
            }


        }
        s.close();

    }
}
