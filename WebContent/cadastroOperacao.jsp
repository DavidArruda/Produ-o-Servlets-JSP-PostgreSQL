<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">
<title>Processo</title>
<script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="resources/css/cadastro.css">

</head>

<body>
	<a href="acessoliberado.jsp"><img alt="Inicio" title="Início"
		src="resources/img/home.jpeg" height="48px" width="48px"></a>
	<a href="index.jsp"><img alt="Sair" title="Sair"
		src="resources/img/sair.png" height="48px" width="48px"></a>
	<center>
		<h1>Cadastro de Operações</h1>
		<h3 style="color: red;">${msg}</h3>
	</center>

	<form action="salvarOperacao" method="post" id="formUser"
		onsubmit="return validarCampos() ? true : false;" enctype="multipart/form-data">
		<ul class="form-style-1">
			<li>
				<table>
					<tr>
						<td>
							<label for="id">Código:</label>
							<input type="text" readonly="readonly" id="id" name="id" value="${operacao.id}" class="field-long">
						</td>
					</tr>
					
					<tr>
						
						<td>
							<label for="descricao">Descrição:</label>
							<input type="text" id="descricao" name="descricao" placeholder="Informe uma descrição" maxlength="90" value="${operacao.descricao}">
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="maquina">Máquina:</label>
							<input type="text" id="maquina" name="maquina" placeholder="Informe a máquina" maxlength="20" value="${operacao.maquina}">
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="n_operacao">N° OP:</label>
							<input type="text" id="n_operacao" name="n_operacao" placeholder="Informe o Nº da op" maxlength="4" value="${operacao.n_operacao}"></td>
					</tr>
					
					<tr>
						<td>
							<label for="tempo_estimado">Tempo estimado:</label>
							<input type="time" id="tempo_estimado" name="tempo_estimado" placeholder="Informe tempo em HH:mm" value="${operacao.tempo_estimado}" >
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="produto_id">Produto:</label>
							<select id="produtos" name="produto_id">
								<c:forEach items="${produtos}" var="pr">
									<option value="${pr.id}" id="${pr.id}"
										<c:if test="${pr.id == operacao.produto_id.id}">
										<c:out value="selected=selected" />
										</c:if>>
									${pr.pn}
									</option>
								</c:forEach>
							
							</select>
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="ficha_preparacao">Ficha preparação:</label>
							<input type="file" name="ficha_preparacao" value="desenho">
						</td>
					</tr>
					
					<tr>
						<td>
							<label for="desenho">Desenho O.P:</label>
							<input type="file" name="desenho" value="desenho">
						</td>
					</tr>

					<tr>
						<td>
							<label></label>
							<label></label>
							<label></label>
							<input type="submit" value="Salvar" style="width: 310px;" >
						</td>
						
					</tr>
					
					<tr>
						<td>
							<label></label>
							<label></label>
							<label></label>
							<input type="submit" value="Cancelar" style="width: 310px;"
							onclick="document.getElementById('formUser').action ='salvarOperacao?acao=reset'">
						</td>
					</tr>
					
				</table>

			</li>
		</ul>
	</form>

	<div class="container">
		<table class="responsive-table">
			<caption>Lista de Produtos</caption>
			<tr>
				<th>CÓDIGO</th>
				<th>Nº OPERAÇÃO</th>
				<th>PRODUTO</th>
				<th>DESCRIÇÃO</th>
				<th>MÁQUINA</th>
				<th>DESENHO</th>
				<th>FICHA</th>
				<th>EXCLUIR</th>
				<th>ATUALIZAR</th>
			</tr>

			<c:forEach items="${operacoes}" var="operacao">
				<tr>
					<td style="width: 150px">
						<c:out value="${operacao.id}"/>
					</td>
						
					<td style="width: 150px">
						<c:out value="${operacao.n_operacao}"/>
					</td>
					
					<td>
						<c:out value="${operacao.produto_id}"/>
					</td>

					<td style="width: 150px">
						<c:out value="${operacao.descricao}"/>
					</td>
					
					<td style="width: 150px">
						<c:out value="${operacao.maquina}"/>
					</td>
					
					<td><a href="salvarOperacao?acao=download&tipo=desenho&operacao=${operacao.id}"><img
							alt="Download desenho" title="Desenho" src="resources/img/download.png"
							width="20px" height="20px"></a></td>
							
							
					<td><a href="salvarOperacao?acao=download&tipo=ficha&operacao=${operacao.id}"><img
							alt="Download ficha" title="Ficha" src="resources/img/download.png"
							width="20px" height="20px"></a></td>
					
					<td>
						<a href="salvarOperacao?acao=delete&operacao=${operacao.id}"
							onclick="return confirm('Confirmar a exclusão?');">
							<img src="resources/img/excluir.png" alt="excluir" title="Excluir" width="20px" height="20px">
						</a>
					</td>

					<td><a
						href="salvarOperacao?acao=editar&operacao=${operacao.id}"><img
							alt="Editar" title="Editar" src="resources/img/editar.png"
							width="20px" height="20px"></a></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<script type="text/javascript">
		function validarCampos() {

			if (document.getElementById("n_operacao").value == '') {
				alert('Informe o Nº da OP');
				return false;
			} else if (document.getElementById("produto_id").value == '') {
				alert('Informe o pn do Produto');
				return false;
			}	
			return true;

		}
	</script>

</body>

</html>