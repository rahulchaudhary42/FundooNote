package com.bridgelabz.fundoo.notes.service;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.response.Response;

@Service
public interface ILabelService {
	
	public Response createLabel(LabelDto labelDto , String token);

}
