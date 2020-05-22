list_circle = [];

var maCarte = L.map('maCarte').setView([45.75, 4.85], 14);

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
attribution: 'Map data <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
maxZoom: 18,
id: 'mapbox.streets',
accessToken: 'pk.eyJ1IjoiYWxpY2Vhc2kiLCJhIjoiY2s5MDkwZ3k1MDMwMDNscnI4dG50YmQwNCJ9.VES0-DJfrLrJUdn7owVLLQ'
}).addTo(maCarte);

function fct_intensite_color(intensite){
    var fire_color;
    if (intensite==1 || intensite==12){
	    fire_color=#8A2BE2;
    }
    else if (intensite==2){
	    fire_color='pink';
    }
    else if (intensite==3){
	    fire_color='orange';
    }
    else if (intensite==4){
	    fire_color='red';
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
        //fillColor: '#f03',
        fillOpacity: 1,
        radius: 15
    })
    maCarte.addLayer(circle);
    list_circle.push(circle);
}

function fct_destroy_markers(){
    var nb_circle = Object.keys(list_circle).length;
    for (var i = 0 ; i<nb_circle ; i++){
        circle = list_circle[i];
        maCarte.removeLayer(circle);
    }
}

function fct_affichage_feux(){
    $.ajax({
        url : 'http://localhost:5000/rest_api/v1.0/incendies',
        type : 'GET',
        dataType : 'json',
        success : function(incendies_json, statut){  
            var nb_incendies = Object.keys(incendies_json).length; 
            console.log(nb_incendies + " incendies");
            fct_destroy_markers();
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

        complete : function(resultat, statut){
        }
    });
    setTimeout(fct_affichage_feux,2000)
}

fct_affichage_feux();
