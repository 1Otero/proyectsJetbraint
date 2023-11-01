package com.example.ventas.utils;

import com.example.ventas.modelos.Pedido;
import com.sun.tools.javac.Main;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class mails {

    public Boolean sendMail(infoMails info){
        try{
            System.out.println(info.getName());
            System.out.println(info.getCorreo());
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public Boolean sendMails(infoMails info){
        try{
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public Boolean sendMailPedido(infoMails info){
        try{
            Properties p= new Properties();
            //FileInputStream i= new FileInputStream("application.properties");
            InputStream i= Main.class.getClassLoader().getResourceAsStream("application.properties");
            p.load(i);

            System.out.println(p.getProperty("datatest").toString());
            System.out.println(p.getProperty("spring.datasource.password"));
            System.out.println(p.getProperty("mail.smtp.host"));
            System.out.println(info.getHost());

            i.close();


            Properties properties= System.getProperties();
            properties.setProperty("mail.smtp.host", info.getHost());
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.port", info.getPort());
            //properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

            System.out.println("********************* mail ******************");
            System.out.print(info.getFrom());
            System.out.print(" ");
            System.out.println(info.getPass());

            Session session= Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(info.getFrom(), info.getPass());
                }
            });

            System.out.println("···························");

            MimeMessage mimeMessage= new MimeMessage(session);
            mimeMessage.setFrom(info.getFrom());
            mimeMessage.addRecipients(Message.RecipientType.TO, new InternetAddress[]{
               new InternetAddress(info.getCorreo()),
               new InternetAddress("trujilloivanzzx@gmail.com")
            });
            mimeMessage.setSubject(info.getSubject());
            //mimeMessage.setText(info.getBody());
            String htmlBody= String.format("<html><body><h1>%s</h1>\n<p>%s</p>\n<h2>%s</h2>\n", "Manzana verde","12 Unidades", info.getBody());
            htmlBody += String.format("<h2>%s</h2>\n", "Fresa");
            htmlBody += String.format("</body></html>");
            mimeMessage.setContent(htmlBody, "text/html");


            /*Transport transport= session.getTransport("smtp");
            transport.connect(info.getCorreo(), info.getPass());
            transport.connect("smtp.gmail.com", info.getCorreo(), info.getPass());
            System.out.println("Boolean: ");
            System.out.println(transport.isConnected());
            System.out.println("Session 1 **** ");
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());*/
            //transport.send
            Transport.send(mimeMessage);
            System.out.println("Session 2 **** ");
            //transport.close();


            System.out.println(info.getName());
            System.out.println(info.getCorreo());
            System.out.println("Send Mail....");
            return true;
        }catch(MessagingException | IOException e){
            System.out.println("Failed send mail");
            System.out.println(e);
            return false;
        }
    }
    public Boolean sendMailsPedido(infoMails info){
        try{
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
