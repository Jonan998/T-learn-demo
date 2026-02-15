ALTER TABLE dictionary
ALTER COLUMN owner_id
TYPE integer
USING owner_id::integer;