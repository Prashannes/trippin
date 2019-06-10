import sqlite3
from flask import Flask, request, Response, jsonify
from flask_cors import CORS
import json
from datetime import datetime, timedelta   

conn = sqlite3.connect('database.db')
c = conn.cursor()


def drop_accounts_table():
  c.execute("DROP TABLE accounts")

def drop_trips_table():
  c.execute("DROP TABLE	trips")

def create_accounts_table():
  c.execute("CREATE TABLE IF NOT EXISTS accounts(email VARCHAR(255), password VARCHAR(255), nickname VARCHAR(255))")

def create_trips_table():
  c.execute("CREATE TABLE IF NOT EXISTS trips(tripcode VARCHAR(10), email VARCHAR(50), long VARCHAR(50), lat VARCHAR(50), destLong VARCHAR(50), destLat VARCHAR(50), nickname VARCHAR(30) )")

def join_trip(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("INSERT INTO trips VALUES('" + trip['tripcode'] + "','" + trip['email'] + "','" + trip['long'] + "', '" + trip['lat'] + "','" + trip['destLong'] +"','" + trip['destLat'] +"','" + trip['nickname'] + "')")
  conn.commit() 

def get_trip_info(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  result = c.execute("SELECT email,long,lat,destLong,destLat,nickname FROM trips WHERE tripcode = '" + trip['tripcode'] + "' LIMIT 6")
  conn.commit()
  return c.fetchall()

def add_accounts_test_data():
  conn = sqlite3.connect('database.db')
  c = conn.cursor() 
  c.execute("INSERT INTO accounts VALUES('user@example.com', 'password', 'usrrrr')")
  conn.commit()

def add_user(user):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("INSERT INTO accounts VALUES('" + user['email'] + "','" + user['password'] + "','" + user['nickname'] + "')")
  conn.commit()

def get_user(user):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  result = c.execute("SELECT email,nickname FROM accounts WHERE email = '" + user['email'] + "' AND password = '" + user['password'] + "' LIMIT 1")
  conn.commit()
  return c.fetchall()

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
   

#drop_accounts_table()
create_accounts_table()
create_trips_table()
#drop_trips_table()
#add_accounts_test_data()

app = Flask(__name__)
CORS(app)

@app.route('/accounts', methods=['GET', 'POST', 'PUT'])
def index():
  if request.method == 'POST':
    add_user({'email':request.values['email'], 'password':request.values['password'], 'nickname':request.values['nickname']})
    return Response("", status=201, mimetype='application/json')
  elif request.method == 'GET':
    usr = json.dumps(get_user({'email':request.values['email'], 'password':request.values['password']}))
    return Response(usr, status=200, mimetype='application/json')

@app.route('/trips', methods=['GET', 'POST', 'PUT'])
def index2():
  if request.method == 'POST':
    join_trip({'tripcode':request.values['tripcode'], 'email':request.values['email'], 'long':request.values['long'], 'lat':request.values['lat'], 'destLong':request.values['destLong'], 'destLat':request.values['destLat'], 'nickname':request.values['nickname']})
    return Response("", status=201, mimetype='application/json')
  elif request.method == 'GET':
    pos = json.dumps(get_trip_info({'tripcode':request.values['tripcode']}))
    return Response(pos, status=200, mimetype='application/json')


if __name__ == '__main__':
  app.run(host='0.0.0.0', debug=True)
  # app.run(debug=True)



