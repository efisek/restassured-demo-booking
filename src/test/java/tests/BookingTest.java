package tests;

import base.BaseSetup;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.BookingDates;
import model.RequestCreateBooking;
import model.ResponseCreateBooking;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BookingTest extends BaseSetup {

    String _bookingId= StringUtils.EMPTY;

    @Test(priority = 1)
    public void createBooking() {
        RequestCreateBooking requestCreateBooking=new RequestCreateBooking();
        BookingDates bookingDates = new BookingDates();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        LocalDate checkinLocalDate = LocalDate.now();
        String checkinDateStr = checkinLocalDate.format(dateFormat);

        LocalDate checkoutLocalDate = checkinLocalDate.plusDays(1);
        String checkoutDateStr = checkoutLocalDate.format(dateFormat);

        Date checkinDate = Date.from(checkinLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date checkoutDate = Date.from(checkoutLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        System.out.println("Check-in Date: " + checkinDateStr);
        System.out.println("Check-out Date: " + checkoutDateStr);

        bookingDates.setCheckin(checkinDate);
        bookingDates.setCheckout(checkoutDate);

        requestCreateBooking.setFirstname("Test");
        requestCreateBooking.setLastname("User");
        requestCreateBooking.setTotalprice(1234);
        requestCreateBooking.setDepositpaid(true);
        requestCreateBooking.setBookingdates(bookingDates);
        requestCreateBooking.setAdditionalneeds("Milk");

        ResponseCreateBooking res = given()
                .contentType(ContentType.JSON)
                .body(requestCreateBooking)
                .log().all()
                .when()
                .post(baseURI+"/booking")
                .then()
                .assertThat().statusCode(200)
                .log().all()
                .extract().as(ResponseCreateBooking.class);

        _bookingId=String.valueOf(res.getBookingId());

        Assert.assertEquals(res.getBooking().getFirstname(),requestCreateBooking.getFirstname(),"Firstname is not equal");
        Assert.assertEquals(res.getBooking().getLastname(),requestCreateBooking.getLastname(),"Lastname is not equal");
        Assert.assertEquals(res.getBooking().getTotalprice(),requestCreateBooking.getTotalprice(),"Total price is not equal");
        Assert.assertEquals(res.getBooking().isDepositpaid(),requestCreateBooking.isDepositpaid(),"Not deposited");
        Assert.assertEquals(res.getBooking().getBookingdates().getCheckin(),requestCreateBooking.getBookingdates().getCheckin(),"Check-in date is not equal");
        Assert.assertEquals(res.getBooking().getBookingdates().getCheckout(),requestCreateBooking.getBookingdates().getCheckout(),"Checkout date is not equal");
        Assert.assertEquals(res.getBooking().getAdditionalneeds(),requestCreateBooking.getAdditionalneeds(),"Additional needs is not equal");
        Assert.assertTrue(res.getBookingId()>0,"Id not created");

    }


}
