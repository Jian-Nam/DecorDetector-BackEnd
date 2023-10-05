package hongik.graduationproject.decordetectorbackend.Client;

import hongik.graduationproject.decordetectorbackend.client.AiApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.util.List;

@SpringBootTest
public class AiApiClientTest {

    @Autowired
    AiApiClient aiApiClient;

    @Test
    void convertToVectorTest() throws Exception{
        Resource resource = new UrlResource("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAATYAAACjCAMAAAA3vsLfAAAAP1BMVEX///+msbegrLKfq7GjrrXi5efEy8+vub6ps7nv8fL29/istrzf4+XY3d/o6+y7w8jR1tm5wcbN09bCyc3y9PTNNlT3AAACUUlEQVR4nO3c6W6DMBBG0XpswhYIEN7/Wcu+tGnjqSrZSPdIlar8Gn0y48GBfHwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAID/dn9mWegaLqcy4kwZuoqLKVIxgzR0HdeSGzem5h6hC7mUYgptiK0JXcmlZHNqxuahK7mS57LYaG0atV0XWxK6lCtp187GhqCxLjZmXY1E5rVmitCVXEozj2wlqal0Q2xOutBlXEF+r6oqmddXYyXrxn/zZPyQ0e0HVWpFnHNis2aIq8jHzKpW5g9F2nvoCiPUyDrcjh3NPsZZrX7K4VMnrgpdZWT68hDaktH0dyYpG8RBLcaPM33oWuPhndoYHLktiq/X4m/sLXS5sWgVsVmO3haJfZ/WlhpDyKr0T02YQFaK/cDR1zY3/87GEdJOcYnS2Da9YkMIXWtEEu/WRmc7aLxbm+Ma3XXesQlHbjv/jVS4G90R258oYqtD1xoR/97GlnCg2EkZQHaV4tQodK0RUdzJc3O1KxQ3V9zK7/xTo7sdPBTNjWPKjWZPILeN5uRoyI3rdKH4LmHgMm4WJv4D74xvryaaEWRab6ELjsRTtdyYQha5arlZzo8WmtGNx+03veKJIxbbTnHoRmc7yN4HtsQWutKo1J67Aq9fnXVe7Y1L9KvUo7053pD8xme18Zz4N/375cbs8ULvTq9ulG2bnd7myFhrLy2/XmGMuNt8OlTcH2tw0gauLmKVsSL29HJVMb2KZUsmj9/k9YvnivKa6xMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAsPoElgcQiyXfWy4AAAAASUVORK5CYII=");
        try {
            List<Float> res = aiApiClient.convertToVector(resource);
        }catch(Exception e){}
    }
}
