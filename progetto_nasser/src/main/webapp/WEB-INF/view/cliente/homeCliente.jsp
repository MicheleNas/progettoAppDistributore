<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="clienteBean" type="nasser.cliente.ClienteBean" scope="session" />
<!DOCTYPE html>
<html>
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	
	<title>Home</title>
</head>
<body class="bg-primary bg-opacity-25">

	<!------------------------- NAVBAR ------------------------->
	<jsp:include page ="../../componenti/navbar.jsp"/>
	<!------------------------- fine NAVBAR ------------------------->
	
	<!------------------------ CONTAINER  ------------------------>
	<div class="container-fluid">
	    <div class="row justify-content-center">
			<div class="col-auto bg-light shadow rounded rounded-3 needs-validation p-4 mt-3 mb-3">
			<!----------------- contenitore 1 per ottenere la vista connessione ------------------->
				<div id="contenitore1" class="text-center">
				 	<h1 class="mb-5	 text-secondary">Connettiti</h1>
					<h5 class="mb-2 text-secondary">Inserisci Codice Distributore</h5>
					<form id="formCerca" class="d-flex" onsubmit="return false;">
				        <input id="codDistributore" class="form-control me-2" type="number" placeholder="Cerca" name="codDistributore">
				        <input class="btn btn-outline-primary" onclick= "cerca()" type="button" value="Invio">
			      	</form>
			      	<div id="messaggeConnettiti" class="row mt-3"></div>
			     </div>
			 <!----------------- contenitore 2 per ottenere la vista ricarica  ------------------->
			     <div id="contenitore2" style="display:none">
			     	<div class="text-center">
			     		<h1 class="mb-5	 text-secondary">Ricarica</h1>
						<h5 class="mb-2 text-secondary">Ricarica da </h5>
			     	</div>
					<div class="row justify-content-center">
						<button type="button" onclick="ricarica(5)" value="5" class="btn btn-success mb-3">&euro; 5</button>
						<button type="button" onclick="ricarica(10)" value="10" class="btn btn-success mb-3">&euro; 10</button>
						<button type="button" onclick="ricarica(15)" value="15" class="btn btn-success mb-3">&euro; 15</button>
					</div>
					<div id="messaggeRicarica" class="row mt-3"></div>
			     </div>
			</div>
	    </div>
	</div>
	<!------------------------ fine CONTAINER ------------------------>
	
	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script src= "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	
	<!-- Jquery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
	<!-- Importo file JavaScript per le funzionalità della pagina -->
	<script src="./javaScript/homeCliente.js"></script>
  
</body>
</html>