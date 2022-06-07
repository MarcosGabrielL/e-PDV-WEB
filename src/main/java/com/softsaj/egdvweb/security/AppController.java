/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.security;

/**
 *
 * @author Marcos
 */
import com.softsaj.egdvweb.security.EmailVerification.EmailSender;
import com.softsaj.egdvweb.security.EmailVerification.EmailService;
import com.softsaj.egdvweb.security.PasswordReset.GenericResponse;
import com.softsaj.egdvweb.security.PasswordReset.PasswordResetServices;
import com.softsaj.egdvweb.security.PasswordReset.PasswordResetToken;
import com.softsaj.egdvweb.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.Relatorios.Evento;
import com.softsaj.egdvweb.Vendas.models.Frete;
import com.softsaj.egdvweb.Vendas.services.EventoService;
import com.softsaj.egdvweb.Vendas.services.NotificationService;
import com.softsaj.egdvweb.models.Person;
import com.softsaj.egdvweb.models.Revendedor;
import com.softsaj.egdvweb.models.Vendedor;
import com.softsaj.egdvweb.security.UserRepository;
import com.softsaj.egdvweb.security.AuthRequest;
import com.softsaj.egdvweb.security.User;
import com.softsaj.egdvweb.security.JwtUtil;
import com.softsaj.egdvweb.services.PersonService;
import com.softsaj.egdvweb.repositories.PersonRepository;
import com.softsaj.egdvweb.services.VendedorService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import com.softsaj.egdvweb.Vendas.services.EventoService;
import com.softsaj.egdvweb.Vendas.services.NotificationService;
import com.softsaj.egdvweb.Vendas.models.Notification;
import com.softsaj.egdvweb.Vendas.services.FreteService;


@RestController
public class AppController {
    
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private PersonService vs;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailSender emailSender;
     @Autowired
    private MessageSource messages;
     @Autowired
    private PasswordResetServices customerService;
     @Autowired
    private VendedorService VendedorService;
     
       @Autowired
    private EventoService es;
      
       @Autowired
    private NotificationService ns;
       
       @Autowired
    private FreteService fs;

	
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

	
     @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
              
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //String encodedPassword = passwordEncoder.encode(authRequest.getPassword());
    //authRequest.setPassword(encodedPassword);
      //  System.out.println("Senha: "+authRequest.getPassword());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getEmail());
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signupform";
    }

