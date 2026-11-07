CREATE TABLE SENTENCES (
                        id SERIAL,
                        sentence VARCHAR(150) NOT NULL,
                        translate VARCHAR(150) NOT NULL,
                        created_at TIMESTAMP,
                        CONSTRAINT pk_sentence_id PRIMARY KEY (id)
);

CREATE TABLE CARDS_SENTENCES (
                              card_id INTEGER,
                              sentence_id INTEGER,
                              CONSTRAINT pk_card_sentence_id PRIMARY KEY (card_id, sentence_id),
                              CONSTRAINT fk_cards_id__card_id FOREIGN KEY (card_id) REFERENCES CARDS (id) ON DELETE CASCADE,
                              CONSTRAINT fk_sentences_id__sentence_id FOREIGN KEY (sentence_id) REFERENCES SENTENCES (id) ON DELETE CASCADE
);