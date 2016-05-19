import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ransong on 5/18/16.
 */
public class WebTrackerTest {
    @Test
    public void webTrackerTest() {
        WebTracker webTracker = new WebTracker();
        PackageTracking packageTracking = webTracker.track("usps", "9200199999977453249942");

        Assert.assertEquals(packageTracking.carrier, "usps");
        Assert.assertEquals(packageTracking.tracking_number, "9200199999977453249942");

        TrackingStatus trackingStatus = packageTracking.tracking_status;
        Assert.assertEquals(trackingStatus.status, "DELIVERED");
        Assert.assertEquals(trackingStatus.status_details, "Delivered, PO Box");
        Assert.assertEquals(trackingStatus.location.city, "TAPPAHANNOCK");
        Assert.assertEquals(trackingStatus.location.state, "VA");
        Assert.assertEquals(trackingStatus.location.zip, "22560");
    }

}