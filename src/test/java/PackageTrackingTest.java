//import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by ransong on 4/11/16.
 */
public class PackageTrackingTest {
    @Test
    public void parseFromJson() {
        PackageTracking tracking = parseObject("{\"carrier\": \"usps\", \"tracking_number\": \"9200199999977453249942\", \"tracking_status\": {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_updated\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"ae1cf54846744192ae00b6ba25e65895\", \"status\": \"DELIVERED\", \"status_details\": \"Your shipment has been delivered to a PO box.\", \"status_date\": \"2016-04-04T08:20:00Z\", \"location\": {\"city\": \"Tappahannock\", \"state\": \"VA\", \"zip\": \"22560\", \"country\": \"US\"}}, \"tracking_history\": [{\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"41a13a6e1d1f4360b7928f29ee8fed0a\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has been accepted at the USPS origin facility.\", \"status_date\": \"2016-04-01T16:28:00Z\", \"location\": {\"city\": \"Moreno Valley\", \"state\": \"CA\", \"zip\": \"92551\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"28bf4ce673c44de1a5a4daac5c6f6bd4\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has arrived at the USPS origin facility.\", \"status_date\": \"2016-04-01T17:43:00Z\", \"location\": {\"city\": \"Moreno Valley\", \"state\": \"CA\", \"zip\": \"92553\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"431347e7f90942babfc6ba47cfb0127b\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has arrived at the USPS destination facility.\", \"status_date\": \"2016-04-03T22:06:00Z\", \"location\": {\"city\": \"Sandston\", \"state\": \"VA\", \"zip\": \"23150\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"e1dd286edb384c7e9963a920b51754c6\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has departed the USPS facility.\", \"status_date\": \"2016-04-04T04:10:00Z\", \"location\": {\"city\": \"Sandston\", \"state\": \"VA\", \"zip\": \"23150\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"b1b64fbf187a4617bb595a1954bf0639\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has arrived at the post office.\", \"status_date\": \"2016-04-04T06:32:00Z\", \"location\": {\"city\": \"Tappahannock\", \"state\": \"VA\", \"zip\": \"22560\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"04df90e6de3b4a048ea81e858230a09e\", \"status\": \"DELIVERED\", \"status_details\": \"Your shipment has been delivered to a PO box.\", \"status_date\": \"2016-04-04T08:20:00Z\", \"location\": {\"city\": \"Tappahannock\", \"state\": \"VA\", \"zip\": \"22560\", \"country\": \"US\"}}]}", PackageTracking.class);
        Assert.assertEquals(tracking.carrier, "usps");
        Assert.assertEquals(tracking.tracking_number, "9200199999977453249942");

        TrackingStatus trackingStatus = tracking.tracking_status;
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
    }

    @Test
    public void parseFromJsonWithNullFields() {
        PackageTracking tracking = parseObject("{\"carrier\": \"usps\", \"tracking_number\": \"9200199999977453249942\", \"tracking_status\": {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_updated\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"ae1cf54846744192ae00b6ba25e65895\", \"status\": \"DELIVERED\", \"status_details\": \"Your shipment has been delivered to a PO box.\", \"status_date\": \"2016-04-04T08:20:00Z\", \"location\": {\"city\": \"Tappahannock\", \"state\": \"VA\", \"zip\": \"22560\", \"country\": \"US\"}}, \"tracking_history\": [{\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"41a13a6e1d1f4360b7928f29ee8fed0a\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has been accepted at the USPS origin facility.\", \"status_date\": \"2016-04-01T16:28:00Z\", \"location\": null }, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"28bf4ce673c44de1a5a4daac5c6f6bd4\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has arrived at the USPS origin facility.\", \"status_date\": \"2016-04-01T17:43:00Z\", \"location\": {\"city\": \"Moreno Valley\", \"state\": \"CA\", \"zip\": \"92553\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"431347e7f90942babfc6ba47cfb0127b\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has arrived at the USPS destination facility.\", \"status_date\": \"2016-04-03T22:06:00Z\", \"location\": {\"city\": \"Sandston\", \"state\": \"VA\", \"zip\": \"23150\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"e1dd286edb384c7e9963a920b51754c6\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has departed the USPS facility.\", \"status_date\": \"2016-04-04T04:10:00Z\", \"location\": {\"city\": \"Sandston\", \"state\": \"VA\", \"zip\": \"23150\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"b1b64fbf187a4617bb595a1954bf0639\", \"status\": \"TRANSIT\", \"status_details\": \"Your shipment has arrived at the post office.\", \"status_date\": \"2016-04-04T06:32:00Z\", \"location\": {\"city\": \"Tappahannock\", \"state\": \"VA\", \"zip\": \"22560\", \"country\": \"US\"}}, {\"object_created\": \"2016-04-06T23:13:22.365Z\", \"object_id\": \"04df90e6de3b4a048ea81e858230a09e\", \"status\": \"DELIVERED\", \"status_details\": \"Your shipment has been delivered to a PO box.\", \"status_date\": \"2016-04-04T08:20:00Z\", \"location\": {\"city\": \"Tappahannock\", \"state\": \"VA\", \"zip\": \"22560\", \"country\": \"US\"}}]}", PackageTracking.class);
        Assert.assertEquals(tracking.carrier, "usps");
    }
}
