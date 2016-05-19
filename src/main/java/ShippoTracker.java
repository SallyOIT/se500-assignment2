import org.jsoup.Jsoup;

import java.io.IOException;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by ransong on 4/30/16.
 */
public class ShippoTracker {
    private String shippoApiEndpoint = "https://api.goshippo.com/v1/tracks/";

    public PackageTracking track(String carrier, String trackingNumber) throws IOException {
        try {
            String trackingJsonStr = fetchJson(getUrl(carrier, trackingNumber));
            PackageTracking tracking = parse(trackingJsonStr);

            return tracking;
        } catch (IOException e) {
            /* TODO: Handle exception */
            throw e;
        }
    }

    private String fetchJson(String url) throws IOException {
        String trackingJsonStr = Jsoup.connect(url)
                .userAgent("")
                .ignoreContentType(true)
                .execute()
                .body();

        return trackingJsonStr;
    }

    private PackageTracking parse(String trackingJsonStr) {
        return parseObject(trackingJsonStr, PackageTracking.class);
    }

    private String getUrl(String carrier, String trackingNumber) {
        return shippoApiEndpoint + carrier + "/" + trackingNumber;
    }
}
