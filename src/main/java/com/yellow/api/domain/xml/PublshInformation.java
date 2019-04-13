package com.yellow.api.domain.xml;

import javax.xml.bind.annotation.XmlElement;

public class PublshInformation {
    @XmlElement(name = "Publish_Date")
    public String publshDate;
    @XmlElement(name = "Record_Count")
    public long recordCount;


}
