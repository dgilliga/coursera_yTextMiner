package edu.yonsei.test.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;


/**
 * <h1>Queries New York times using article search api, and stores results to a file as json</h1>
 * Keeps querying NYT site for pages of results until receives a 429.
 * Stores results to a file.
 *
 * @author Reinald Kim Amplayo & Min Song (modified by Darren Gilligan)
 * @version 1.01
 * @since   2016-01-28
 */

public class NYTimesDataCrawler {

    public static void main(String[] args) throws Exception {
        int day = 27, month = 1, year = 2017;
        int count = 0;
        int page = 4;
        int[] days = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        while (true) {
            if (year == 0) break;
            if (day == 0) break;

            for (int i = page; i < 100; i++) {
                PrintWriter printer = new PrintWriter(new FileOutputStream("data/corpus/nytimes_news_url.txt", true));
                PrintWriter jsonprinter = new PrintWriter(new FileOutputStream("data/corpus/nytimes_news_json.txt", true));
                try {
                    System.out.println("page " + i + " of " + month + "/" + day);

                    String url = "https://api.nytimes.com";
                    url += "/svc/search/v2/articlesearch.json?";
                    url += "q=election&";
                    // c1e42d50a88a4d09a8b175b7bfe61d19
                    // bdeff9a845cd4482a71779d04544be2e
                    // 4c5f45e3d5a945dfa5b2e814299d49bf
                    url += "api-key=" + System.getenv("NYT_ARTICLE_SEARCH_KEY");
                    url += "&end_date=" + year + "" + (month < 10 ? "0" : "") + month + "" + (day < 10 ? "0" : "") + day + "&";
                    url += "begin_date=" + year + "" + (month < 10 ? "0" : "") + month + "" + (day < 10 ? "0" : "") + (day-7) + "&";
                    url += "fq=type_of_material:(\"News\")+AND+source:(\"The+New+York+Times\")&";
                    url += "page=" + i;

                    URL uri = new URL(url);
                    ReadableByteChannel rbc = Channels.newChannel(uri.openStream());
                    FileOutputStream fos = new FileOutputStream("data/NYT_data.json");
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    fos.close();
                    Scanner s = new Scanner(new File("data/NYT_data.json"));
                    String line = "";
                    while (s.hasNext()) line += s.nextLine();
                    s.close();
                    count++;
                    System.out.println("\nCOUNT: " + count);

                    if (line.contains("\"docs\":[]")) break;

                    jsonprinter.println(line + "\n");

                    String[] split = line.split("\"web_url\":");
                } catch (Exception e) {
                    e.printStackTrace();
                    year = 0;
                    break;
                }
                printer.close();
                jsonprinter.close();
            }
            page = 0;
            day--;
            if (day == 0) {
                month--;
                if (month == 0) {
                    month = 12;
                    year--;
                }
                day = days[month - 1];
            }
        }
    }

}
