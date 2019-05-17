package com.bridgelabz.fundoo.notes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.response.Response;

@Service
public interface ILabelService {
	
	// Method to create label
	public Response createLabel(LabelDto labelDto , String token);
	
	// Method to delete label
	public Response deleteLabel(String labelId, String token);

	// Method to update label
	public Response updateLabel(String labelId , String token ,LabelDto labelDto);
	
	public List<LabelDto> getAllLabel(String token);
	
	public Response addLabelToNote(String labelId, String token , String noteId);
 
	
}
