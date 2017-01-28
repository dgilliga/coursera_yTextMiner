package edu.yonsei.test.data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * <h1>Downloads twitter streams and stores to a file</h1>
 * Streams to a file until the process is killed.
 * Uses env variables as auth
 *
 * @author Reinald Kim Amplayo & Min Song (modified by Darren Gilligan)
 * @version 1.01
 * @since   2016-01-28
 */

public class TwitterDataCrawler {

    public static void main(String[] args) throws Exception {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(System.getenv("TwitterConsumerKey"))
                .setOAuthConsumerSecret(System.getenv("TwitterConsumerSecret"))
                .setOAuthAccessToken(System.getenv("TwitterAccessToken"))
                .setOAuthAccessTokenSecret(System.getenv("TwitterAccessTokenSecret"));
        final long start = System.currentTimeMillis();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                PrintWriter printer;
                long end = System.currentTimeMillis();
                if (end - start >= 3600000 * 4) System.exit(0);
                try {
                    printer = new PrintWriter(new FileOutputStream("data/corpus/streamtweets.txt", true));
                    String text = status.getText();
                    if (text.startsWith("RT @")) {
                        printer.close();
                        return;
                    }
                    printer.println(text);
                    System.out.println(text);
                    printer.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }


            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }


            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }
        };
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        twitterStream.addListener(listener);
        twitterStream.sample("en");
    }

}
