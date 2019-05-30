import sqlite3



def drop_table():
  c.execute("DROP TABLE accounts")

def create_table():
  c.execute("CREATE TABLE IF NOT EXISTS accounts(email VARCHAR(255), password VARCHAR(255), nickname VARCHAR(255))")


conn = sqlite3.connect('database.db')
c = conn.cursor()
create_table() 
conn.commit()
conn.close()