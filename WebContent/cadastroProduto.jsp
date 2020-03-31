<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Cadastro de Produto</title>
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<script src="resources/javascript/jquery.maskMoney.min.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
	<a href="acessoliberado.jsp"><img alt="Inicio" title="Início"
		src="resources/img/home.jpeg" height="48px" width="48px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair"
		src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>Cadastro de Produto</h1>
		<h3 style="color: red;">${msg}</h3>
	</center>

	<form action="salvarProduto" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="codProduto" name="id"
							value="${produto.id}" class="field-long"></td>
					</tr>
					<tr>
						<td>PN:</td>
						<td><input type="text" id="pn" name="pn"
							placeholder="Informe o pn do produto" maxlength="13"
							value="${produto.pn}"></td>
					</tr>

					<tr>
						<td>Cliente:</td>
						<td><input type="text" id="cliente" name="cliente"
							placeholder="Informe o cliente" maxlength="45"
							value="${produto.cliente}"></td>
					</tr>

					<tr>
						<td>Descrição:</td>
						<td><input type="text" id="descricao" name="descricao"
							placeholder="Informe uma descrição" value="${produto.descricao}"
							maxlength="60"></td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 95px;">
							<input type="submit" value="Cancelar" style="width: 95px;"
							onclick="document.getElementById('formUser').action ='salvarProduto?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Produtos</caption>
			<tr>
				<th>Código</th>
				<th>PN</th>
				<th>Cliente</th>
				<th>Descrição</th>
			</tr>
			<c:forEach items="${produtos}" var="produto">
				<tr>
					<td style="width: 150px"><c:out value="${produto.id}">
						</c:out></td>

					<td style="width: 150px"><c:out value="${produto.pn}">
						</c:out></td>

					<td><c:out value="${produto.cliente}"></c:out></td>

					<td><c:out value="${produto.descricao}"></c:out></td>

					<td><a
						href="salvarProduto?acao=delete&produto=${produto.id}"
						onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>

					<td><a
						href="salvarProduto?acao=editar&produto=${produto.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("pn").value == '') {
				alert('Informe o PN');
				return false;
			} else if (document.getElementById("cliente").value == '') {
				alert('Informe o cliente');
				return false;
			} else if (document.getElementById("descricao").value == '') {
				alert('Informe a descrição');
				return false;
			}

			return true;

		}
	</script>

</body>

</html>