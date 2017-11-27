package com.example.android.restaurantdiary;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentOptions;

/**
 * Created by Jake on 11/26/2017.
 */

public class AiStuff {

    private static final String USERNAME = "9aff14e2-5530-40e8-98f9-fdb8a25e0e7c";
    private static final String PASSWORD = "AZeTU8O1UOSt";

    public static String AiSentiment(String textsToAnalyse){
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                USERNAME,
                PASSWORD
        );

        String text = textsToAnalyse;

        SentimentOptions sentiment = new SentimentOptions.Builder().build();

        Features features = new Features.Builder().sentiment(sentiment).build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder().text(text).features(features).build();

        AnalysisResults response = service.analyze(parameters).execute();

        return response.toString();
    }
}
