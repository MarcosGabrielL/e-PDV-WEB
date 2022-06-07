package com.softsaj.egdvweb.security.PasswordReset;



import com.softsaj.egdvweb.security.EmailVerification.EmailSender;
import com.softsaj.egdvweb.exception.NotFoundException;
import com.softsaj.egdvweb.security.JwtUtil;
import com.softsaj.egdvweb.security.User;
import com.softsaj.egdvweb.security.UserRepository;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import net.bytebuddy.utility.RandomString;
import org.aspectj.apache.bcel.classfile.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Marcos
 */

import org.springframework.mail.javamail.JavaMailSender;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PasswordResetController {
    
    @Autowired
    private EmailSender emailSender;
     
    @Autowired
    private PasswordResetServices customerService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserRepository userRepo;
     
   
 
    @PostMapping("/forgot_password")
public String processForgotPassword( @RequestParam("email") String email) {
    String message = "We have sent a reset password link to your email. Please check.";
    
     System.out.println("IIIIIIIII"+email);
     User user = userRepo.findByEmail(email);
     
      if (user == null) {
            throw new IllegalStateException("email not registred");
        }
     
    try {
        String token = jwtUtil.generateToken(email);
        
        customerService.updateResetPasswordToken(token, email);
        String resetPasswordLink = "https://emiele.herokuapp.com/reset_password?token=" + token + "&email="+email;
        
        emailSender.send(
                email,
                buildEmailReset(resetPasswordLink));
        
         
    } catch (Exception ex) {
       message ="error:" + ex.getMessage();
    } 
         
    return message;
}
     
     
     
 
     
    @PostMapping("/reset_password")
public String processResetPassword(@RequestParam("token") String token,
            @RequestParam("email") String email, @RequestParam("password") String password) {
    
    String message = "You have successfully changed your password.";
    
    //Verifica se token existe
    PasswordResetToken customer = customerService.getByResetPasswordToken(token);
    
     if (customer == null) {
        return "Invalid Token";
     } else {   
         
        //Verifica se token expirou
        if (jwtUtil.isTokenExpired(token)) {
            throw new IllegalStateException("token expired");
        }
        
        //Verifica se token pertence
        if(!jwtUtil.validateToken(token, email)){
            throw new IllegalStateException("token is not yours");
        }
        
        //Pega usuario pelo email e ayualiza password
        User user = userRepo.findByEmail(email);
        user.setPassword(password);
        User newUser = userRepo.save(user);
        
        //Apaga token
        customerService.deleteResetPasswordToken(token, email);
         
     }
     
    return message;
}
    
    public String buildEmailReset(String link)  {
    
     
    return "Here's the link to reset your password"
            + "<p>Hello,</p>"
            + "<p>You have requested to reset your password.</p>"
            + "<p>Click the link below to change your password:</p>"
            + "<p><a href=\"" + link + "\">Change my password</a></p>"
            + "<br>"
            + "<p>Ignore this email if you do remember your password, "
            + "or you have not made the request.</p>";
     
    
}
    
}
