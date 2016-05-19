import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by ransong on 4/11/16.
 */
public class WebTracker {

    public PackageTracking track(String carrier, String trackingNumber) {

        Document document = null;
        try {
            document = fetchHtml(getUrl(carrier, trackingNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (document != null)
            return parse(document);

        return null;
    }

    private Document fetchHtml(String url) throws IOException {

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
                    .get();
            return doc;

        } catch (IOException e) {
            throw e;
        }



    }

    private PackageTracking parse(Document doc) {

        PackageTracking packageTracking = new PackageTracking();
        packageTracking.carrier = "usps";
        Elements numberElement = doc.select("#results-multi > div.panel.tracking-result.result-delivered.result-open > div > div.tracking-summary > div.tracking-number > span.value");
        if (numberElement != null && !numberElement.isEmpty()) {
            packageTracking.tracking_number = numberElement.first().ownText();
        }
        TrackingStatus trackingStatus = new TrackingStatus();
        Elements statusElement = doc.select("#results-multi > div.panel.tracking-result.result-delivered.result-open > div > div.tracking-summary > div.tracking-progress.status-delivered > div.progress-indicator > h2");
        if (statusElement != null && !statusElement.isEmpty()) {
            trackingStatus.status = statusElement.first().ownText().toUpperCase();
        }
        Elements dataElement = doc.select("#tc-hits > tbody > tr.detail-wrapper.latest-detail > td.date-time > p");
        if (dataElement != null && !dataElement.isEmpty()) {
            String dateStr = dataElement.first().ownText();
            dateStr = dateStr.replace("\\n", "");
            dateStr = dateStr.replace("\\r", "");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy , h:mm a");

            try {
                trackingStatus.status_date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        Elements statusDetailElement = doc.select("#tc-hits > tbody > tr.detail-wrapper.latest-detail > td.status > p.info-text.first");
        trackingStatus.status_details = statusDetailElement.first().ownText();

        Elements locationElement = doc.select("#tc-hits > tbody > tr.detail-wrapper.latest-detail > td.location > p");
        if (locationElement != null && !locationElement.isEmpty()) {
            String location = locationElement.first().html();
            String[] locationItems = location.replace(",", "").split("&nbsp;");
            Location location1 = new Location();
            location1.city = locationItems[0];
            location1.state = locationItems[1];
            location1.zip = locationItems[2];
            trackingStatus.location = location1;
        }

        packageTracking.tracking_status = trackingStatus;

        return packageTracking;
    }

    private String getUrl(String carrier, String trackingNumber) {
        if (carrier.equals("usps")) {
            return "https://tools.usps.com/go/TrackConfirmAction.action?tRef=fullpage&tLc=1&text28777=&tLabels=" + trackingNumber;
        }
        return "";
    }
}
