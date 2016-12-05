package br.cefetrj.webdep.view.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.webdep.model.entity.FormatoLog;
import br.cefetrj.webdep.model.entity.Servidor;
import br.cefetrj.webdep.model.entity.Sistema;
import br.cefetrj.webdep.services.ServidorServices;
import br.cefetrj.webdep.services.SistemaServices;

public class InsertSistemaCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter pw = response.getWriter();
			Sistema s = new Sistema();
			String nome = request.getParameter("nome");
			String server = request.getParameter("servidor");
			String formatoLog = request.getParameter("formatoLog");
			FormatoLog fmt = new FormatoLog();
			fmt.setId(Long.parseLong(formatoLog));
			Servidor serv = new Servidor();
			serv.setFormatoLog(fmt);
			serv.setId(Long.parseLong(server));
			String ptLogs = request.getParameter("ptLogs");
			String pxLogs = request.getParameter("pxLogs");
			String ptLogs2 = request.getParameter("ptLogs2");
			String pxLogs2 = request.getParameter("pxLogs2");
			/*String nova = request.getParameter("novaData");
			LocalDate ld = LocalDate.parse(request.getParameter("data"));
			LocalTime lt = LocalTime.parse(request.getParameter("time"));*/
			int novaDias = Integer.parseInt(request.getParameter("novaLeituraDia")); // MEXI AQUI
            String novaHora = request.getParameter("novaLeituraHora"); // MEXI AQUI
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // MEXI AQUI
            LocalTime tempoNovaLeitura = LocalTime.parse(novaHora, formatter); // MEXI AQUI
            LocalDate ld = LocalDate.parse(request.getParameter("dataPrimeiraLeitura")); // MEXI AQUI
            LocalTime lt = LocalTime.parse(request.getParameter("horaPrimeiraLeitura")); // MEXI AQUI
			LocalDateTime l = LocalDateTime.of(ld, lt);
			String mensagem = "";
			
			GregorianCalendar c = new GregorianCalendar();
            c.set(Calendar.DAY_OF_YEAR, novaDias); // MEXI AQUI
            c.set(Calendar.HOUR_OF_DAY, tempoNovaLeitura.getHour()); // MEXI AQUI
            c.set(Calendar.MINUTE, tempoNovaLeitura.getMinute()); // MEXI AQUI
           
            String nova = c.get(Calendar.DAY_OF_YEAR)+" "+ c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE); // MEXI AQUI
			
		try{	
			s.setNome(nome);
			s.setServidor(ServidorServices.searchServidor(serv).get(0));
			s.setPastaLogAcesso(ptLogs);
			s.setPrefixoLogAcesso(pxLogs);
			s.setPrefixoLogErro(pxLogs2);
			s.setPastaLogErro(ptLogs2);
			s.setPrimeiraLeitura(l);
			s.setPeriodicidadeLeitura(new SimpleDateFormat("DD HH:mm").parse(nova).getTime()); // MEXI AQUI
			//s.setPeriodicidadeLeitura(new SimpleDateFormat("hh:mm").parse(nova).getTime());
			SistemaServices.insertSistema(s);
			mensagem = "Sistema cadastrado com sucesso!";
		} catch (Exception e) {
			mensagem = "Erro no cadastro: " + e.getMessage();
			//javax.persistence.EntityExistsException j� existe
			e.printStackTrace();
		} finally {
			String json = "{\"mensagem\": \"" + mensagem + "\"}";
			pw.write(json);
		}
	}
}