

class AccountManager:
    
    def __init__(self, database):
        self.database = database

    def create_account(self, user):
    conn = sqlite3.connect(database)
    c = conn.cursor()
    c.execute("INSERT INTO accounts VALUES(" + user['email'] + "," + user['password'] + "," + user['nickname'] + ")")
    conn.commit()

    def delete_user(self, id):
    conn = sqlite3.connect(database)
    #TODO
    conn.commit()
