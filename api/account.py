from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash

from werkzeug.security import generate_password_hash, check_password_hash

class Login(Resource):
    def __init__(self):
        super().__init__()

    def post(self): 
        data = request.json

        return {'data': data}

class Signup(Resource):
    def __init__(self):
        super().__init__()


    def post(self):
        data = request.json

        if users.find_one({"username": data["username"]}):
            return {"error": "Username already taken"}

        users.insert_one({
                            "username": data["username"],
                            "password": generate_password_hash(escape(data["password"])),
                            "creation": datetime.now(),
                            "ip": request.remote_addr
                          })

        return {"message": "User created successfully", "username": data["username"]}