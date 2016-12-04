$(document).ready(
		function() {
			if (window.location.href.indexOf("cadastrodesistema.jsp?u=") > -1) {
				$("#update").val("update");
				var url = window.location.href;
				var id = url.substring(url.length - 1, url.length)
				$("#id-sistema-update").val(id);
				$("#action").val("updateSistema");
				$.ajax({
					type : "POST",
					url : "FrontControllerServlet",
					data : {
						action : "fillSistema",
						filtro : id
						},
						success : function(response) {
							var resposta = $.parseJSON(response);
							$("#nome").val(resposta.sistema.nome);
							$("#servidor option").val(resposta.sistema.servidor);
							$("#formatoLog option").val(resposta.sistema.formatolog);
							$("#ptLogs").val(resposta.sistema.ptLogs);
							$("#pxLogs").val(resposta.sistema.pxLogs);
							$("#ptLogs2").val(resposta.sistema.ptLogs2);
							$("#pxLogs2").val(resposta.sistema.pxLogs2);
							$("#data").val(resposta.sistema.data);
							$("#time").val(resposta.sistema.time);
							$("#novaData").val(resposta.sistema.novaData);
							}
						});
				} else if (window.location.href.indexOf("gerenciadorsistema.jsp") > -1) {
					$.ajax({
						type : "POST",
						url : "FrontControllerServlet",
						data : {
							action : "listSistema",
							filtro : "all"
								},
								success : function(response) {
									var resposta = $.parseJSON(response);
									var sistemas = resposta.sistemas;
									var erro = resposta.Erro;
									if (resposta.hasOwnProperty("sistemas")) {
										$("#table-sistemas").children().remove();
										sistemas.forEach(function(el) {
											$("#table-sistemas").append("<tr><td>"
													+ el.nome + "</td><td>"
													+ el.servidor + "</td><td>"
													+ el.formatolog + "</td><td>"
													+ el.periodicidade + "</td><td>"
													+ el.proximaleitura + "</td>" 
													+ "<td><a onclick=alterar(\""
													+ el.id + "\"); id=\"" + el.id
													+ "-alterar\" class=\"alterar-sistema\">Alterar</a></td>"
													+ "<td><a onclick=excluir(\""
													+ el.id + "\"); id=\"" + el.id 
													+ "-excluir\" class=\"excluir-sistema\">Excluir</a></td></tr>");
											});
										} else if (resposta.hasOwnProperty("Erro")) {
											alert(resposta.Erro);
											}
									}
								});
					}
			
				/* LISTAGEM DE SISTEMA */
				$("#buscar-sistemas").click(function() {
					var filtro = $("#filtro-busca-sistemas").val();
					// validação form
					if (filtro.trim() === "") {
						$("#div-buscar-sistemas").addClass("has-error");
						$("#div-buscar-sistemas span").addClass("glyphicon glyphicon-remove form-control-feedback");
						alert("Favor preencher o filtro de busca");
					} else {
						if (filtro.trim().length > 100) {
							$("#div-buscar-sistemas").addClass("has-error");
							$("#div-buscar-sistemas span").addClass("glyphicon glyphicon-remove form-control-feedback");
							alert("Tamanho do filtro excedido. \nFavor não ultrapassar 100 caractéres");
						}else{
							$("#div-buscar-sistemas").removeClass("has-error");
							$("#div-buscar-sistemas span").removeClass("glyphicon glyphicon-remove form-control-feedback");
								$.ajax({
									type : "POST",
									url : "FrontControllerServlet",
									data : {
										action : "listSistema",
										filtro : filtro
										},
										success : function(response) {
											var resposta = $.parseJSON(response);
											var sistemas = resposta.sistemas;
											var erro = resposta.Erro;
											if (resposta.hasOwnProperty("sistemas")) {
												$("#table-sistemas").children().remove();
												sistemas.forEach(function(el) {
													$("#table-sistemas").append("<tr><td>"
															+ el.nome
															+ "</td><td>"
															+ el.servidor
															+ "</td><td>"
															+ el.formatolog
															+ "</td><td>"
															+ el.periodicidade
															+ "</td><td>"
															+ el.proximaleitura
															+ "</td>"
															+ "<td><a onclick=alterar(\""
															+ el.id
															+ "\"); id=\""
															+ el.id
															+ "-alterar\" class=\"alterar-sistema\">Alterar</a></td>"
															+ "<td><a onclick=excluir(\""
															+ el.id
															+ "\"); id=\""
															+ el.id
															+ "-excluir\" class=\"excluir-sistema\">Excluir</a></td></tr>");
													});
												} else if (resposta.hasOwnProperty("Erro")) {
													alert(resposta.Erro);
													}
											}
										});
								}
						}
				});
				/* FIM LISTAGEM DE SISTEMA */

				/* MODAL PADRAO URL */
				// abrir modal Novo Padrão de URL
				$('#myModal').on('shown.bs.modal', function() {
					$('#myInput').focus();
				});
				// Buscar Expressão Regular
				$("#submit-regex").click(function() {
					var regex = $("#regex").val();
					if (regex.trim() === "") {
						$("#div-regex").toggleClass("has-error");
						} else {
							$("#div-regex").removeClass("has-error");
							$.ajax({
								type : "POST",
								url : "FrontControllerServlet",
								data : {
									action : "regexPadraoURL",
									regex : regex
									},
									success : function(response) {
										var resposta = $.parseJSON(response);
										if (resposta.hasOwnProperty("url")) {
											var urls = resposta.url;
											urls.forEach(function(el) {
												$("#table-url").append("<tr><td>"
														+ el
														+ "</td></tr>");
												});
											refresh = false;
											} else if (resposta.hasOwnProperty("Erro")) {
												alert(resposta.Erro);
												}
										}
									});
							}
					return false;
					});
				// Salvar Padrão URL
				$("#submitpadraourl").on("click", function() {
					var regex = $("#regex").val();
					var nome = $("#padrao-url-nome").val();
					if (regex.trim() === ""){
						$("#div-regex").toggleClass("has-error");
					}else if (nome.trim() === ""){
						$("#div-nome").toggleClass("has-error");
					}else if (regex.trim() !== "" && nome.trim() !== "") {
						$("#div-regex").removeClass("has-error");
						$("#div-nome").removeClass("has-error");
						$.ajax({
							type : "POST",
							url : "FrontControllerServlet",
							data : {
								action : "insertPadraoURL",
								nome : nome,
								regex : regex
								},
								success : function(response) {
									var resposta = $.parseJSON(response);
									if (resposta.hasOwnProperty("mensagem") > -1) {
										var mensagem = resposta.mensagem;
										alert(mensagem);
										window.location.replace("HTTPreport.jsp");
									} else if (resposta.indexOf("Erro")) {
										alert("Erro de conexão com o servidor");
										}
									}
								});
						}
					return false;
					});
				
				$("#deletePadraoURL").on("click", function() {
					var idPadrao = $("#selectPadraoURL").val();
					$.ajax({
						type : "POST",
						url : "FrontControllerServlet",
						data : {
							action : "deletePadraoURL",
							id : idPadrao
							},
							success : function(response) {
								var resposta = $.parseJSON(response);
								if (resposta.hasOwnProperty("mensagem") > -1) {
									var mensagem = resposta.mensagem;
									alert(mensagem);
									window.location.replace("HTTPreport.jsp");
								} else if (resposta.indexOf("Erro")) {
									alert("Erro de conexão com o servidor");
									}
								}
							});
				});
				/* FIM PADRAO URL */
				/* DATEPICKER */
				// não é necessário cada grupo colocar os
				// codigos javascript em cada página
				// isoladamente
				$('.form_datetime').datetimepicker({
					language : 'pt-BR',
					weekStart : 1,
					todayBtn : 1,
					autoclose : 1,
					todayHighlight : 1,
					startView : 2,
					forceParse : 0,
					showMeridian : 1,
					format : 'YYYY-MM-DD HH:mm:ss'
				});
				$('.form_date').datetimepicker({
					language : 'pt-BR',
					weekStart : 1,
					todayBtn : 1,
					autoclose : 1,
					todayHighlight : 1,
					startView : 2,
					minView : 2,
					forceParse : 0
				});
				$('.form_time').datetimepicker({
					language : 'pt-BR',
					weekStart : 1,
					todayBtn : 1,
					autoclose : 1,
					todayHighlight : 1,
					startView : 1,
					minView : 0,
					maxView : 1,
					forceParse : 0
				});
				/* FIM DATEPICKER */
				$("#cadastro-sistema-submit")
						.click(
								function() {
									var nome = $("#nome").val();
									var servidor = $("#servidor").val();
									var formatoLog = $("#formatoLog").val();
									var ptLogs = $("#ptLogs").val();
									var pxLogs = $("#pxLogs").val();
									var ptLogs2 = $("#ptLogs2").val();
									var pxLogs2 = $("#pxLogs2").val();
									var data = $("#data").val();
									var time = $("#time").val();
									var novaData = $("#novaData").val();
									var action = $("#action").val();
									var id_sistema_update = $(
											"#id-sistema-update").val();
									// validação form
									if (nome.trim() === "") {
										$("#div-nome").toggleClass(
												"has-error");
									}
									if (data.trim() === "") {
										$("#div-data").toggleClass(
												"has-error");
									}
									if (time.trim() === "") {
										$("#div-time").toggleClass(
												"has-error");
									}
									if (novaData.trim() === "") {
										$("#div-nova").toggleClass(
												"has-error");
									} else {
										$
												.ajax({
													type : "POST",
													url : "FrontControllerServlet",
													data : {
														nome : nome,
														servidor : servidor,
														formatoLog : formatoLog,
														ptLogs : ptLogs,
														pxLogs : pxLogs,
														ptLogs2 : ptLogs2,
														pxLogs2 : pxLogs2,
														data : data,
														novaData : novaData,
														time : time,
														action : action,
														id_sistema_update : id_sistema_update
													}, // action fica no
													// jsp porque o form
													// está sendo
													// serializado(o
													// action está
													// dentro de
													// sistemaForm)
													success : function(
															response) {
														var resposta = $
																.parseJSON(response);
														if (resposta
																.hasOwnProperty("mensagem")) {
															var mensagem = resposta.mensagem;
															alert(mensagem);
															if (!confirm("Você deseja cadastrar outro Sistema? \n Ok, para cadastrar outro Sistema \n Cancelar, para ser sair desta página.")) {
																window.location
																		.replace("gerenciadorsistema.jsp");
															} else {
																window.location
																		.replace("cadastrodesistema.jsp");
																}
															}
														}
													});
											return false;
										}
									});
				});

