<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Cadastro de Ordem de Serviço</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet" href="resources/css/cadastro.css">

<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

</head>

<body>
	<a href="acessoliberado.jsp"><img alt="Inicio" title="Início"
		src="resources/img/home.jpeg" height="48px" width="48px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair"
		src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>APONTAMENTO</h1>
		<h3 style="color: red;">${msg}</h3>
	</center>

	<fieldset>
	<legend>DADOS APONTAMENTO</legend>
	<form action="salvarApontamento" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;">

		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><label>Código:</label></td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${apontamento.id}" class="field-long" style="width: 250px;"> </td>
					</tr>
					
					<tr>
						<td><label>N° O.S:</label></td>
						<td>
							<input type="text" id="ordemServico" name="ordemServico" value="${apontamento.ordemServico.id}" class="field-long" style="width: 250px;">
						</td>
					</tr>
					
					<tr>
						<td><label>Produto:</label></td>
						<td>
							<input type="text" id="produto" name="produto" value="${apontamento.ordemServico.produtoId.id}" class="field-long" style="width: 250px;">
						</td>
					</tr>
					
					<tr>
						<td><label>Operação:</label></td>
						<td>
							<input type="text" id="operacao" name="operacao" value="${apontamento.operacao.id}" class="field-long" style="width: 250px;">
						</td>
					</tr>
					
					<tr>
						<td><label>Qtd O.S:</label></td>
						<td>
							<input type="text" id="qtdOs" name="qtdOs" value="${apontamento.ordemServico.quantidade}"
							 class="field-long" style="width: 250px;" readonly="readonly">
						</td>
					</tr>
					
					<tr>
						<td><label>Qtd Produzida:</label></td>
						<td><input type="text" id="quantidade" name="quantidade" value="${apontamento.quantidade}" class="field-long" style="width: 250px;"></td>
					</tr>
					
					<tr>
						<td><label>Data:</label></td>
						<td><input type="text" id="dataAp" name="dataAp" value="${apontamento.dataAp}" placeholder="EX: 15/03/2020" style="width: 250px;"></td>
					</tr>
		
					<tr>
						<td><label>Inicio:</label></td>
						<td><input type="time" id="inicio" name="inicio" value="${apontamento.inicio}"  style="width: 250px;"></td>
					</tr>
					
					<tr>
						<td><label>Termino:</label></td>
						<td><input type="time" id="termino" name="termino" value="${apontamento.termino}" style="width: 250px;"></td>
					</tr>
					
					<tr>
						<td><label>Usuário:</label></td>
						<td><input type="text" id="usuario" name="usuario" value="${apontamento.usuario.codUsuario}" class="field-long" style="width: 250px;"></td>
					</tr>
					
					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 123px;">
							<input type="submit" value="Cancelar" style="width: 123px;"
							onclick="document.getElementById('formUser').action ='salvarOrdemServico?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>
	</fieldset>
	
	<fieldset>
	<legend>BUSCAR O.S</legend>
	<form method="post" action="servletPesquisaOs" >
	<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><label>N° O.S</label></td>
						<td>
							<input type="text" id="os" name="os" style="width: 210px;">
						</td>
						
						<td>
							<input type="submit" value="Pesquisar" style="width:123px;">
						</td>
					</tr>
				</table> 
			</li> 
	</ul>
	</form>
	</fieldset>
	
		<div class="container">
		<table class="responsive-table">
			<caption>APONTAMENTOS</caption>
			<tr>
				<th>ORDEM DE SERVICO</th>
				<th>OPERAÇÃO</th>
				<th>APONTAR</th>
			</tr>
			
			<c:forEach items="${statusOs}" var="statusOs">
				<tr>
					<td style="width: 150px">
						<c:out value="${statusOs.ordemServico}"/>
					</td>

					<td style="width: 150px">
						<c:out value="${statusOs.operacao}"/>
					</td>
					
					<td>
						<a href="salvarApontamento?acao=apontar&operacaoAtual=${statusOs.operacao}&os=${statusOs.ordemServico}">
							<img alt="Apontar" title="Apontar" src="resources/img/editar.png" width="20px" height="20px">
						</a>
					</td>
					
				</tr>
			</c:forEach>
			
		</table>
	</div>
	
	<script type="text/javascript">
		$(function(){
			$('#dataAp').datepicker({dateFormat: 'dd/mm/yy'});
		});
	</script>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("dateEmissao").value == '') {
				alert('Informe a Data de Emissão');
				return false;
			} else if (document.getElementById("dataEntrega").value == '') {
				alert('Informe a data de entrega');
				return false;
			} else if (document.getElementById("quantidade").value == '') {
				alert('Informe a quantidade');
				return false;
			} else if (document.getElementById("status").value == '') {
				alert('Informe o status');
				return false;
			}else if (document.getElementById("produto").value == '') {
				alert('Informe o produto');
				return false;
			}
			
			return true;

		}
	
	</script>
	
<script type="text/javascript">
	function consultarOs() {
		valorInformado = $('#ordemServico').val();
		
		$.ajax({
			method: "GET",
			url: "salvarApontamento", //para qual servlet
			data: { valorParam: valorInformado } 
		})
		.done(function(response) { //ok nenhum erro
			alert("Sucesso: " + response);
			//Fazer algo
		})
		.fail(function(xhr,status, errorThrow) { //resposta erro algum problema ocorreu
			alert("Erro: " + xhr.responseText);
			//Fazer algo se der errado
		});
	}
</script>

</body>

</html>