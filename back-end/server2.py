import sqlite3
from flask import Flask, request, Response, jsonify
from flask_cors import CORS
import json
from datetime import datetime, timedelta   
import time

conn = sqlite3.connect('database.db')
c = conn.cursor()


def drop_accounts_table():
  c.execute("DROP TABLE accounts")

def drop_trips_table():
  c.execute("DROP TABLE	trips")

def create_accounts_table():
  c.execute("CREATE TABLE IF NOT EXISTS accounts(username VARCHAR(255), password VARCHAR(255))")

def create_trips_table():
  c.execute("CREATE TABLE IF NOT EXISTS trips(tripcode VARCHAR(10), username VARCHAR(255), lat VARCHAR(50), long VARCHAR(50), destLat VARCHAR(50), destLong VARCHAR(50), eta VARCHAR(50), msg VARCHAR(255) )")

def join_trip(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("INSERT INTO trips VALUES('" + trip['tripcode'] + "','" + trip['username'] + "','" + trip['lat'] + "', '" + trip['long'] + "','" + trip['destLat'] +"','" + trip['destLong'] + "', 'ETA', '' )")
  conn.commit() 

def get_trip_info(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  result = c.execute("SELECT username, lat, long, destLat, destLong, eta, msg FROM trips WHERE tripcode = '" + trip['tripcode'] + "' LIMIT 6")
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
  c.execute("DELETE FROM trips WHERE username = '" + user['username'] + "'")
  result = c.execute("SELECT COUNT(username) FROM accounts WHERE username = '" + user['username'] + "' AND password = '" + user['password'] + "' LIMIT 1")
  conn.commit()
  return c.fetchall()   

def end_trip(user):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("DELETE FROM trips WHERE username = '" + user['username'] + "'")
  conn.commit()

def update_location(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("UPDATE trips SET lat = '" + trip['lat'] + "', long = '" + trip['long'] + "', eta = '" + trip['eta'] +  "' WHERE tripcode = '" + trip['tripcode'] + "'AND username = '" + trip['username'] + "'")
  conn.commit()

def update_msg(trip):
  conn = sqlite3.connect('database.db')
  c = conn.cursor()
  c.execute("UPDATE trips SET msg = '" + trip['msg'] +  "' WHERE tripcode = '" + trip['tripcode'] + "'AND username = '" + trip['username'] + "'")
  conn.commit()

def test(numTrips):
  start = time.time()
  for count in range(0,numTrips):
    join_trip({'tripcode':str(count), 'username':"", 'lat':"", 'long':"", 'destLat':"", 'destLong':""})
  end = time.time()
  drop_trips_table()
  return (end - start)

def testRunner():
  f = open("data.txt","w+")
  for count in range(0, 500):
    f.write(str(count) + ", " + str(test(count) + ""))
  f.close()



create_accounts_table()
create_trips_table()
drop_trips_table()
drop_accounts_table()
create_accounts_table()
create_trips_table()
testRunner()
