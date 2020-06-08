# -*- coding: utf-8 -*-
"""
Created on Thu Apr 16 10:08:49 2020

@author: cypri
"""

from flask import Flask, request, jsonify
from app import app
from app import db
from app.models import Vehicules, Personnel, Caserne, Sonde
import requests, json
from math import sqrt

@app.route('/')
@app.route('/index')
def index():
    return "Hello, World!"

@app.route('/rest_api/v1.0/vehicule', methods=['GET','POST']) # Permet d'obtenir la liste de tout les véhicules
def get_vehicules():
    if request.method == 'GET':
        vehicules = Vehicules.query.all()
        resultat = []
        for i in vehicules :
            dictionnaire_intermediaire = dict()
            dictionnaire_intermediaire["position_x"] = i.position_x
            dictionnaire_intermediaire["position_y"] = i.position_y
            dictionnaire_intermediaire["type_vehicule"] = i.type_vehicule
            dictionnaire_intermediaire["type_produit"] = i.type_produit
            dictionnaire_intermediaire["produit"] = i.produit
            dictionnaire_intermediaire["carburant"] = i.carburant
            dictionnaire_intermediaire["id"] = i.id
            dictionnaire_intermediaire["caserne"] = i.caserne
            dictionnaire_intermediaire["disponibilite"] = i.disponibilite
            resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)


@app.route('/rest_api/v1.0/vehicule/<int:vehicule_id>', methods=['GET', 'DELETE','PUT']) # Permet d'obtenir les informations sur 1 véhicule précis
def get_vehicules_par_id(vehicule_id):
    if request.method == 'GET':
        vehicules = Vehicules.query.all()
        resultat = []
        for i in vehicules :
            if i.id == vehicule_id:
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["position_x"] = i.position_x
                dictionnaire_intermediaire["position_y"] = i.position_y
                dictionnaire_intermediaire["type_vehicule"] = i.type_vehicule
                dictionnaire_intermediaire["type_produit"] = i.type_produit
                dictionnaire_intermediaire["produit"] = i.produit
                dictionnaire_intermediaire["carburant"] = i.carburant
                dictionnaire_intermediaire["id"] = i.id 
                dictionnaire_intermediaire["caserne"] = i.caserne
                dictionnaire_intermediaire["disponibilite"] = i.disponibilite
                resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)

@app.route('/rest_api/v1.0/vehicule/ajout', methods=['GET','POST']) # Permet de stocker un nouveau véhicule
def ajout_vehicule():
    if request.method == 'POST':
        vehicule = Vehicules(disponibilite=0, position_x=request.json['position_x'], position_y=request.json['position_y'], type_vehicule = request.json['type_vehicule'], type_produit = request.json['type_produit'], produit= request.json['produit'], carburant= request.json['carburant'], caserne= request.json['caserne'])
        db.session.add(vehicule)
        db.session.commit()
    return('Votre véhicule est ajoute !')
    
    
@app.route('/rest_api/v1.0/vehicule/suppression/<int:vehicule_id>', methods=['GET', 'DELETE','PUT']) # Supprime un véhicule
def suppression_vehicule(vehicule_id):
    if request.method == 'DELETE':
        vehicule = Vehicules.query.get(vehicule_id)
        db.session.delete(vehicule)
        db.session.commit()
    return('Le véhicule est supprime !')


@app.route('/rest_api/v1.0/vehicule/mise_a_jour/<int:vehicule_id>', methods=['GET', 'DELETE','PUT']) 
def maj_vehicule(vehicule_id):
    if request.method == 'PUT':
        vehicule = Vehicules.query.get(vehicule_id)
        vehicule.disponibilite = request.json.get('disponibilite', vehicule.disponibilite)
        db.session.commit()
    return('attributs du véhicule modifiés !')

@app.route('/rest_api/v1.0/vehicule/mise_a_jour_position/<int:vehicule_id>', methods=['GET', 'DELETE','PUT']) # Met à jour la position du véhicule, son essence et sa disponibilite
def maj_vehicule_position(vehicule_id):
    if request.method == 'PUT':
        vehicules = Vehicules.query.all()
        for i in vehicules :
            if i.id == vehicule_id:
                i.position_x = request.json.get('position_x', i.position_x)
                i.position_y = request.json.get('position_y', i.position_y)
                i.carburant = request.json.get('carburant', i.carburant)
                i.disponibilite = request.json.get('disponibilite', i.disponibilite)
                db.session.commit()
    return('attributs du véhicule modifiés !')


