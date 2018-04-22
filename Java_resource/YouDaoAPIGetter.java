import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class YouDaoAPIGetter {

    static String sign;
    static String url = "https://openapi.youdao.com/api";
    static String appKey = "YOUR APPKEY";
    static String from = "auto";
    static String to = "EN";
    static String salt = String.valueOf(System.currentTimeMillis());
    static String appSecret = "YOUR APPSECRET";

    public static String checkWord(String query) throws Exception
    {
        sign = md5(appKey+query+salt+appSecret);

        Map params = new HashMap();

        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", appKey);

        return doRequest(url, params);
    }

    public static String doRequest(String url, Map params) throws Exception {
        String result = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        List<BasicNameValuePair> list = new ArrayList<>();
        Iterator<Map.Entry> i = params.entrySet().iterator();

        while (i.hasNext()) {
            Map.Entry en = i.next();
            list.add(new BasicNameValuePair((String) en.getKey(), (String) en.getValue()));
        }

        post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));

        try(CloseableHttpResponse response = client.execute(post))
        {

            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, "utf-8");
            EntityUtils.consume(httpEntity);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String md5(String string)
    {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }
}
