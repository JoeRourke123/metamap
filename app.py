from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask_cors import CORS
from flask_api import status
from flask_restful import Resource, Api
from flask_session import Session

from datetime import datetime
from random import choice

import json
import requests
import string
import os

from api.account import Login, Signup
from api.database import Database
from api.post import Post
from api.upload import Upload
from api.like import Like, Unlike

app = Flask(__name__)
app.app_context().push()
CORS(app)
api = Api(app) 

#app.json_encoder = MongoEngineJSONEncoder
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

@app.route("/", methods=["GET"])
def index():
    return  '''{"name": "metamap",
                "timestamp": "2020-02-02T06:38:36.47069",
                "about": ["a location based social network", "you can post things like text, links and images"],
                "by": ["joseph rourke", "sean escreet", "george honeywood"]
                }''', status.HTTP_200_OK, {'Content-Type':'application/json'}

api.add_resource(Login, '/login')
api.add_resource(Signup, '/signup')

api.add_resource(Post, '/post')

api.add_resource(Upload, '/upload')

api.add_resource(Like, '/like')
api.add_resource(Unlike, '/unlike')

if __name__ == '__main__': 
    app.run(debug = True)