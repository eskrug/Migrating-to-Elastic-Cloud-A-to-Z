package java_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class Main {

	public static void main(String[] args) {
		// 설치형 클러스터 - id/pw x
		try {

			RestHighLevelClient client = new RestHighLevelClient(RestClient
					.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")));

			SearchRequest searchRequest = new SearchRequest("sales*");
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			searchRequest.source(searchSourceBuilder);

			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

			System.out.println("hits.total : " + searchResponse.getHits().getTotalHits());
			System.out.println("docs : " + searchResponse.getHits().getAt(0).toString());
			System.out.println("end");

			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// 클라우드 클러스터 - id/pw O
		try {
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY,
					new UsernamePasswordCredentials("id", "password"));

			RestClientBuilder builder = RestClient.builder(
					new HttpHost("cloud ip", 9243, "https"));
			builder.setHttpClientConfigCallback(
					httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
			RestHighLevelClient client = new RestHighLevelClient(builder);

			SearchRequest searchRequest = new SearchRequest("sales*");
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.matchAllQuery());
			searchRequest.source(searchSourceBuilder);

			SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

			System.out.println("hits.total : " + searchResponse.getHits().getTotalHits());
			System.out.println("docs : " + searchResponse.getHits().getAt(0).toString());
			System.out.println("end");

			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
}
