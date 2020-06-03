# -*- coding: utf-8 -*-
"""
Created on Thu Apr 16 10:03:58 2020

@author: cypri
"""


from flask import Flask
from config import Config
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
from flask_cors import CORS


app = Flask(__name__)
app.config['SECRET_KEY'] = 'the quick brown fox jumps over the lazy   dog'
app.config['CORS_HEADERS'] = 'Content-Type'
CORS(app, resources={r"/rest_api/v1.0/*": {"origins": "http://localhost:5001"}})


app.config.from_object(Config)
db = SQLAlchemy(app)
migrate = Migrate(app, db)

from app import routes, models
