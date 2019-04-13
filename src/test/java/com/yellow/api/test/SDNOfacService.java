package com.yellow.api.test;

import com.yellow.api.service.OfacLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SDNOfacService {
    @Test
    public void contextLoads() {
    }

    @Test
    public void testXmlToObject() throws JAXBException, FileNotFoundException {

        OfacLoader loader = new OfacLoader();
        System.out.println(loader.loadSDNList());

    }
}
