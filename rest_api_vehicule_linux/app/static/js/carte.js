list_marker = [];

var maCarte = L.map('maCarte').setView([45.75, 4.85], 14);

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
attribution: 'Map data <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
maxZoom: 18,
id: 'mapbox.streets',
accessToken: 'pk.eyJ1IjoiYWxpY2Vhc2kiLCJhIjoiY2s5MDkwZ3k1MDMwMDNscnI4dG50YmQwNCJ9.VES0-DJfrLrJUdn7owVLLQ'
}).addTo(maCarte);

function fct_intensite_color(intensite){
    var fire_color;
    if (intensite==1){
	    fire_color='yellow';
    }
    else if (intensite==2){
	    fire_color='orange';
    }
    else if (intensite==3){
	    fire_color='red';
    }
    else if (intensite==4){
	    fire_color='purple';
    }
    else {
        fire_color='black';
    }
    return fire_color;
}


function fct_tracer_circle(coord_x, coord_y, intensite, categorie){
    var fire_color=fct_intensite_color(intensite);
    var circle = L.circle([coord_x, coord_y], {
        color: fire_color,
        fillOpacity: 1,
        radius: 15
    })
    maCarte.addLayer(circle);
    list_marker.push(circle);

    var label = new L.marker([coord_x, coord_y], { opacity: 0.5});
    label.bindTooltip(categorie, {permanent: false, offset: [0, 0] });
    label.addTo(maCarte);
    list_marker.push(label);

}

function fct_tracer_sonde(coord_x, coord_y, type){
    var circle = L.circle([coord_x, coord_y], {
        color: 'green',
        fillOpacity: 1,
        radius: 20
    })
    maCarte.addLayer(circle);
    list_marker.push(circle);

    var label = new L.marker([coord_x, coord_y], { opacity: 0.5});
    label.bindTooltip(type, {permanent: false, offset: [0, 0] });
    label.addTo(maCarte);
    list_marker.push(label);

}


function fct_tracer_vehicule(coord_x, coord_y, type_vehicule, type_produit, produit, carburant){

    var imageUrl = 'https://img-31.ccm2.net/pBcO5KwJ-Fwbb5D8sZdFxpoW0Qk=/1240x/smart/d17c2a4ced4043798034c51e7b5d41fa/ccmcms-hugo/10781896.jpg',
    imageBounds = [[coord_x - 0.0001, coord_y - 0.0001], [[coord_x + 0.0008, coord_y + 0.0008]]];

    vehicule = L.imageOverlay(imageUrl, imageBounds);
    maCarte.addLayer(vehicule);
    list_marker.push(vehicule);
}

function fct_tracer_caserne(coord_x, coord_y){
    var bounds = [[coord_x - 0.0001, coord_y - 0.0001], [coord_x + 0.0008, coord_y + 0.0008]];
    var rectangle = new L.Rectangle(bounds, { 
        color: 'grey'});
    maCarte.addLayer(rectangle);
    list_marker.push(rectangle);
}


function fct_destroy_markers(){
    var nb_marker = Object.keys(list_marker).length;
    for (var i = 0 ; i<nb_marker ; i++){
        marker = list_marker[i];
        maCarte.removeLayer(marker);
    }
}

function fct_affichage_feux(){
    $.ajax({
        url : 'http://localhost:5001/rest_api/v1.0/sonde/get_incendies',
        type : 'GET',
        dataType : 'json',
        success : function(incendies_json, statut){  
            var nb_incendies = Object.keys(incendies_json).length; 
            console.log(nb_incendies + " incendies");
            
            for(var num_incendie = 0; num_incendie<nb_incendies; num_incendie++){
                feu=incendies_json[num_incendie];
                var coord_x=feu['position_x'];
                var coord_y=feu['position_y'];
                var intensite=feu['intensite'];
                var categorie=feu['categorie'];
                fct_tracer_circle(coord_x, coord_y, intensite, categorie); 
            }
        },

        error : function(resultat, statut, erreur){
            console.log(erreur);   
        },

    });
    
}

function fct_affichage_vehicules(){
    $.ajax({
        url : 'http://localhost:5001/rest_api/v1.0/vehicule',
        type : 'GET',
        dataType : 'json',
        success : function(vehicules_json, statut){  
            var nb_vehicules = Object.keys(vehicules_json).length; 
            console.log(nb_vehicules + " vehicules");
            for(var num_vehicule = 0; num_vehicule<nb_vehicules; num_vehicule++){
                vehicule=vehicules_json[num_vehicule];
                var coord_x=vehicule['position_x'];
                var coord_y=vehicule['position_y'];
                var type_vehicule=vehicule['type_vehicule'];
                var type_produit=vehicule['type_produit'];
                var produit=vehicule['produit'];
                var carburant=vehicule['carburant'];
                fct_tracer_vehicule(coord_x, coord_y, type_vehicule, type_produit, produit, carburant); 
            }   
        },

        error : function(resultat, statut, erreur){
            console.log(erreur);   
        },

    });
}

function fct_affichage_sondes(){
    $.ajax({
        url : 'http://localhost:5001/rest_api/v1.0/sonde',
        type : 'GET',
        dataType : 'json',
        success : function(sondes_json, statut){  
            var nb_sondes = Object.keys(sondes_json).length; 
            console.log(nb_sondes + " sondes");
            
            for(var num_sonde = 0; num_sonde<nb_sondes; num_sonde++){
                sonde=sondes_json[num_sonde];
                var coord_x=sonde['position_x'];
                var coord_y=sonde['position_y'];
                var type=sonde['type'];
                fct_tracer_sonde(coord_x, coord_y,type); 
            }
        },

        error : function(resultat, statut, erreur){
            console.log(erreur);   
        },

    });
    
}

function fct_affichage_casernes(){
    $.ajax({
        url : 'http://localhost:5001/rest_api/v1.0/caserne',
        type : 'GET',
        dataType : 'json',
        success : function(casernes_json, statut){  
            var nb_casernes = Object.keys(casernes_json).length; 
            console.log(nb_casernes + " casernes");
            
            for(var num_caserne = 0; num_caserne<nb_casernes; num_caserne++){
                caserne=casernes_json[num_caserne];
                var coord_x=caserne['position_x'];
                var coord_y=caserne['position_y'];
                fct_tracer_caserne(coord_x, coord_y); 
            }
        },

        error : function(resultat, statut, erreur){
            console.log(erreur);   
        },

    });
    
}

function fct_affichage_carte(){
    fct_destroy_markers();
    fct_affichage_feux();
    fct_affichage_vehicules();
    fct_affichage_sondes();
    fct_affichage_casernes();

    setTimeout(fct_affichage_carte,8000)
}

fct_affichage_carte()







