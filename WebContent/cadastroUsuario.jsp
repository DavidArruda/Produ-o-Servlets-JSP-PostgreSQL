<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Cadastro de Usuário</title>
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
	<a href="acessoliberado.jsp"><img alt="Inicio" title="Início"
		src="resources/img/home.jpeg" height="48px" width="48px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair"
		src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>Cadastro de Usuário</h1>
		<h3 style="color: red;">${msg}</h3>
	</center>

	<form action="salvarUsuario" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>Código:</td>
						<td><input type="text" readonly="readonly" id="codUsuario" name="codUsuario"
							value="${usuario.codUsuario}" class="field-long"></td>
					</tr>
					<tr>
						<td>Nome:</td>
						<td><input type="text" id="nome" name="nome"
							placeholder="Informe o nome" maxlength="90"
							value="${usuario.nome}"></td>
					</tr>

					<tr>
						<td>Login:</td>
						<td><input type="text" id="login" name="login"
							placeholder="Informe o login" maxlength="15"
							value="${usuario.login}"></td>
					</tr>

					<tr>
						<td>Senha:</td>
						<td><input type="password" id="senha" name="senha"
							placeholder="Informe uma senha" value="${usuario.senha}"
							maxlength="10"></td>
					</tr>
					
					<tr>
						<td>Cargo:</td>
						<td>
							<select id="cargos" name="tipo_usuario">
								<c:forEach items="${cargos}" var="cat">
									<option value="${cat.codTipo}" id="${cat.codTipo}"
										<c:if test="${cat.codTipo == usuario.tipo_usuario.codTipo}">
										<c:out value="selected=selected" />
										</c:if>>
									${cat.descricao}
									</option>
								</c:forEach>
							
							</select>
						</td>
					</tr>

					<tr>
						<td></td>
						<td><input type="submit" value="Salvar" style="width: 95px;">
							<input type="submit" value="Cancelar" style="width: 95px;"
							onclick="document.getElementById('formUser').action ='salvarUsuario?acao=reset'"></td>
					</tr>
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Usuario</caption>
			<tr>
				<th>Código</th>
				<th>Nome</th>
				<th>Login</th>
				<th>Cargo</th>
				<th>Deletar</th>
				<th>Atualizar</th>
			</tr>
			<c:forEach items="${usuarios}" var="usuario">
				<tr>
					<td style="width: 150px"><c:out value="${usuario.codUsuario}">
						</c:out></td>

					<td style="width: 150px"><c:out value="${usuario.nome}">
						</c:out></td>

					<td><c:out value="${usuario.login}"></c:out></td>

					<td><c:out value="${usuario.tipo_usuario}"></c:out></td>

					<td><a
						href="salvarUsuario?acao=delete&usuario=${usuario.codUsuario}"
						onclick="return confirm('Confirmar a exclusão?');"><img
							src="resources/img/excluir.png" alt="excluir" title="Excluir"
							width="20px" height="20px"> </a></td>

					<td><a
						href="salvarUsuario?acao=editar&usuario=${usuario.codUsuario}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("nome").value == '') {
				alert('Informe o nome');
				return false;
			} else if (document.getElementById("login").value == '') {
				alert('Informe o login');
				return false;
			} else if (document.getElementById("senha").value == '') {
				alert('Informe a senha');
				return false;
			}
			
			return true;

		}
	</script>

</body>

</html>