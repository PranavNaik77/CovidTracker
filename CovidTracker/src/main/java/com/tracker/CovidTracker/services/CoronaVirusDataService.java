package com.tracker.CovidTracker.services;

import com.tracker.CovidTracker.models.locationstat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class CoronaVirusDataService {

    Date today = new Date();
    Calendar cal = Calendar.getInstance();
    private static String URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/02-16-2022.csv";
    private List<locationstat> allstats= new ArrayList<>();

    public List<locationstat> getAllstats() {
        return allstats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")

    public void fetch_virus_data() throws IOException,InterruptedException{
        //System.out.println(dtf.format(now));
        cal.setTime(today);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH) - 2;
        int yesterday = dayOfMonth - 3;
        int month = cal.get(Calendar.MONTH) + 1; // 0 being January
        int year = cal.get(Calendar.YEAR);
        String dd = String.format("%02d", dayOfMonth);
        String ydd = String.format("%02d", yesterday);
        String mm = String.format("%02d", month);
        String yy = String.format("%02d", year);
        String todaysdate = mm + "-" + dd + "-" + yy;
        String yesterdaysdate = mm + "-" + ydd + "-" + yy;
        String todaysURL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/"+todaysdate+".csv";
        String yesterdaysURL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/"+yesterdaysdate+".csv";
        List<locationstat> newstats= new ArrayList<>();
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest todays_request =HttpRequest.newBuilder()
            .uri(URI.create(todaysURL))
            .build();
        HttpRequest yesterdays_request =HttpRequest.newBuilder()
                .uri(URI.create(yesterdaysURL))
                .build();
        HttpResponse<String> todayshttpResponse =client.send(todays_request,HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> yesterdayshttpResponse =client.send(yesterdays_request,HttpResponse.BodyHandlers.ofString());
        StringReader todayscsvBodyReader =new StringReader(todayshttpResponse.body());
        StringReader yesterdayscsvBodyReader =new StringReader(yesterdayshttpResponse.body());
        Iterable<CSVRecord> todaysrecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(todayscsvBodyReader);
        Iterable<CSVRecord> yesterdaysrecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(yesterdayscsvBodyReader);
        for(CSVRecord yesterdaysrecord : yesterdaysrecords) {
            for (CSVRecord todaysrecord : todaysrecords) {
                locationstat locationStat = new locationstat();
                locationStat.setState(todaysrecord.get("Province_State"));
                locationStat.setCountry(todaysrecord.get("Country_Region"));
                long todayscases=Long.parseLong(todaysrecord.get("Confirmed"));
                locationStat.setConfirmedCases(todayscases);
                long yesterdayscases=Long.parseLong(yesterdaysrecord.get("Confirmed"));
                long diff=todayscases-yesterdayscases;
                locationStat.setTodayscases(diff);
                locationStat.setCity(todaysrecord.get("Combined_Key"));
                locationStat.setDeaths(Long.parseLong(todaysrecord.get("Deaths")));
                locationStat.setLast_Update(todaysrecord.get("Last_Update"));
                System.out.println(locationStat);
                newstats.add(locationStat);
            }
        }
        this.allstats=newstats;
    }
}
