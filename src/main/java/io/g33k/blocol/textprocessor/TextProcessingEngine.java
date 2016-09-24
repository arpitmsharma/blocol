package io.g33k.blocol.textprocessor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TextProcessingEngine {

	private static final Logger log = LoggerFactory
			.getLogger(TextProcessingEngine.class);

	public List<String> preprocessText(String text) {
		log.info("Preprocessing Text");
		log.debug("Text >> " + text);
		List<String> words = StopWordRemover.removeStopWords(text);
		List<String> stems = Stemmer.stem(words);
		return stems;
	}
}
