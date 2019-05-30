from api import V0Api

if __name__ == '__main__':

    accountManager = AccountManager(db='database.db')
    api = V0Api(accountManager=accountManager)
    api.run(host='0.0.0.0', debug=True)

