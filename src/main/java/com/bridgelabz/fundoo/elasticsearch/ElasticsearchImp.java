package com.bridgelabz.fundoo.elasticsearch;
import java.io.IOException;
import java.util.Map;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.notes.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticsearchImp implements IElasticsearch {

	String INDEX = "es";
	String TYPE = "createnote";

	@Autowired
	private RestHighLevelClient client;
	@Autowired

	ObjectMapper objectMapper;

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

	       return objectMapper
	               .convertValue(resultMap, Note.class);
	}
	
	@Override
	public String upDateNote(Note note) throws Exception {
	Note notes=findById(note.getId());
	@SuppressWarnings("deprecation")
	UpdateRequest updateRequest=new UpdateRequest(INDEX, TYPE, note.getId());
	 
	@SuppressWarnings({ "unused", "unchecked" })
	Map<String ,Object> mapDoc = objectMapper.convertValue(note, Map.class);
	updateRequest.doc(mapDoc);
	UpdateResponse updateResponse = 
	               client.update(updateRequest, RequestOptions.DEFAULT);
	return updateResponse
	               .getResult()
	               .name();
	}

}
