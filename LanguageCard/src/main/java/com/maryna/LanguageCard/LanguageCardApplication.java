package com.maryna.LanguageCard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LanguageCardApplication {


	public static void main(String[] args) {
		SpringApplication.run(LanguageCardApplication.class, args);
	}

}
/*
thema
-id
-name

TC
-thema_id
-card_id

card
-id
-word
-translate
-plural

CS
-card_id
-sentance_id

sentance
-id
-text
-translate

* */