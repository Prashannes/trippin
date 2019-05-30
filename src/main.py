from api import V0Api
from store import AccountManager

if __name__ == '__main__':

    accountManager = AccountManager(database='database.db')
    api = V0Api(accountManager=accountManager)
    api.run(host='0.0.0.0', debug=True)