@app.route('/rest_api/v1.0/vehicule/remplir_produit/<int:vehicule_id>', methods=['POST', 'DELETE','PUT']) # modifie la quantité de produit du véhicule
def maj_produit(vehicule_id):
    if request.method == 'PUT':
        vehicules = Vehicules.query.all()
        for i in vehicules :
            if i.id == vehicule_id:
                if i.type_produit == request.json.get('type_produit'): # On modifie la quantité de produit dans le reservoir (On ne peut pas changer le type de produit)
                    i.produit = request.json.get('produit', i.produit)
                    db.session.commit()
                elif i.produit == 0: # reservoir vide, on peut ajouter tout type de produit
                    i.produit = request.json.get('produit', i.produit)
                    i.type_produit = request.json.get('type_produit', i.type_produit)
                    db.session.commit()
                elif request.json.get('produit') == 0: # On souhaite vider le reservoir, donc peu importe le type de produit
                    i.produit = request.json.get('produit', i.produit)
                    db.session.commit()
    return('Mise à jour du produit effectuée !')




@app.route('/rest_api/v1.0/personnel/affectation/<int:personnel_id>', methods=['POST', 'DELETE','PUT']) # affecte un véhicule à un personnel
def affectation(personnel_id):
    if request.method =='PUT':
        personnels = Personnel.query.all()
        vehicules = Vehicules.query.all()
        variable_au_pif = 0
        for k in vehicules:
            if k.id == request.json.get('vehicule'):
                variable_au_pif = k.caserne
        for i in personnels:
            if i.id == personnel_id:
                if i.caserne == variable_au_pif:
                    i.vehicule = request.json.get('vehicule', i.vehicule)
                    db.session.commit()
    return('Affectation réussie !')

@app.route('/rest_api/v1.0/vehicule/personnel_embarque/<int:vehicule_id>', methods=['GET', 'DELETE','PUT']) # renvoie la liste du personnel dans un véhicule
def get_personnel_vehicule(vehicule_id):
    if request.method == 'GET':
        personnels = Personnel.query.filter_by(vehicule = vehicule_id)
        resultat = []
        for i in personnels :
            dictionnaire_intermediaire = dict()
            dictionnaire_intermediaire["categorie_personnel"] = i.categorie_personnel
            dictionnaire_intermediaire["vehicule"] = i.vehicule
            dictionnaire_intermediaire["experience"] = i.experience
            dictionnaire_intermediaire["id"] = i.id
            dictionnaire_intermediaire["caserne"] = i.caserne
            resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)

@app.route('/rest_api/v1.0/personnel/infos/<int:personnel_id>', methods=['GET', 'DELETE','PUT']) # renvoie les informations d'un personnel
def get_personnel_by_id(personnel_id):
    if request.method == 'GET':
        personnels = Personnel.query.all()
        resultat = []
        for i in personnels :
            if i.id == personnel_id :
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["categorie_personnel"] = i.categorie_personnel
                dictionnaire_intermediaire["vehicule"] = i.vehicule
                dictionnaire_intermediaire["experience"] = i.experience
                dictionnaire_intermediaire["id"] = i.id
                dictionnaire_intermediaire["caserne"] = i.caserne
                resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)

@app.route('/rest_api/v1.0/personnel', methods=['GET', 'DELETE','PUT']) # renvoie la liste de tout les personnels
def get_personnels():
    if request.method == 'GET':
        personnels = Personnel.query.all()
        resultat = []
        for i in personnels :
            dictionnaire_intermediaire = dict()
            dictionnaire_intermediaire["categorie-personnel"] = i.categorie_personnel
            dictionnaire_intermediaire["vehicule"] = i.vehicule
            dictionnaire_intermediaire["experience"] = i.experience
            dictionnaire_intermediaire["id"] = i.id
            dictionnaire_intermediaire["caserne"] = i.caserne
            resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)

@app.route('/rest_api/v1.0/personnel/modifier_xp/<int:personnel_id>', methods=['POST', 'DELETE','PUT']) # modifie l'experience d'un personnel
def modifier_xp(personnel_id):
    if request.method == 'PUT':
        personnels = Personnel.query.all()
        for i in personnels :
            if i.id == personnel_id:
                i.experience = request.json.get('experience', i.experience)
                db.session.commit()
    return('Modification faite !')

