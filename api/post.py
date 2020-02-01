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
            post_type = data.get("type")
            post =  {
                    "username": session["user"].get("username"),
                    "location": {
                                    "type": "Point",
                                    "coordinates": data.get("coordinates")
                                },
                    "timestamp": datetime.now().isoformat()
                    }
            if post_type == "text":
                post["type"] = "text"
                post["data"] = data.get("data")
            elif post_type == "link":
                post["type"] = "link"
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

            # nearPostList = []

            # for post in nearPosts:
            #     post["timestamp"] = post["timestamp"]
            #     nearPostList.append(post)
                
            return {"posts": [post for post in nearPosts]}, 200

        else:
            return {"error": "Invalid operation"}, 405


