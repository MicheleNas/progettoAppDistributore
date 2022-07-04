//------------------------------------------ CountDown ------------------------------------------------------------
var x;
function countdown(timer) {
	x = setInterval(function(){

	      // Time calculations for minutes and seconds
	      var minuti = Math.floor((timer % (1000 * 60 * 60)) / (1000 * 60));
	      var secondi = Math.floor((timer % (1000 * 60)) / 1000);
		  
		  $("#timer").html("Timer: "+minuti+" minut" + (minuti==1 ? "o " : "i " )+secondi+ " second"+ (secondi==1 ? "o " : "i " ));
		  timer = timer - 1000;
		  
		  if(timer <= 0){
			  $("#timer").html("");
			  clearInterval(x);
		  }
	  }, 1000);
};
		
// -------------------------------- VERIFCA STATO CONNESSIONE UTENTE --------------------------------------------
var flagCountdown = 0;
setInterval(function() {
	//richiesta ajax per verificare lo stato della connessione
	$.get("./ConnessioneDistributoreServlet", 
			function(data, status){
				if(status == "success"){
					$("#riga1").html(data);
				}
			}
	);
	
	//richiesta ajax per il timer;
	$.get("./timer",
			function(data, status){
				if(status == "success" && data != "disconnesso" && flagCountdown == 0){
					console.log("timer: "+data);
					flagCountdown = 1;
					countdown(data);
				}
				else if(data == "disconnesso"){
					console.log("timer in disconnesso");
					clearInterval(x);
					flagCountdown = 0;
					 $("#timer").html("");
				}
			}
	);
	
}, 2000); 
	
//------------------------------------------------- ACQUSITA ---------------------------------------------------- 
function acquista(id){
	var elementId = document.getElementById(id);
	var prezzo = elementId.value;
	var codProdotto = id; //l'id del prodotto corrisponde con il codProdotto memorizzato nel DB (in questo modo è univoco)
	
	$.post("./acquista", {prezzo: prezzo, codProdotto: codProdotto},
			function(data, status){
				
				if(data == "ok" && status == "success"){
					//attivo il modal
					$("body").attr("class","bg-primary bg-opacity-25 modal-open");
					$("body").attr("style", "overflow: hidden; padding-right: 0px;");
					
					$("#modal").attr("class","modal fade show");
					$("#modal").attr("style", "display : block;");
					$("#modal").removeAttr("aria-hidden");
					$("#modal").attr("aria-modal","true");
					$("#modal").attr("role","dialog");
					
					$("#modalBackdrop").attr("class","modal-backdrop fade show");
					
					//modifico modal dopo 5 secondi
					setTimeout(terminato, 5000);
					
					function terminato(){
						$("#modal_body").html("<span class=\"d-block text-success\">Terminato, grazie!</span>");
						
						//chiudo modal dopo 3 secondi
						
						setTimeout(chiudi , 3000);
						
						function chiudi (){
							$("body").attr("class","bg-primary bg-opacity-25");
							$("body").attr("style", "");
							
							$("#modal").attr("class","modal fade");
							$("#modal").attr("style", "display : none;");
							$("#modal").removeAttr("aria-modal");
							$("#modal").removeAttr("role");
							$("#modal").attr("aria-hidden","true");
							
							$("#modalBackdrop").removeAttr("class","modal-backdrop fade show");
							
							$("#modal_body").html("<button class=\"btn btn-primary\" type=\"button\" disabled><span class=\"spinner-border spinner-border-sm\" role=\"status\" aria-hidden=\"true\"></span>Erogazione Prodotto </button>");
						}
					}
				}
				else if (data == "ricarica"){
					alert('Ricarica Conto');
				}
				else if(data == "esaurito"){
					alert('Prodotto esaurito');
				}
				else if(data == "disconnesso"){
					alert('Distributore Offline');
				}
			}
	);	
}