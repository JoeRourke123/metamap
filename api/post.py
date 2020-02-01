from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask import current_app as app

from datetime import datetime

from api.database import Database
from api.account import login_required

database = Database()
posts = database.get_posts()

class Post(Resource):
    def __init__(self):
        super().__init__()

    @login_required
    def post(self):
        data = request.json

        if data.get("operation") == "add":
            if data.get("type") == "text":
                post =  {
                        "type": "text",
                        "data": data.get("data"),
                        "coordinates": data.get("coordinates"),
                        "timestamp": datetime.now()
                        }
            else:
                return {"error": "Invalid type"}, 418
            
            posts.insert_one(post)
            return {"message": "Post created successfully"}, 200
        
        else:
            return {"error": "Invalid operation"}, 405


