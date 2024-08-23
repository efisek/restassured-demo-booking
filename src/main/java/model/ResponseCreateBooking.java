package model;

import com.google.gson.annotations.SerializedName;

public class ResponseCreateBooking extends Booking{

    @SerializedName("bookingid")
    private int bookingId;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }


    @SerializedName("booking")
    private RequestCreateBooking booking;

    public RequestCreateBooking getBooking() {
        return booking;
    }

    public void setBooking(RequestCreateBooking booking) {
        this.booking = booking;
    }

}
