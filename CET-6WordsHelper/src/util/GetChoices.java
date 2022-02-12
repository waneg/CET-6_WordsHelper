package util;

import java.util.ArrayList;

public class GetChoices {
    public static void get4Choices() {
        for (int i =0; i < 4; i++) {
            int t = (int) (Math.random() * DataImport.words.size());
            Conf.eng[i]= DataImport.words.get(t);
            Conf.chi[i]= DataImport.meanings.get(t);
        }
    }
}
