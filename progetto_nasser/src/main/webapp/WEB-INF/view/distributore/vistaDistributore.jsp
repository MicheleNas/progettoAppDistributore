<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="beanListaDistributore" type="nasser.distributore.lista_prodotti.ListaProdottiModel" scope="request" />

<html>
<head>
	<!-- Required meta tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<!-- Bootstrap CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	
	<link href="./css/listaDistributore.css" rel="stylesheet">
	
	<title>Distributore</title>
</head>
<body class="bg-primary bg-opacity-25">

	<!------------------------ CONTAINER CONNESSIONE ------------------------>
	<div class="container-fluid">
	    <div class="row justify-content-center">
			<div id="contenitoreConnessione" class="col-10 col-sm-7 col-md-4 bg-light shadow rounded rounded-3 needs-validation p-4 mt-3 mb-3">
				<!------------------------------------------------------------------------->
				<div id="riga1">
					<div class="row justify-content-center text-center">
						<span class="d-block col-auto text-muted">Disconnesso</span>
						<span class="dot col-auto"></span>
					</div>
				</div>
				<!----------------------------- TIMER ----------------------------------->
				<div class="row justify-content-center">
					<div id="timer" class="mb-2 text-secondary col-auto"></div>
				</div>
			</div>
	    </div>
	</div>
	<!------------------------------- FINE CONTAINER CONNESSIONE --------------------------------->
	
	<!------------------------ CONTAINER LISTA ------------------------>
	<div class="container-fluid">
	    <div class="row justify-content-center">
			<div id="contenitoreLista" class="col-auto bg-light shadow rounded rounded-3 needs-validation p-4 mt-3 mb-3">
				<!------------------------------------ TABS ------------------------------------->
				<ul class="nav nav-tabs" id="myTab" role="tablist">
			        <% for(int j=0; j<beanListaDistributore.getCategorieProdotti().size(); j++){ %>
			            <li class="nav-item" role="presentation">
			                <button class="nav-link <% if(j==0){out.print("active");}%>" id="<%=beanListaDistributore.getCategorieProdotti().get(j)%>-tab" data-bs-toggle="tab" data-bs-target="#<%=beanListaDistributore.getCategorieProdotti().get(j)%>" type="button" role="tab" aria-controls="<%=beanListaDistributore.getCategorieProdotti().get(j)%>" aria-selected="<% if(j==0){out.print("true");}else{out.print("false");} %>"><%=beanListaDistributore.getCategorieProdotti().get(j)%></button>
			            </li>
			        <% } %>
			    </ul>
				<!------------------------------ LISTE -------------------------------------------------------------->
				<!-- vengono generate le varie liste suddivise per categoria in maniera del tutto automatica.
					 All'interno del DB possono essere inseriti, modificati ed eliminati dei prodotti anche
					 in un secondo momento -->
				<div class="tab-content" id="myTabContent">
					<% for(int j=0; j<beanListaDistributore.getCategorieProdotti().size(); j++){ %>
					    <div class="tab-pane fade <% if(j==0){out.print("show active");} %>" id="<%=beanListaDistributore.getCategorieProdotti().get(j)%>" role="tabpanel" aria-labelledby="<%=beanListaDistributore.getCategorieProdotti().get(j)%>-tab">
					        <table class="table">
					            <thead>
					                <tr>
					                    <th scope="col">Cod Prodotto</th>
					                    <th scope="col">Nome Prodotto</th>
					                    <th scope="col">Prezzo</th>
					                </tr>
					            </thead> 
					            <tbody>
					                <% for (int i=0; i< beanListaDistributore.getFileJson().getAsJsonArray(beanListaDistributore.getCategorieProdotti().get(j)).size(); i++) { 
					                        out.println("<tr>");
					                        for (int l=0; l < beanListaDistributore.getFileJson().getAsJsonArray(beanListaDistributore.getCategorieProdotti().get(j)).get(i).getAsJsonArray().size(); l++){
					                            out.println("<td>" + beanListaDistributore.getFileJson().getAsJsonArray(beanListaDistributore.getCategorieProdotti().get(j)).get(i).getAsJsonArray().get(l).getAsString() + "</td>");
					                        }
					                        out.println("<td><button id=\""+beanListaDistributore.getFileJson().getAsJsonArray(beanListaDistributore.getCategorieProdotti().get(j)).get(i).getAsJsonArray().get(0).getAsString()
					                                +"\" class=\"btn btn-outline-primary\" onclick=\"acquista("+beanListaDistributore.getFileJson().getAsJsonArray(beanListaDistributore.getCategorieProdotti().get(j)).get(i).getAsJsonArray().get(0).getAsString()
					                                +")\" type=\"button\" value=\""+ beanListaDistributore.getFileJson().getAsJsonArray(beanListaDistributore.getCategorieProdotti().get(j)).get(i).getAsJsonArray().get(2).getAsString() +"\">Acquista</button></td></tr>");
					                    } %>
					            </tbody>
					        </table>
					    </div>
					<% } %>
				  <div class="tab-pane fade" id="bevande" role="tabpanel" aria-labelledby="bevande-tab">...</div>
				</div>
			</div>
	    </div>
	</div>
	<!------------------------------- FINE CONTAINER LISTA --------------------------------->
	
	<!------------ MODAL (Serve per simulare l'erogazione di un prodotto)--------------------->
	<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="modal_body" class="modal-body text-center">
					<button class="btn btn-primary" type="button" disabled>
						<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
						Erogazione Prodotto
					</button>
				</div>
			</div>
		</div>
	</div>
	<div id="modalBackdrop"></div>
	
	<!-- Jquery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script src= "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	
	<!-- Importo file JavaScript per le funzionalità della pagina -->
	<script src="./javaScript/vistaDistributore.js"></script>

</body>
</html>