package com.web.crawler;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.web.crawler.entity.Entry;
import com.web.crawler.util.WebParser;
import com.web.crawler.util.WordCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class CrawlerApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerApplication.class);

	@Value("${path.terms}")
	private String termsPath;

	@Value("${path.pages}")
	private String pagesPath;

	@Value("${path.result}")
	private String resultPath;

	@Value("${path.topresult}")
	private String topresult;

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

	public void run(String... args) throws Exception {

		/*Reading data about web pages from file*/
		Map<String, String> pagesData = null;
		try (Stream<String> stream = Files.lines(Paths.get(pagesPath))) {
			pagesData = stream.collect(Collectors.toMap(Function.identity(),
					page -> WebParser.parse(page)));
			LOGGER.info("Pages are read");
		} catch (Exception e) {
			LOGGER.info("Error were occurred during collect data");
		}


		/*Reading data about terms from file*/
		List<String> terms = null;
		try (Stream<String> stream = Files.lines(Paths.get(termsPath))) {
			terms = stream.collect(Collectors.toList());
			LOGGER.info("Tearms are read");
		} catch (Exception e) {
			LOGGER.info("Error were occurred during read terms");
		}



		/*Iterating of web pages and writing results to csv file*/
		File file = new File(resultPath);
		FileWriter outputfile = null;
		CSVWriter writer = null;
		try {
			outputfile = new FileWriter(file);
			writer = new CSVWriter(outputfile);
			String[] pageHeader = { "Page"};
			String[] header = Stream.concat(Arrays.stream(pageHeader), terms.stream()).toArray(String[]::new);
			writer.writeNext(header);

			for (String page : pagesData.keySet()) {
				ArrayList<String> integers = new ArrayList<>();
				String pageData = pagesData.get(page);

				for (String term : terms) {
					integers.add(WordCounter.count(pageData,term) + "");
				}
				String[] data = Stream.concat(Arrays.asList(page).stream(), integers.stream()).toArray(String[]::new);
				writer.writeNext(data);
			}
			LOGGER.info("Result is complete");

		}
		catch (IOException e) {
			LOGGER.info("Write data error");
		} finally {
			writer.close();
		}


		/* Sorting of recived data*/
		ArrayList<Entry> list = new ArrayList<>();
		FileReader inputfile = new FileReader(file);
		CSVReader reader = new CSVReader(inputfile);
		File topfile = new File(topresult);
		outputfile = new FileWriter(topfile);
		writer = new CSVWriter(outputfile);
		String[] pageHeader = { "Page"};
		String[] header = Stream.concat(Arrays.stream(pageHeader), terms.stream()).toArray(String[]::new);
		writer.writeNext(header);
		String[] nextLine;
		reader.readNext();
		while ((nextLine = reader.readNext()) != null) {
			list.add(new Entry(nextLine));
		}
		Collections.sort(list);

		boolean stopFlag = true;
		int counter = 0;
		while(stopFlag) {
			Entry entry = list.get(counter);
			System.out.println(entry);
			writer.writeNext(entry.getStrings());
			counter++;
			if (counter > 10 || counter >= list.size() ) {
				stopFlag = false;
			}
		}

		writer.close();
		reader.close();



	}

}
