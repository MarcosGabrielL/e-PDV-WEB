
package com.softsaj.egdvweb.Vendas.controllers;



/**
 *
 * @author Marcos
 */

import com.softsaj.egdvweb.Vendas.Relatorios.Evento;
import com.softsaj.egdvweb.Vendas.models.Notification;
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.RequestWrapper;
import com.softsaj.egdvweb.Vendas.models.ResponseVendas;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import com.softsaj.egdvweb.Vendas.repositories.VendasRepository;
import com.softsaj.egdvweb.Vendas.services.VendasService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.softsaj.egdvweb.Vendas.models.Vendas;
import com.softsaj.egdvweb.Vendas.models.Vendidos;
import com.softsaj.egdvweb.Vendas.services.EventoService;
import com.softsaj.egdvweb.Vendas.services.NotificationService;
import com.softsaj.egdvweb.Vendas.services.ProdutoService;
import com.softsaj.egdvweb.Vendas.services.VendasService;
import com.softsaj.egdvweb.Vendas.services.VendidosService;
import com.softsaj.egdvweb.Vendas.util.validateToken;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vendas")
public class VendasController {
    
    
     @Autowired
    private VendasService vs;
   
     @Autowired
     private validateToken validatetoken;
     
     @Autowired
    private VendidosService vds;
     
      @Autowired
    private EventoService es;
      
       @Autowired
    private NotificationService ns;
      
     
    @GetMapping
    public ResponseEntity<List<Vendas>> getAll() {
        List<Vendas> vendas =  vs.findAll();
        return new ResponseEntity<>(vendas, HttpStatus.OK);
    }
    
    //GEt Vendas
     @GetMapping("/venda/{id}")
    public ResponseEntity<Vendas> getCienfiloById (@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
      //  if(!validatetoken.isLogged(token)){
        //     throw new IllegalStateException("token not valid");
        //}
        
        Vendas venda = vs.findVendasById(id);
        return new ResponseEntity<>(venda, HttpStatus.OK);
    }
    
    @GetMapping("/venda/comprador")
     public ResponseEntity<List<Vendas>> getCienfiloById (@RequestParam("id") String id
            ,@RequestParam("token") String token) {
        
     
        
        List<Vendas> venda = vs.findVendasByComprador(id);
        return new ResponseEntity<>(venda, HttpStatus.OK);
    }
    
    @PostMapping("/venda")
    public ResponseEntity<Vendas> addVendas(
            @RequestBody RequestWrapper requestWrapper) {
        
      
        
       Vendas venda =  requestWrapper.getVendas();
       List<Produto> produtos = requestWrapper.getProdutos();
       
        
        Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date d = new Date();
                String data = formatador.format(d.getTime());
                System.out.println("DATA: "+data);
                //Salva venda e pega id venda
                
        venda.setDiavenda(formatador1.format(d.getTime()));
        venda.setStatus("0");
        venda.setDatavenda(data);
        Vendas newVendas = vs.addVendas(venda);
                
        
        //Salva produtos vendidos
        for(Produto p : produtos){
            Vendidos v = new Vendidos();
            v.setCodigo(p.getCodigo());
            v.setDataSaida(data);
            v.setIdVenda(newVendas.getId().intValue());
            v.setQuantidade(p.getQuantidade());
            v.setTipo(p.getTipo());
            v.setVendedor_ID(newVendas.getVendedor_id());
	    v.setDescrição(p.getDescricao());
            vds.addVendidos(v);
        }
        
        salvaEvento("1",data,"Vendas","1","Venda: "+venda.getValor(),venda.getVendedor_id());
        
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/venda/{id}").buildAndExpand(venda.getId()).toUri();
        
        return new ResponseEntity<>(newVendas, HttpStatus.CREATED);
    }
    
    
     @PostMapping("/venda/att")
    public ResponseEntity<Vendas> attVendas(
            @RequestBody Vendas newvenda,
            @RequestParam("token") String token) {
        
      
        Vendas newVendas = vs.addVendas(newvenda);
                
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/venda/{id}").buildAndExpand(newvenda.getId()).toUri();
        
        return new ResponseEntity<>(newVendas, HttpStatus.CREATED);
    }
    
