import java.io.*;
import java.net.*;


public class test1 {
    static String
            user_agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36",
            refer = "https://mail.qq.com/",
            upgrade_insecure_requests = "1",
            accept_encoding = "gzip, deflate, sdch",
            accept_language = "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4",
            charset = "UTF-8",
            url_str1 = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=522005705&daid=4&s_url=" +
                    "https://mail.qq.com/cgi-bin/login?vt=passport%26vm=wpt%26ft=loginpage%26target=&" +
                    "style=25&low_login=1&proxy_url=https://mail.qq.com/proxy.html&need_qr=0&hide_border=" +
                    "1&border_radius=0&self_regurl=http://zc.qq.com/chs/index.html?type=1&app_id=11005?" +
                    "t=regist&pt_feedback_link=http://support.qq.com/discuss/350_1.shtml&css=" +
                    "https://res.mail.qq.com/zh_CN/htmledition/style/ptlogin_input24e6b9.css",
            url_str2 = "http://ck101.com/",
            url_str3 = "http://ck101.com/data/cache/forum.js?D11";

    static void print(Object obj) {
        System.out.println(obj);
    }

    static Reader chain2reader(InputStream input_stream, String encoding) throws UnsupportedEncodingException {
        //Ex: InputStreamReader(inputstream, "8859_1")
        return new InputStreamReader(new BufferedInputStream(input_stream), encoding);
    }

    static String readResponse(InputStream connectionInputStream, String encoding) throws IOException {
        try (Reader reader = chain2reader(connectionInputStream, encoding)) {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(" String Buffer");

            int character;
            while ((character = reader.read()) != -1) {
                sBuffer.append((char) character);
            }
            return sBuffer.toString();
        }
    }

    static void setProperties(URLConnection connection) {
        connection.setRequestProperty("User-Agent", user_agent);
        //connection.setRequestProperty("Referer", refer);
        //connection.setRequestProperty("Upgrade-Insecure-Requests", upgrade_insecure_requests);
        connection.setRequestProperty("Accept-Language", accept_language);
    }

    static String GET_request(String url_str) throws IOException {
        URL url = new URL(url_str);
        // openConnection do not really connect to the opponent.
        // It just creates a connection object.
        HttpURLConnection http_connection = (HttpURLConnection) url.openConnection();

        //TODO
        setProperties(http_connection);



        // getInputStream() will auto implicitly connect to opponent.
        String response = readResponse(http_connection.getInputStream(), "utf-8");
        print("Response Header:");
        print(http_connection.getHeaderFields().entrySet());
        return response;
    }

    public static void main(String[] args) throws IOException {

        //Before Java will store and return cookies, you need to enable it
        //receive cookies from sites and send them back to those sites.
        //But it may lost cookies!
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(manager);


        //Default is false. Set it true if we want to send data out.
        //And we have to set property of connection object before it connect to opponent.
        //After it connected, we cannot set or change property anymore.
        //TODO http_connection.setDoOutput(true);


        GET_request(url_str2);
        print(manager.getCookieStore().getCookies());

        GET_request(url_str3);
        print(manager.getCookieStore().getCookies());

        //URLconnection did not implement disconnect but HttpURLConnection did.
        //TODO http_connection.disconnect();
    }

}











