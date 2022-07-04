<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="connessioneBean" type="nasser.cliente.connessione.ConnessioneClienteModel" scope="session" />

<div class="row text-center">
	<div class="my-5 col-8"><h3 class="text-secondary">Connesso</h3></div>
	<div class="my-5 col-2">
		<div class="spinner-grow text-success" role="status">
			<span class="visually-hidden">Loading...</span>
		</div>
	</div>
</div>
<div class="row text-center" >
	<button type="button" class="btn btn-danger" onclick="disconnettiti()" >Disconnettiti</button>
</div>
<div class="row mt-3" >
	<div class="alert alert-warning alert-dismissible fade show" role="alert" style="<% if(connessioneBean.getConnessioneAperta()){out.print("dispaly:block");}else{out.print("display:none");} %>">
		<strong>E' stata ripresa la connessione ancora aperta</strong>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	</div>
</div>

