package com.benbillion;
import com.benbillion.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TodoApplication {
	@Autowired
	EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail(){
		senderService.sendEmail("benbillion62@gmail.com",
				"Interview From Digerati", "You are highly welcome");
	}

}

