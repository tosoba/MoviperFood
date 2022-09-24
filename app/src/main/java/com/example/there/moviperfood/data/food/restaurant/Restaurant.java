package com.example.there.moviperfood.data.food.restaurant;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(tableName = "restaurants")
@Getter
@Setter
@EqualsAndHashCode
public class Restaurant implements Parcelable {

    private String cuisines;

    @SerializedName("photos_url")
    private String photosUrl;

    @SerializedName("has_online_delivery")
    private String hasOnlineDelivery;

    private RestaurantLocation location;

    @SerializedName("featured_image")
    private String featuredImage;

    private String[] offers;

    @SerializedName("menu_url")
    private String menuUrl;

    @SerializedName("is_delivering_now")
    private String isDeliveringNow;

    @SerializedName("establishment_types")
    private String[] establishmentTypes;

    private String url;

    @SerializedName("switch_to_order_menu")
    private String switchToOrderMenu;

    @SerializedName("user_rating")
    private RestaurantUserRating userRating;

    private String currency;

    @PrimaryKey
    @NonNull
    private String id;

    @SerializedName("price_range")
    private String priceRange;

    private String name;

    @SerializedName("deeplink")
    private String deepLink;

    @SerializedName("events_url")
    private String eventsUrl;

    @SerializedName("average_cost_for_two")
    private String averageCostForTwo;

    private String thumb;

    @SerializedName("has_table_booking")
    private String hasTableBooking;

    @Nullable
    private Date lastSearched;

    public Restaurant(String cuisines,
                      String photosUrl,
                      String hasOnlineDelivery,
                      RestaurantLocation location,
                      String featuredImage,
                      String[] offers,
                      String menuUrl,
                      String isDeliveringNow,
                      String[] establishmentTypes,
                      String url,
                      String switchToOrderMenu,
                      RestaurantUserRating userRating,
                      String currency,
                      @NonNull String id,
                      String priceRange,
                      String name,
                      String deepLink,
                      String eventsUrl,
                      String averageCostForTwo,
                      String thumb,
                      String hasTableBooking,
                      @Nullable Date lastSearched) {
        this.cuisines = cuisines;
        this.photosUrl = photosUrl;
        this.hasOnlineDelivery = hasOnlineDelivery;
        this.location = location;
        this.featuredImage = featuredImage;
        this.offers = offers;
        this.menuUrl = menuUrl;
        this.isDeliveringNow = isDeliveringNow;
        this.establishmentTypes = establishmentTypes;
        this.url = url;
        this.switchToOrderMenu = switchToOrderMenu;
        this.userRating = userRating;
        this.currency = currency;
        this.id = id;
        this.priceRange = priceRange;
        this.name = name;
        this.deepLink = deepLink;
        this.eventsUrl = eventsUrl;
        this.averageCostForTwo = averageCostForTwo;
        this.thumb = thumb;
        this.hasTableBooking = hasTableBooking;
        this.lastSearched = lastSearched;
    }

    private Restaurant(Parcel in) {
        cuisines = in.readString();
        photosUrl = in.readString();
        hasOnlineDelivery = in.readString();
        location = in.readParcelable(RestaurantLocation.class.getClassLoader());
        featuredImage = in.readString();
        offers = in.createStringArray();
        menuUrl = in.readString();
        isDeliveringNow = in.readString();
        establishmentTypes = in.createStringArray();
        url = in.readString();
        switchToOrderMenu = in.readString();
        userRating = in.readParcelable(RestaurantUserRating.class.getClassLoader());
        currency = in.readString();
        id = in.readString();
        priceRange = in.readString();
        name = in.readString();
        deepLink = in.readString();
        eventsUrl = in.readString();
        averageCostForTwo = in.readString();
        thumb = in.readString();
        hasTableBooking = in.readString();
        lastSearched = (Date) in.readSerializable();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cuisines);
        dest.writeString(photosUrl);
        dest.writeString(hasOnlineDelivery);
        dest.writeParcelable(location, flags);
        dest.writeString(featuredImage);
        dest.writeStringArray(offers);
        dest.writeString(menuUrl);
        dest.writeString(isDeliveringNow);
        dest.writeStringArray(establishmentTypes);
        dest.writeString(url);
        dest.writeString(switchToOrderMenu);
        dest.writeParcelable(userRating, flags);
        dest.writeString(currency);
        dest.writeString(id);
        dest.writeString(priceRange);
        dest.writeString(name);
        dest.writeString(deepLink);
        dest.writeString(eventsUrl);
        dest.writeString(averageCostForTwo);
        dest.writeString(thumb);
        dest.writeString(hasTableBooking);
        dest.writeSerializable(lastSearched);
    }
}
