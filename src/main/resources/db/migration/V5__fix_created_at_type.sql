ALTER TABLE users
ALTER COLUMN created_ad_new
TYPE DATE
USING created_ad_new::date;