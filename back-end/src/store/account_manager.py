import sqlite3

class AccountManager:
    
    def __init__(self, database):
        self.database = database

    def create_account(self, user):
        conn = sqlite3.connect(self.database)
        c = conn.cursor()
        print('WE GOT HERE\n')
        print('user:', str(user))
        c.execute("INSERT INTO accounts VALUES('" + user.email + "', '" + user.password + "', '" + user.nickname + "')")
        conn.commit()
        return "hello"

    def delete_user(self, id):
        conn = sqlite3.connect(self.database)
        #TODO
        conn.commit()
