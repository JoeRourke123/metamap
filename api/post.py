from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask import current_app as app

from datetime import datetime
from random import choice

import string

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
            post_type = data.get("type")
            post =  {
                    "post_id": "".join([choice(string.ascii_letters + string.digits) for char in range(8)]),
                    "username": session["user"].get("username"),
                    "location": {
                                    "type": "Point",
                                    "coordinates": data.get("coordinates")
                                },
                    "timestamp": datetime.now().isoformat(),
                    "likes": []
                    }
            if post_type == "text":
                post["type"] = "text"
                post["data"] = data.get("data")
            elif post_type == "link":
                post["type"] = "link"
                post["link"] = data.get("link")
            elif post_type == "image":
                post["type"] = "image"
                post["link"] = data.get("link")
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

            username = session["user"].get("username")
            nearPostList = []

            for post in nearPosts:
                if username in post.get("likes"):
                    post["liked"] = True
                else:
                    post["liked"] = False

                nearPostList.append(post)
                
            return {"posts": nearPostList}, 200

        else:
            return {"error": "Invalid operation"}, 405


