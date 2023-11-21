db.createUser(
    {
        user: "mancala",
        pwd: "mancala_password",
        roles: [
            {
                role: "readWrite",
                db: "mancala"
            }
        ]
    }
);