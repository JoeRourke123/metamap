from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask import current_app as app

from datetime import datetime
from bson import json_util

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
                        "username": session["user"].get("username"),
                        "data": data.get("data"),
                        "location": {
                                    "type": "Point",
                                    "coordinates": data.get("coordinates")
                                    },
                        "timestamp": datetime.now()
                        }
            else:
                return {"error": "Invalid type"}, 418
            
            posts.insert_one(post)
            return {"message": "Post created successfully"}, 200
        
        elif data.get("operation") == "get":
            coordinates = data.get("coordinates")

            nearPosts = posts.find({
                                    "location": {
                                        "$near": {
                                            "$geometry": {
                                                "type": "Point" ,
                                                "coordinates": [coordinates[0], coordinates[1]]
                                            },
                                            "$maxDistance": 100,
                                            "$minDistance": 0
                                            }
                                        }
                                    }, 
                                    {
                                    '_id': False
                                    })

            nearPostList = []

            for post in nearPosts:
                post["timestamp"] = post["timestamp"].isoformat()
                nearPostList.append(post)
                
            return {"posts": nearPostList}, 200

        else:
            return {"error": "Invalid operation"}, 405