@app.route('/rest_api/v1.0/personnel/ajout', methods=['POST', 'DELETE','PUT']) # cree un personnel
def creer_personnel():
    if request.method == 'POST':
        personnel = Personnel(categorie_personnel=request.json['categorie_personnel'], vehicule=request.json['vehicule'], experience = request.json['experience'], caserne = request.json['caserne'])
        db.session.add(personnel)
        db.session.commit()
    return('Votre personnel est ajoute !')

@app.route('/rest_api/v1.0/personnel/suppression/<int:personnel_id>', methods=['POST', 'DELETE','PUT']) # supprime un personnel
def suppression_personnel(personnel_id):
    if request.method == 'DELETE':
        personnels = Personnel.query.all()
        for i in personnels:
            if i.id == personnel_id:
                db.session.delete(i)
                db.session.commit()
    return('Le personnel est supprime !')

@app.route('/rest_api/v1.0/vehicule/changer_caserne/<int:vehicule_id>', methods=['POST', 'DELETE','PUT']) # change la caserne d'un véhicule
def modifier_caserne_vehicule(vehicule_id):
    if request.method == 'PUT':
        vehicules = Vehicules.query.all()
        for i in vehicules :
            if i.id == vehicule_id:
                i.caserne = request.json.get('caserne', i.caserne)
                db.session.commit()
    return('Modification faite !')

@app.route('/rest_api/v1.0/personnel/changer_caserne/<int:personnel_id>', methods=['POST', 'DELETE','PUT']) # change la caserne d'un personnel
def modifier_caserne_personnel(personnel_id):
    if request.method == 'PUT':
        personnels = Personnel.query.all()
        for i in personnels :
            if i.id == personnel_id:
                i.caserne = request.json.get('caserne', i.caserne)
                db.session.commit()
    return('Modification faite !')

@app.route('/rest_api/v1.0/caserne/ajout', methods=['POST', 'DELETE','PUT']) # crée une caserne
def creer_caserne():
    if request.method == 'POST':
        caserne = Caserne(position_x=request.json['position_x'], position_y=request.json['position_y'])
        db.session.add(caserne)
        db.session.commit()
    return('Votre caserne est ajoutée !')

@app.route('/rest_api/v1.0/caserne/suppression/<int:caserne_id>', methods=['POST', 'DELETE','PUT']) # supprime une caserne
def suppression_caserne(caserne_id):
    if request.method == 'DELETE':
        casernes = Caserne.query.all()
        for i in casernes:
            if i.id == caserne_id:
                db.session.delete(i)
                db.session.commit()
    return('La caserne est supprimée !')

@app.route('/rest_api/v1.0/sonde/ajout', methods=['POST', 'DELETE','PUT']) # cree une sonde
def creer_sonde():
    if request.method == 'POST':
        sonde = Sonde(type=request.json['type'], rate=request.json['rate'], position_x=request.json['position_x'], position_y=request.json['position_y'], etat=request.json['etat'], alarme=0)
        db.session.add(sonde)
        db.session.commit()
    return('Votre sonde est ajoutée !')

@app.route('/rest_api/v1.0/sonde/suppression/<int:sonde_id>', methods=['POST', 'DELETE','PUT']) # supprime une sonde
def suppression_sonde(sonde_id):
    if request.method == 'DELETE':
        sondes = Sonde.query.all()
        for i in sondes:
            if i.id == sonde_id:
                db.session.delete(i)
                db.session.commit()
    return('La sonde est supprimée !')

@app.route('/rest_api/v1.0/caserne/infos/<int:caserne_id>', methods=['GET', 'DELETE','PUT']) # affiche 1 caserne
def get_caserne_by_id(caserne_id):
    if request.method == 'GET':
        casernes = Caserne.query.all()
        resultat = []
        for i in casernes :
            if i.id == caserne_id :
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["position_x"] = i.position_x
                dictionnaire_intermediaire["position_y"] = i.position_y
                dictionnaire_intermediaire["id"] = i.id
                resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)

