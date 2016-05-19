import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created by ransong on 5/2/16.
 */
public class ShippoTrackerTest {
    @Test
    public void testShippoTracker(){
        ShippoTracker shippoTracker = new ShippoTracker();
        PackageTracking packageTracking = null;
        try {
            packageTracking = shippoTracker.track("usps", "9200199999977453249942");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(packageTracking.carrier, "usps");
        Assert.assertEquals(packageTracking.tracking_number, "9200199999977453249942");

        TrackingStatus trackingStatus = packageTracking.tracking_status;
        Assert.assertEquals(trackingStatus.status, "DELIVERED");
        Assert.assertEquals(trackingStatus.status_details, "Your shipment has been delivered to a PO box.");
        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String dateStr = "2016-04-04T08:20:00Z";
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = format.parse(dateStr);
            Assert.assertEquals(trackingStatus.status_date, date);
        } catch (ParseException e) {
            Assert.fail("Parse date string error: " + e.getMessage());
        }

        Location location = trackingStatus.location;
        Assert.assertEquals(location.city, "Tappahannock");
        Assert.assertEquals(location.state, "VA");
        Assert.assertEquals(location.zip, "22560");
        Assert.assertEquals(location.country, "US");
    }

    @Test
    public void testShippoTrakerwithwronginfo(){
        ShippoTracker shippoTracker = new ShippoTracker();
        PackageTracking packageTracking = null;
        try {
            packageTracking = shippoTracker.track("ups", "9200199999977453249942");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(packageTracking.carrier, "ups");
        Assert.assertEquals(packageTracking.tracking_number, "9200199999977453249942");

        TrackingStatus trackingStatus = packageTracking.tracking_status;
        Assert.assertEquals(trackingStatus, null);
    }



}