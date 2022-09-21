package br.com.driver.finder.server.InMemoryDataBase;

public class DriverEntity {

    private int id;
    private String name;
    private String ipAddress;
    private String clientReceiverPort;
    private Double latitude;
    private Double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
}
