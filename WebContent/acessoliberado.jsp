<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inicial</title>
</head>
<body>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/sair.png" height="48px" width="48px"></a>
	
	<center style="padding-top: 10%">
	<h3>BEM-VINDO AO SISTEMA !</h3> <br/>
	
	<table>
		 <tr>
		 	<td><a href="salvarUsuario?acao=listartodos"> <img alt="Cadastrar Usuário" title="Cadastrar Usuários" src="resources/img/usuario.png" width="100px" height="100px" ></a> </td>
		 	<td><a href="salvarProduto?acao=listartodos"> <img alt="Cadastrar Produto" title="Cadastrar Produto" src="resources/img/produtoImg.png" width="100px" height="100px" ></a> </td>
		 	<td><a href="salvarOperacao?acao=listartodos"> <img alt="Processo" title="Cadastrar Processos " src="resources/img/produtoImg.png" width="100px" height="100px" ></a> </td>
		 	<td><a href="salvarOrdemServico?acao=listartodos"> <img alt="OD" title="Cadastrar OS " src="resources/img/produtoImg.png" width="100px" height="100px" ></a> </td>
		 	<td><a href="salvarApontamento?acao=listartodos"> <img alt="Apontamento" title="Novo Apontamento " src="resources/img/produtoImg.png" width="100px" height="100px" ></a> </td>
		  </tr>
		  
		  <tr>
		  	<td>Cad. Usuário</td>
		  	<td>Cad. Produto</td>
		  	<td>Cad. Processo</td>
		  	<td>Cad. O.S</td>
		  	<td>Novo Apontamento</td>
		  </tr>
	
	</table>
	
	</center>
</body>
</html>