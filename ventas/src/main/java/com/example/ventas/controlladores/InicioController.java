package com.example.ventas.controlladores;

import com.example.ventas.modelos.Pedido;
import com.example.ventas.modelos.PedidoProduct;
import com.example.ventas.repositorys.clientRepository;
import com.example.ventas.services.*;
import com.example.ventas.modelos.*;
import com.example.ventas.utils.mails;
import com.example.ventas.utils.infoMails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.json.JSONObject;

import javax.crypto.spec.PSource;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Controller
@RequestMapping(value = "/ini")
public class InicioController {
    @Autowired
    private clientRepository cliente;
    @Autowired
    private clientService clientS;
    @Autowired
    private productService product;
    private mails sendMail;
    private infoMails infoMail;
    public InicioController(){
        try{
            FileInputStream serviceAccount =
                    new FileInputStream("path/to/serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @GetMapping(value = "/")
    public String bienvenido(HttpServletRequest req, HttpServletResponse res, Model model){
        model.addAttribute("testBody","Ropa exclusiva Mor!!!");
        return "views/index";
    }
    @GetMapping(value = "/file")
    public String file(HttpServletRequest req, HttpServletResponse res){
        System.out.print("Url: ");
        System.out.println(req.getRequestURL());
            if (req.getCookies() != null){
            System.out.print("Cookies: ");
            System.out.println(req.getCookies());
        }
        return "views/upfile";
    }

    @PostMapping(value = "/allProducts")
    public ResponseEntity allProducts(){
        List<String> listBody= new ArrayList<>();
        List<Product> listProduct= this.product.getAllProducts();
        Path path= Paths.get("archivos");
        //Path pathArchivo= Paths.get(path.toAbsolutePath().toString().concat("/").concat("usingthymeleaf.pdf"));
        Path pathArchivo= Paths.get(path.toAbsolutePath().toString().concat("/").concat("Social Business Model Canvas - Template.jpg"));
        File file= new File(pathArchivo.toAbsolutePath().toString());

        try{
            System.out.println("Tamano file: ");
            int tamano= (int)file.length();
            byte[] byteImg= new byte[tamano];
            System.out.println(tamano);

            byte[] b= Files.readAllBytes(pathArchivo);

            Product p= new Product();
            p.setId(1l);
            p.setName("Manzana verde");
            p.setCantidad(1);
            p.setPrecio(200.0);
            p.setPhoto(b);
            JSONObject obj= new JSONObject(p);
            listBody.add(obj.toString());

            p= new Product();
            p.setId(2l);
            p.setName("Manzana roja");
            p.setPhoto(b);
            p.setCantidad(1);
            p.setPrecio(250.0);
            obj= new JSONObject(p);
            listBody.add(obj.toString());

            System.out.println("*************");
            System.out.println(b.toString());
            System.out.println("*************");
            System.out.println(p.getPhoto().toString());
            System.out.println("*************");
            System.out.println("*************");

            List<String> newBodys= new ArrayList<>();

            for (Product p2: listProduct){
                System.out.println(p2.getName());
                System.out.println(p2.getPhoto().toString());
                JSONObject json= new JSONObject(p2);
                System.out.println(b);
                System.out.println("*************");
                newBodys.add(json.toString());
            }

            return ResponseEntity.ok().body(newBodys);

        }catch(Exception e){
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(value = "/upfile")
    public ResponseEntity subirPhoto(@RequestParam(value = "file") MultipartFile file, @RequestParam(value= "data") String upP){
    //public ResponseEntity subirPhoto(@RequestParam(value = "file") MultipartFile file){
    //public ResponseEntity subirPhoto(@RequestBody upProduct product){
        System.out.println("inicio solicitud....");
        try{
            List<String> lProducts= new ArrayList<>();

            System.out.println(file.getSize());
            ObjectMapper obj= new ObjectMapper();
            upProduct p= obj.readValue(upP,upProduct.class);
            JSONObject o= new JSONObject(upP);
            System.out.println(p.getName());
            System.out.println(o.getString("description"));
            System.out.println("ÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇÇ");

            Gson g= new Gson();
            upProduct product1= g.fromJson(upP, upProduct.class);
            System.out.println(product1.getName());

            Product p1= new Product();
            //p1.setPhoto(file.getBytes());
            Path path= Paths.get("archivos");
            Path file1= Paths.get(path.toAbsolutePath().toString().concat("/").concat("Social Business Model Canvas - Template.jpg"));
            p1.setPhoto(file.getBytes());
            p1.setName(o.getString("name"));
            p1.setPrecio(product1.getValor());
            p1.setCantidad(1);
            product.saveProduct(p1);

            //System.out.println(o.toString());

            return ResponseEntity.ok().body(lProducts);
            //return new ResponseEntity<>(fileMultipartFile,h ,HttpStatus.OK);

        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        try{
            String afterToken= request.getHeader("Authorization");
            response.setHeader("Authorization", "null");
            response.getWriter().flush();
        }catch(Exception e){
            System.out.println("Error logout");
            e.fillInStackTrace();
        }
        return "views/index";
    }
    @GetMapping(value = "/info")
    public ModelAndView info(){
        ModelAndView m= new ModelAndView("views/info");
        m.addObject("info","Celular: 3107578322");
        return m;
    }

    @PostMapping(value = "/tstRecibir")
    public String recibir(@RequestParam(name = "id", defaultValue = "1") int id, @RequestParam(name = "name", defaultValue = "fff") String name){
    //public String recibir(@Param(value = "id") String id, @Param(value = "name") String name){
        System.out.println("************");
        System.out.println("body received : ");
        System.out.println(id);
        System.out.println(name);
        System.out.println("************");
        return "views/index";
    }

    @PostMapping(value = "/testR")
    //public String recibe(@RequestParam(value = "model") Product p){
    public String recibe(@RequestBody Product p){
        System.out.println("***********");
        System.out.println("Body: ");
        System.out.println(p.getId());
        System.out.println(p.getName());
        System.out.println("***********");
        return "views/index";
    }

    @Transactional
    @PostMapping(value = "/receiveProducts")
    public ResponseEntity makePayment(@RequestBody Client client){
        System.out.println("name cliente: ");
        System.out.println(client.getName());
        System.out.println("mail cliente: ");
        System.out.println(client.getCorreo());
        System.out.println("name product: ");
        /*
        System.out.println(client.getListPedidos().get(0).getlProduct().get(0).getProduct().getName());
        System.out.println("????????");
        System.out.println(client.getListPedidos().get(0).getlProduct().get(0).getProduct().getPhoto());*/
        System.out.println("????????");
        //System.out.println(client.getListPedidos().get(0).getListProduct().get(0).getName());
        /*System.out.println(client.getListPedidos().get(0).getListProduct().get(0).getPhoto().length);
        */

        //Descomentar *******************************************
        //Product p= client.getListPedidos().get(0).getlProduct().get(0).getProduct();
        //Product prod= product.saveProduct(p);
        //System.out.println("New product:");
        //System.out.println(prod.getName());
        /////****************************************************

        System.out.println("????????");
        System.out.println("count pedido: ");
        System.out.println(client.getListPedidos().get(0).getCountProducts());
        sendMail= new mails();
        infoMail= new infoMails();
        infoMail.setName(client.getName());
        infoMail.setCorreo(client.getCorreo());
        infoMail.setSubject(client.getSubject());
        infoMail.setBody(client.getBody());


        System.out.print("Send Mail ");
        System.out.println(sendMail.sendMailPedido(infoMail));

        //clientS.testSave(client);
        Client cli= new Client();
        cli.setBody(client.getBody());
        cli.setSubject(client.getSubject());
        cli.setName(client.getName());
        cli.setCorreo(client.getCorreo());
        //clientS.saveCli(cli);

        clientS.testSave(client);

        clientS.testService();
        System.out.println("Busco ____________________ data all Client ");
        //clientService clientS= new clientService();
        //clientS.testService();
        return ResponseEntity.ok().body("successfully");
    }
    @GetMapping("/saveClient")
    public ResponseEntity saveClient(){
        return ResponseEntity.ok().body("Save successful");
    }
}

