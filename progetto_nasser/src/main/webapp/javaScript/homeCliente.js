//------------------ RICHIESTA AJAX PER CONNETTERSI CON DISTRIBUTORE --------------------------------
function cerca(){
	var codDistributore = Number($("#codDistributore").val());
	if(Number.isInteger(codDistributore)){
		$.post("./connessioneCliente", {codDistributore : codDistributore},
			function(data, status){
				if(data == "codiceInesistente"){
					$("#messaggeConnettiti").html("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\"><strong>Il Codice inserito non esiste</strong><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
				}
				else if(data == "impegnato"){
					$("#messaggeConnettiti").html("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\"><strong>Sei impegnato con un altro distributore</strong><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
				}
				else if(data == "disImpegnato"){
					$("#messaggeConnettiti").html("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\"><strong>Il distributore è impegnato</strong><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
				}
				else if(status == "success"){
					$("#contenitore1").html(data);
				}
			}
		);
	}
	else{
		$("#messaggeConnettiti").html("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\"><strong>Valore inserito non valido</strong><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
	}
}
		
//--------------------- RICHIESTA AJAX PER DISCONNETTERSI CON DISTRIBUTORE ----------------------------
function disconnettiti(){
	$.get("./disconnessioneServlet", 
		function(data, status){
			if(status == "success"){
				$("#contenitore1").html(data);
			}
		}
	);
}


//--------------------------------------- CONTROLLO Navbar -----------------------------------------------
$("#connessione").click(function() {
	document.getElementById("contenitore1").style.display="block";
	document.getElementById("contenitore2").style.display="none";
});

$("#credito").click(function() {
	document.getElementById("contenitore1").style.display="none";
	document.getElementById("contenitore2").style.display="block";
});
	
//----------------------------------------------- Ricarica -------------------------------------------
function ricarica(valore){
	var ricarica = valore;
	
	//verifico che il valore inserito
	if(ricarica > 0 && (valore == 5 | valore == 10 | valore == 15)){
		$.post("./ricarica", {ricarica: ricarica},
				function(data, status){
					if(status == "success" && !(data == "fail") ){
						$("#credito").html("Credito &euro; "+data);
						$("#messaggeRicarica").html("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\"><strong>Ricarica effettuata</strong><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
					}
					else{
						alert('Errore nella ricarica');
					}
				}
		);
	}
	else{
		$("#messaggeRicarica").html("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\"><strong>Valore inserito non valido</strong><button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
	}
}

//---------------- richiesta per aggironare credito in Navbar ------------------------------

function aggiornaCredito(){
	$.get("./aggiornaCredito",
				function(data, status){
					if(status == "success"){
						$("#credito").html("Credito &euro; "+data);
					}
				}
		);
}