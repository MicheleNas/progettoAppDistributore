<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="clienteBean" type="nasser.cliente.ClienteBean" scope="session" />

<nav class="navbar navbar-expand navbar-dark bg-primary bg-gradient">
  <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
    <ul class="navbar-nav justify-content-center">
		<li class="nav-item">
			<a id="connessione" class="nav-link text-white fs-5 fw-bold mx-3" href="#" >Connessione</a>
		</li>
		<li class="nav-item">
			<div class="row">
				<a id="credito" class="nav-link text-white fs-5 fw-bold mx-3 col-auto" href="#">Credito &euro; <%= clienteBean.getCredito() %></a>
				<a id="aggiorna" class="nav-link text-white fs-5 fw-bold mx-3 col-auto" href="#" onclick="aggiornaCredito()" ><i class="bi bi-arrow-clockwise"></i></a>
			</div>		
		</li>
    </ul>
  </div>
</nav>