function alterar(nome) {
	window.location.replace("cadastrodesistema.jsp?u=" + nome);
}

function excluir(nome) {
	decisao = confirm("Tem certeza que deseja excluir este sistema?\nTodos os registros de logs serão apagados.");
if (decisao) {
	// alert("Não disponível");
	$.ajax({
		type : "POST",
		url : "FrontControllerServlet",
		data : {
			action : "deleteSistema",
			filtro : nome
		},
		success : function(response) {
			var resposta = $.parseJSON(response);
			if (resposta.mensagem.indexOf("sucesso") > -1) {
				alert(resposta.mensagem);
				window.location.replace("gerenciadorsistema.jsp");
				}

			}
		});
	}
}
// Teste acesso
$("#pxLogs-teste-btn").click(function() {
var pxLogs = $("#pxLogs").val();
var ptLogs = $("#ptLogs").val();
if (pxLogs === "" || ptLogs === "") {
	$("#div-prefixo-acesso").toggleClass("has-error");
} else {
	$.ajax({
		type : "POST",
		url : "FrontControllerServlet",
		data : {
			action : "testAccessLogFolder",
				pxLogs : pxLogs,
				ptLogs : ptLogs
			},
			success : function(response) {
				var resposta = $.parseJSON(response);
				alert(resposta.mensagem);
			}
		});
	}
});

