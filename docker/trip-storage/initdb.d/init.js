db.createUser({
    user: 'trip',
    pwd: 'password',
    roles: [
        { role: 'dbOwner', db: 'trip' }
    ]
});