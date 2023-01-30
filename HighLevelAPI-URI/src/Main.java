import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        try {

            // part 1
//            URI uri = new URI("db://username:password@myserver.com:5000/catalogue/phones?os=android#samsung");
//            System.out.println("Scheme = " + uri.getScheme()); // db
//            System.out.println("Scheme-specific part = " + uri.getSchemeSpecificPart()); // //username:password@myserver.com:5000/catalogue/phones?os=android
//            System.out.println("Authority = " + uri.getAuthority());  //  username:password@myserver.com:5000
//            System.out.println("User info = " + uri.getUserInfo());  //  username:password
//            System.out.println("Host = " + uri.getHost()); // myserver.com
//            System.out.println("Port = " + uri.getPort());  // 5000
//            System.out.println("Path = " + uri.getPath());  //  /catalogue/phones
//            System.out.println("Query = " + uri.getQuery());  // os=android
//            System.out.println("Fragment = " + uri.getFragment()); // samsung

            // part 2
//            // URI uri = new URI("http://username:password@myserver.com:5000/catalogue/phones?os=android#samsung");
//
//            // absolute path
//            URI baseUri = new URI("http://username:password@myserver.com:5000");
//            // this is relative path from uri, because it doesn't completely identify the resource.
//            // (doesn't contain schema, web page or db or file, root location
//            URI uri1 = new URI("/catalogue/phones?os=android#samsung");
//            URI uri2 = new URI("/catalogue/tvs?manufacturer=samsung");
//            URI uri3 = new URI("/stores/locations?zip=12345");
//
//            URI resolvedUri1 = baseUri.resolve(uri1);
//            URI resolvedUri2 = baseUri.resolve(uri2);
//            URI resolvedUri3 = baseUri.resolve(uri3);
//            //.resolve method appending that and forming a valid absolute path
//
//
//
//
//            // uri convert to url
//            URL url1 = resolvedUri1.toURL();
//            System.out.println("URL = " + url1);
//
//            URL url2 = resolvedUri2.toURL();
//            System.out.println("URL = " + url2);
//
//            URL url3 = resolvedUri3.toURL();
//            System.out.println("URL = " + url3);
//
//        } catch (URISyntaxException e) {
//            System.out.println("URI Bad Syntax: " + e.getMessage());
//        } catch (MalformedURLException e) {
//            System.out.println("URL Malformed: " + e.getMessage());
//        }

        // part 3 first way viewing web page in intellij console
//            URL url = new URL("http://example.org");
//
//            BufferedReader inputStream = new BufferedReader( new InputStreamReader(url.openStream()));
//
//            String line = "";
//            while(line != null){
//                line = inputStream.readLine();
//                System.out.println(line);
//            }
//            inputStream.close();


        // part 4- second way viewing web page in intellij console (URL connection class)
//            URL url = new URL("http://example.org");
//            URLConnection urlConnection = url.openConnection(); // creating connection instance
//            urlConnection.setDoOutput(true);
//            urlConnection.connect(); // connect to url with using connection instance
//
//            BufferedReader inputStream = new BufferedReader( new InputStreamReader(urlConnection.getInputStream()));
//
//            String line = "";
//            while(line != null){
//                line = inputStream.readLine();
//                System.out.println(line);
//            }
//            inputStream.close();

        // part 5 using HttpURLConnection class
            URL url = new URL("https://api.flickr.com/services/feeds/photos_public.gne?tags=cats");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // creating instance
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode(); // get response code and implicitly perform connect
            System.out.println("Response code: " + responseCode);

            if(responseCode != 200) {
                System.out.println("Error reading web page");
                System.out.println(connection.getResponseMessage()); // Not Found   vb
                return;
            }

            BufferedReader inputReader = new BufferedReader( new InputStreamReader(connection.getInputStream()));
            String line;
            while((line =inputReader.readLine()) != null){
                System.out.println(line);
            }

            inputReader.close();


        }catch (MalformedURLException e){
            System.out.println("MalformedURLException: " + e.getMessage());
        }catch (IOException e){
            System.out.println("IOException: "+ e.getMessage());
        }

    }
}