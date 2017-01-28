package edu.yonsei.test.data;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.*;
import com.jayway.jsonpath.JsonPath;

/**
 * <h1>Downloads an article from New York times, extracts article text and stores to a file</h1>
 * NYTimesArticleCrawler reads a json file with search results from NYT as a list of articles.
 * Extracts the urls of all articles.
 * Downloads the first one.
 * Parses the html and extracts the article text to store to a file.
 *
 * @author Reinald Kim Amplayo & Min Song (modified by Darren Gilligan)
 * @version 1.01
 * @since   2016-01-28
 */

public class NYTimesArticleCrawler {

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(new FileReader("data/NYT_data.json"));    // Open the NYT search results json

        PrintWriter printer = new PrintWriter(new FileOutputStream("data/corpus/nytimes_news_articles_2.txt",
                false));

        // Need to extract the urls and format them
        ArrayList<String> web_urls = JsonPath.read(new JSONObject(s.nextLine()).toString(), "$..web_url");
        ArrayList<String> final_web_urls = new ArrayList<String>();

        web_urls.forEach((url) -> {
            final_web_urls.add(url.replaceAll("\\\\", ""));
        });


        // Get the article text of first url and store it.
        String url = final_web_urls.get(0);
        try {

            Document doc = Jsoup.connect(url).get();
            Elements texts = doc.getElementsByClass("story-body-text");

            for (Element text : texts) {
                System.out.println(text.text());
                printer.println(text.text());
            }


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        printer.println();
        printer.close();
        s.close();
    }

}
