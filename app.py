from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask_cors import CORS
from flask_api import status
from flask_session import Session
from flask_restful import Resource, Api 

from datetime import datetime
from random import choice
from functools import wraps

import json
import requests
import string
import os

from api.account import Login, Signup

app = Flask(__name__)
CORS(app)
api = Api(app) 

app.secret_key = os.environ["SECRET_KEY"].encode("utf-8")
#app.wsgi_app = ProxyFix(app.wsgi_app, x_proto=1, x_host=1)

client = MongoClient(os.environ["MONGODB_URI"], retryWrites=False)
db = client["heroku_9pm8qrb1"]
pastes = db.pastes
users = db.users

SESSION_COOKIE_NAME="flask_sess"
SESSION_TYPE = "mongodb"
SESSION_MONGODB = client
SESSION_MONGODB_DB = "heroku_9pm8qrb1"

app.config.from_object(__name__)
Session(app)

def login_required(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if session.get("authenticated"):
            return f(*args, **kwargs)
        else:
            return {error:401}

    return wrap

# @app.route("/", methods=["GET"])
# def index():
#     return '{"hello": "there"}', status.HTTP_201_CREATED, {'Content-Type':'application/json'}

# class Hello(Resource): 
  
#     # corresponds to the GET request. 
#     # this function is called whenever there 
#     # is a GET request for this resource 
#     def get(self): 
  
#         return jsonify({'message': 'hello world'}) 
  
#     # Corresponds to POST request 
#     def post(self): 
          
#         data = request.get_json()     # status code 
#         return jsonify({'data': data}), 201

api.add_resource(Login, '/login')
api.add_resource(Signup, '/signup')

if __name__ == '__main__': 
    app.run(debug = True) 