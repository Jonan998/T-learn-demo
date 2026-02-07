ALTER TABLE users
ALTER COLUMN created_ad_new TYPE TIMESTAMP USING created_ad_new::timestamp;

ALTER TABLE users
ALTER COLUMN created_ad_repeat TYPE TIMESTAMP USING created_ad_repeat::timestamp;