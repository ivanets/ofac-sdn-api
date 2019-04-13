package com.yellow.api.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class ProgramList {
    @XmlElement(name = "program")
    public List<String> program;
}
