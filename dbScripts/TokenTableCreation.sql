CREATE TABLE Tokens
(
    id INT PRIMARY KEY,
    token VARCHAR, 
    created_time BIGINT, 
    user_id INT REFERENCES Users(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)