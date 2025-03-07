import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.ysy.open.api.token.GetToken;
import cn.ysy.open.constants.Constants;
import cn.ysy.open.response.AccessToken;
import cn.ysy.open.response.BasicResponse;

/**
 * @author wumu
 * @version V1.0
 * @date 5/20/18
 * @time 2:30 PM
 */
public class Test {

    static String appKey = "";
    static String appSecret = "";

    public static void main(String[] args) throws IOException {

        // load();
        getToken();
        // String accessToken = "at.12y5y6zc2vp1a4o347ccwnn1b2p5bxxn-5j1m3mm1k3-1xt4uep-ebpwm2du0";
        String accessToken = "at.9c8u1vsv8z7uypqh31umdtui6vhwm3ub-9dbi0gr6i5-1jv39yc-dvf6ftpno";
        String deviceSerial = "C64116817";
        // String source = "C64116817:1";
        // source = URLEncoder.encode(source, String.valueOf(StandardCharsets.UTF_8));
        // System.out.println(source);
        // OpenLive openLive = new OpenLive(accessToken, source);
        // BasicResponse<List<OpenLiveResponse>> responseBasicResponse = openLive.executeApi();
        // System.out.println(responseBasicResponse);

    }

    private static void getToken() {
        // GetToken getToken = new GetToken(appKey, appSecret);
        GetToken getToken = new GetToken(Constants.APP_KEY, Constants.APP_SECRET);
        BasicResponse<AccessToken> response = getToken.executeApi();
        AccessToken dataInternal = response.getData();
        String accessToken = dataInternal.getAccessToken();
        System.out.println(response);
        System.out.println(dataInternal);
        System.out.println(accessToken);
    }

    private static void load() throws IOException {

        InputStream in = Test.class.getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        p.load(in);
        System.out.println(p.get("appKey"));
        System.out.println(p.get("appSecret"));
    }
}
