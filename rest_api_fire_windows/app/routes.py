# -*- coding: utf-8 -*-
"""
Created on Thu Apr 16 10:08:49 2020

@author: cypri
"""

from flask import Flask, request, jsonify
from app import app
from app import db
from app.models import Incendies
from math import sqrt
import requests
from flask_cors import CORS,cross_origin


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
            dictionnaire_intermediaire["detecte"] = i.detecte
            dictionnaire_intermediaire["prise_en_charge"] = i.prise_en_charge
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
                dictionnaire_intermediaire["detecte"] = i.detecte
                dictionnaire_intermediaire["prise_en_charge"] = i.prise_en_charge  
                nb = i.id
                resultat[nb] = dictionnaire_intermediaire 
        return jsonify(resultat)

@app.route('/rest_api/v1.0/incendies/ajout', methods=['GET','POST'])
def ajout_incendie():
    if request.method == 'POST':
        incendie = Incendies(detecte=0, prise_en_charge=0, position_x=request.json['position_x'], position_y=request.json['position_y'], intensite = request.json['intensite'], categorie= request.json['categorie'])
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

@app.route('/rest_api/v1.0/incendies/get_detectes', methods=['GET', 'DELETE','PUT'])
@cross_origin(origin='localhost',headers=['Content-Type','Authorization'])
def get_detectes():
    if request.method == 'GET':
        incendies = Incendies.query.all()
        resultat = []
        for i in incendies :
            if (i.detecte != 0):
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["id"] = i.id
                dictionnaire_intermediaire["position_x"] = i.position_x
                dictionnaire_intermediaire["position_y"] = i.position_y
                dictionnaire_intermediaire["intensite"] = i.intensite
                dictionnaire_intermediaire["categorie"] = i.categorie
                dictionnaire_intermediaire["detecte"] = i.detecte
                dictionnaire_intermediaire["prise_en_charge"] = i.prise_en_charge
                nb = i.id
                resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)

@app.route('/rest_api/v1.0/incendies/detection/<int:sonde_id>', methods=['GET', 'DELETE','PUT'])
def detecter_par_sonde(sonde_id):
    if request.method == 'GET':
        url1 = 'http://localhost:5001/rest_api/v1.0/sonde/infos/' + str(sonde_id)
        r = 0
        r = requests.get(url1).json()
        for s in r :
            x = s["position_x"]
            y = s["position_y"]
            rayon = 0
            if s["etat"] == 1 :
                rayon = 0.001
            if s["etat"] == 2 :
                rayon = 0.002
            if s["etat"] == 3 :
                rayon = 0.003
            if s["etat"] == 4 :
                rayon = 0.004
            if s["etat"] == 5 :
                rayon = 0.005
            alarme = s["alarme"]
        
        incendies = Incendies.query.all()
        if alarme == 1:
            url2 = 'http://localhost:5001/rest_api/v1.0/sonde/modifier_alarme/' + str(sonde_id)
            rb = 0
            rb = requests.get(url2).text()
        for i in incendies :
            if(calcul_distance(i.position_x, i.position_y, x, y) <= rayon):
                i.detecte = sonde_id
                db.session.commit()
                if alarme == 0:
                    url2 = 'http://localhost:5001/rest_api/v1.0/sonde/modifier_alarme/' + str(sonde_id)
                    r = 0
                    r = requests.get(url2).text()

        return('Detection_effectuée !')


def calcul_distance(x1,y1,x2,y2):
	distance=sqrt((x1-x2)**2+(y1-y2)**2)
	return distance

@app.route('/rest_api/v1.0/incendies/prendre_en_charge/<int:incendie_id>', methods=['GET', 'DELETE','PUT'])
def prendre_en_charge(incendie_id):
    if request.method == 'PUT':
        incendies = Incendies.query.all()
        for i in incendies :
            if (i.id == incendie_id):
                i.prise_en_charge = request.json.get('prise_en_charge', i.prise_en_charge)
                db.session.commit()
    return("Prise en charge effectuée")