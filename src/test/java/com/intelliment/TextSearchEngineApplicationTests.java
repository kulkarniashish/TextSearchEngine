package com.intelliment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.intelliment.model.SearchResult;
import com.intelliment.model.SearchText;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TextSearchEngineApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class TextSearchEngineApplicationTests {

	private RestTemplate restTemplate = new TestRestTemplate("test", "test");
	
	@Before 
	public void setUp(){
		List<HttpMessageConverter<?>> messageConvertors = new ArrayList<>();
		List<MediaType> types = Arrays.asList(
				new MediaType("text", "html"),
				new MediaType("application", "json"),
				new MediaType("application", "*+json")
				);		
		
		MappingJackson2HttpMessageConverter httpMessageConvertor = new MappingJackson2HttpMessageConverter();
		httpMessageConvertor.setSupportedMediaTypes(types);
		messageConvertors.add(httpMessageConvertor);
		
	        
		restTemplate.setMessageConverters(messageConvertors);
	}
	
	@Test
	public void searchTest(){
	    
		ResponseEntity<SearchResult> entity = 
                restTemplate.postForEntity("http://localhost:9000/counter-api/search", new SearchText(), SearchResult.class);

        Assert.assertTrue(entity.getStatusCode().is2xxSuccessful());        
        System.out.println(entity.getBody());
		
	}
}
