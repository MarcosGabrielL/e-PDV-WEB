/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.util;



import org.joda.time.DateTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.joda.time.*;
import org.joda.time.Seconds;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.Months;
import org.joda.time.Hours;
import org.joda.time.DateTime;
import org.joda.time.Days;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
/**
 *
 * @author Marcos
 */
public class TempoDecorrido {
    
    public static String TempoDecorrido(String horacomentad){
        Locale locale = new Locale("pt","BR");
        SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        DateTime date = null;
        String tempo = new String();
        Period period = new Period();
        PeriodFormatter formatter = new PeriodFormatterBuilder().toFormatter();
            try{
                date = new DateTime(formattime.parse(horacomentad));
            }catch(Exception e){
                
            }finally{
                DateTime agora = new DateTime(new Date());

        
        
        period = new Period(date, agora);
        formatter = new PeriodFormatterBuilder().toFormatter();
        if(period.getYears()>0){
            if(period.getYears()==1){
                formatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix(" Ano atrás").toFormatter();
            }else{
                formatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix(" Anos atrás").toFormatter();
            }
        }else{
            if(period.getMonths()>0){
                if(period.getMonths()==1){
                formatter = new PeriodFormatterBuilder()
                    .appendMonths().appendSuffix(" Mês atrás").toFormatter();
            }else{
                formatter = new PeriodFormatterBuilder()
                    .appendMonths().appendSuffix(" Meses atrás").toFormatter();
            }
            }else{
                if(period.getDays()>0){
                    if(period.getDays()==1){
                formatter = new PeriodFormatterBuilder()
                    .appendDays().appendSuffix(" Dia atrás").toFormatter();
            }else{
                formatter = new PeriodFormatterBuilder()
                    .appendDays().appendSuffix(" Dias atrás").toFormatter();
            }
                }else{
                    if(period.getHours()>0){
                        if(period.getHours()==1){
                            formatter = new PeriodFormatterBuilder()
                                .appendHours().appendSuffix(" Hora atrás").toFormatter();
                        }else{
                            formatter = new PeriodFormatterBuilder()
                                .appendHours().appendSuffix(" Horas atrás").toFormatter();
                        }
                    }else{
                        if(period.getMinutes()>0){
                            if(period.getMinutes()==1){
                            formatter = new PeriodFormatterBuilder()
                                .appendMinutes().appendSuffix(" Minuto atrás").toFormatter();
                        }else{
                            formatter = new PeriodFormatterBuilder()
                                .appendMinutes().appendSuffix(" Minutos atrás").toFormatter();
                        }
                        }else{
                            if(period.getMillis()>0){
                                formatter = new PeriodFormatterBuilder()
                                .appendLiteral(" Agora Mesmo").toFormatter();
                            }else{
                                tempo = "?";
                            }
                        }
                    }
                }
            }
        }
 }  
    tempo = formatter.print(period);
         
    return tempo;
    }
}
