from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask import current_app as app

from api.database import Database
from api.account import login_required

database = Database()
posts = database.get_posts()

def find_post_by_id(data):
    return posts.find_one({"post_id": data.get("post_id")}, {'_id': False})

def replace_post_by_id(data, replacement):
    posts.replace_one({"post_id": data.get("post_id")}, replacement)

class Like(Resource):
    def __init__(self):
        super().__init__()
    
    @login_required
    def post(self):
        data = request.json
        post = find_post_by_id(data)

        username = session["user"].get("username")
        if username in post.get("likes"):
            return {"error": "You've already liked this post"}, 405

        post.get("likes").append(username)

        replace_post_by_id(data, post)

        return {"message": "Post liked successfully"}, 200

class Unlike(Resource):
    def __init__(self):
        super().__init__()

    @login_required
    def post(self):
        data = request.json
        post = find_post_by_id(data)

        username = session["user"].get("username")
        if username not in post.get("likes"):
            return {"error": "You've already unliked this post"}, 405

        post.get("likes").remove(username)

        replace_post_by_id(data, post)

        return {"message": "Post unliked successfully"}, 200
