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

</head>

<body>
	<a href="acessoliberado.jsp"><img alt="Inicio" title="Início"
		src="resources/img/home.jpeg" height="48px" width="48px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair"
		src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>Cadastro de Ordem de Serviço</h1>
		<h3 style="color: red;">${msg}</h3>
	</center>

	<form action="salvarOrdemServico" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;">

		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td><label>Código:</label></td>
						<td><input type="text" readonly="readonly" id="id" name="id"
							value="${os.id}" class="field-long" style="width: 193px;"> </td>
					</tr>
					
					<tr>
						<td><label>Produto:</label></td>
						<td>
							<select id="produtos" name="produto" style="width: 193px;"> 
								<c:forEach items="${produtos}" var="pr">
									<option value="${pr.id}" id="${pr.id}"
										<c:if test="${pr.id == os.produtoId.id}">
										<c:out value="selected=selected" />
										</c:if>>
									${pr.pn}
									</option>
								</c:forEach>
							
							</select>
						</td>
					</tr>

					<tr>
						<td><label>Quantidade:</label></td>
						<td><input type="number" id="quantidade" name="quantidade"
							placeholder="Informe a quantidade" value="${os.quantidade}"
							maxlength="10"></td>
					</tr>
					
					<tr>
						<td><label>Status:</label></td>
						<td><input type="text" id="status" name="status"
							placeholder="Informe o status" value="${os.status}"
							maxlength="10"></td>
					</tr>
		
					<tr>
						<td><label>Data Emissão:</label></td>
						<td><input id="dateEmissao" name="dateEmissao" value="${os.dateEmissao}" style="width: 189px;"></td>
					
					</tr>
					
					<tr>
						<td><label>Data Entrega:</label></td>
						<td><input id="dataEntrega" name="dateEntrega" value="${os.dateEmissao}" style="width: 189px;"></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 95px;">
							<input type="submit" value="Cancelar" style="width: 95px;"
							onclick="document.getElementById('formUser').action ='salvarOrdemServico?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Ordens de Serviço</caption>
			<tr>
				<th>Código</th>
				<th>Data Emissão</th>
				<th>Data Entrega</th>
				<th>Quantidade</th>
				<th>Status</th>
				<th>Produto</th>
				<th>Deletar</th>
				<th>Atualizar</th>
				
			</tr>
			<c:forEach items="${ordens}" var="os">
				<tr>
					<td style="width: 150px"><c:out value="${os.id}">
						</c:out></td>

					<td style="width: 150px"><c:out value="${os.dataEmissao}">
						</c:out></td>
						
					<td style="width: 150px"><c:out value="${os.dataEntrega}">
						</c:out></td>
						
					<td style="width: 150px"><c:out value="${os.quantidade}">
						</c:out></td>
					
					<td style="width: 150px"><c:out value="${os.status}">
						</c:out></td>	
						
					<td style="width: 150px"><c:out value="${os.produtoId}">
						</c:out></td>			

					<td><a
						href="salvarOrdemServico?acao=delete&os=${os.id}"
						onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>

					<td><a
						href="salvarOrdemServico?acao=editar&os=${os.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
		$(function(){
			$('#dateEmissao').datepicker({dateFormat: 'dd/mm/yy'});
		});
		
		$(function(){
			$('#dataEntrega').datepicker({dateFormat: 'dd/mm/yy'});
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

</body>

</html>