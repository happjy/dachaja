package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.events.MouseEvent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Controller {
    @FXML
    TextField txt_Search;
    @FXML
    Button btn_Search;
    @FXML
    ListView<String> list_Name;
    @FXML
    ListView<String> list_Location;
    @FXML
    ListView<String> list_PhoneNum;
    @FXML
    CheckBox chb1;
    @FXML
    CheckBox chb2;
    @FXML
    CheckBox chb3;
    @FXML
    CheckBox chb4;
    @FXML
    CheckBox chb5;
    @FXML
    CheckBox chb6;
    @FXML
    CheckBox chb7;
    @FXML
    CheckBox chb8;
    @FXML
    CheckBox chb9;
    @FXML
    CheckBox chb10;
    @FXML
    CheckBox chb11;
    @FXML
    CheckBox chb12;
    @FXML
    CheckBox chb13;
    @FXML
    CheckBox chb14;
    @FXML
    Button RandomSelection;
    @FXML
    WebView wv_Search;
    @FXML
    Label randomResult;

    @FXML
    public void handleSearchAction(ActionEvent event) throws Exception {
        if (txt_Search.getText().equals("")) {
            list_Name.getItems().clear();
            list_Location.getItems().clear();
            list_PhoneNum.getItems().clear();
        } else {
            list_Name.getItems().clear();
            list_Location.getItems().clear();
            list_PhoneNum.getItems().clear();
            String searchKeyword = txt_Search.getText();
            String app_key = "702de7379cdf4dc990403334e572f2b5";
            try {
                // 키워드로 주소 검색
                String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                con.setRequestProperty("Authorization", "KakaoAK " + app_key);
                String query = URLEncoder.encode(searchKeyword, "UTF-8");

                String postParams = "query=" + query;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
                parseResult(response.toString());
                Map map = new ObjectMapper().readValue(response.toString(), Map.class);
                List<Map<String,String>> list = (List) map.get("documents");
                int i=0;
                for (Map<String, String> cell : list) {
                    i++;
                    String name = cell.get("place_name");
                    list_Name.getItems().add(name);
                    String location = cell.get("address_name");
                    list_Location.getItems().add(location);
                    String phone = cell.get("phone");
                    if(phone.equals("")){
                        list_PhoneNum.getItems().add("   ");
                    }else {
                        list_PhoneNum.getItems().add(phone);
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    @FXML
    public void handleNameClick(){
        try {
            wv_Search.getEngine().load("https://m.search.naver.com/search.naver?sm=mtp_hty.top&where=m&query=" + list_Name.getSelectionModel().getSelectedItem());
            System.out.println("clicked on " + list_Name.getSelectionModel().getSelectedItem());
        }catch(Exception e){

        }
    }

    private static void parseResult(String json) throws Exception {
        Map map = new ObjectMapper().readValue(json, Map.class);
        List<Map<String, String>> list = (List) map.get("documents");

        int i = 0;
        for (Map<String, String> cell : list) {
            String name = cell.get("place_name");
            String address = cell.get("address_name");
            String phNum = cell.get("phone");
            System.out.printf("%d. %s %s %s\n", ++i, name, address, phNum);
        }
    }


    @FXML
    private void handleSelectAction(ActionEvent event)throws Exception{
        int i=0;
        List<String> foodArray = new ArrayList<>();
        if(chb1.isSelected()){
            i++;
            foodArray.add(chb1.getText());
        }
        if(chb2.isSelected()){
            i++;
            foodArray.add(chb2.getText());
        }
        if(chb3.isSelected()){
            i++;
            foodArray.add(chb3.getText());
        }
        if(chb4.isSelected()){
            i++;
            foodArray.add(chb4.getText());
        }
        if(chb5.isSelected()){
            i++;
            foodArray.add(chb5.getText());
        }
        if(chb6.isSelected()){
            i++;
            foodArray.add(chb6.getText());
        }
        if(chb7.isSelected()){
            i++;
            foodArray.add(chb7.getText());
        }
        if(chb8.isSelected()){
            i++;
            foodArray.add(chb8.getText());
        }
        if(chb9.isSelected()){
            i++;
            foodArray.add(chb9.getText());
        }
        if(chb10.isSelected()){
            i++;
            foodArray.add(chb10.getText());
        }
        if(chb11.isSelected()){
            i++;
            foodArray.add(chb11.getText());
        }
        if(chb12.isSelected()){
            i++;
            foodArray.add(chb12.getText());
        }
        if(chb13.isSelected()){
            i++;
            foodArray.add(chb13.getText());
        }
        if(chb14.isSelected()){
            i++;
            foodArray.add(chb14.getText());
        }
        int foodNum = new Random().nextInt(i);
        randomResult.setText(foodArray.get(foodNum));
    }
}


