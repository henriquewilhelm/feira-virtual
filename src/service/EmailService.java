package service;

import models.Mail;
import models.Pedido;
import models.Usuario;


public class EmailService  implements Runnable {
		
		private Mail mail;
		private Usuario usuario;
		private Pedido pedido;
		private String nome;
		
	   public EmailService(Usuario usuario, String nome) {
		   this.usuario	= usuario;
		   this.nome = nome;
		   
	   }
	   
	   public EmailService(Pedido pedido, String nome) {
		   this.pedido= pedido;
		   this.nome = nome;
		   
	   }
	   
	   public void run() {
		   		// recebe msgs do servidor e imprime na tela
		   		try{
		   			mail = new Mail();
		   			if (nome.equals("Novo Cadastro")){
		   				mail.setAssunto("Cadastro realizado");
		   				mail.setDestino(usuario.getEmail());
		   				mail.setMsg("Seu cadastro foi realizado com sucesso! Seu login de acesso é "+usuario.getEmail()+" e sua senha é "+usuario.getPassword()+".");
		   				mail.setNomeDestino(usuario.getNome());
		   				mail.sendMail();
		   				
		   			}
		   			else if (nome.equals("Update Usuario")){ 
		   				
		   				mail.setAssunto("Alteração no cadastro realizada");
		   				mail.setDestino(usuario.getEmail());
		   				mail.setMsg("Seu cadastro foi alterado com sucesso! Seu login de acesso é "+usuario.getEmail()+" e sua senha é "+usuario.getPassword()+"");
		   				mail.setNomeDestino(usuario.getNome());
		   				mail.sendMail();
		   				
		   			}
		   			else if (nome.equals("Esqueci Senha")){
		   				mail.setAssunto("Lembrete de Senha");
		   				mail.setDestino(usuario.getEmail());
		   				mail.setMsg("Lembrete de Login e Senha, seu login de acesso é "+usuario.getEmail()+" e sua senha é "+usuario.getPassword()+".");
		   				mail.setNomeDestino(usuario.getNome());
		   				mail.sendMail();
		   				
		   			}
		   			else if (nome.equals("Novo Pedido")){
		   		        mail.setAssunto("LNB - Pedido realizado");
		   				mail.setDestino(pedido.getUsuario().getEmail());
		   				
		   				String msg = "Olá "+pedido.getNome()+", seu pedido foi realizado com sucesso!\n\nQuantidade de Produtos: "+pedido.getListItens().size() +" \n";
		   				for (int i = 0; i< pedido.getListItens().size(); i++){
		   					msg = msg + " " + pedido.getListItens().get(i).getProduto().getNome() + " - " + pedido.getListItens().get(i).getQuantidade() + "x " + pedido.getListItens().get(i).getProduto().getTipo().getNome() + " = " + pedido.getListItens().get(i).getTotal()+ "\n";
		   				}
		   				msg = msg + "Até a entrega, facilite o troco! Qualquer dúvida entraremos em contato!";
		   				mail.setMsg(msg);
		   				mail.setNomeDestino(pedido.getNome());
		   				mail.sendMail();
		   			}
		   			
		   		}catch (Exception e) {
					// TODO: handle exception
		   			e.printStackTrace();
				}
		  }
}

