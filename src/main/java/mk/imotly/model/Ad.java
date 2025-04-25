package mk.imotly.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ad {
    private Long id;
    private String title;
    private Integer price;

    private String location;
    @JsonProperty("date_posted")
    LocalDate datePosted;
    private String url;
    private String source;

    @JsonProperty("imageurl")
    private String imageUrl;
    @JsonProperty("num_rooms")
    private Integer numRooms;
    private Integer floor;
    @JsonProperty("num_floors")
    private Integer numFloors;
    private Integer size;
    private String heating;
    @JsonProperty("type_of_obj")
    private String typeOfObj;
    private String description;
    private String state;
    @JsonProperty("for_sale")
    private Boolean forSale;
    private Boolean terrace;
    private Boolean parking;
    private Boolean furnished;
    private Boolean basement;
    @JsonProperty("new_building")
    private Boolean newBuilding;
    private Boolean duplex;
    private Boolean renovated;
    private Boolean lift;


    public Ad() {
    }

    public Ad(String title, Integer price, String location, LocalDate datePosted, String url, String source, String imageUrl, Integer numRooms, Integer floor, Integer numFloors, Integer size, String heating, String typeOfObj,  String state, Boolean forSale, Boolean terrace, Boolean parking, Boolean furnished, Boolean basement, Boolean newBuilding, Boolean duplex, Boolean renovated, Boolean lift) {
        this.title = title;
        this.price = price;
        this.location = location;
        this.datePosted = datePosted;
        this.url = url;
        this.source = source;
        this.imageUrl = imageUrl;
        this.numRooms = numRooms;
        this.floor = floor;
        this.numFloors = numFloors;
        this.size = size;
        this.heating = heating;
        this.typeOfObj = typeOfObj;
        this.description = null;
        this.state = state;
        this.forSale = forSale;
        this.terrace = terrace;
        this.parking = parking;
        this.furnished = furnished;
        this.basement = basement;
        this.newBuilding = newBuilding;
        this.duplex = duplex;
        this.renovated = renovated;
        this.lift = lift;
        if(!forSale){
            if(title.toLowerCase().contains("prodava") || title.toLowerCase().contains("продава") ){
                forSale = true;
            }
        }

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
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

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getNumFloors() {
        return numFloors;
    }

    public void setNumFloors(Integer numFloors) {
        this.numFloors = numFloors;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getTypeOfObj() {
        return typeOfObj;
    }

    public void setTypeOfObj(String typeOfObj) {
        this.typeOfObj = typeOfObj;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getForSale() {
        return forSale;
    }

    public void setForSale(Boolean forSale) {
        this.forSale = forSale;
    }

    public Boolean getTerrace() {
        return terrace;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getFurnished() {
        return furnished;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Boolean getBasement() {
        return basement;
    }

    public void setBasement(Boolean basement) {
        this.basement = basement;
    }

    public Boolean getNewBuilding() {
        return newBuilding;
    }

    public void setNewBuilding(Boolean newBuilding) {
        this.newBuilding = newBuilding;
    }

    public Boolean getDuplex() {
        return duplex;
    }

    public void setDuplex(Boolean duplex) {
        this.duplex = duplex;
    }

    public Boolean getRenovated() {
        return renovated;
    }

    public void setRenovated(Boolean renovated) {
        this.renovated = renovated;
    }

    public Boolean getLift() {
        return lift;
    }

    public void setLift(Boolean lift) {
        this.lift = lift;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", location='" + location + '\'' +
                ", datePosted='" + datePosted + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", numRooms=" + numRooms +
                ", floor=" + floor +
                ", numFloors=" + numFloors +
                ", size=" + size +
                ", heating='" + heating + '\'' +
                ", typeOfObj='" + typeOfObj + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", forSale=" + forSale +
                ", terrace=" + terrace +
                ", parking=" + parking +
                ", furnished=" + furnished +
                ", basement=" + basement +
                ", newBuilding=" + newBuilding +
                ", duplex=" + duplex +
                ", renovated=" + renovated +
                ", lift=" + lift +
                '}';
    }
}