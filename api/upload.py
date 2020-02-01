from flask_restful import Resource, Api
from flask import Flask, session, request, url_for, render_template, redirect, abort, escape, flash
from flask import current_app as app

import os
import string

from datetime import datetime
from random import choice

from b2sdk.v1 import InMemoryAccountInfo, B2Api

from api.database import Database
from api.account import login_required

info = InMemoryAccountInfo()

application_key_id = "00051777be7b9360000000001"
application_key = os.environ["B2_KEY"]

b2_api = B2Api(info)
b2_api.authorize_account("production", application_key_id, application_key)

bucket = b2_api.get_bucket_by_name("metamapp",)

database = Database()
posts = database.get_posts()

class Upload(Resource):
    def __init__(self):
        super().__init__()

    @login_required
    def post(self):
        data = request.files.get("data")
        filename = "".join([choice(string.ascii_letters + string.digits) for char in range(8)])
        _, ext = os.path.splitext(data.filename)
 
        data.save("temp/" + filename + ext)

        bucket.upload_local_file("temp/" + filename + ext, filename + ext)

        return {"url": bucket.get_download_url(filename + ext)}, 201