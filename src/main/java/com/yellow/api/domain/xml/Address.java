package com.yellow.api.domain.xml;


import javax.xml.bind.annotation.XmlElement;

public class Address {
    @XmlElement(name = "address1")
    public String address1;
    @XmlElement(name = "city")
    public String city;
    @XmlElement(name = "postalCode")
    public String postalCode;
    @XmlElement(name = "country")
    public String country;
    @XmlElement(name = "stateOrProvince")
    public String stateOrProvince;
}
