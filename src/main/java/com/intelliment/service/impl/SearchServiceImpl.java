package com.intelliment.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.intelliment.model.SearchResult;
import com.intelliment.model.SearchText;
import com.intelliment.service.SearchService;
import com.opencsv.CSVWriter;

@Service
public class SearchServiceImpl implements SearchService {

	private static final String RESULT_CSV = "result.csv";
	private static final String INPUT_FILE = "input.txt";

	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

	@Override
	public Object search(SearchText searchText) {

		// Read file
		Map<String, Integer> counts = null;
		try {
			counts = readFileAndReturnsCount();
		} catch (FileNotFoundException e) {
			logger.error("Error reading input file", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		final Map<String, Integer> finaMap = counts;
		List<String> strings = searchText.getSearchText();
		LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
		strings.stream().forEach((word) -> result.put(word, finaMap.containsKey(word) ? finaMap.get(word) : 0));

		SearchResult searchResult = new SearchResult();
		searchResult.setCounts(result);
		return searchResult;
	}

	private Map<String, Integer> readFileAndReturnsCount() throws FileNotFoundException {

		Map<String, Integer> counts = new TreeMap<String, Integer>();

		Scanner s = new Scanner(new File(INPUT_FILE));

		while (s.hasNext()) {
			String str = s.next();

			if (counts.containsKey(str)) {
				counts.put(str, counts.get(str) + 1);
			} else {
				counts.put(str, 1);
			}
		}
		s.close();
		return counts;
	}

	@Override
	public Response topN(Integer N) throws IOException {
		// Read file
		Map<String, Integer> counts = null;
		try {
			counts = readFileAndReturnsCount();
		} catch (FileNotFoundException e) {
			logger.error("Error reading input file", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		LinkedHashMap<String, Integer> result = counts.entrySet().stream()
				.sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue())).limit(N)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		File csvFile = createCSV(result);

		Response.ResponseBuilder response = Response.ok((Object) csvFile);
		response.header("Content-Disposition", "attachment; filename=result.csv");
		return response.build();

	}

	private File createCSV(LinkedHashMap<String, Integer> result) throws IOException {
		File csvFile = new File(RESULT_CSV);
		CSVWriter writer = new CSVWriter(new FileWriter(csvFile));

		for (Entry<String, Integer> entry : result.entrySet()) {
			String[] record = new String[2];
			record[0] = entry.getKey();
			record[1] = entry.getValue().toString();
			writer.writeNext(record);
		}

		writer.close();
		return csvFile;
	}
}
