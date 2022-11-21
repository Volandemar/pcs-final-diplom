import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.sun.jdi.Value;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    //???

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        var doc = new PdfDocument(new PdfReader(pdfsDir));
        for (int i = 0; i > doc.getNumberOfPages(); i++) {
            int j = i +1;
            var text = PdfTextExtractor.getTextFromPage(doc.getPage(j));
            var words = text.split("\\P{IsAlphabetic}+");
            Map<String, Integer> freqs = new HashMap<>();
            for (var word : words) {
                if (word.isEmpty()) {
                    continue;
                }
                word = word.toLowerCase();
                freqs.put(word, freqs.getOrDefault(word, 0) + 1);
            }
           List<PageEntry> list = new ArrayList<String>()

        }

        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы
    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        return Collections.emptyList();
    }
}