@app.route('/rest_api/v1.0/caserne', methods=['GET', 'DELETE','PUT']) # affiche toutes les casernes
def get_casernes():
    if request.method == 'GET':
        casernes = Caserne.query.all()
        resultat = []
        for i in casernes :
            dictionnaire_intermediaire = dict()
            dictionnaire_intermediaire["position_x"] = i.position_x
            dictionnaire_intermediaire["position_y"] = i.position_y
            dictionnaire_intermediaire["id"] = i.id
            resultat.append(dictionnaire_intermediaire)
    return jsonify(resultat)

@app.route('/rest_api/v1.0/sonde/infos/<int:sonde_id>', methods=['GET', 'DELETE','PUT']) # affiche 1 sonde
def get_sonde_by_id(sonde_id):
    if request.method == 'GET':
        sondes = Sonde.query.all()
        resultat = []
        for i in sondes :
            if i.id == sonde_id :
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["rate"] = i.rate
                dictionnaire_intermediaire["position_x"] = i.position_x
                dictionnaire_intermediaire["position_y"] = i.position_y
                dictionnaire_intermediaire["etat"] = i.etat
                dictionnaire_intermediaire["id"] = i.id
                dictionnaire_intermediaire["alarme"] = i.alarme
                dictionnaire_intermediaire["type"] = i.type
                resultat.append(dictionnaire_intermediaire)
        return jsonify(resultat)

@app.route('/rest_api/v1.0/sonde', methods=['GET', 'DELETE','PUT']) # affiche toutes les sondes
def get_sondes():
    if request.method == 'GET':
        sondes = Sonde.query.all()
        resultat = []
        for i in sondes :
            dictionnaire_intermediaire = dict()
            dictionnaire_intermediaire["rate"] = i.rate
            dictionnaire_intermediaire["position_x"] = i.position_x
            dictionnaire_intermediaire["position_y"] = i.position_y
            dictionnaire_intermediaire["etat"] = i.etat
            dictionnaire_intermediaire["id"] = i.id
            dictionnaire_intermediaire["alarme"] = i.alarme
            dictionnaire_intermediaire["type"] = i.type
            resultat.append(dictionnaire_intermediaire)
    return jsonify(resultat)

@app.route('/rest_api/v1.0/caserne/afficher_vehicules/<int:caserne_id>', methods=['GET', 'DELETE','PUT']) # affiche tout les véhicules d'une caserne
def get_vehicules_par_caserne(caserne_id):
    if request.method == 'GET':
        vehicules = Vehicules.query.all()
        resultat = []
        for i in vehicules:
            if i.caserne == caserne_id :
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["position_x"] = i.position_x
                dictionnaire_intermediaire["position_y"] = i.position_y
                dictionnaire_intermediaire["type_vehicule"] = i.type_vehicule
                dictionnaire_intermediaire["type_produit"] = i.type_produit
                dictionnaire_intermediaire["produit"] = i.produit
                dictionnaire_intermediaire["carburant"] = i.carburant
                dictionnaire_intermediaire["id"] = i.id
                dictionnaire_intermediaire["caserne"] = i.caserne
                dictionnaire_intermediaire["disponibilite"] = i.disponibilite
                resultat.append(dictionnaire_intermediaire)
    return jsonify(resultat)

@app.route('/rest_api/v1.0/caserne/afficher_personnel/<int:caserne_id>', methods=['GET', 'DELETE','PUT']) # affiche tout les personnels d'une caserne
def get_personnel_par_caserne(caserne_id):
    if request.method == 'GET':
        personnels = Personnel.query.all()
        resultat = []
        for i in personnels:
            if i.caserne == caserne_id:
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["categorie_personnel"] = i.categorie_personnel
                dictionnaire_intermediaire["vehicule"] = i.vehicule
                dictionnaire_intermediaire["experience"] = i.experience
                dictionnaire_intermediaire["id"] = i.id
                dictionnaire_intermediaire["caserne"] = i.caserne
                resultat.append(dictionnaire_intermediaire)
    return jsonify(resultat)

@app.route('/rest_api/v1.0/sonde/modifier_etat/<int:sonde_id>', methods=['POST', 'DELETE','PUT']) # Répare (ou abîme) une sonde
def modifier_etat(sonde_id):
    if request.method == 'PUT':
        sondes = Sonde.query.all()
        for i in sondes :
            if i.id == sonde_id:
                i.etat = request.json.get('etat', i.etat)
                db.session.commit()
    return('Modification faite !')


