package com.benbillion;
import com.benbillion.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}
//	@EventListener(ApplicationReadyEvent.class)
//	public String sendMail(){
//		senderService.sendMail("benbillion62@gmail.com",
//				"Interview From Digerati");
//		return "Mail sent successfully";
//	}

}

