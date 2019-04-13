import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.apache.commons.codec.language.Soundex;

public class Main {



    public static void main(String args[]){
        Soundex soundex = new Soundex();
        String phoneticValue1 = soundex.encode("Caterina Csaszar");
        String phoneticValue2 = soundex.encode("a");

        System.out.println(FuzzySearch.weightedRatio(phoneticValue1,phoneticValue2));
    }
}


