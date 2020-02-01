from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask import current_app as app

from datetime import datetime
from bson import json_util

from api.database import Database
from api.account import login_required

database = Database()
posts = database.get_posts()

class Upload(Resource):
    def __init__(self):
        super().__init__()

    @login_required
    def post(self):
        