package com.test.feedburner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FeedBurnerService {

	public List<FeedBurnerDTO> getAll(String url) {
		log.info("Entry - FeedBurnerService getAll : {}", url);
		List<FeedBurnerDTO> feedBurners = new ArrayList<FeedBurnerDTO>();

		try (XmlReader reader = new XmlReader(new URL(url))) {
			SyndFeed feed = null;
			try {
				feed = new SyndFeedInput().build(reader);
			} catch (IllegalArgumentException | FeedException e) {
				e.printStackTrace();
			}
			log.info("Feed Title : {}", feed.getTitle());
			for (SyndEntry entry : feed.getEntries()) {
				FeedBurnerDTO feedBurnerDTO = new FeedBurnerDTO();

				String[] split = entry.getUri().split("=");
				String elementClass = "wp-image-" + Integer.toString((Integer.parseInt(split[1]) + 1));
				String value = entry.getContents().get(0).getValue();

				Document doc = Jsoup.parse(value);
				Elements image = doc.getElementsByClass(elementClass);
				feedBurnerDTO.setImg(image.get(0).attr("src"));

				feedBurnerDTO.setTitle(entry.getTitle());

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
				LocalDate localDate = LocalDate.parse(entry.getPublishedDate().toString(), formatter);
				feedBurnerDTO.setPublished(localDate);

				feedBurnerDTO.setUri(entry.getUri());
				feedBurnerDTO.setLink(entry.getLink());
				feedBurners.add(feedBurnerDTO);

			}
			log.info("EXIT - FeedBurnerService getAll : {}", url);
			return feedBurners;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

}
