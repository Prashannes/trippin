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
  c.execute("CREATE TABLE IF NOT EXISTS accounts(username VARCHAR(255), password VARCHAR(255))")

def create_trips_table():
  c.execute("CREATE TABLE IF NOT EXISTS trips(tripcode VARCHAR(10), username VARCHAR(255), lat VARCHAR(50), long VARCHAR(50), destLat VARCHAR(50), destLong VARCHAR(50) )")

def join_trip(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("INSERT INTO trips VALUES('" + trip['tripcode'] + "','" + trip['username'] + "','" + trip['lat'] + "', '" + trip['long'] + "','" + trip['destLat'] +"','" + trip['destLong'] + "')")
  conn.commit() 

def get_trip_info(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  result = c.execute("SELECT username, lat, long, destLat, destLong FROM trips WHERE tripcode = '" + trip['tripcode'] + "' LIMIT 6")
  conn.commit()
  return c.fetchall()

def add_user(user):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("INSERT INTO accounts VALUES('" + user['username'] + "','" + user['password']+ "')")
  conn.commit()

def get_user(user):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  result = c.execute("SELECT COUNT(username) FROM accounts WHERE username = '" + user['username'] + "' AND password = '" + user['password'] + "' LIMIT 1")
  conn.commit()
  return c.fetchall()   

def end_trip(user):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("DELETE FROM trips WHERE username = '" + user['username'] + "'")
  conn.commit()


create_accounts_table()
create_trips_table()
#drop_accounts_table()
#drop_trips_table()
#

app = Flask(__name__)
CORS(app)

@app.route('/accounts', methods=['GET', 'POST', 'PUT'])
def index():
  if request.method == 'POST':
    add_user({'username':request.values['username'], 'password':request.values['password']})
    return Response("", status=201, mimetype='application/json')
  elif request.method == 'GET':
    resp = json.dumps(get_user({'username':request.values['username'], 'password':request.values['password']}))
    return Response(resp, status=200, mimetype='application/json')

@app.route('/trips', methods=['GET', 'POST', 'PUT'])
def index2():
  if request.method == 'POST':
    join_trip({'tripcode':request.values['tripcode'], 'username':request.values['username'], 'lat':request.values['lat'], 'long':request.values['long'], 'destLat':request.values['destLat'], 'destLong':request.values['destLong']})
    return Response("", status=201, mimetype='application/json')
  elif request.method == 'GET':
    pos = json.dumps(get_trip_info({'tripcode':request.values['tripcode']}))
    return Response(pos, status=200, mimetype='application/json')
  elif request.method == 'PUT':
    end_trip({'username':request.values['username']})
    return Response("", status=200, mimetype='application/json')



if __name__ == '__main__':
  app.run(host='0.0.0.0', debug=True)
  # app.run(debug=True)



