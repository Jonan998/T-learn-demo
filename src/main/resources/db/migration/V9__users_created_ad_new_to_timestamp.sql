ALTER TABLE users
  ALTER COLUMN created_ad_new TYPE timestamp(6)
  USING created_ad_new::timestamp;
