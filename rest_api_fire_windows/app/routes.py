# -*- coding: utf-8 -*-
"""
Created on Thu Apr 16 10:08:49 2020

@author: cypri
"""

from flask import Flask, request, jsonify
from app import app
from app import db
from app.models import Incendies

@app.route('/')
@app.route('/index')
def index():
    return "Hello, World!"

@app.route('/rest_api/v1.0/incendies', methods=['GET','POST'])
def get_incendies():
    if request.method == 'GET':
        incendies = Incendies.query.all()
        resultat = []
        for i in incendies :
            dictionnaire_intermediaire = dict()
            dictionnaire_intermediaire["id"] = i.id
            dictionnaire_intermediaire["position_x"] = i.position_x
            dictionnaire_intermediaire["position_y"] = i.position_y
            dictionnaire_intermediaire["intensite"] = i.intensite
            dictionnaire_intermediaire["categorie"] = i.categorie
            nb = i.id
            resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)


@app.route('/rest_api/v1.0/incendies/<int:incendie_id>', methods=['GET', 'DELETE','PUT'])
def get_incendies_par_id(incendie_id):
    if request.method == 'GET':
        incendies = Incendies.query.all()
        resultat = dict()
        for i in incendies :
            if i.id == incendie_id :
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["position_x"] = i.position_x
                dictionnaire_intermediaire["position_y"] = i.position_y
                dictionnaire_intermediaire["intensite"] = i.intensite
                dictionnaire_intermediaire["categorie"] = i.categorie
                nb = i.id
                resultat[nb] = dictionnaire_intermediaire 
        return jsonify(resultat)

@app.route('/rest_api/v1.0/incendies/ajout', methods=['GET','POST'])
def ajout_incendie():
    if request.method == 'POST':
        incendie = Incendies(position_x=request.json['position_x'], position_y=request.json['position_y'], intensite = request.json['intensite'], categorie= request.json['categorie'])
        db.session.add(incendie)
        db.session.commit()
    return('Votre incendie est ajoute !')
    
    
@app.route('/rest_api/v1.0/incendies/suppression/<int:incendie_id>', methods=['GET', 'DELETE','PUT'])
def suppression_incendie(incendie_id):
    if request.method == 'DELETE':
        incendies = Incendies.query.all()
        for i in incendies:
            if i.id == incendie_id:
                db.session.delete(i)
                db.session.commit()
    return('l incendie est supprime !')

@app.route('/rest_api/v1.0/incendies/mise_a_jour/<int:incendie_id>', methods=['GET', 'DELETE','PUT'])
def maj_incendie(incendie_id):
    if request.method == 'PUT':
        incendies = Incendies.query.all()
        for i in incendies :
            if i.id == incendie_id:
                i.intensite = request.json.get('intensite', i.intensite)
                i.categorie = request.json.get('categorie', i.categorie)
                db.session.commit()
    return('Incendie modifie !')

@app.route('/rest_api/v1.0/incendies/tout_supprimer', methods=['GET', 'DELETE','PUT'])
def suppression_totale():
    if request.method == 'DELETE':
        incendies = Incendies.query.all()
        for i in incendies:
            db.session.delete(i)
            db.session.commit()
    return('Tout est supprimé !')