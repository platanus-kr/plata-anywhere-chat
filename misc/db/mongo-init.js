db = db.getSiblingDB('pac');

db.createUser(
    {
        user: "localtest",
        pwd: "localtest",
        roles: [
            {
                role: "readWrite",
                db: "pac"
            }
        ]
    }
);