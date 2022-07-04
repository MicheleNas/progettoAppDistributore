<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="distributoreBean" type="nasser.distributore.DistributoreBean" scope="session" />

	<div class="row justify-content-center text-center">
		<div class="col-auto"><h3 class="text-secondary">Connesso</h3></div>
		<div class="col-auto">
			<div class="spinner-grow text-success" role="status">
				<span class="visually-hidden">Loading...</span>
			</div>
		</div>
	</div>
	<div class="row justify-content-center text-center">
		<div class="mb-2 text-secondary col-auto">Cliente: <%= distributoreBean.getNomeClienteConnesso() %></div>
		<div class="mb-2 text-secondary col-auto">Credito: &euro; <%= distributoreBean.getCreditoClienteConnesso() %></div>
	</div>
