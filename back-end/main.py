from api import V0Api

if __name__ == '__main__':

    # db = sqlite3.connect('database.db')
    # databaseSaver = AccountManager(db=db)
    api = V0Api()
    api.run(host='146.169.47.204', debug=True)

