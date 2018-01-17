import twitter4j.*;
import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public final class PrintSampleStream {

    public static void main(String[] args) throws TwitterException, IOException {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey("Z6p84zp6sR5LENRzOIKaPC3iJ")
                .setOAuthConsumerSecret("zfvGVIUaApZ08yFZVtKSrPIoCPgjn6TQvLFrYq9XtZzlPPW8d0")
                .setOAuthAccessToken("793748592859156481-7peK28weU6C3dSkOtIXNLNoLYRZwWyt")
                .setOAuthAccessTokenSecret("RW9sQOMfDl4V3bktMuGxaTQOadYe0kCUsVHq8T3eYDRjU");

        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                //System.out.println(status.getUser().getName() + " : " + status.getText());


                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("tweets_app_newline.json",true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JSONObject obj = new JSONObject();
                obj.put("user",status.getUser().getName());
                obj.put("tweet",status.getText());



                try {


                    fileWriter.write(obj.toJSONString());
                    fileWriter.write(System.getProperty( "line.separator" ));
                    fileWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(obj);


            }


            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

            public void onScrubGeo(long l, long l1) {

            }

            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }


        };
        TwitterStream twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
        twitterStream.addListener(listener);
        FilterQuery tweetFilterQuery = new FilterQuery(); // See
        tweetFilterQuery.track(new String[]{"Bieber"});
        // sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(tweetFilterQuery);
    }
}