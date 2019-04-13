package com.yellow.api.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class AddressList {

    @XmlElement(name = "address")
    public List<Address> addressList;
}
