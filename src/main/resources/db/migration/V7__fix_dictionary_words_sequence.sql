SELECT setval(
    'dictionary_words_id_seq',
    (SELECT COALESCE(MAX(id), 1) FROM dictionary_words),
    true
);
