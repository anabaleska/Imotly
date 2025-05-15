package mk.imotly.model;

public class Filters {
    private Boolean basement;
    private String description;
    private Boolean duplex;
    private Integer floor;
    private Boolean forSale;
    private Boolean furnished;
    private String heating;
    private Boolean lift;
    private String location;
    private Integer maxPrice;
    private Integer maxSize;
    private Boolean newBuilding;
    private Integer numFloors;
    private Integer numRooms;
    private Boolean parking;
    private Boolean renovated;
    private String state;
    private Boolean terrace;
    private String title;
    private String typeOfObj;

    public Boolean getBasement() {
        return basement;
    }

    public void setBasement(Boolean basement) {
        this.basement = basement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDuplex() {
        return duplex;
    }

    public void setDuplex(Boolean duplex) {
        this.duplex = duplex;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getForSale() {
        return forSale;
    }

    public void setForSale(Boolean forSale) {
        this.forSale = forSale;
    }

    public Boolean getFurnished() {
        return furnished;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public Boolean getLift() {
        return lift;
    }

    public void setLift(Boolean lift) {
        this.lift = lift;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Boolean getNewBuilding() {
        return newBuilding;
    }

    public void setNewBuilding(Boolean newBuilding) {
        this.newBuilding = newBuilding;
    }

    public Integer getNumFloors() {
        return numFloors;
    }

    public void setNumFloors(Integer numFloors) {
        this.numFloors = numFloors;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getRenovated() {
        return renovated;
    }

    public void setRenovated(Boolean renovated) {
        this.renovated = renovated;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getTerrace() {
        return terrace;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeOfObj() {
        return typeOfObj;
    }

    public void setTypeOfObj(String typeOfObj) {
        this.typeOfObj = typeOfObj;
    }

    @Override
    public String toString() {
        return "Filters{" +
                "basement=" + basement +
                ", description='" + description + '\'' +
                ", duplex=" + duplex +
                ", floor=" + floor +
                ", forSale=" + forSale +
                ", furnished=" + furnished +
                ", heating='" + heating + '\'' +
                ", lift=" + lift +
                ", location='" + location + '\'' +
                ", maxPrice=" + maxPrice +
                ", maxSize=" + maxSize +
                ", newBuilding=" + newBuilding +
                ", numFloors=" + numFloors +
                ", numRooms=" + numRooms +
                ", parking=" + parking +
                ", renovated=" + renovated +
                ", state='" + state + '\'' +
                ", terrace=" + terrace +
                ", title='" + title + '\'' +
                ", typeOfObj='" + typeOfObj + '\'' +
                '}';
    }

}
