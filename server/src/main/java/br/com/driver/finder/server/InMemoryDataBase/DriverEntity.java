package br.com.driver.finder.server.InMemoryDataBase;

public class DriverEntity {

    private Integer id;
    private String name;
    private String ip;
    private String clientReceiverPort;
    private Double latitude;
    private Double longitude;
    private String address;
    private DriverStatus status;

    public DriverEntity(){
        this.status = DriverStatus.AVAILABLE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientReceiverPort() {
        return clientReceiverPort;
    }

    public void setClientReceiverPort(String clientReceiverPort) {
        this.clientReceiverPort = clientReceiverPort;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }
}
