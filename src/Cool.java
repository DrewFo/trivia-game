import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;

import org.json.*;

import java.util.Collections;
import java.util.Scanner;

public class Cool {

    public static void main(String[] args) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create("https://opentdb.com/api.php?amount=1"))
                .build();
        var response = client.send(request,BodyHandlers.ofString());
        //System.out.println(response.body());

        JSONObject jsonBody = new JSONObject(response.body());
        JSONArray results = jsonBody.getJSONArray("results");
        JSONObject questionDetails = results.getJSONObject(0);

        String coolQuestion = questionDetails.getString("question");
        String coolCorrectAnswer = questionDetails.getString("correct_answer");
        JSONArray coolFalseAnswers = questionDetails.getJSONArray("incorrect_answers");

        ArrayList<String> coolChoices = new ArrayList<>();
        for(int i = 0; i < coolFalseAnswers.length(); i++){
            coolChoices.add(coolFalseAnswers.getString(i));
        }

        int position = (int) (Math.random() * (coolFalseAnswers.length() + 1));

        coolChoices.add(position, coolCorrectAnswer);

        Collections.shuffle(coolChoices);

        System.out.println(coolQuestion);
        int count = 0;
        for (String choice : coolChoices) {
            System.out.println(count + ": " + choice);
            count++;
        }

        System.out.println("Type the number corresponding to the correct answer:");

        Scanner input = new Scanner(System.in);

        int playerChoice = input.nextInt();

        if(playerChoice != position){
            System.out.println("Wrong answer!");
        }else{
            System.out.println("Good choice!");
        }

    }
}
