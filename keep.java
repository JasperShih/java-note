CookieManger
Proxies
//Reading the Header

============================
/*
There are no methods that directly read or change the value of connected of URLconnection.
However, any method that causes the URLConnection to connect should set this variable to true,
including connect(), getInputStream(), and getOutputStream().

There are no disconnect methods in java.net.URLConnection.

In HttpURLConnection keep-alive, servers will time out and close the connection in as little as 5 seconds of inactivity.
However, it’s still preferred for the client to close the connection as soon as it knows it’s done.
The HttpURLConnection class transparently supports HTTP Keep-Alive unless you explicitly turn it off.
That is, it will reuse sockets if you connect to the same server again before the server has closed the connection.
Once you know you’re done talking to a particular host, the disconnect() method enables a client to break
the connection: public abstract void disconnect()
*/

用http_connection.getHeaderFields()得到的header會有漏; cookieManger也會有漏cookie的情況;
我看之後可能自己開socket寫比較穩.

HttpURLConnection:

try {
URL url = new URL("http://lesswrong.com/");
HttpURLConnection http_connection = (HttpURLConnection) url.openConnection();
} catch (MalformedURLException ex) {
System.err.println(args[0] + " is not a parseable URL");
}catch (IOException ex) {
System.err.println(ex);
}

Http Reader:

try (InputStream inputstream =
new BufferedInputStream(url_connection.getInputStream())) {
// chain the InputStream to a Reader
Reader reader = new InputStreamReader(inputstream);
//Reader reader = new InputStreamReader(inputstream, "8859_1"); Thst's right!

int character;
while ((character = r.read()) != -1) {
System.out.print((char) character);
}
}

POST:

try {
URL url = new URL("http://lesswrong.com/");
HttpURLConnection http_connection = (HttpURLConnection) url.openConnection();
http_connection.setDoOutput(true);
OutputStream buffered = new BufferedOutputStream(raw);
OutputStreamWriter out = new OutputStreamWriter(buffered, "8859_1");
try (OutputStreamWriter out
= new OutputStreamWriter(
new BufferedOutputStream(
http_connection.getOutputStream()), "UTF-8")) {
// The POST line, the Content-type header,
// and the Content-length headers are sent by the URLConnection.
// We just need to send the data
out.write("first=Julie&middle=&last=Harting&work=String+Quartet\r\n");
out.flush();
//Because we use try with statement, we don't have close out manually
//out.close();
//I don't know this disconnection proper or not
//http_connection.disconnect()
}
} catch (MalformedURLException ex) {
  System.err.println(args[0] + " is not a parseable URL");
}catch (IOException ex) {
System.err.println(ex);
}


SaveBinaryFile:

try (InputStream raw = uc.getInputStream()) {
InputStream in = new BufferedInputStream(raw);
byte[] data = new byte[contentLength];
int offset = 0;
while (offset < contentLength) {
int bytesRead = in.read(data, offset, data.length - offset);
if (bytesRead == -1) break;
offset += bytesRead;
}
if (offset != contentLength) {
throw new IOException("Only read " + offset
+ " bytes; Expected " + contentLength + " bytes");
}
String filename = u.getFile();
filename = filename.substring(filename.lastIndexOf('/') + 1);
try (FileOutputStream fout = new FileOutputStream(filename)) {
fout.write(data);
fout.flush();
}
}


TimeOut:

URL u = new URL("http://www.example.org");
URLConnuction uc = u.openConnection();
uc.setConnectTimeout(30000);
uc.setReadTimeout(45000);

Set Property:

//This method can be used only before the connection is opened.
//You can set the same property to a new value, but this changes
//the existing property value.
uc.setRequestProperty("Cookie",
"username=elharo; password=ACD0X9F23JJJn6G; session=100678945");
//public Map<String,List<String>> getRequestProperties()

Get Header:
http_connection.getHeaderFields().entrySet()

Cookie Manager:

//Before Java will store and return cookies, you need to enable it
//receive cookies from sites and send them back to those sites
CookieManager manager = new CookieManager();
CookieHandler.setDefault(manager);

//You can retrieve the store in which the CookieManager saves its cookies with
the getCookieStore() method:
CookieStore store = manager.getCookieStore();
//The CookieStore class allows you to add, remove, and list cookies so you can control
//the cookies that are sent outside the normal flow of HTTP requests and responses:
public void add(URI uri, HttpCookie cookie)
public List<HttpCookie> get(URI uri)
public List<HttpCookie> getCookies()
public List<URI> getURIs()
public boolean remove(URI uri, HttpCookie cookie)
public boolean removeAll()
//Each cookie in the store is encapsulated in an HttpCookie object


Proxies:

public URLConnection openConnection(Proxy proxy) throws IOException

Since java 1.5 you can also pass a java.net.Proxy instance to the openConnection(proxy) method:
//Proxy instance, proxy ip = 10.0.0.1 with port 8080
//Proxy.Type.DIRECT|Proxy.Type.HTTP|Proxy.Type.SOCKS
Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));
conn = new URL(urlString).openConnection(proxy);
If your proxy requires authentication it will give you response 407.

In this case you'll need the following code:

    Authenticator authenticator = new Authenticator() {

        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication("user",
                    "password".toCharArray()));
        }
    };
    Authenticator.setDefault(authenticator);


//http proxy
System.setProperty("http.proxyHost", "192.168.254.254");
System.setProperty("http.proxyPort", "9000");
System.setProperty("http.nonProxyHosts", "java.oreilly.com|xml.oreilly.com");
//sock proxy. it don't have nonProxyHosts
System.setProperty("socksProxyHost", "192.168.254.254");
System.setProperty("socksProxyPort", "9000");

SocketAddress address = new InetSocketAddress("proxy.example.com", 80);
Proxy proxy = new Proxy(Proxy.Type.HTTP, address);


URLEncoder:

String encoded = URLEncoder.encode("This*string*has*asterisks", "UTF-8");

String url = "https://www.google.com/search?";
url += URLEncoder.encode("hl", "UTF-8");
url += "=";
url += URLEncoder.encode("en", "UTF-8");
url += "&";
url += URLEncoder.encode("as_q", "UTF-8");
url += "=";
url += URLEncoder.encode("Java", "UTF-8");
url += "&";
url += URLEncoder.encode("as_epq", "UTF-8");
url += "=";
url += URLEncoder.encode("I/O", "UTF-8");
System.out.println(url);

String input = "https://www.google.com/" +
"search?hl=en&as_q=Java&as_epq=I%2FO";
String output = URLDecoder.decode(input, "UTF-8");
System.out.println(output);


JavaScript:
onclick  click
onsubmit submit
all javascript events

disable javascript of browser to login!!

onfocus 進入欄位
onblur  離開欄位

script

把所有的網址拔掉;
再叫瀏覽器取出password;