create table IF NOT EXISTS "MESSAGES" (
  MESSAGE_ID INTEGER IDENTITY,
  MESSAGE_FROM_NAME VARCHAR NOT NULL,
  MESSAGE VARCHAR NOT NULL
);
