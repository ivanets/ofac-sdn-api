package com.yellow.api.service;

import com.yellow.api.domain.Sanction;
import com.yellow.api.domain.SearchResult;
import com.yellow.api.domain.xml.Address;
import com.yellow.api.domain.xml.SdnEntry;
import com.yellow.api.domain.xml.SdnList;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SDNOfacService {

    private static List<Sanction> allSactions = null;

    private static String publDate = null;


    private static Soundex soundex = new Soundex();

    private static List<Sanction> getAllSanctions(){
        List<Sanction> result = new ArrayList<>();
        OfacLoader loader = new OfacLoader();

        SdnList sdnL = loader.loadSDNList();
        publDate = sdnL.publshInformation.publshDate;
        for (SdnEntry v : sdnL.sdnEntry) {
            System.out.println("SdnEn: "+v.sdnType);
            Sanction s = new Sanction();
            if (v.sdnType != null && v.sdnType.equalsIgnoreCase("Individual")) {
                s.setName(v.firstName + " " + v.lastName);
                System.out.println("SdnEn - Name : "+s.getName());
                s.setNameCodec("");
                if (s.getName() != null) {
                    String[] names = s.getName().split(" ");
                    for (int i = 0; i < names.length; i++) {
                        s.setNameCodec(s.getNameCodec() +soundex.encode(names[i].toUpperCase()));
                    }
                }
                //s.setNameCodec(soundex.encode(s.getName().toUpperCase()));
                s.setType(v.sdnType);
                s.setId(v.uid);
                if (v.addressList != null && v.addressList.addressList != null && !v.addressList.addressList.isEmpty()) {
                    s.setAddresses(new ArrayList<>());
                    for (Address address:
                    v.addressList.addressList) {
                        StringBuilder sb = new StringBuilder();
                        if (address.address1 != null) {
                            sb.append(address.address1);
                            sb.append(" ,");
                        }
                        if (address.city != null) {
                            sb.append(address.city);
                            sb.append(" ,");
                        }
                        if (address.stateOrProvince != null) {
                            sb.append(address.stateOrProvince);
                            sb.append(" ,");
                        }
                        if (address.postalCode != null) {
                            sb.append(address.postalCode);
                            sb.append(" ,");
                        }
                        if (address.country != null) {
                            sb.append(address.country);
                        }
                        if (sb.substring(0, 2).equals(" ,")) {
                            sb.delete(0, 2);
                        }
                        if (sb.substring(sb.length() - 2, sb.length()).equals(" ,")) {
                            sb.delete(sb.length() - 2, sb.length());
                        }

                        s.getAddresses().add(sb.toString());
                    }

                }
                if (v.programList != null && v.programList.program != null && !v.programList.program.isEmpty()) {

                    s.setProgram(new ArrayList<>());
                    v.programList.program.forEach(s1 -> {
                        s.getProgram().add(s1);
                    });
                }
                result.add(s);
            }


        }

        return result;
    }

    static {
        if (allSactions == null)
            allSactions = getAllSanctions();

    }

    public SearchResult[] matcheIndividualName(String name, int minScore){
        List<SearchResult> resultList = new ArrayList<SearchResult>();
        String nameS = new String(soundex.encode(name.toUpperCase()));

        allSactions.parallelStream().forEach((v)->{
            int rF = FuzzySearch.ratio(v.getName().toUpperCase(),name.toUpperCase());
            if(rF >= minScore){
                SearchResult r = new SearchResult();
                r.setSanction(v);
                r.setScore(rF);
                r.setSdnRevisionDate(publDate);
                resultList.add(r);
            }
        });

        Collections.sort(resultList, new Comparator<SearchResult>() {
            @Override
            public int compare(SearchResult o1, SearchResult o2) {
                return o1.getScore() - o2.getScore();
            }
        });


        return resultList.toArray(new SearchResult[resultList.size()]);
    }

}
