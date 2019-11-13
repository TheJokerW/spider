public class House {

    private  String houseName;
    private  String housePrice;
    private  String lng;
    private  String lat;

    public House(String houseName, String housePrice,Object[] o) {
        this.houseName = houseName;
        this.housePrice = housePrice;
        this.lng=(String)o[0];
        this.lat=(String)o[1];
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(String housePrice) {
        this.housePrice = housePrice;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
