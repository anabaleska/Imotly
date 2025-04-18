package mk.imotly.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ad {
    private Long id;
    private String title;
    private String price;
    private String location;
    @JsonProperty("date_posted")
    private String datePosted;
    private String url;
    private String source;

    @JsonProperty("imageurl")
    private String imageUrl;
    public Ad() {
    }

    public Ad(String title, String price, String location, String datePosted, String url, String source, String imageUrl) {
        this.title = title;
        this.price = price;
        this.location = location;
        this.datePosted = datePosted;
        this.url = url;
        this.source = source;
        this.imageUrl = imageUrl;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", datePosted=" + datePosted +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}