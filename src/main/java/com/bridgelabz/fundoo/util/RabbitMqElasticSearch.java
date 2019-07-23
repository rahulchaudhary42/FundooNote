package com.bridgelabz.fundoo.util;

 

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 

import com.bridgelabz.fundoo.elasticsearch.IElasticsearch;
import com.bridgelabz.fundoo.elasticsearch.NoteContainer;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.INotesRepository;

@Component
public class RabbitMqElasticSearch {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${elastic.rabbitmq.exchange}")
	private String exchange1;

	@Value("${elastic.rabbitmq.routingkey}")
	private String routingkey1;

	@Autowired
	INotesRepository noteRepository;

	@Autowired
	NoteContainer noteContainer;

	@Autowired
	private IElasticsearch elasticsearch;

	public void rabitSenderElastic(NoteContainer noteContainer) {
		System.out.println(noteContainer.getNotes());
		rabbitTemplate.convertAndSend(exchange1, routingkey1, noteContainer);
		System.out.println("send the messgae ");

	}

	@RabbitListener(queues = "fundooelastic.queue1")
	public void operation(NoteContainer noteContainer) throws Exception {
		Note notes = noteContainer.getNotes();
		switch (noteContainer.getNoteoperation()) {
		case CREATE:
			System.out.println("Notes are created");
			elasticsearch.createNote(notes);
			break;
		case UPDATE:
			System.out.println("Notes are updated");
			elasticsearch.updateNote(notes);
			break;
		case DELETE:
			System.out.println("Notes are deleted");
			elasticsearch.deleteNote(notes.getId());
			break;

		}
	}

}