@PostMapping("/process_register")
public ResponseEntity<User> processRegister(@RequestBody User user) {
   // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //String encodedPassword = passwordEncoder.encode(user.getPassword());
    //user.setPassword(encodedPassword);
    
    //SALVA USUARIO
     user.setVerify(false);
    User newUser = userRepo.save(user);
    
     Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("YYYY-MM-dd hh:mm:ssXXX",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date d = new Date();
                String data = formatador.format(d.getTime());
    
    //SALVA MODEL
    if(user.getTipo().equals("1")){ //VENDEDOR
        Vendedor vendedor = new Vendedor();
        vendedor.setId(newUser.getId());
        vendedor.setNomefantasia(user.getFirstName() + " " + user.getLastName());
        vendedor.setRua("Rua dos Bobos");
        vendedor.setEmail(newUser.getEmail());
        vendedor.setTelefone("+55 (75) 9 88525220");
        vendedor.setDatainicio(data);
        vendedor.setDatafim(data);
        vendedor.setAmbiente(0);
        vendedor.setSerie(0);
        vendedor.setDescricao("Olá, sou "+vendedor.getNomefantasia()+" : Decisões: Se você não consegue decidir, a resposta é não. Se dois caminhos igualmente difíceis, escolha o mais doloroso a curto prazo (evitar a dor é criar uma ilusão de igualdade).");
        VendedorService.addVendedor(vendedor);

	//Salva informações Frete
        Frete frete = new Frete();
        frete.setCobrafrete(true);
        frete.setFrete10k("");
        frete.setFretefixo("5");
        frete.setVendedorid(newUser.getId().toString());
        fs.addFrete(frete);
        
        
        
    }
    if(user.getTipo().equals("2")){ //Revendedor
        Revendedor r = new Revendedor();
        r.setCodigo(user.getFirstName() + user.getLastName()+newUser.getId());
        r.setEmail(newUser.getEmail());
        r.setFirstName(user.getFirstName());
        r.setId(newUser.getId());
        r.setLastName(user.getLastName());
        
        //revendedorservice.add(r);
    }
    if(user.getTipo().equals("3")){ //CLIENTE
         Person person = new Person();
        person.setId(newUser.getId());
        person.setEmail(user.getEmail());
        person.setPassword(user.getPassword());
        person.setFirstName(user.getFirstName());
        person.setLastName(user.getLastName());
        personRepo.save(person);
    }
   
    
    
    
                
     //Registro no sistema
     Evento evento = new Evento();
         evento.setCod("2"); //Não Mostra
         evento.setDate(data);
         evento.setInfo("");
         evento.setLevel("2"); //Não apaga ao ver
         evento.setMessage("Registro no Sistema");
         evento.setUsuario(user.getId().toString());
         
         es.addEvento(evento);
         
         
          salvaNotify("1",data,"Confirmação","2","Comfirme seu E-mail",user.getId().toString());
          salvaNotify("1",data,"Cardapio","2","Cadastre seu primeiro Produto",user.getId().toString());
          salvaNotify("1",data,"Loja","2","Edite as informações da Loja",user.getId().toString());
         
       /* try {
            EventoService.SaveEvento(evento,"7");
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
}

    @GetMapping("/users")
    public String listUsers(Model model) {
    List<User> listUsers = userRepo.findAll();
    model.addAttribute("listUsers", listUsers);
     
    
    return "users";
}

 @GetMapping("/user/{email}")
    public ResponseEntity<User> getUserByEmail (@PathVariable("email") String email) {
        User user = userRepo.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

@GetMapping("/user/id/{email}")
    public ResponseEntity<User> getUserById (@PathVariable("email") Long email) {
        User user = userRepo.findByid(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    
     @PostMapping("/sendingemail")
    public String generateEmail(@RequestBody AuthRequest authRequest) throws Exception {
              
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //String encodedPassword = passwordEncoder.encode(authRequest.getPassword());
    //authRequest.setPassword(encodedPassword);
      //  System.out.println("Senha: "+authRequest.getPassword());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            
        } catch (Exception ex) {
            throw new Exception("invalid request to email", ex.getCause());
        }
        
         String link = "https://emiele.herokuapp.com/confirm?token=" + jwtUtil.generateToken(authRequest.getEmail()) +"&email="+authRequest.getEmail();
        emailSender.send(
                authRequest.getEmail(),
                buildEmail(userRepo.findByEmail(authRequest.getEmail()).getFirstName(), link));

        return link;
        
    }
    
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
    
    
    @GetMapping(path = "confirm")
    public Boolean confirm(@RequestParam("token") String token,
            @RequestParam("email") String email) {
        
        User user = userRepo.findByEmail(email);
        
        //Verifica se email já foi verificado
        if (user.isVerify()) {
            throw new IllegalStateException("email already confirmed");
        }

        
        //Verifica se token expirou
        if (jwtUtil.isTokenExpired(token)) {
            throw new IllegalStateException("token expired");
        }

        //ConfirmaEmail
        user.setVerify(true);
        User newUser = userRepo.save(user);
        
        Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("YYYY-MM-dd hh:mm:ssXXX",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date d = new Date();
                String data = formatador.format(d.getTime());
         Evento evento = new Evento();
         evento.setCod("Reg1");
         evento.setDate(data);
         evento.setInfo("");
         evento.setLevel("2");
         evento.setMessage("Confirmou Email");
         evento.setUsuario(user.getId().toString());

	es.addEvento(evento);
         
         
       /* try {
            EventoService.SaveEvento(evento,"7");
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        return true;
    }
    
    
  @GetMapping(path = "islogged")
    public Boolean confirm(@RequestParam("token") String token){
        if (jwtUtil.isTokenExpired(token)) {
            throw new IllegalStateException("token expired");
        }
        
        return true;
    }
    
    public void salvaNotify(String cod,String date, String info, String level, String message,String usuario){
         
        
          
          Notification notification = new Notification();
          notification.setCod("VC1");
         notification.setDate(date);
         notification.setInfo(info);
         notification.setLevel(level);
         notification.setMessage(message);
         notification.setUsuario(usuario);
         notification.setIsRead(false);
         
           ns.addNotification(notification);
          
       
          
     }
   
    
}