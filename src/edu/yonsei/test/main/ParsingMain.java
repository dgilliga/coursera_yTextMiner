package edu.yonsei.test.main;

import java.io.FileReader;
import java.util.Scanner;

import edu.yonsei.util.Document;
import edu.yonsei.util.Sentence;

public class ParsingMain {
	
	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(new FileReader("data/corpus/nytimes_news_articles_2.txt"));

			
		for(int i=0; i<10; i++) {
			String text = s.nextLine();
			Sentence sent = new Sentence(text);
			sent.preprocess();

			System.out.println("==================");
			System.out.println();
			System.out.println("Sentence " + (i+1));
			System.out.println(sent.getSentence());
			System.out.println();
			System.out.println("Parse Tree " + (i+1));
			System.out.println(sent.getParseTree());
			System.out.println();
			System.out.println("Dependencies" + (i+1));
			for(String dependency : sent.getDependencies())
				System.out.println(dependency);
//			System.out.println();
		}

		
		s.close();
	}

}
