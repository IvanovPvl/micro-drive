db.createUser({
    user: 'microDrive',
    pwd: 'password',
    roles: [
        { role: 'dbOwner', db: 'microDrive' }
    ]
});
