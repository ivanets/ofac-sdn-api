package com.yellow.api.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sdnList")
public class SdnList {
    @XmlElement(name = "publshInformation")
    public PublshInformation publshInformation;

    @XmlElement(name = "sdnEntry")
    public List<SdnEntry> sdnEntry;




    @Override
    public String toString() {
        return "sdnList{" +
                "\n publshInformation.date='" + publshInformation.publshDate + '\'' +
                ",\n publshInformation.count ='" + publshInformation.recordCount + '\'' +
                '}';
    }
}
