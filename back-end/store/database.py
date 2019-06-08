import sqlite3
from flask import Flask, request, Response
from flask_cors import CORS
import json
from datetime import datetime, timedelta   


def drop_table():
  c.execute("DROP TABLE accounts")

def create_table():
  c.execute("CREATE TABLE IF NOT EXISTS accounts(email VARCHAR(255), password VARCHAR(255), nickname VARCHAR(255))")


# def add_test_data():
#   conn = sqlite3.connect('database.db')
#   c = conn.cursor() 
#   c.execute("INSERT INTO accounts VALUES(" + "user@example.com" + "," + "password" + "," + "usrrrr" + ")")
#   conn.commit()

   

# drop_table()
# create_table()
# add_test_data()
