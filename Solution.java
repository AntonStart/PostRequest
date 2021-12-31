package com.javarush.task.task40.task4002;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/* 
Опять POST, а не GET
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        Solution solution = new Solution();
        solution.sendPost("http://httpbin.org/post", "name=zapp&mood=good&locale=&id=777");
    }
    //метод отправляет POST-запрос с переданными параметрами
    public void sendPost(String url, String urlParameters) throws Exception {
        //базовый интерфейс для осуществления http-запроса
        HttpClient client = getHttpClient();
        //создаём POST запрос
        HttpPost httpPost = new HttpPost(url);
        //Добавляет заголовок к этому сообщению. Заголовок будет добавлен в конец списка.
        httpPost.addHeader("User-Agent", "Mozilla/5.0");
        //URLEncodedUtils.parse() -Возвращает список параметров запроса URI {@link NameValuePair}.
        List<NameValuePair> params = URLEncodedUtils.parse(urlParameters,Charset.forName("UTF-8"));
        // записываем параметры в наш пост запрос,
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        // экземляр HTTP-ответа
        HttpResponse response = client.execute(httpPost);
        // вывод статуса ответа (200/404 и прочие)
        System.out.println("Response Code: " + response.getStatusLine().getStatusCode());
        // буфер чтения инфы из HTTP ответа
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        //запись в stringbuilder инфы из HTTP ответа
        StringBuffer result = new StringBuffer();
        String responseLine;
        while ((responseLine = bufferedReader.readLine()) != null) {
            result.append(responseLine);
        }
        //вывод инфы из HTTP ответа
        System.out.println("Response: " + result.toString());
    }


    protected HttpClient getHttpClient() {
        /*
        Конструктор для экземпляров {@link CloseableHttpClient}.
        Если конкретный компонент явно не задан, этот класс будет
        использовать его реализацию по умолчанию.
        Свойства системы будут приниматься во внимание при настройке реализаций по умолчанию,
        когда метод {@link #useSystemProperties()} вызывается перед вызовом {@link #build()}.
         */
        return HttpClientBuilder.create().build();
    }
}