    //Update nome,telefone,idade,foto;
    @PutMapping("/venda/update/{id}")
    public ResponseEntity<Vendas> updateVendas(@PathVariable("id") Long id, @RequestBody Vendas newvenda
     ,@RequestParam("token") String token) {
        
        if(!validatetoken.isLogged(token)){
             throw new IllegalStateException("token not valid");
        }
        Vendas venda = vs.findVendasById(id);
        //venda.setNome(newvenda.getNome());
        //c//inefilo.setTelefone(newvenda.getTelefone());
        //venda.setIdade(newvenda.getIdade());
        //venda.setFoto(newvenda.getFoto());
        Vendas updateVendas = vs.updateVendas(venda);//s
        return new ResponseEntity<>(updateVendas, HttpStatus.OK);
    }
    
     @PostMapping("/venda/cancela")
    public ResponseEntity<Vendas> addVendascancel(
            @RequestParam("id") Long id
             ,@RequestParam("token") String token) {
        
       // if(!validatetoken.isLogged(token)){
         //    throw new IllegalStateException("token not valid");
        //}
        
        Vendas venda = vs.findVendasById(id);
        
        Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
                Date d = new Date();
                String data = formatador.format(d.getTime());
                
                //Salva venda e pega id venda
        venda.setStatus("7");
        venda.setDatacancelamento(data);
        Vendas newVendas = vs.addVendas(venda);
        
        
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/venda/{id}").buildAndExpand(venda.getId()).toUri();
        
        return new ResponseEntity<>(newVendas, HttpStatus.CREATED);
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVendas(@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
       // if(!validatetoken.isLogged(token)){
         //    throw new IllegalStateException("token not valid");
        //}
        vs.deleteVendas(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //Pega vendas do dia
     @GetMapping("/venda/hoje")
     public ResponseEntity<List<Vendas>> findAllByDataSaida(
             @RequestParam("token") String token,
              @RequestParam("idvendedor") String idvendedor) {
         
         Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date d = new Date(); 
                String data = formatador1.format(d.getTime());
           
         
          List<Vendas> vendas =  vs.findAllByData(data,idvendedor);
        return new ResponseEntity<>(vendas, HttpStatus.OK);
    }
     
      @GetMapping("/venda/dia")
     public ResponseEntity<List<Vendas>> findAllByDia(
             @RequestParam("token") String token,
              @RequestParam("dia") String data,
              @RequestParam("idvendedor") String idvendedor) {
         
         Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date d = new Date(); 
                //String data = formatador1.format(d.getTime());
          
         
          List<Vendas> vendas =  vs.findAllByData(data,idvendedor);
        return new ResponseEntity<>(vendas, HttpStatus.OK);
    }
     
    //Pega Total de vendidod dia
      @GetMapping("/hoje")
     public ResponseEntity<ResponseVendas> findTotalToday(
             @RequestParam("token") String token,
              @RequestParam("idvendedor") String idvendedor){
         
         float totalhoje = 0;
         float totalontem = 0;
         float percentual = 0;
         ResponseVendas response = new ResponseVendas();
         
                Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd");
                Date date = new Date();
                String data = formatador1.format(date.getTime());
                
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, -1);
                date = c.getTime();
                
                String ontem = formatador1.format(date.getTime());
                System.out.println("Hoje: "+data+" Ontem: "+ontem );
                for(Vendas a : vs.findAllByData(data, idvendedor)){
                     try {
                        totalhoje = totalhoje + Float.parseFloat(a.getValor());
                    } catch (NumberFormatException ex) {
                      
                    }
                }
                for(Vendas a : vs.findAllByData(ontem, idvendedor)){
                     try {
                        totalontem = totalontem + Float.parseFloat(a.getValor());
                    } catch (NumberFormatException ex) {
                      
                    }
                }
                    System.out.println("Total Hoje: " + totalhoje + "Total Ontem: " + totalontem);
                percentual = (((totalhoje/totalontem)*100)-100);
                
                response.setPercentual(String.valueOf(percentual)+" %");
                response.setTotal(String.valueOf(totalhoje));
         
         return new ResponseEntity<>(response, HttpStatus.OK);
     
     }
     
        @GetMapping("/dia")
     public ResponseEntity<ResponseVendas> findTotalDay(
             @RequestParam("token") String token,
             @RequestParam("dia") String data,
              @RequestParam("idvendedor") String idvendedor){
         
         float totalhoje = 0;
         float totalontem = 0;
         float percentual = 0;
         ResponseVendas response = new ResponseVendas();
         
                Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd");
                Date date = new Date();
               // String data = formatador1.format(date.getTime());
                
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, -1);
                date = c.getTime();
                
                String ontem = formatador1.format(date.getTime());
                System.out.println("Hoje: "+data+" Ontem: "+ontem );
                for(Vendas a : vs.findAllByData(data,idvendedor )){
                     try {
                        totalhoje = totalhoje + Float.parseFloat(a.getValor());
                    } catch (NumberFormatException ex) {
                      
                    }
                }
                for(Vendas a : vs.findAllByData(ontem, idvendedor)){
                     try {
                        totalontem = totalontem + Float.parseFloat(a.getValor());
                    } catch (NumberFormatException ex) {
                      
                    }
                }
                    System.out.println("Total Hoje: " + totalhoje + "Total Ontem: " + totalontem);
                percentual = (((totalhoje/totalontem)*100)-100);
                
                response.setPercentual(String.valueOf(percentual)+" %");
                response.setTotal(String.valueOf(totalhoje));
         
         return new ResponseEntity<>(response, HttpStatus.OK);
     
     }
     
    //Pega Total Mês atual
     @GetMapping("/mes")
     public ResponseEntity<ResponseVendas> findTotalMes(
             @RequestParam("token") String token,
              @RequestParam("idvendedor") String idvendedor){
         
         float totalmesatual = 0;
         float totalmesanterior = 0;
         float percentual = 0;
         ResponseVendas response = new ResponseVendas();
         
                Locale locale = new Locale("pt","BR");
                GregorianCalendar calendar = new GregorianCalendar();
                SimpleDateFormat formatador1 = new SimpleDateFormat("YYYY-MM-dd",locale);
                Date datehoje = new Date();
                String hoje = formatador1.format(datehoje.getTime());
                datehoje.setMonth((datehoje.getMonth() - 1) );
                String menos30dias = formatador1.format(datehoje.getTime());
                datehoje.setMonth((datehoje.getMonth() - 1) );
                String menos60dias = formatador1.format(datehoje.getTime()); 
                System.out.println("Hoje: "+hoje+" inicio primeiro mes: "+menos30dias );
                System.out.println("final segundo mes: "+menos30dias+" inicio segundo mes: "+menos60dias );
                
                
                for(Vendas a : vs.findAllByMes(menos30dias,hoje, idvendedor)){
                     try {
                        totalmesatual = totalmesatual + Float.parseFloat(a.getValor());
                    } catch (Exception ex) {
                      
                    }
                }
                for(Vendas a : vs.findAllByMes(menos60dias,menos30dias, idvendedor)){
                     try {
                        totalmesanterior = totalmesanterior + Float.parseFloat(a.getValor());
                    } catch (Exception ex) {
                      
                    }
                }
          
                percentual = ((totalmesatual/totalmesanterior)*100)-100;
                
                response.setPercentual(String.valueOf(percentual)+" %");
                response.setTotal(String.valueOf(totalmesatual));
         
         return new ResponseEntity<>(response, HttpStatus.OK);
     
     }
     
     
     public void salvaEvento(String cod,String date, String info, String level, String message,String usuario){
         
         Evento evento = new Evento();
         evento.setCod("2");
         evento.setDate(date);
         evento.setInfo(info);
         evento.setLevel(level);
         evento.setMessage(message);
         evento.setUsuario(usuario);
         
          es.addEvento(evento);
          
          Notification notification = new Notification();
          notification.setCod("1");
         notification.setDate(date);
         notification.setInfo(info);
         notification.setLevel(level);
         notification.setMessage("Novo Pedido: "+message);
         notification.setUsuario(usuario);
         notification.setIsRead(false);
          
          ns.addNotification(notification);
     }
}


