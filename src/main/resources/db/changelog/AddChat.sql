CREATE TABLE chat_message
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sender_id   BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content     TEXT,
    sent_at     TIMESTAMP
);