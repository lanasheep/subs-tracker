CREATE TABLE "user" (
    id INT NOT NULL PRIMARY KEY,
    login VARCHAR(50) NOT NULL
);

CREATE TABLE subscription (
    id SERIAL NOT NULL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "user"(id),
    title VARCHAR(50) NOT NULL,
    cost INT NOT NULL
);

ALTER TABLE subscription ADD CONSTRAINT unique_user_id_title UNIQUE (user_id, title);