import org.apache.fop.apps.*;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Created by Intellij IDEA
 * User:  malsalkini
 */
public class FOPPdfDemo {

    public static void main(String[] args) {

        FOPPdfDemo  fopPdfDemo = new FOPPdfDemo();

        try {
            fopPdfDemo.convertToPDF();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FOPException e) {
            e.printStackTrace();
        }
    }

    private void convertToPDF() throws IOException, FOPException, TransformerException {
        File xsltFile = new File("files/template.xsl");

        StreamSource xmlSource = new StreamSource(new File("files/Employees.xml"));

        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new FileOutputStream("D:\\Temp\\employee.pdf");
        out = new BufferedOutputStream(out);
        try {

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlSource, result);
        } finally {
            out.close();
        }
    }
}
