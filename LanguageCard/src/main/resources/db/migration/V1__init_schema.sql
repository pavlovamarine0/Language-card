CREATE TABLE CARDS (
                       id SERIAL,
                       word VARCHAR(50) NOT NULL,
                       translation VARCHAR(50),
                       plural VARCHAR(50),
                       created_at TIMESTAMP,
                       CONSTRAINT pk_card_id PRIMARY KEY (id)
);

CREATE TABLE THEMES (
                        id SERIAL,
                        name VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP,
                        CONSTRAINT pk_theme_id PRIMARY KEY (id)
);

CREATE TABLE THEMES_CARDS (
                              card_id INTEGER,
                              theme_id INTEGER,
                              CONSTRAINT pk_theme_card_id PRIMARY KEY (card_id, theme_id),
                              CONSTRAINT fk_cards_id__card_id FOREIGN KEY (card_id) REFERENCES CARDS (id) ON DELETE CASCADE,
                              CONSTRAINT fk_themes_id__theme_id FOREIGN KEY (theme_id) REFERENCES THEMES (id) ON DELETE CASCADE
);

CREATE INDEX idx_cards_word ON CARDS (word);