package hongik.graduationproject.decordetectorbackend.client;

import java.util.ArrayList;
import java.util.List;

public class AiApiClient {
    public List<Float> convertToVector(String imagelink){
        List<Float> vector1 = new ArrayList<Float>();
        vector1.add(3.14F);
        vector1.add(8.05F);
        vector1.add(5.29F);
        vector1.add(3.28F);
        return vector1;
    }
}
