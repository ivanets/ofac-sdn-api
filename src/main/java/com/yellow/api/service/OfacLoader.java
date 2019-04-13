package com.yellow.api.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.yellow.api.domain.xml.SdnList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class OfacLoader {
    public static final String OFAC_S3_REGION = System.getenv("OFAC_S3_REGION");
    public static final String OFAC_S3_BUCKET_NAME = System.getenv("OFAC_S3_BUCKET_NAME");
    private static final String fileObjKeyName = "ofac-sdn.xml";
    public static String downloadFromS3() {
        S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;


        try {

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(OFAC_S3_REGION)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            // Get an object and print its contents.
            System.out.println("Downloading an object");
            fullObject = s3Client.getObject(new GetObjectRequest(OFAC_S3_BUCKET_NAME, fileObjKeyName));
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            System.out.println("Content: ");

            // Get a range of bytes from an object and print the bytes.
            GetObjectRequest rangeObjectRequest = new GetObjectRequest(OFAC_S3_BUCKET_NAME, fileObjKeyName)
                    .withRange(0, 9);
            objectPortion = s3Client.getObject(rangeObjectRequest);
            System.out.println("Printing bytes retrieved.");

            // Get an entire object, overriding the specified response headers, and print the object's content.
            ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides()
                    .withCacheControl("No-cache")
                    .withContentDisposition("attachment; filename=example.txt");
            GetObjectRequest getObjectRequestHeaderOverride = new GetObjectRequest(OFAC_S3_BUCKET_NAME, fileObjKeyName)
                    .withResponseHeaders(headerOverrides);
            headerOverrideObject = s3Client.getObject(getObjectRequestHeaderOverride);
            return displayTextInputStream(headerOverrideObject.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        }
        return null;
    }
    private static String displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        StringBuilder doc = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            doc.append(line);
        }
        return doc.toString();
    }

    public SdnList loadSDNList(){




        try{

            JAXBContext jc = JAXBContext.newInstance(SdnList.class);

            XMLInputFactory xif = XMLInputFactory.newFactory();
            xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false); // this is the magic line
            StreamSource source = new StreamSource(new StringReader(downloadFromS3()));
            XMLStreamReader xsr =  xif.createXMLStreamReader(source);
            Unmarshaller un = jc.createUnmarshaller();
            SdnList sdnList2 = (SdnList) un.unmarshal(xsr);

            return sdnList2;
        } catch (XMLStreamException | JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
