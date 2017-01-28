package edu.yonsei.test.main;

import java.io.FileReader;
import java.util.Scanner;

import edu.yonsei.util.Sentence;
import edu.yonsei.util.Token;


/**
 * <h1>Writes stem and stop words for a file passed in</h1>
 * At the moment this reads a text file with New York times article
 * And just prints the stop words and stems to the screen for the first 10 lines.
 * @author Reinald Kim Amplayo & Min Song (modified by Darren Gilligan)
 * @version 1.01
 * @since 2016-01-28
 */

public class StemAndStopMain {

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(new FileReader("data/corpus/nytimes_news_articles_2.txt"));
		
		for(int i=0; i<10; i++) {
			String text = s.nextLine();
				
			Sentence sent = new Sentence(text);
			sent.preprocess();

			System.out.println();
			System.out.println("Sentence # " + (i+1));
			System.out.println(sent.getSentence());
			System.out.println("After stemming");
			for(Token token : sent) {
				System.out.print(token.getStem() + " ");
				if (token.isStopword()) System.out.print("(Is Stopword) ");
				System.out.println();
			}
//			System.out.println("After lemmatization");
//			for(Token token : sent)
//				System.out.print(token.getLemma() + " ");
//			System.out.println();
			System.out.println();
		}
		
		s.close();
	}

}
