package com.yellow.api.domain.xml;

import javax.xml.bind.annotation.XmlElement;

public class SdnEntry {

    @XmlElement(name = "uid")
    public String uid;
    @XmlElement(name = "firstName")
    public String firstName;
    @XmlElement(name = "lastName")
    public String lastName;
    @XmlElement(name = "title")
    public String title;
    @XmlElement(name = "sdnType")
    public String sdnType;
    @XmlElement(name = "programList")
    public ProgramList programList;
    @XmlElement(name = "akaList")
    public AkaList akaList;
    @XmlElement(name = "addressList")
    public AddressList addressList;
}
