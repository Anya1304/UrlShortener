CREATE TABLE short_link
(
    link        VARCHAR(255) NOT NULL PRIMARY KEY,
    count       INT          NOT NULL,
    original_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (original_id) REFERENCES original_link (link)
);