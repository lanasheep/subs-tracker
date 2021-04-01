CREATE TABLE "user" (
    id SERIAL NOT NULL PRIMARY KEY,
    login VARCHAR(50) NOT NULL
);

CREATE TABLE subscription (
    id SERIAL NOT NULL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "user"(id),
    title VARCHAR(50) NOT NULL,
    cost MONEY NOT NULL
);