@app.route('/rest_api/v1.0/vehicule/disponible', methods=['GET', 'DELETE','PUT']) # donne tous les véhicules disponibles
def get_vehicules_disponibles():
    if request.method == 'GET':
        vehicules = Vehicules.query.all()
        resultat = []
        for v in vehicules :
            if (v.disponibilite == 0):
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["position_x"] = v.position_x
                dictionnaire_intermediaire["position_y"] = v.position_y
                dictionnaire_intermediaire["type_vehicule"] = v.type_vehicule
                dictionnaire_intermediaire["type_produit"] = v.type_produit
                dictionnaire_intermediaire["produit"] = v.produit
                dictionnaire_intermediaire["carburant"] = v.carburant
                dictionnaire_intermediaire["id"] = v.id
                dictionnaire_intermediaire["caserne"] = v.caserne
                dictionnaire_intermediaire["disponibilite"] = v.disponibilite
                resultat.append(dictionnaire_intermediaire)
    return jsonify(resultat)


@app.route('/rest_api/v1.0/vehicule/non_disponible', methods=['GET', 'DELETE','PUT']) # donne tous les vehicules non disponibles
def get_vehicules_non_disponibles():
    if request.method == 'GET':
        vehicules = Vehicules.query.all()
        resultat = []
        for v in vehicules :
            if v.disponibilite != 0:
                dictionnaire_intermediaire = dict()
                dictionnaire_intermediaire["position_x"] = v.position_x
                dictionnaire_intermediaire["position_y"] = v.position_y
                dictionnaire_intermediaire["type_vehicule"] = v.type_vehicule
                dictionnaire_intermediaire["type_produit"] = v.type_produit
                dictionnaire_intermediaire["produit"] = v.produit
                dictionnaire_intermediaire["carburant"] = v.carburant
                dictionnaire_intermediaire["id"] = v.id
                dictionnaire_intermediaire["caserne"] = v.caserne
                dictionnaire_intermediaire["disponibilite"] = v.disponibilite
                resultat.append(dictionnaire_intermediaire)
    return jsonify(resultat)


"""
@app.route('/rest_api/v1.0/sonde/get_incendies', methods=['GET', 'DELETE','PUT']) 
def recuperer_feux_par_sonde():
    r = 0
    if request.method == 'GET':
        r = requests.get('http://localhost:5000/rest_api/v1.0/incendies').json()
        sondes = Sonde.query.all()
        liste = []
        for s in sondes :
            x = s.position_x
            y = s.position_y
            rayon = 0
            if s.etat == 1 :
                rayon = 0.001
            if s.etat == 2 :
                rayon = 0.002
            if s.etat == 3 :
                rayon = 0.003
            if s.etat == 4 :
                rayon = 0.004
            if s.etat == 5 :
                rayon = 0.005
            for i in r :
                if(calcul_distance(i['position_x'],i['position_y'],x,y) <= rayon):
                    s.alarme = 1
                    if i in liste :
                        continue
                    else :
                        dictionnaire_intermediaire = dict()
                        dictionnaire_intermediaire["id"] = i["id"]
                        dictionnaire_intermediaire["position_x"] = i["position_x"]
                        dictionnaire_intermediaire["position_y"] = i["position_y"]
                        dictionnaire_intermediaire["intensite"] = i["intensite"]
                        dictionnaire_intermediaire["categorie"] = i["categorie"]
                        liste.append(dictionnaire_intermediaire)
                else :
                    s.alarme = 0
    db.session.commit()
            
    return(jsonify(liste))

def calcul_distance(x1,y1,x2,y2):
	distance=sqrt((x1-x2)**2+(y1-y2)**2)
	return distance

   """ 
@app.route('/rest_api/v1.0/sonde/modifier_alarme/<int:sonde_id>', methods=['GET', 'DELETE','PUT']) # modifie le bit d'alarme d'une sonde
def modifier_alarme(sonde_id):
    if request.method == 'GET':
        sondes = Sonde.query.all()
        for i in sondes :
            if i.id == sonde_id:
                if i.alarme == 0:
                    i.alarme = 1
                else :
                    i.alarme = 0
                db.session.commit()
    return('Modification faite !')
