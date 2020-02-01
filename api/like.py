from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask import current_app as app

from api.database import Database

database = Database()
posts = database.get_posts()

def find_post_by_id(data):
    return posts.find_one({"post_id": data.get("post_id")})

class Like(Resource):
    def __init__(self):
        super().__init__()
    
    def post(self):
        post = find_post_by_id(request.json)

class Unlike(Resource):
    def __init__(self):
        super().__init__()

    def post(self):
        post = find_post_by_id(request.json)
