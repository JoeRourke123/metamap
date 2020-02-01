from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from werkzeug.security import generate_password_hash, check_password_hash

from datetime import datetime

from api.database import Database

database = Database()
users = database.get_users()

class Login(Resource):
    def __init__(self):
        super().__init__()

    def post(self): 
        data = request.json

        user = users.find_one({"username": data.get("username")})

        if not user:
            return {"error": "Username not found"}, 404

        if not check_password_hash(user["password"], escape(data.get("password"))):
            return {"error": "Password incorrect"}, 401

        session["authenticated"] = True
        session["user"] = user

        return {"message": "Login successful", "username": data.get("username"}, 200

class Signup(Resource):
    def __init__(self):
        super().__init__()


    def post(self):
        data = request.json

        if users.find_one({"username": data.get("username")}):
            return {"error": "Username already taken"}, 409

        users.insert_one({
                            "username": data["username"],
                            "password": generate_password_hash(escape(data.get("password"))),
                            "creation": datetime.now(),
                            "ip": request.remote_addr
                          })

        return {"message": "User created successfully", "username": data.get("username")}, 201