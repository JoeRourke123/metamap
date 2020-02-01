from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash

from api.database import Database
from api.account import login_required

database = Database()

class Post(Resource):
    def __init__(self):
        super().__init__()

    @login_required
    def post(self):
        pass
        return {"thank mr skeltal": "yes"}, 200
