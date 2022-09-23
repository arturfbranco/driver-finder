package br.com.driver.finder.server.InMemoryDataBase;

import br.com.driver.finder.server.Exception.AddressNotFoundException;
import br.com.driver.finder.server.Util.Util;
import org.json.JSONObject;

public class ClientEntity {

    private Integer id;
    private String name;
    private String clientIp;
    private Integer clientReceiverPort;
    private Double latitude;
    private Double longitude;
    private String address;
    private ClientType clientType;
    private ClientStatus status;

    public ClientEntity(JSONObject json) throws AddressNotFoundException {
        JSONObject address = Util.getAddress(json.getString("address"));
        this.name = json.getString("name");
        this.clientIp = json.getString("clientIp");
        this.clientReceiverPort = json.getInt("port");
        this.latitude = address.getDouble("latitude");
        this.longitude = address.getDouble("longitude");
        this.address = address.getString("formattedAddress");
    }
    public ClientEntity(){};

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

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getClientReceiverPort() {
        return clientReceiverPort;
    }

    public void setClientReceiverPort(Integer clientReceiverPort) {
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

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }
}
