package mk.imotly.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class SavedSearch {
    private Long id;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("min_price")
    private Integer minPrice;
    @JsonProperty("max_price")
    private Integer maxPrice;
    private String location;
    @JsonProperty("num_rooms")
    private Integer numRooms;
    private Integer floor;
    @JsonProperty("num_floors")
    private Integer numFloors;
    private Integer size;
    private String heating;
    @JsonProperty("type_of_obj")
    private String typeOfObj;
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
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return "SavedSearch{" +
                "id=" + id +
                ", userId=" + userId +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", location='" + location + '\'' +
                ", numRooms=" + numRooms +
                ", floor=" + floor +
                ", numFloors=" + numFloors +
                ", size=" + size +
                ", heating='" + heating + '\'' +
                ", typeOfObj='" + typeOfObj + '\'' +
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
