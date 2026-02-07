ALTER TABLE cards_words ADD COLUMN IF NOT EXISTS learned_at TIMESTAMP;
CREATE INDEX idx_cards_words_learned_at ON cards_words(user_id, learned_at);
