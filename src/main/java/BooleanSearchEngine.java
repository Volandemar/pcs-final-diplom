import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.sun.jdi.Value;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BooleanSearchEngine implements SearchEngine {
    protected Map<String, List<PageEntry>> resultMap = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File path : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(path.getAbsoluteFile()));
            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                int j = i + 1;
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
                freqs.forEach((k, v) -> {
                            PageEntry pageEntry = new PageEntry(pdfsDir.getName(), j, v);
                            if (!resultMap.containsKey(k)) {
                                resultMap.put(k, new ArrayList<>(List.of(pageEntry)));
                            } else {
                                resultMap.get(k).add(pageEntry);
                            }

                        }
                );
            }

        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> searchList = resultMap.get(word);
        searchList.sort(PageEntry::compareTo);
        return searchList;
    }
}
