import org.json.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Task {
    public static void main(String[] args) throws IOException, JSONException {
        HashMap<String, Integer> educations = new HashMap<>();
        String urlApi = String.format("%s%s%s",
                "https://api.vk.com/method/groups.getMembers",
                "?group_id=iritrtf_urfu&fields=city,sex,education&access_token=",
                "3b8c43d93b8c43d93b8c43d9f33bf8c0a133b8c3b8c43d96433c38b4ed97e268535f27e&v=5.126");
        URL url = new URL(urlApi);
        URLConnection urlConnection = url.openConnection();
        BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String result = buffer.readLine();
        buffer.close();
        JSONObject peopleJSON = new JSONObject(result);
        JSONArray arrayPeople = peopleJSON.getJSONObject("response").getJSONArray("items");
        ArrayList<Human> people = new ArrayList();

        for (int i = 0; i < arrayPeople.length(); i++) {
            JSONObject info = arrayPeople.getJSONObject(i);
            String city;
            String fName = info.get("first_name").toString();
            String lName = info.get("last_name").toString();
            String sex = info.get("sex").equals(1) ? "Women" : "Man";
            String education;

            try {
                JSONObject cityJson = info.getJSONObject("city");
                city = cityJson.get("title").toString();
            } catch (Exception e) {
                city = "empty";
            }

            try {
                education = info.get("education_status").toString();
                educations.put(education, educations.containsKey(education) ? educations.get(education) + 1 : 1);
            } catch (Exception e) {
                education = "empty";
            }
            people.add(new Human(fName, lName, sex, city, education));
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("result.txt"))) {
            for (Human e : people) {
                outputStream.writeObject(e);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        for (Map.Entry<String, Integer> item : educations.entrySet()) {
            System.out.println(item.getKey() + ": " + item.getValue());
        }
    }
}
