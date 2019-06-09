import sqlite3
from flask import Flask, request, Response
from flask_cors import CORS
import json
from datetime import datetime, timedelta   

conn = sqlite3.connect('database.db')
c = conn.cursor()


def drop_table():
  c.execute("DROP TABLE accounts")

def create_table():
  c.execute("CREATE TABLE IF NOT EXISTS accounts(email VARCHAR(255), password VARCHAR(255), nickname VARCHAR(255))")


def add_test_data():
  conn = sqlite3.connect('database.db')
  c = conn.cursor() 
  c.execute("INSERT INTO accounts VALUES('user@example.com', 'password', 'usrrrr')")
  conn.commit()


def add_user(user):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("INSERT INTO accounts VALUES('" + user['email'] + "','" + user['password'] + "','" + user['nickname'] + "')")
  conn.commit()

def delete_entry(id):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  #TODO
  conn.commit()
   
def undelete_entry(id):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()  
  #TODO
  conn.commit()
   

drop_table()
create_table()
add_test_data()

app = Flask(__name__)
CORS(app)

@app.route('/account', methods=['GET', 'POST', 'PUT'])
def index():
  if request.method == 'POST':
    add_user({'email':request.values['email'], 'password':request.values['password'], 'nickname':request.values['nickname']})
    return Response("", status=201, mimetype='application/json')

if __name__ == '__main__':
  app.run(host='0.0.0.0', debug=True)
  # app.run(debug=True)



