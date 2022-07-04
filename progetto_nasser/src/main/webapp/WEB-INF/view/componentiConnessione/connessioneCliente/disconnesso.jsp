<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<h1 class="mb-5	 text-secondary">Connettiti</h1>
<h5 class="mb-2 text-secondary">Inserisci Codice Distributore</h5>
<form id="formCerca" class="d-flex" onsubmit="return false;">
	<input id="codDistributore" class="form-control me-2" type="number" placeholder="Cerca" name="codDistributore">
	<input class="btn btn-outline-primary" onclick= "cerca()" type="button" value="Invio">
</form>
<div id="messaggeConnettiti" class="row mt-3"></div>
<div class="row mt-3 ">
	<div class="alert alert-warning alert-dismissible fade show" role="alert">
		<strong>Disconnesso</strong>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	</div>
</div>
