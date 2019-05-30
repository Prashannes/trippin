class AccountManager:
    def __init__(self, db):
        self.db = db

    def add_user(self, user):
    db = sqlite3.connect('database.db')
    c = db.cursor()
    c.execute("INSERT INTO accounts VALUES(" + user['email'] + "," + user['password'] + "," + user['nickname'] + ")")
    db.commit()



    def delete_entry(id):
    conn = sqlite3.connect('database.db')
    c = conn.cursor()
    #TODO
    conn.commit()
    
