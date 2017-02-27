import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public class Res {

    private static Document loadTestDocument(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }

    public static void main(String[] args) throws Exception {
        Document doc = loadTestDocument("http://library.blackboard.com/ref/df5b20ed-ce8d-4428-a595-a0091b23dda3/Content/admin_server_samples/sample.xml");
        System.out.println(doc);
        //doc = loadTestDocument("http://localhost/array.xml");
        //System.out.println(doc);
    }
}
