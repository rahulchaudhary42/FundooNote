package com.bridgelabz.fundoo.notes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.response.Response;

@Service
public interface ILabelService {
	
	public Response createLabel(LabelDto labelDto , String token);
	
	public Response deleteLabel(String labelId, String token);

	public Response updateLabel(String labelId , String token ,LabelDto labelDto);
	
	public List<LabelDto> getAllLabel(String token);
}
