from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask_cors import CORS
from flask_api import status
from flask_restful import Resource, Api
from flask_session import Session

from datetime import datetime
from random import choice
from functools import wraps

import json
import requests
import string
import os

from api.account import Login, Signup
from api.database import Database

app = Flask(__name__)
app.app_context().push()
CORS(app)
api = Api(app) 

app.secret_key = os.environ["SECRET_KEY"].encode("utf-8")
#app.wsgi_app = ProxyFix(app.wsgi_app, x_proto=1, x_host=1)

database = Database()
client = database.get_client()

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

@app.route("/", methods=["GET"])
def index():
    session["authenticated"] = True
    print(session["authenticated"])
    return '{"hello": "there"}', status.HTTP_201_CREATED, {'Content-Type':'application/json'}

api.add_resource(Login, '/login')
api.add_resource(Signup, '/signup')

if __name__ == '__main__': 
    app.run(debug = True) 