$("#cancela-btn").click(function() {
window.location.replace("home.jsp?");
});

$("#volta-btn").click(function() {
window.location.replace("home.jsp?");
});

$("#pxLogs2-teste-btn").click(function() {
var pxLogs2 = $("#pxLogs2").val();
var ptLogs2 = $("#ptLogs2").val();
if (pxLogs2 === "" || ptLogs2 === "") {
	$("#div-prefixo-erro").toggleClass("has-error");
} else {
	$.ajax({
		type : "POST",
		url : "FrontControllerServlet",
		data : {
			action : "testErrorLogFolder",
				pxLogs2 : pxLogs2,
				ptLogs2 : ptLogs2
			},
			success : function(response) {
				var resposta = $.parseJSON(response);
				alert(resposta.mensagem);
			}
		});
	}
});
$("#svg-chart").on("load", reportCharts());
function reportCharts(){
	var data = {
			labels : [ 'resilience', 'maintainability',
					'accessibility', 'uptime', 'functionality',
					'impact' ],
			series : [ {
				label : '2012',
				values : [ 4, 8, 15, 16, 23, 42 ]
			}, {
				label : '2013',
				values : [ 12, 43, 22, 11, 73, 25 ]
			}, {
				label : '2014',
				values : [ 31, 28, 14, 8, 15, 21 ]
			}, ]
		};

		var chartWidth = 300, barHeight = 20, groupHeight = barHeight
				* data.series.length, gapBetweenGroups = 10, spaceForLabels = 150, spaceForLegend = 150;

		// Zip the series data together (first values, second values, etc.)
		var zippedData = [];
		for (var i = 0; i < data.labels.length; i++) {
			for (var j = 0; j < data.series.length; j++) {
				zippedData.push(data.series[j].values[i]);
			}
		}

		// Color scale
		var color = d3.scale.category20();
		var chartHeight = barHeight * zippedData.length
				+ gapBetweenGroups * data.labels.length;

		var x = d3.scale.linear().domain([ 0, d3.max(zippedData) ])
				.range([ 0, chartWidth ]);

		var y = d3.scale.linear().range(
				[ chartHeight + gapBetweenGroups, 0 ]);

		var yAxis = d3.svg.axis().scale(y).tickFormat('').tickSize(
				0).orient("left");

		// Specify the chart area and dimensions
		var chart = d3.select(".chart").attr("width",
				spaceForLabels + chartWidth + spaceForLegend).attr(
				"height", chartHeight);

		// Create bars
		var bar = chart
				.selectAll("g")
				.data(zippedData)
				.enter()
				.append("g")
				.attr(
						"transform",
						function(d, i) {
							return "translate("
									+ spaceForLabels
									+ ","
									+ (i * barHeight + gapBetweenGroups
											* (0.5 + Math
													.floor(i
															/ data.series.length)))
									+ ")";
						});

		// Create rectangles of the correct width
		bar.append("rect").attr("fill", function(d, i) {
			return color(i % data.series.length);
		}).attr("class", "bar").attr("width", x).attr("height",
				barHeight - 1);

		// Add text label in bar
		bar.append("text").attr("x", function(d) {
			return x(d) - 3;
		}).attr("y", barHeight / 2).attr("fill", "red").attr("dy",
				".35em").text(function(d) {
			return d;
		});

		// Draw labels
		bar.append("text").attr("class", "label").attr("x",
				function(d) {
					return -10;
				}).attr("y", groupHeight / 2).attr("dy", ".35em")
				.text(
						function(d, i) {
							if (i % data.series.length === 0)
								return data.labels[Math.floor(i
										/ data.series.length)];
							else
								return ""
						});

		chart.append("g").attr("class", "y axis").attr(
				"transform",
				"translate(" + spaceForLabels + ", "
						+ -gapBetweenGroups / 2 + ")").call(yAxis);
}