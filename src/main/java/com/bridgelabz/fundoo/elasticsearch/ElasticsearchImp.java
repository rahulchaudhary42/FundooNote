package com.bridgelabz.fundoo.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.util.UserToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticsearchImp implements IElasticsearch {

	String INDEX = "es";
	String TYPE = "createnote";

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private UserToken userToken;

	@Override
	public String createNote(Note note) throws IOException {
		@SuppressWarnings({ "unchecked" })
		Map<String, Object> documentMappper = objectMapper.convertValue(note, Map.class);
		@SuppressWarnings("deprecation")
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getId()).source(documentMappper);
		IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}

	@Override
	public String updateNote(Note note) throws Exception {
		@SuppressWarnings("unused")
		Note notes = findById(note.getId());
		@SuppressWarnings("deprecation")
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, note.getId());

		@SuppressWarnings({ "unused", "unchecked" })
		Map<String, Object> mapDoc = objectMapper.convertValue(note, Map.class);
		updateRequest.doc(mapDoc);
		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		return updateResponse.getResult().name();
	}

	@Override
	public String deleteNote(String id) throws IOException {
		@SuppressWarnings("deprecation")
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
		return response.getResult().name();
	}

	@Override
	public Note findById(String id) throws Exception {
		@SuppressWarnings("deprecation")
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();

		return objectMapper.convertValue(resultMap, Note.class);
	}

	private List<Note> getSearchResult(SearchResponse response) {

		SearchHit[] searchHit = response.getHits().getHits();

		List<Note> profileDocuments = new ArrayList<>();

		if (searchHit.length > 0) {

			Arrays.stream(searchHit)
					.forEach(hit -> profileDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}

		return profileDocuments;
	}

	@Override
	public List<Note> searchByTitle(String title, String userId) throws IOException {
		userId = userToken.tokenVerify(userId);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.queryStringQuery("*"+title+"*")
				.analyzeWildcard(true).field("title", 2.0f).field("description").field("label")).filter(QueryBuilders.termsQuery("userId", userId));
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryBuilder);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.source(searchSourceBuilder);
		SearchResponse response = null;

		response = client.search(searchRequest, RequestOptions.DEFAULT);

		return getSearchResult(response);
	}
 

}
